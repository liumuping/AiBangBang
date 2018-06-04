package com.example.administrator.controller.adapter.gerenadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.controller.activity.geren.WaitHelp.WaitHelpActivity;
import com.example.administrator.controller.fragment.geren.review.RVhavefragment;
import com.example.administrator.controller.fragment.geren.review.RVwaitfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyacceptfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyapplyfragment;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ReviewPageAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private RVhavefragment vhavefragment = null;
    private RVwaitfragment vwaitfragment = null;




    public ReviewPageAdapter(FragmentManager fm) {
        super(fm);
        vhavefragment = new RVhavefragment();
        vwaitfragment = new RVwaitfragment();

    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case WaitHelpActivity.PAGE_ONE:
                fragment = vhavefragment;
                break;
            case WaitHelpActivity.PAGE_TWO:
                fragment = vwaitfragment;
                break;
        }
        return fragment;
    }

}
