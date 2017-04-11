package com.example.mysimplenews.news.model;

/**
 * Created by lenovo on 2017/3/21.
 */

public interface NewsModel {
    /**
     *
     * @param url   “新闻”的URL
     * @param type  新闻的类型
     * @param listener  加载后的回调，主要是更新NewsListFragment界面
     */
    void loadNews(String url, int type, OnLoadNewsListListener listener);
    
}//End
