package com.example.administrator.controller.activity.geren.setting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

public class UpdataPassawordActivity extends AppCompatActivity {
    private ImageView udps_im_back;
    private EditText udps_old;
    private EditText udps_new;
    private Button btn_udps;
    private String oldpassword;
    private String newpassword;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_passaword);
        initview();
        initdata();
    }

    private void initview() {
        udps_im_back=(ImageView)findViewById(R.id.udps_im_back);
        udps_old=(EditText)findViewById(R.id.udps_old);
        udps_new=(EditText)findViewById(R.id.udps_new);
        btn_udps=(Button)findViewById(R.id.btn_udps);
    }

    private void initdata() {
        listenner();
    }

    private void listenner() {
        udps_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_udps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatepassword();
            }
        });
    }

    private void updatepassword() {
        String updateUrl ="http://119.23.226.102/aibangbang/v1/users/"+ MainActivity.uid;
        oldpassword=udps_old.getText().toString();
        newpassword=udps_new.getText().toString();
        if (Regex.getStringpasswored(newpassword)){
            new UpdateAsyncTask().execute(updateUrl,MainActivity.uid,oldpassword,newpassword);
        }else {
            System.out.println("密码必须是6位包含字母和数字");
        }
    }
    private class UpdateAsyncTask extends AsyncTask<String, Integer, String> {
        public UpdateAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("id",params[1]);
                json.put("oldPassword",params[2]);
                json.put("newPassword",params[3]);
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
                            finish();
                        }
                    }else{
                        System.out.println("修改密码失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("修改密码失败");

            }
        }
    }
}
