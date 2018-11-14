package com.example.administrator.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class ResetPasActivity extends AppCompatActivity{
    private EditText regist_username;
    private EditText regist_password;
    private String code;
    private String phone;
    private Button regist_btn;

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        phone=intent.getStringExtra("phone");
        initView();
        initListener();
    }

    private void initListener() {
        regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regist();
            }
        });
    }

    private void initView() {
        regist_username=(EditText)findViewById(R.id.regist_username);
        regist_password=(EditText)findViewById(R.id.regist_password);
        regist_btn=(Button)findViewById(R.id.regist_cheack_btn);
        regist_username.setText(phone);


    }
    private void regist() {

        String username = regist_username.getText().toString();//账号
        String password = regist_password.getText().toString();//密码

        if (!"".equals(username) && !"".equals(password))
        {
            Regist(username, password);
        } else {
            Toast.makeText(ResetPasActivity.this, "账号或者密码为空",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void Regist(String username, String password) {
        String loginUrl = "http://119.23.226.102/aibangbang/v1/reset-pass";
        new RegistAsyncTask().execute(loginUrl, username,code, password);
    }

    private class RegistAsyncTask extends AsyncTask<String, Integer, String> {
        public RegistAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("phone",params[1]);
                json.put("code",params[2]);
                json.put("password",params[3]);
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
                    System.out.println(registresult);
                    if("200".equals(registresult)){
                        Toast.makeText(ResetPasActivity.this,"重置密码成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(ResetPasActivity.this,"账号或密码错误",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(ResetPasActivity.this,"账号或密码有误",Toast.LENGTH_LONG)
                        .show();

            }
        }
    }
}
