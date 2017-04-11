package com.example.mysimplenews.news.model;

import com.example.mysimplenews.beans.NewsDetailBean;
import com.example.mysimplenews.configs.Urls;
import com.example.mysimplenews.utitls.NewsDetailJsonUtils;
import com.example.mysimplenews.utitls.OkHttpUtils;

/**
 * Created by lenovo on 2017/3/24.
 */

public class NewsDetailModelImpl implements NewsDetailModel {
    
    
    
    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsDetailJsonUtils.readJsonNewsDetailBeans(response, docid);
                listener.onSuccess(newsDetailBean);
            }
        
            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }//loadDetailUrl
    
    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }
}//End
