package com.example.mysimplenews.images.model;

import com.example.mysimplenews.beans.ImageBean;

import java.util.List;

/**
 * Created by lenovo on 2017/3/21.
 */

public interface OnLoadImageListListener {
    void onSuccess(List<ImageBean> list);
    
    void onFailure(String msg, Exception e);
}
