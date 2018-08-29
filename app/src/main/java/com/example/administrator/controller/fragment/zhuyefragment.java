package com.example.administrator.controller.fragment;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.zhuye.QuestionActivity;
import com.example.administrator.controller.activity.zhuye.SearchActivity;
import com.example.administrator.controller.adapter.ZhuyePagerAdapter;
import com.example.administrator.controller.fragment.zhuye.GuanZhuFragment;
import com.example.administrator.controller.fragment.zhuye.ReBangFragment;
import com.example.administrator.controller.fragment.zhuye.TuiJianFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/24.
 */

public class zhuyefragment extends BaseFragment implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    private ZhuyePagerAdapter zhuyePagerAdapter;
    private RadioGroup rd_group;
    private RadioButton rd_guanzhu;
    private RadioButton rd_rebang;
    private RadioButton rd_tuijian;
    private ViewPager viewpager;
    private List<BaseFragment> mBaseFragment;
    private LinearLayout layout;
    private TextView zhuye_question_text;
    @Override
    protected View initView() {
        initFragment();
        View view=View.inflate(mcontext, R.layout.fragment_zhuye,null);
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);
        rd_group=(RadioGroup) view.findViewById(R.id.rd_group);
        rd_guanzhu=(RadioButton)view.findViewById(R.id.rd_guanzh) ;
        rd_tuijian=(RadioButton)view.findViewById(R.id.rd_tuijian) ;
        rd_rebang=(RadioButton)view.findViewById(R.id.rd_rebang) ;
        viewpager.setAdapter(new ZhuyePagerAdapter(getChildFragmentManager()));
        rd_group.check(R.id.rd_tuijian);
        layout = view.findViewById(R.id.lt_zhuye_search);
        zhuye_question_text=(TextView)view.findViewById(R.id.zhuye_question_text);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        layout.setOnClickListener(this);
        zhuye_question_text.setOnClickListener(this);
        rd_group.setOnCheckedChangeListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(1);


    }



    //重写ViewPager页面切换的处理方法

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    public void onPageSelected(int position) {
    }


    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewpager.getCurrentItem()) {
                case PAGE_ONE:
                    rd_guanzhu.setChecked(true);
                    break;
                case PAGE_TWO:
                    rd_tuijian.setChecked(true);
                    break;
                case PAGE_THREE:
                    rd_rebang.setChecked(true);
                    break;


            }
        }
    }


    private void initFragment() {
        mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new GuanZhuFragment());
        mBaseFragment.add(new TuiJianFragment());
        mBaseFragment.add(new ReBangFragment());

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rd_guanzh:
                viewpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rd_tuijian:
                viewpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rd_rebang:
                viewpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lt_zhuye_search:
                Intent sintent = new Intent(mcontext, SearchActivity.class);
                startActivity(sintent);
                break;
            case R.id.zhuye_question_text:
                Intent qintent = new Intent(mcontext, QuestionActivity.class);
                startActivity(qintent);
                break;



        }
    }
}
