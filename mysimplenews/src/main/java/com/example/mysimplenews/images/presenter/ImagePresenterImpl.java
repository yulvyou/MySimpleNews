package com.example.mysimplenews.images.presenter;

import com.example.mysimplenews.beans.ImageBean;
import com.example.mysimplenews.images.model.ImageModel;
import com.example.mysimplenews.images.model.ImageModelImpl;
import com.example.mysimplenews.images.model.OnLoadImageListListener;
import com.example.mysimplenews.images.view.ImageView;

import java.util.List;

/**
 * Presenter 只是起到一个调度的作用，具体的实现由具体的View和model处理。
 * 显示相关的由View写方法处理，加载数据或者数据处理由Model处理
 */

public class ImagePresenterImpl implements ImagePresenter, OnLoadImageListListener {
    ImageView mImageView;
    ImageModel mImageModel;

    public ImagePresenterImpl(ImageView mImageView){
        this.mImageModel = new ImageModelImpl();
        this.mImageView = mImageView;
    }
    @Override
    public void onLoadImageList() {
        //显示进度条
        mImageView.showProgress();
        //加载数据
        mImageModel.loadImageList(this);

    }

    //onSuccess 和 onFailure 在Model中的loadImageList中调用
    //list参数是由Model加载完数据后传过来的数据
    @Override
    public void onSuccess(List<ImageBean> list) {
        mImageView.addImages(list);//这个方法里面有刷新操作
        mImageView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();
    }
}//End
