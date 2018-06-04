package com.example.administrator.controller.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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
 * Created by Administrator on 2018/3/27.
 */

public class LoginActivity extends Activity {
    private EditText login_username;
    private EditText login_password;
    private Button login_btn;
    private Button regist_btn;
    private CheckBox remember;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        initListener();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember",false);
        if (isRemember){
            //将账号和密码设置在文本框
            String  username=pref.getString("username","");
            String password=pref.getString("password","");
            login_username.setText(username);
            login_password.setText(password);
            remember.setChecked(true);

        }

    }

    private void initListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }


        });
        regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               regist();
            }
        });
    }
    private void regist() {
        Intent intent=new Intent(this,RegistActivity.class);
        startActivity(intent);

    }





    private void initView() {
        login_username=(EditText) findViewById(R.id.login_username);
        login_password=(EditText) findViewById(R.id.login_password);
        login_btn=(Button) findViewById(R.id.login_btn);
        regist_btn=(Button) findViewById(R.id.regist_btn);
        remember=(CheckBox)findViewById(R.id.remember);
    }
    private void login() {

        String username = login_username.getText().toString();//账号
        String password = login_password.getText().toString();//密码

        if (!"".equals(username) && !"".equals(password))
        {
            Login(login_username.getText().toString(), login_password.getText().toString());
        } else {
            Toast.makeText(LoginActivity.this, "账号或者密码有误",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void Login(String username, String password) {
      String loginUrl = "http://10.0.2.2:8080/LoginServlet";
        //   String loginUrl = "http://192.168.1.106:8080/LoginServlet";

        new LoginAsyncTask().execute(loginUrl, username, password);
    }

    private class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        public LoginAsyncTask() {
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
                    String loginresult = results.getString("result");
                    System.out.println(loginresult);
                    if(!"0".equals(loginresult)){
                        MainActivity.user.setUserid(results.getInt("userid"));
                        MainActivity.user.setUsername(results.getString("username"));
                        MainActivity.user.setPassword(results.getString("password"));
                        editor=pref.edit();
                        if (remember.isChecked()){
                            //检查复选框是否被选中
                            editor.putBoolean("remember",true);
                            editor.putString("username",MainActivity.user.getUsername());
                            editor.putString("password",MainActivity.user.getPassword());

                        }else {
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent=new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"账号或密码错误",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(LoginActivity.this,"账号或密码有误",Toast.LENGTH_LONG)
                        .show();

            }
        }
    }
}

