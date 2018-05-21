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
    private EditText regist_password;
    private Button re_regist_btn;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initListener();


    }

    private void initListener() {
        re_regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

    }

    private void regist() {
        String username = regist_username.getText().toString();//账号
        String password = regist_password.getText().toString();//密码
        if (!"".equals(username) && !"".equals(password))
        {
            Regist(regist_username.getText().toString(), regist_password.getText().toString());
        } else {
            Toast.makeText(RegistActivity.this, "账号或者密码有误",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void Regist(String username, String password) {
        String registUrl = "http://10.0.2.2:8080/RegistServlet";
        // String registUrl = "http://192.168.1.106:8080/RegistServlet";
        new RegistAsyncTask().execute(registUrl, username, password);
    }

    private void initView() {
        regist_username=(EditText)findViewById(R.id.regist_username);
        regist_password=(EditText)findViewById(R.id.regist_password);
        re_regist_btn=(Button) findViewById(R.id.re_regist_btn);
    }

    public class RegistAsyncTask extends AsyncTask<String, Integer, String> {
        public RegistAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("username",params[1]);
                json.put("password",params[2]);
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
                    int registresult = results.getInt("result");
                    System.out.println("=======================>"+registresult);
                    if(registresult!=0){
                        Toast.makeText(RegistActivity.this,
                                "正在注册，请稍后",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RegistActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
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
}
