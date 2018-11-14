package com.example.administrator.controller.fragment.geren.review;


import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.adapter.gerenadapter.FinishApplyAdapter;
import com.example.administrator.controller.adapter.gerenadapter.ReviewWaitAdapter;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.utils.DateChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RVwaitfragment extends BaseFragment {
    private RecyclerView recyclerview;
    private ReBang needHelp;
    private LinearLayoutManager layoutManager;
    private ReviewWaitAdapter adapter;
    private List<ReBang> finishList=new ArrayList<>();

    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.baserecycle,null);
        recyclerview=(RecyclerView)view.findViewById(R.id.baserecycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        adapter = new ReviewWaitAdapter(finishList);
        recyclerview.setAdapter(adapter);
        return view;
    }
    protected void initData() {
        super.initData();
        getNeedHelp();
    }
    private   void getNeedHelp() {
        String getUrl =" http://119.23.226.102/aibangbang/v1/need-help?uid="+ MainActivity.uid+"&status=3";
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
                    finishList = new ArrayList<>();
                    for (int i = 0; i <results.length() ; i++) {
                        jsonObject= (JSONObject) results.get(i);
                        needHelp= new ReBang();
                        // 将获取到的帮助信息对象解析并添加到帮助信息对象中
                        needHelp.setNeedHelpId(jsonObject.getInt("needHelpId"));
                        needHelp.setUserNeedHelpId(jsonObject.getInt("userNeedHelpId"));
//                        getUser(jsonObject.getString("userNeedHelpId"));
                        needHelp.setStatus(Integer.valueOf(jsonObject.getString("status")));
                        needHelp.setDetails(jsonObject.getString("details"));
                        // 将得到的创建时间与等待时间进行转换最终得到剩余时间
                        String stime1 = jsonObject.getString("createDateTime");
                        String chgtime1 = DateChange.waitTime(stime1,jsonObject.getInt("willingToWaitTime"));
                        needHelp.setWillingToWaitTime(chgtime1);
                        needHelp.setCreateDateTime(DateChange.getChatTimeStr(Long.valueOf(jsonObject.getString("createDateTime"))));
                        if (jsonObject.get("userComment").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setUserComment(jsonObject.getString("userComment"));
                        }
                        if (jsonObject.get("endDateTime").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setEndDateTime(DateChange.getChatTimeStr(Long.valueOf(jsonObject.getString("endDateTime"))));
                        }
                        if (jsonObject.get("userCommentDateTime").toString().equals("null")){
                            needHelp.setUserComment("");
                        }else {
                            needHelp.setUserCommentDateTime(DateChange.getChatTimeStr(Long.valueOf(jsonObject.getString("userCommentDateTime"))));
                        }
                        if(needHelp.getUserComment().equals("")){
                            finishList.add(needHelp);
                        }
                        System.out.println(needHelp);
                    }
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    adapter = new ReviewWaitAdapter(finishList);
                    recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("获取信息失败");

            }
        }
    }

}
