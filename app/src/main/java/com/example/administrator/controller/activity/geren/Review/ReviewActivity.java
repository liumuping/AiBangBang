package com.example.administrator.controller.activity.geren.Review;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.FinishAdapter;
import com.example.administrator.controller.adapter.gerenadapter.ReviewAdapter;
import com.example.administrator.controller.adapter.gerenadapter.ReviewPageAdapter;
import com.example.administrator.controller.adapter.gerenadapter.WaitHelpPageAdapter;
import com.example.administrator.controller.fragment.geren.review.RVhavefragment;
import com.example.administrator.controller.fragment.geren.review.RVwaitfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyacceptfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyapplyfragment;
import com.example.administrator.model.bean.Finish;
import com.example.administrator.model.bean.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener{
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    private RadioGroup rv_rd_group;
    private RadioButton rv_rb_have;
    private RadioButton rv_rb_wait;
    private int position;
    private ViewPager viewpager;
    private List<BaseFragment> mBaseFragment;
    private Fragment mContent;
    private ImageView review_im_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initview();
        initFragment();
        initData();
        initListen();


    }

    private void initFragment() {
        mBaseFragment = new ArrayList();
        mBaseFragment.add(new RVhavefragment());
        mBaseFragment.add(new RVwaitfragment());
    }

    private void initview() {
        review_im_back=(ImageView) findViewById(R.id.review_im_back);
        viewpager = findViewById(R.id.reviewviewpager);
        rv_rd_group = (RadioGroup) findViewById(R.id.rv_rd_group);
        rv_rb_have = (RadioButton) findViewById(R.id.rv_rb_have);
        rv_rb_wait = (RadioButton) findViewById(R.id.rv_rb_wait);

    }
    private void initListen() {
        review_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initData() {

        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        rv_rd_group.check(R.id.rv_rb_have);
        viewpager.setAdapter(new ReviewPageAdapter(getSupportFragmentManager()));
        rv_rd_group.setOnCheckedChangeListener(this);


    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == 2) {
            switch (viewpager.getCurrentItem()) {
                case PAGE_ONE:
                    rv_rb_have.setChecked(true);
                    break;
                case PAGE_TWO:
                    rv_rb_wait.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.rv_rb_have:
                viewpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rv_rb_wait:
                viewpager.setCurrentItem(PAGE_TWO);
                break;
        }
    }
}

