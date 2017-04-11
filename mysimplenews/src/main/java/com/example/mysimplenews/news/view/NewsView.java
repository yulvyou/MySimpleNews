package com.example.mysimplenews.news.view;

import com.example.mysimplenews.beans.NewsBean;

import java.util.List;

/**
 * Created by lenovo on 2017/3/13.
 */

public interface NewsView {
    //加载成功后，将加载得到的数据填充到RecyclerView展示给用户
    void addNews(List<NewsBean> list);
    //加载数据的过程中需要提示“正在加载”的反馈信息给用户
    void showProgress();
    //加载成功后，需要将“正在加载”反馈信息取消掉
    void hideProgress();
    //若加载数据失败，如无网络连接，则需要给用户提示信息
    void showLoadFailMsg();
}
