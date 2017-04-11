package com.example.mysimplenews.main.presenter;

import com.example.mysimplenews.R;
import com.example.mysimplenews.main.model.MainModel;
import com.example.mysimplenews.main.view.MainView;

/**
 * Created by lenovo on 2017/2/27.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;
    private MainModel mMainModel;

    public MainPresenterImpl(MainView mainView){
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
            default:
                mMainView.switch2News();
                break;
        }
    }
}//End
