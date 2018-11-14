package com.example.administrator.utils;

import android.os.AsyncTask;

import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.zhuye.RebangActivity;
import com.example.administrator.model.bean.User;
import com.example.administrator.model.bean.UserOfferHelpWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetUser {

    public  void getUser(String uid, User user) {
//        String uid = MainActivity.uid;
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
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    User user = new User();
                    // 将数据封装为user对象
                    user.setId(results.getInt("id"));
                    user.setPhone(results.getString("phone"));
                    String s1=results.get("age").toString();
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



//    private void getUserofHpwait() {
//        String url = "http://119.23.226.102/aibangbang/v1/help-waits/user?hid="+reBang.getNeedHelpId();
//        new RebangActivity.GetUserwtAsyncTask().execute(url);
//    }

//    private class GetUserwtAsyncTask extends AsyncTask<String, Integer, String> {
//        public GetUserwtAsyncTask() {
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            Response response = null;
//            String results = null;
//            try {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(params[0])
//                        .get()
//                        .build();
//                response=okHttpClient.newCall(request).execute();
//                results=response.body().string();
//                //判断请求是否成功
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return results;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            if (s != null){
//                try {
//                    JSONArray results = new JSONArray(s);
//                    JSONObject jsonObject = new JSONObject();
//                    userwtList = new ArrayList<>();
//                    for (int i = 0; i <results.length() ; i++) {
//                        jsonObject= (JSONObject) results.get(i);
//                        userwt= new UserOfferHelpWait();
//                        // 将获取到的等待帮助信息对象解析并添加到等待帮助信息对象中
//                        userwt.setNeedHelpId(jsonObject.getInt("needHelpId"));
//                        userwt.setUserOfferHelpWaitId(jsonObject.getInt("userOfferHelpWaitId"));
//                        userwt.setStatus(Integer.valueOf(jsonObject.getString("status")));
//                        userwt.setStartWaitDateTime(jsonObject.getString("startWaitDateTime"));
//                        userwt.setWillingToWaitTime(jsonObject.getInt("createDateTime"));
//                        userwt.setWillingToWaitFlag(jsonObject.getInt("createDateTime"));
//                        if (jsonObject.get("endDateTime").toString().equals("null")){
//                            userwt.setEndDateTime("");
//                        }else {
//                            userwt.setEndDateTime(jsonObject.getString("endDateTime"));
//                        }
//                        userwtList.add(userwt);
//                        System.out.println(userwt+"userwt");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }else {
//                System.out.println("获取信息失败");
//
//            }
//        }
//    }
}
