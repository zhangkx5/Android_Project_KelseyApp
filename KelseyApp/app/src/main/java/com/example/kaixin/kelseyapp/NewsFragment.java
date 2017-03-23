package com.example.kaixin.kelseyapp;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixin on 2017/3/19.
 */

public class NewsFragment extends Fragment {

    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(5);

        setupViewPager(mViewPager);

        mTablayout.addTab(mTablayout.newTab().setText("头条"));
        mTablayout.addTab(mTablayout.newTab().setText("娱乐"));
        mTablayout.addTab(mTablayout.newTab().setText("军事"));
        mTablayout.addTab(mTablayout.newTab().setText("笑话"));
        mTablayout.addTab(mTablayout.newTab().setText("科技"));
        mTablayout.addTab(mTablayout.newTab().setText("财经"));
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }
    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TopNewsFragment(), "头条");
        adapter.addFragment(new AmuseNewsFragment(), "娱乐");
        adapter.addFragment(new MilitaryNewsFragment(), "军事");
        adapter.addFragment(new STechNewsFragment(), "科技");
        adapter.addFragment(new FinanceNewsFragment(), "财经");
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
