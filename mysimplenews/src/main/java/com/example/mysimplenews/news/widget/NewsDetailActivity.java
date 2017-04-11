package com.example.mysimplenews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mysimplenews.R;
import com.example.mysimplenews.beans.NewsBean;
import com.example.mysimplenews.news.presenter.NewsDetailPresenter;
import com.example.mysimplenews.news.presenter.NewsDetailPresenterImpl;
import com.example.mysimplenews.news.view.NewsDetailView;
import com.example.mysimplenews.utitls.ImageLoaderUtils;
import com.example.mysimplenews.utitls.ToolsUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by lenovo on 2017/3/24.
 */

public class NewsDetailActivity extends SwipeBackActivity implements NewsDetailView {
    
    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.htNewsContent)
    HtmlTextView mHtNewsContent;
    @Bind(R.id.ivImage)
    ImageView mIvImage;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    
    private SwipeBackLayout mSwipeBackLayout;
    
    private NewsBean mNews;
    private NewsDetailPresenter mNewsDetailPresenter;
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
    
        //ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        
        mNews = (NewsBean) getIntent().getSerializableExtra("news");
    
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getTitle());
    
        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mNews.getImgsrc());
    
        mNewsDetailPresenter = new NewsDetailPresenterImpl(this);
        mNewsDetailPresenter.onLoadNewsDetail(mNews.getDocid());
    }//onCreate
    
    @Override
    public void showNewsDetialContent(String newsDetailContent) {
        mHtNewsContent.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());
    }
    
    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
    
}//End
