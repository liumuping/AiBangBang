package com.example.administrator.controller.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.controller.Base.BaseFragment;

/**
 * Created by Administrator on 2018/3/24.
 */

public class qitafragment extends BaseFragment {
    private TextView textView;
    @Override
    protected View initView() {
        textView=new TextView(mcontext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        textView.setText("我是其他");
    }
}
