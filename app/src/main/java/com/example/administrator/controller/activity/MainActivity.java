package com.example.administrator.controller.activity;


import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.administrator.controller.fragment.zhuyefragment;
import com.example.administrator.model.bean.User;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends FragmentActivity  {
    private RadioGroup radioGroup;
    private List<BaseFragment> mBaseFragment;
    private int position;
    private Fragment mContent;
    public  static User user=new User();
    public  static String uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uid =intent.getStringExtra("uid");
        getUser(uid);
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
             case R.id.geren :
                 position=2;
                 break;
//             case R.id.qita :
//                 position=2;
//                 break;
//             case R.id.geren:
//                 position=3;
//                 break;
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
       // mBaseFragment.add(new qitafragment());
        mBaseFragment.add(new gerenfragment());

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        radioGroup=(RadioGroup)findViewById(R.id.rd_group);

    }
        private void getUser(String uid) {
            String registUrl ="http://119.23.226.102/aibangbang/v1/users/"+uid;
            System.out.println(registUrl);
            new GetUserAsyncTask().execute(registUrl, uid);
        }

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
                    response=okHttpClient.newCall(request).execute();
                    results=response.body().string();
                    //判断请求是否成功
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return results;
            }

            @Override
            protected void onPostExecute(String s) {
//                System.out.println(s);
                if (s != null){
                    try {
                        JSONObject results = new JSONObject(s);
                                // 将数据封装为user对象
                                user.setId(results.getInt("id"));
                                user.setPhone(results.getString("phone"));
                                String s1=results.get("age").toString();
                                System.out.println(s1);;
                                if (s1.equals("null")){
                                    user.setAge(0);
                                }else {
                                    user.setAge(results.getInt("age"));
                                }
                                if (results.get("gender").toString().equals("null")){
                                    user.setGender(" ");
                                }else {
                                    user.setGender(results.getString("gender"));
                                }
                                if (results.get("nickname").toString().equals("null")){
                                    user.setNickname(" ");
                                }else {
                                    user.setNickname(results.getString("nickname"));
                                }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("获取信息失败");

                }
            }
        }

}
