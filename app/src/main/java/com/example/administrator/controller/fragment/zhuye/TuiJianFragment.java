package com.example.administrator.controller.fragment.zhuye;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.zhuyeadapter.TuiJianAdapter;
import com.example.administrator.model.bean.NeedHelp;
import com.example.administrator.utils.DateChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10.
 */

public class TuiJianFragment extends BaseFragment {
    private ArrayList<NeedHelp> tuiJianList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private TuiJianAdapter adapter;
    private SwipeRefreshLayout tj_swipe_refresh;
    private NeedHelp needHelp;
//    private User user;
//    private HashMap<Integer,User> userMap = new HashMap<>();
//    private ArrayList<NeedHelp> list;
    @Override
    protected View initView() {
        View view = View.inflate(mcontext, R.layout.tuijian, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.tj_recycle_view);
        layoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        adapter = new TuiJianAdapter(tuiJianList,userMap);
        adapter = new TuiJianAdapter(tuiJianList);
        recyclerView.setAdapter(adapter);
        tj_swipe_refresh=(SwipeRefreshLayout)view.findViewById(R.id.tj_swipe_refresh);
        return view;
    }

    @Override
    protected void initData() {
            super.initData();
            getNeedHelp();
            Listenner();


    }

    private void Listenner() {
        tj_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getNeedHelp();
                        if(tuiJianList.size()>0) {

                            if (tuiJianList == null) {
                                tuiJianList = new ArrayList<>();
                            } else {
                                tuiJianList.clear();
                            }

                            for (int i = 0; i < tuiJianList.size(); i++) {
                                tuiJianList.add(tuiJianList.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }
                        tj_swipe_refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private   void getNeedHelp() {
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
//                    list = new ArrayList<>();
                    tuiJianList = new ArrayList<>();
                    for (int i = 0; i <results.length() ; i++) {
                        jsonObject= (JSONObject) results.get(i);
                        needHelp= new NeedHelp();
                        // 将获取到的帮助信息对象解析并添加到帮助信息对象中
                        needHelp.setNeedHelpId(jsonObject.getInt("needHelpId"));
                        needHelp.setUserNeedHelpId(jsonObject.getInt("userNeedHelpId"));
//                        getUser(jsonObject.getString("userNeedHelpId"));
                        needHelp.setStatus(Integer.valueOf(jsonObject.getString("status")));
                        needHelp.setDetails(jsonObject.getString("details"));
                        // 将得到的创建时间与等待时间进行转换最终得到剩余时间
                        String stime1 = jsonObject.getString("createDateTime");
                        String chgtime1 =DateChange.waitTime(stime1,jsonObject.getInt("willingToWaitTime"));
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
                        tuiJianList.add(needHelp);
//                        System.out.println(tuiJianList.size());
                        System.out.println(needHelp);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    adapter = new TuiJianAdapter(tuiJianList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("获取信息失败");

            }
        }
    }

//    public  void getUser(String uid) {
//        String registUrl ="http://119.23.226.102/aibangbang/v1/users/"+uid;
//        System.out.println(registUrl);
//        new GetUserAsyncTask().execute(registUrl);
//    }
//
//    private class GetUserAsyncTask extends AsyncTask<String, Integer, String> {
//        public GetUserAsyncTask() {
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
//                    JSONObject results = new JSONObject(s);
//                    userMap = new HashMap<>();
//                    user=new User();
//                    // 将数据封装为user对象
//                    user.setId(results.getInt("id"));
//                    user.setPhone(results.getString("phone"));
//                    String s1=results.get("age").toString();
//                    if (s1.equals("null")){
//                        user.setAge(0);
//                    }else {
//                        user.setAge(results.getInt("age"));
//                    }
//                    if (results.get("gender").toString().equals("null")){
//                        user.setGender(" ");
//                    }else {
//                        user.setGender(results.getString("gender"));
//                    }
//                    if (results.get("nickname").toString().equals("null")){
//                        user.setNickname(" ");
//
//                    }else {
//                        user.setNickname(results.getString("nickname"));
//
//                    }
//                    userMap.put(user.getId(),user);
//                    System.out.println(user.getNickname());
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