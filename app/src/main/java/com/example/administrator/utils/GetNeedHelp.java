package com.example.administrator.utils;

import android.os.AsyncTask;


import com.example.administrator.model.bean.NeedHelp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNeedHelp {
    public  void getNeedHelp() {
        String getUrl ="http://119.23.226.102/aibangbang/v1/need-help/recommend";
        System.out.println(getUrl);
        new GetNeedHelpAsyncTask().execute(getUrl);
    }
    private class GetNeedHelpAsyncTask extends AsyncTask<String, Integer, String> {
        public GetNeedHelpAsyncTask() {
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
                    JSONArray results = new JSONArray(s);
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i <results.length() ; i++) {
                         jsonObject= (JSONObject) results.get(i);
                    }
                    NeedHelp needHelp = new NeedHelp();
                    ArrayList<NeedHelp> list = new ArrayList<>();
                    for (int i=0;i<results.length();i++){
                        // 将获取到的帮助信息对象解析并添加到帮助信息对象中
                        needHelp.setNeedHelpId(Integer.valueOf(jsonObject.getString("needHelpId")));
                        needHelp.setUserNeedHelpId(Integer.valueOf(jsonObject.getString("userNeedHelpId")));
                        needHelp.setStatus(Integer.valueOf(jsonObject.getString("status")));
                        needHelp.setDetails(jsonObject.getString("details"));
                        needHelp.setWillingToWaitTime(jsonObject.getString("willingToWaitTime"));
                        needHelp.setCreateDateTime(GetTime.getdate(Long.valueOf(jsonObject.getString("createDateTime"))));
                        if (jsonObject.get("userComment").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setUserComment(jsonObject.getString("userComment"));
                        }
                        if (jsonObject.get("endDateTime").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setEndDateTime(GetTime.getdate(Long.valueOf(jsonObject.getString("endDateTime"))));
                        }
                        if (jsonObject.get("userCommentDateTime").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setUserCommentDateTime(GetTime.getdate(Long.valueOf(jsonObject.getString("userCommentDateTime"))));
                        }
                        list.add(needHelp);
                        System.out.println(needHelp);
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
