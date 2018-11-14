package com.example.administrator.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginUserActivity extends AppCompatActivity {
    private Button jump_btn;
    private Button sure_btn;
    private EditText gender;
    private EditText nickname;
    private EditText phone;
    private EditText age;
    private String sure_phone;
    private String sure_nickname;
    private String sure_age;
    private String sure_gender;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        Intent intent = getIntent();
        sure_phone=intent.getStringExtra("phone");
        initview();
        initlistener();

    }

    private void initlistener() {

        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suremessage();
            }
        });
        jump_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUserActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void suremessage() {
        sure_age=age.getText().toString();
        sure_gender=gender.getText().toString();
        System.out.println(sure_gender);
        sure_nickname=nickname.getText().toString();
        sure_phone=phone.getText().toString();
        if (getUserMessage(sure_nickname,sure_age,sure_gender)){
        sureMessage(sure_phone,sure_nickname,sure_age,sure_gender);
        }else {
            System.out.println("请检查输入");
        }

    }

    private void sureMessage(String sure_phone, String sure_nickname, String sure_age, String sure_gender) {
        String url = "http://119.23.226.102/aibangbang/v1/users"+MainActivity.uid;
        new SureMessageAsyncTask().execute(url, sure_phone,sure_gender,sure_age,sure_nickname);
    }
    private class SureMessageAsyncTask extends AsyncTask<String, Integer, String> {
        public SureMessageAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("id",params[1]);
                json.put("gender",params[2]);
                json.put("nickname",params[3]);
                json.put("age",params[4]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .put(requestBody)
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
                    String sureresult = results.getString("code");
                    if("200".equals(sureresult)){
                        Intent intent=new Intent(LoginUserActivity.this,
                                LoginActivity.class);

                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginUserActivity.this,"请重新确认",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initview() {
        sure_btn=findViewById(R.id.sure_btn);
        jump_btn=findViewById(R.id.jump_btn);
        nickname=findViewById(R.id.sure_nickname);
        phone=findViewById(R.id.sure_phone);
        age=findViewById(R.id.sure_age);
        gender=findViewById(R.id.sure_sex);
        phone.setText(sure_phone);
    }
    public  boolean getUserMessage( String sure_nickname, String sure_age, String sure_gender){
        if (Regex.getStringnickname(sure_nickname)){
                if (Regex.getStringage(sure_age)){
                    if (Regex.getStringgender(sure_gender)){
                        return true;
                    }else {
                        System.out.println("性别输入有误");
                        Toast.makeText(this,"性别输入有误",Toast.LENGTH_LONG).show();
                    }
                    return true;
                }else {
                    System.out.println("年龄输入有误");
                    Toast.makeText(this,"年龄输入有误",Toast.LENGTH_LONG).show();
                }
        }else {
            System.out.println("昵称不符合规范");
            Toast.makeText(this,"昵称不符合规范",Toast.LENGTH_LONG).show();
        }
        return false;
    }


}
