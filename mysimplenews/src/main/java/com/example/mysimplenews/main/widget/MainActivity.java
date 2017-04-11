package com.example.mysimplenews.main.widget;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mysimplenews.R;
import com.example.mysimplenews.about.AboutFragment;
import com.example.mysimplenews.images.widget.ImagesFragment;
import com.example.mysimplenews.main.presenter.MainPresenter;
import com.example.mysimplenews.main.presenter.MainPresenterImpl;
import com.example.mysimplenews.main.view.MainView;
import com.example.mysimplenews.news.widget.NewsFragment;
import com.example.mysimplenews.weather.WeatherFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.frame_content)
    FrameLayout mFrameContent;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    private MainPresenter mMainPresenter;

    //左侧菜单开关
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        //默认是新闻界面
        switch2News();

    }//onCreate


    /**
     * 初始化View
     */
    private void initView() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);
    }//initView

    /**
     * 初始化数据
     */
    private void initData() {
        mMainPresenter = new MainPresenterImpl(this);
    }



    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mMainPresenter.switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsFragment()).commit();
        mToolbar.setTitle(R.string.news_title);
    }

    @Override
    public void switch2Images() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ImagesFragment()).commit();
        mToolbar.setTitle(R.string.image_title);
    }

    @Override
    public void switch2Weather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WeatherFragment()).commit();
        mToolbar.setTitle(R.string.weather_title);
    }

    @Override
    public void switch2About() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
        mToolbar.setTitle(R.string.about_title);
    }


}//End
