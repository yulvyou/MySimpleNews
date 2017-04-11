package com.example.mysimplenews.news.model;

import com.example.mysimplenews.beans.NewsBean;
import com.example.mysimplenews.configs.Urls;
import com.example.mysimplenews.news.widget.NewsFragment;
import com.example.mysimplenews.utitls.NewsJsonUtils;
import com.example.mysimplenews.utitls.OkHttpUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/3/21.
 */

public class NewsModelImpl implements NewsModel{
    
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(response, getID(type));
                listener.onSuccess(newsBeanList);
            }
    
            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }//loadNews
    
    /**
     * 获取ID,因为这里的
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }//getID
    
}//End
