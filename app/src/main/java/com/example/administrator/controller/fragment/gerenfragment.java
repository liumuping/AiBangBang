package com.example.administrator.controller.fragment;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.geren.Finish.FinishHelpActivity;
import com.example.administrator.controller.activity.geren.Mymessage.MyMessageActivity;
import com.example.administrator.controller.activity.geren.Review.ReviewActivity;
import com.example.administrator.controller.activity.geren.Setting.SettingActivity;
import com.example.administrator.controller.activity.geren.WaitHelp.WaitHelpActivity;

/**
 * Created by Administrator on 2018/3/24.
 */

public class gerenfragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout ly_message;
    private LinearLayout ly_finishhelp;
    private LinearLayout ly_review;
    private LinearLayout ly_waithelp;
    private LinearLayout ly_setting;

    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.fragment_geren,null);
        ly_message=(LinearLayout)view.findViewById(R.id.ly_message);
        ly_finishhelp=(LinearLayout)view.findViewById(R.id.ly_finishhelp);
        ly_review=(LinearLayout)view.findViewById(R.id.ly_review);
        ly_waithelp=(LinearLayout)view.findViewById(R.id.ly_waithelp);
        ly_setting=(LinearLayout)view.findViewById(R.id.ly_setting);
       return view;
    }

    @Override
    protected void initData() {
        super.initData();
        ly_message.setOnClickListener(this);
        ly_finishhelp.setOnClickListener(this);
        ly_review.setOnClickListener(this);
        ly_waithelp.setOnClickListener(this);
        ly_setting.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_message:
                Intent mintent=new Intent(mcontext, MyMessageActivity.class);
                startActivity(mintent);
                break;
            case R.id.ly_finishhelp:
                Intent fintent=new Intent(mcontext, FinishHelpActivity.class);
                startActivity(fintent);
                break;
            case R.id.ly_review:
                Intent rintent=new Intent(mcontext, ReviewActivity.class);
                startActivity(rintent);
                break;
            case R.id.ly_waithelp:
                Intent wintent=new Intent(mcontext, WaitHelpActivity.class);
                startActivity(wintent);
                break;
            case R.id.ly_setting:
                Intent sintent=new Intent(mcontext, SettingActivity.class);
                startActivity(sintent);
                break;
        }
    }
}
