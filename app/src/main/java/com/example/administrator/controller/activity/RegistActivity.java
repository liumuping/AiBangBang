package com.example.administrator.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.controller.R;
import com.example.administrator.utils.Regex;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/17.
 */

public class RegistActivity extends AppCompatActivity {
    private EditText regist_username;
    private EditText regist_cheack;
    private Button regist_account_btn;
    private Button regist_cheack_btn;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initListener();


    }

    private void initListener() {
        regist_cheack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });
        regist_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registaccount();
            }
        });

    }
    private void regist() {
        String username = regist_username.getText().toString();//账号
        String cheack = regist_cheack.getText().toString();//密码
        if (Regex.getStringusername(username))
        {
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
//            editText.setClickable(true);
            // 如果验证手机号码正确，设置验证码框可以输入
            regist_cheack.setFocusable(true);
            regist_cheack.setFocusableInTouchMode(true);
            regist_cheack.setClickable(true);
            System.out.println("------------------------------");
            Regist(regist_username.getText().toString());
        } else {
            Toast.makeText(RegistActivity.this, "请输入正确的手机号",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void Regist(String username) {
        String registUrl ="http://119.23.226.102/aibangbang/v1/register-codes";
//        String registUrl = "http://10.0.2.2:8080/RegistServlet";
        // String registUrl = "http://192.168.1.106:8080/RegistServlet";
        new RegistAsyncTask().execute(registUrl, username);
    }

    private void initView() {
        regist_username=(EditText)findViewById(R.id.regist_username);
        regist_cheack=(EditText)findViewById(R.id.regist_cheack);
        regist_account_btn=(Button) findViewById(R.id.regist_account_btn);
        regist_cheack_btn=(Button)findViewById(R.id.regist_cheack_btn);
    }
//  获取验证码
    public class RegistAsyncTask extends AsyncTask<String, Integer, String> {
        public RegistAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("phone",params[1]);
//                json.put("password",params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response=okHttpClient.newCall(request).execute();
                results=response.body().string();
                //判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    String registresult = results.getString("code");
//                    int registresult = results.getInt("result");
                    System.out.println("=======================>"+registresult);
                    if("200".equals(registresult)){
                        // 当验证码发送成功将按钮进行隐藏显示
                        regist_cheack_btn.setVisibility(View.GONE);
                        regist_account_btn.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(RegistActivity.this,"账号或密码错误",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(RegistActivity.this,"账号或密码有误",
                        Toast.LENGTH_LONG)
                        .show();

            }
       }
    }
// 对验证码进行验证
    private void registaccount() {

        String username = regist_username.getText().toString();//账号
        String cheack = regist_cheack.getText().toString();//验证码
        if (Regex.getStringusername(username))
        {
            RegistCheack(username,cheack);
        } else {
            Toast.makeText(RegistActivity.this, "请检查输入是否正确",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void RegistCheack(String username, String cheack) {
        String registUrl ="http://119.23.226.102/aibangbang/v1/register-codes/"+username;
        System.out.println(registUrl);
        new RegistCheackAsyncTask().execute(registUrl, username,cheack);
    }

    public class RegistCheackAsyncTask extends AsyncTask<String, Integer, String> {
        public RegistCheackAsyncTask() {
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
            System.out.println(s);
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    String registresult = results.getString("code");
//                    System.out.println("=======================>"+registresult);
                    if(regist_cheack.getText().toString().equals(registresult)){
                        Intent intent=new Intent(RegistActivity.this,
                                RegistUserActivity.class);
                        intent.putExtra("phone",regist_username.getText().toString());
                        intent.putExtra("code",regist_cheack.getText().toString());
                        startActivity(intent);
                    }else{
//                        Toast.makeText(RegistActivity.this,"账号或密码错误",
//                        Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(RegistActivity.this,"账号或密码有误",
                        Toast.LENGTH_LONG)
                        .show();

            }
        }
    }
}
