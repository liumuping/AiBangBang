package com.example.administrator.controller.activity.geren.waitHelp;



import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.administrator.controller.Base.BaseFragment;

import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.WaitHelpPageAdapter;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyacceptfragment;
import com.example.administrator.controller.fragment.geren.waithelp.WaitMyapplyfragment;
import com.example.administrator.model.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WaitHelpActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener{
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    private RadioGroup wt_rd_group;
    private RadioButton rb_myapply;
    private RadioButton rb_myaccept;
    private User user=new User();
    private List<User> mUserList=new ArrayList<>();
    private int position;
    private ViewPager viewpager;
    private List<BaseFragment> mBaseFragment;
    private Fragment mContent;
    private ImageView wait_im_back;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_help);
        initview();
        initFragment();
        initdata();
//        getUser();
        listenner();

    }

    private void listenner() {
        wait_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initview() {

        viewpager = findViewById(R.id.waitviewpager);
        wt_rd_group = (RadioGroup) findViewById(R.id.wt_rd_group);
        rb_myaccept = (RadioButton) findViewById(R.id.rb_myaccept);
        rb_myapply = (RadioButton) findViewById(R.id.rb_myapply);
        wait_im_back=(ImageView)findViewById(R.id.wait_im_back);



    }

    private void initdata() {
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        wt_rd_group.check(R.id.rb_myapply);
        viewpager.setAdapter(new WaitHelpPageAdapter(getSupportFragmentManager()));
        wt_rd_group.setOnCheckedChangeListener(this);


    }

    private void initFragment() {
        mBaseFragment = new ArrayList();
        mBaseFragment.add(new WaitMyapplyfragment());
        mBaseFragment.add(new WaitMyacceptfragment());
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
                    rb_myapply.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_myaccept.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.rb_myapply:
                viewpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_myaccept:
                viewpager.setCurrentItem(PAGE_TWO);
                break;
        }
    }
//    public void getUser() {
//        String registUrl = "http://119.23.226.102/aibangbang/v1/need-help?uid="+MainActivity.uid+"&status=1";
//        System.out.println(registUrl);
//        new GetUserAsyncTask().execute(registUrl);
//    }

    private class GetUserAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUserAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(params[0])
                        .get()
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
                //判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
//                    User user=new User();
                    user = new User();
                    // 将数据封装为user对象
                    user.setId(results.getInt("id"));
                    user.setPhone(results.getString("phone"));
                    if (results.get("age").toString().equals("null")) {
                        user.setAge(0);
                    } else {
                        user.setAge(results.getInt("age"));
                    }
                    if (results.get("gender").toString().equals("null")) {
                        user.setGender(" ");
                    } else {
                        user.setGender(results.getString("gender"));
                    }
                    if (results.get("nickname").toString().equals("null")) {
                        user.setNickname(" ");
                    } else {
                        user.setNickname(results.getString("nickname"));
                    }
                    mUserList.add(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("获取用户等待帮助信息失败");
            }
        }
    }
}