package com.example.mysimplenews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mysimplenews.R;
import com.example.mysimplenews.adapter.NewsAdapter;
import com.example.mysimplenews.beans.NewsBean;
import com.example.mysimplenews.configs.Urls;
import com.example.mysimplenews.news.presenter.NewsPresenter;
import com.example.mysimplenews.news.presenter.NewsPresenterImpl;
import com.example.mysimplenews.news.view.NewsView;
import com.example.mysimplenews.utitls.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/28.
 */

public class NewsListFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener {
    
    @Bind(R.id.recycle_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    
    private static final String TAG = "NewsListFragment";
    private NewsAdapter mAdapter;
    private List<NewsBean> mData;
    private LinearLayoutManager mLayoutManager;
    private NewsPresenter mNewsPresenter;
    
    //新闻的类型，默认是“头条”，数据数据的类型，onRefresh方法中有用到
    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;
    
    public static NewsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter = new NewsPresenterImpl(this);
        //获取传过来的参数
        mType = getArguments().getInt("type");//这里的type参数由NewsListFragment的构造函数确定
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        ButterKnife.bind(this, view);
        
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);//设置SwipeRefreshWidget下拉事件监听
    
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    
        //设置适配器
        mAdapter = new NewsAdapter(getActivity().getApplicationContext());
        //设置Item的事件监听器（）
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    
        onRefresh();
                
        return view;
    }
    
    
    @Override
    public void addNews(List<NewsBean> list) {
//        Log.i("test", "返回给NewsListFragment的结果是："+list.toString());
        mAdapter.setShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<NewsBean>();
        }
        
//        mData.addAll(list);
//        if(pageIndex == 0) {//如果这里是pageIndex就则不需要notifyDataSetChanged()；
//            mAdapter.setmDate(mData);
//        } else {
//            //如果没有更多数据了,则隐藏footer布局
//            if(list == null || list.size() == 0) {
//                mAdapter.setShowFooter(false);
//            }
//            mAdapter.notifyDataSetChanged();
//        }
        
        
        if(list == null || list.size() == 0) {
            mAdapter.setShowFooter(false);
        }else{
            mData.addAll(list);
            mAdapter.setmDate(mData);
        }
        mAdapter.notifyDataSetChanged();
        pageIndex += Urls.PAZE_SIZE;
    }//addNews
    
    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }
    
    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }
    
    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.setShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.activity_main);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }
    
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    
    /**
     * 实现SwipeRefreshLayout.OnRefreshListener接口需要完成的方法
     * 下拉刷新数据
     */
    @Override
    public void onRefresh() {
        Log.i("TAG", "调用了onRefresh函数");
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mNewsPresenter.onLoadNews(mType, pageIndex);
    }
    
    /**
     * Item点击事件监听器
     */
    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            NewsBean news = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news",  news);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };
    
    /**
     * RecyclerView滑动监听器
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        
        private int lastVisibleItem;
        
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
        
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //如果是最后一个则网络加载数据
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.setShowFooter()) {
                //加载更多
                LogUtils.d(TAG, "loading more data");
                mNewsPresenter.onLoadNews(mType, pageIndex + Urls.PAZE_SIZE);//pageIndex在addNews方法中有修改
            }
        }
    };
    
    
}//End
