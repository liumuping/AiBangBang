package com.example.administrator.controller.adapter.gerenadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.controller.activity.geren.waitHelp.WaitHelpActivity;
import com.example.administrator.controller.fragment.geren.finish.FinishMyacceptfragment;
import com.example.administrator.controller.fragment.geren.finish.FinishMyapplyfragment;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FinishPageAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private FinishMyapplyfragment myapplyfragment = null;
    private FinishMyacceptfragment myacceptfragment = null;




    public FinishPageAdapter(FragmentManager fm) {
        super(fm);
        myapplyfragment = new FinishMyapplyfragment();
        myacceptfragment = new FinishMyacceptfragment();

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
