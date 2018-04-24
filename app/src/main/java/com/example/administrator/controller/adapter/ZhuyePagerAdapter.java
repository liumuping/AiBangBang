package com.example.administrator.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.controller.fragment.zhuye.GuanZhuFragment;
import com.example.administrator.controller.fragment.zhuye.ReBangFragment;
import com.example.administrator.controller.fragment.zhuye.TuiJianFragment;
import com.example.administrator.controller.fragment.zhuyefragment;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ZhuyePagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private GuanZhuFragment guanZhuFragment = null;
    private TuiJianFragment tuiJianFragment = null;
    private ReBangFragment reBangFragment= null;



    public ZhuyePagerAdapter(FragmentManager fm) {
        super(fm);
        guanZhuFragment = new GuanZhuFragment();
        tuiJianFragment = new TuiJianFragment();
        reBangFragment = new ReBangFragment();


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
            case zhuyefragment.PAGE_ONE:
                fragment = guanZhuFragment;
                break;
            case zhuyefragment.PAGE_TWO:
                fragment = tuiJianFragment;
                break;
            case zhuyefragment.PAGE_THREE:
                fragment = reBangFragment;
                break;

        }
        return fragment;
    }

}
