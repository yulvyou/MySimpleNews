package com.example.mysimplenews.news.model;


import com.example.mysimplenews.beans.NewsDetailBean;

/**
 * Description : 新闻详情加载回调
 */
public interface OnLoadNewsDetailListener {

    void onSuccess(NewsDetailBean newsDetailBean);

    void onFailure(String msg, Exception e);
}
