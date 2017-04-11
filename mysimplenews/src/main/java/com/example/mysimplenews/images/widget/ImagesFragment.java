package com.example.mysimplenews.images.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mysimplenews.R;
import com.example.mysimplenews.adapter.ImageAdapter;
import com.example.mysimplenews.beans.ImageBean;
import com.example.mysimplenews.images.presenter.ImagePresenter;
import com.example.mysimplenews.images.presenter.ImagePresenterImpl;
import com.example.mysimplenews.images.view.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/28.
 */

public class ImagesFragment extends Fragment implements ImageView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private ImagePresenter mImagePresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageAdapter mAdapter;
    private List<ImageBean> mData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePresenter = new ImagePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, view);

        //设置下拉进度条的颜色
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        //设置SwipeRefresh的下拉监听器,触发onRefresh函数
        mSwipeRefreshWidget.setOnRefreshListener(this);

        //设置固定大小
        mRecycleView.setHasFixedSize(true);
        //设置布局管理器
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置RecyclerView的动画
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        mAdapter = new ImageAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.addOnScrollListener(mOnScrollListener);
        //刷新数据
        onRefresh();

        return view;
    }


    @Override
    public void addImages(List<ImageBean> list) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.clear();
        mData.addAll(list);
        mAdapter.setmDate(mData);//有刷新UI操作
    }

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
        if (isAdded()) {
            View view = getActivity() == null ? mRecycleView.getRootView() : getActivity().findViewById(R.id.activity_main);
            Snackbar.make(view, R.string.load_fail, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * implements SwipeRefreshLayout.OnRefreshListener 的接口函数
     */
    @Override
    public void onRefresh() {
        //内部有刷新UI操作
        mImagePresenter.onLoadImageList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //RecyclerView 滑动监听器
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount() ) {
                Snackbar.make(getActivity().findViewById(R.id.activity_main), R.string.image_hit, Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    private ImageAdapter.OnItemClickListener mOnItemClickListener = new ImageAdapter.OnItemClickListener(){

        @Override
        public void onItemClick(View view, int position) {
            Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
        }
    };
}//End
