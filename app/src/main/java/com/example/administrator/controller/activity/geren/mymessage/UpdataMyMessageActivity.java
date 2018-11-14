package com.example.administrator.controller.activity.geren.mymessage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.utils.Regex;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdataMyMessageActivity extends AppCompatActivity {
//    private EditText  my_message_phone;
    private EditText my_message_age;
    private EditText  my_message_nickname;
    private EditText  my_message_gender;
    private ImageView updata_message_im_back;
    private Button update_message_btn;
    private String nickname;
    private String age;
    private String gender;
//    private String phone;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_my_message);
        initview();
        initdata();
    }

    private void initview() {
        my_message_age = findViewById(R.id.my_message_age);
        my_message_nickname = findViewById(R.id.my_message_nickname);
//        my_message_phone = findViewById(R.id.my_message_phone);
        my_message_gender = findViewById(R.id.my_message_gender);
        updata_message_im_back = (ImageView)findViewById(R.id.updata_message_im_back);
        update_message_btn = (Button)findViewById(R.id.update_message_btn);
        my_message_age.setText(String.valueOf(MainActivity.user.getAge()));
        my_message_nickname.setText(MainActivity.user.getNickname());
//        my_message_phone.setText(MainActivity.user.getPhone());
        my_message_gender.setText(MainActivity.user.getGender());

    }

    private void initdata() {
        updata_message_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserMessage(MainActivity.uid);
            }
        });

    }
    public  boolean getUserMessage(String sure_gender ,String sure_nickname, String sure_age) {
        if (Regex.getStringnickname(sure_nickname)) {
            if (Regex.getStringage(sure_age)) {
                if (Regex.getStringgender(sure_gender)) {
                    return true;
                } else {
                    System.out.println("性别输入有误");
                    Toast.makeText(UpdataMyMessageActivity.this, "性别输入有误", Toast.LENGTH_SHORT).show();
                }
            } else {
                System.out.println("年龄输入有误");
                Toast.makeText(UpdataMyMessageActivity.this, "年龄输入有误", Toast.LENGTH_SHORT).show();
            }
        } else {
            System.out.println("昵称不符合规范");
            Toast.makeText(UpdataMyMessageActivity.this, "昵称不符合规范", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void addUserMessage(String uid) {
        String addUrl ="http://119.23.226.102/aibangbang/v1/users/"+uid;
        gender=my_message_gender.getText().toString();
        nickname=my_message_nickname.getText().toString();
        age=my_message_age.getText().toString();
//        phone=my_message_phone.getText().toString();
        System.out.println("@"+addUrl+gender+nickname+age+"@");
        System.out.println("@"+Regex.getStringnickname(nickname)+"@");
        System.out.println("@"+Regex.getStringgender(gender)+"@");
        System.out.println("@"+getUserMessage(gender,nickname,age)+"@");
        if (getUserMessage(gender,nickname,age)){
            new GetUserAsyncTask().execute(addUrl, uid,gender,nickname,age);
        }else {
            Toast.makeText(UpdataMyMessageActivity.this, "请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetUserAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUserAsyncTask() {
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
                    String getresult = results.getString("code");
                    if("200".equals(getresult)){
                        {
                            Intent intent=new Intent(UpdataMyMessageActivity.this,MyMessageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                            System.out.println("修改信息失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("修改信息失败");

            }
        }
    }
}
