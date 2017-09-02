package com.example.abdilla.badrtest.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.abdilla.badrtest.model.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdilla on 31/08/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    List<Page> pages = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        //this.pages = pages;
    }

    public void addFragment(Page page) {
        pages.add(page);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position).fragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).title;
    }
}
