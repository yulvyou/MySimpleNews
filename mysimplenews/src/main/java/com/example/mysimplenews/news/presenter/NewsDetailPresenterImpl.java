package com.example.mysimplenews.news.presenter;

import com.example.mysimplenews.beans.NewsDetailBean;
import com.example.mysimplenews.news.model.NewsDetailModel;
import com.example.mysimplenews.news.model.NewsDetailModelImpl;
import com.example.mysimplenews.news.model.OnLoadNewsDetailListener;
import com.example.mysimplenews.news.view.NewsDetailView;

/**
 * Created by lenovo on 2017/3/24.
 */

public class NewsDetailPresenterImpl implements NewsDetailPresenter,OnLoadNewsDetailListener {
    private NewsDetailView mNewsDetailView;
    private NewsDetailModel mNewsDetailModel;
    
    public NewsDetailPresenterImpl(NewsDetailView mNewsDetailView){
        this.mNewsDetailView = mNewsDetailView;
        mNewsDetailModel = new NewsDetailModelImpl();
    }
    
    
    
    @Override
    public void onLoadNewsDetail(String docId) {
        mNewsDetailView.showProgress();
        mNewsDetailModel.loadNewsDetail(docId, this);
    }
    
    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mNewsDetailView.showNewsDetialContent(newsDetailBean.getBody());
        }
        mNewsDetailView.hideProgress();
    }
    
    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();
    }
}//End
