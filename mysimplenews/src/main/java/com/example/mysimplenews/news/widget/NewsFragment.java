package com.example.mysimplenews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mysimplenews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/28.
 */

public class NewsFragment extends Fragment {
    
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
//        mViewpager.setOffscreenPageLimit(1);
        //向适配器中添加Fragment，同时给mViewPage设置适配器
        setupViewPager(mViewpager);
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.top));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.nba));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.cars));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.jokes));
        
        //将Tablayout和ViewPager关联，关联后就不需要addTab了
        mTabLayout.setupWithViewPager(mViewpager);
        
        return view;
    }
    
    
    /**
     * 设置ViewPage的适配器
     *
     * @param mViewPager
     */
    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP), getString(R.string.top));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA), getString(R.string.nba));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS), getString(R.string.cars));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES), getString(R.string.jokes));
        
//        adapter.addFragment(new TopFragment(), getString(R.string.top));
//        adapter.addFragment(new NBAFragment(), getString(R.string.nba));
//        adapter.addFragment(new CarsFragment(), getString(R.string.cars));
//        adapter.addFragment(new JokesFragment(), getString(R.string.jokes));
        
        mViewPager.setAdapter(adapter);
    }
    
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        //向适配器中添加Fragment
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
        
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        
        @Override
        public int getCount() {
            return mFragments.size();
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
    
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}//End
