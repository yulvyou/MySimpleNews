package com.example.mysimplenews.images.model;

import com.example.mysimplenews.beans.ImageBean;
import com.example.mysimplenews.configs.Urls;
import com.example.mysimplenews.utitls.ImageJsonUtils;
import com.example.mysimplenews.utitls.OkHttpUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/2/28.
 */

public class ImageModelImpl implements ImageModel {

    /**
     * 获取图片列表
     */
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = Urls.IMAGES_URL;
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                List<ImageBean> iamgeBeanList = ImageJsonUtils.readJsonImageBeans(response);
                //交给Presenter处理
                listener.onSuccess(iamgeBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }


    //Model只负责加载数据,和处理数据
    //Model加载完数据后要将这些数据交给Presenter，那么就通过一个接口将数据交给Presenter
    //这个接口由Presenter实现
//    public interface OnLoadImageListListener {
//        void onSuccess(List<ImageBean> list);
//
//        void onFailure(String msg, Exception e);
//    }

}//End
