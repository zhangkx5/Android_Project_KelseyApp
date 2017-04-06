package com.example.kaixin.kelseyapp.fragment;

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

import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.view.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixin on 2017/3/19.
 */

public class NewsFragment extends Fragment {
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_AMUSE = 1;
    public static final int NEWS_TYPE_MILITARY = 2;
    public static final int NEWS_TYPE_TECHNOLOGY = 3;
    public static final int NEWS_TYPE_ECONOMICS = 4;

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private String[] TITLES = {"头条","娱乐", "军事","科技","财经"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(5);
        setupViewPager(mViewPager);
        for (int i = 0; i < 5; ++i) {
            mTablayout.addTab(mTablayout.newTab().setText(TITLES[i]));
        }
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }
    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsSubFragment.newInstance(NEWS_TYPE_TOP), "头条");
        adapter.addFragment(NewsSubFragment.newInstance(NEWS_TYPE_AMUSE), "娱乐");
        adapter.addFragment(NewsSubFragment.newInstance(NEWS_TYPE_MILITARY), "军事");
        adapter.addFragment(NewsSubFragment.newInstance(NEWS_TYPE_TECHNOLOGY), "科技");
        adapter.addFragment(NewsSubFragment.newInstance(NEWS_TYPE_ECONOMICS), "财经");
        /*for (int i = 0; i < 5; ++i) {

            //adapter.addFragment(new NewsSubFragment(), TITLES[i]);
        }*/
        mViewPager.setAdapter(adapter);
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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
}
