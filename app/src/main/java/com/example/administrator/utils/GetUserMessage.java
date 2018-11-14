package com.example.administrator.utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.model.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetUserMessage {
    public  void getUser() {
        String uid = MainActivity.uid;
        String registUrl ="http://119.23.226.102/aibangbang/v1/users/"+uid;
        System.out.println(registUrl);
        new GetUserAsyncTask().execute(registUrl);
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
            System.out.println(s);
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    // 将数据封装为user对象
                    MainActivity.user.setId(results.getInt("id"));
                    MainActivity.user.setPhone(results.getString("phone"));
                    String s1=results.get("age").toString();
                    System.out.println(s1);;
                    if (s1.equals("null")){
                        MainActivity.user.setAge(0);
                    }else {
                        MainActivity.user.setAge(results.getInt("age"));
                    }
                    if (results.get("gender").toString().equals("null")){
                        MainActivity.user.setGender(" ");
                    }else {
                        MainActivity.user.setGender(results.getString("gender"));
                    }
                    if (results.get("nickname").toString().equals("null")){
                        MainActivity.user.setNickname(" ");

                    }else {
                        MainActivity.user.setNickname(results.getString("nickname"));

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
