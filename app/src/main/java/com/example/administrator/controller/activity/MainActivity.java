package com.example.administrator.controller.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;

import android.widget.RadioGroup;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.fragment.chatfragment;
import com.example.administrator.controller.fragment.gerenfragment;
import com.example.administrator.controller.fragment.qitafragment;
import com.example.administrator.controller.fragment.zhuyefragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity  {
    private RadioGroup radioGroup;
    private List<BaseFragment> mBaseFragment;
    private int position;
    private Fragment mContent;  //上次的fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initFragment();
        setListener();

    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new MyCheckedChangeListener());
        radioGroup.check(R.id.zhuye);
    }
 class    MyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

     @Override
     public void onCheckedChanged(RadioGroup group, int checkedId) {
         switch (checkedId){
             case R.id.zhuye:
                 position=0;
                 break;
             case R.id.chat:
                 position=1;
                 break;
             case R.id.qita :
                 position=2;
                 break;
             case R.id.geren:
                 position=3;
                 break;
                 default:
                     position=0;
                     break;
         }
         BaseFragment to=getFragment();
         switchFragment (mContent,to);
     }
 }
//from 被隐藏的fragment to 即将显示的fragment
    private void switchFragment( Fragment from,Fragment to) {
        if (from!=to){
           mContent=to;
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();

        if (!to.isAdded()){
         if (from!=null){
             ft.hide(from);
         }
         if (to!=null){
             ft.add(R.id.fram,to).commit();
         }
        }
        else {
            if (from!=null){
                ft.hide(from);
            }
            if (to!=null){
                ft.show(to).commit();
            }
        }

    }}

    /*  private void switchFragment(BaseFragment fragment) {
          FragmentManager fm=getSupportFragmentManager();
          FragmentTransaction transaction=fm.beginTransaction();
          transaction.replace(R.id.fram,fragment);
          transaction.commit();
      }
  */
    private BaseFragment getFragment() {
        BaseFragment fragment=mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
     mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new zhuyefragment());
        mBaseFragment.add(new chatfragment());
        mBaseFragment.add(new qitafragment());
        mBaseFragment.add(new gerenfragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        radioGroup=(RadioGroup)findViewById(R.id.rd_group);

    }
}
