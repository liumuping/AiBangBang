package com.example.administrator.controller.adapter.gerenadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.controller.activity.geren.waitHelp.WaitHelpActivity;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyacceptfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyapplyfragment;

/**
 * Created by Administrator on 2018/4/10.
 */

public class WaitHelpPageAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private WaitMyapplyfragment myapplyfragment = null;
    private WaitMyacceptfragment myacceptfragment = null;




    public WaitHelpPageAdapter(FragmentManager fm) {
        super(fm);
        myapplyfragment = new WaitMyapplyfragment();
        myacceptfragment = new WaitMyacceptfragment();

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
                fragment = myapplyfragment;
                break;
            case WaitHelpActivity.PAGE_TWO:
                fragment = myacceptfragment;
                break;
        }
        return fragment;
    }

}
