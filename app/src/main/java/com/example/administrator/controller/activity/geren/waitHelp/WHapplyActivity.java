package com.example.administrator.controller.activity.geren.waitHelp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.zhuye.CommentQuesActivity;
import com.example.administrator.controller.activity.zhuye.UpdateQuestionActivity;
import com.example.administrator.controller.adapter.zhuyeadapter.OfferHpHisAdapter;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.model.bean.User;
import com.example.administrator.model.bean.UserOfferHelpHistory;
import com.example.administrator.utils.DateChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WHapplyActivity extends AppCompatActivity  {
    private ArrayList<UserOfferHelpHistory> reViewList = new ArrayList<>();
    private ArrayList<User> userwtList = new ArrayList<>();
    private UserOfferHelpHistory userOfferHelpHistory;
    private StaggeredGridLayoutManager layoutManager;
    private OfferHpHisAdapter adapter;
    private User usercur=new User();
    private User userwt=new User();
    private TextView tv_rebang_data;
    private TextView tv_rebang_cre;
    private TextView tv_rebang_name;
    private TextView tv_rebang_wtime;
    private LinearLayout ly_hw;
    private Button update_btn;
    private Button quesfh_btn;
    private ReBang reBang;
    private ImageView back;
    private RecyclerView recyclerView;
    private int flag = 1;
    private int endflag = 2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datashowbase);
        recyclerView = (RecyclerView) findViewById(R.id.review_recycle_view);
        layoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        adapter = new OfferHpHisAdapter(reViewList);
        recyclerView.setAdapter(adapter);
        initview();
        initdata();
    }

    private void initview() {
        update_btn = findViewById(R.id.update_btn);
        quesfh_btn = findViewById(R.id.quesfh_btn);
        tv_rebang_data = (TextView) findViewById(R.id.tv_datebase_data);
        tv_rebang_cre = (TextView) findViewById(R.id.dateshowbase_cre);
        tv_rebang_name = findViewById(R.id.dateshowbase_username);
        tv_rebang_wtime = findViewById(R.id.dateshowbase_waittime);
        ly_hw = findViewById(R.id.ly_hw);
        ly_hw.setVisibility(View.VISIBLE);
        update_btn.setVisibility(View.VISIBLE);
        quesfh_btn.setVisibility(View.VISIBLE);
        back = (ImageView) findViewById(R.id.dateshowbase_im_back);
    }

    private void initdata() {
        reBang = (ReBang) getIntent().getSerializableExtra("reBang");
        tv_rebang_data.setText(reBang.getDetails());
        tv_rebang_name.setText(MainActivity.user.getNickname());
        tv_rebang_cre.setText(reBang.getCreateDateTime());
        tv_rebang_wtime.setText(reBang.getWillingToWaitTime());
        getUserHpHty();
        getUserofHpcurrent();
        getUserofHpwait();
        Listenner();
    }


    private void Listenner() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        发起用户修改帮助内容
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WHapplyActivity.this, UpdateQuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reBang", reBang);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        发起用户结束帮助
        quesfh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        http://119.23.226.102/aibangbang/v1/need-help/end
                AlertDialog.Builder dialog = new AlertDialog.Builder(WHapplyActivity.this);
                {
                    dialog.setMessage("是否要进行评价或提供书面帮助");
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            quesfh();
                            finish();
                        }
                    });
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            quesfh();

                            Intent intent = new Intent(WHapplyActivity.this, CommentQuesActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("reBang", reBang);
                            bundle.putSerializable("usercur", usercur);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }



    //    获取历史帮助用户的信息
    private void getUserHpHty() {
        String url = "http://119.23.226.102/aibangbang/v1/help-histories/help?uid=" + reBang.getUserNeedHelpId() + "&status=" + reBang.getStatus();
        new GetUserHelpHisAsyncTask().execute(url);

    }

    private class GetUserHelpHisAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUserHelpHisAsyncTask() {
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
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
                //    判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONArray results = new JSONArray(s);
                    JSONObject jsonObject = new JSONObject();
//                    list = new ArrayList<>();
                    reViewList = new ArrayList<>();
                    for (int i = 0; i < results.length(); i++) {
                        jsonObject = (JSONObject) results.get(i);
                        userOfferHelpHistory = new UserOfferHelpHistory();
                        // 将获取到的帮助信息对象解析并添加到帮助信息对象中
                        userOfferHelpHistory.setNeedHelpId(jsonObject.getInt("needHelpId"));
                        userOfferHelpHistory.setUserOfferHelpHistoryId(jsonObject.getInt("userOfferHelpHistoryId"));
                        userOfferHelpHistory.setStatus(Integer.valueOf(jsonObject.getString("status")));
                        userOfferHelpHistory.setStartWaitDateTime(jsonObject.getString("startWaitDateTime"));
                        userOfferHelpHistory.setStartHelpDateTime(jsonObject.getString("startHelpDateTime"));
                        if (jsonObject.get("userComment").toString().equals("null")) {
                            userOfferHelpHistory.setUserComment("");
                        } else {
                            userOfferHelpHistory.setUserComment(jsonObject.getString("userComment"));
                        }
                        if (jsonObject.get("endDateTime").toString().equals("null")) {
                            userOfferHelpHistory.setUserComment("");
                        } else {
                            userOfferHelpHistory.setEndDateTime(DateChange.getChatTimeStr(Long.valueOf(jsonObject.getString("endDateTime"))));
                        }
                        if (jsonObject.get("userCommentDateTime").toString().equals("null")) {
                            userOfferHelpHistory.setUserComment("");
                        } else {
                            userOfferHelpHistory.setUserCommentDateTime(DateChange.getChatTimeStr(Long.valueOf(jsonObject.getString("userCommentDateTime"))));
                        }
                        reViewList.add(userOfferHelpHistory);
                        System.out.println("-----------------------------用户评价");
                    }
                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    adapter = new OfferHpHisAdapter(reViewList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("获取信息失败");

            }
        }

    }


    //    获取当前正在帮助的用户信息
    private void getUserofHpcurrent() {
        String url = "http://119.23.226.102/aibangbang/v1/help-currents/user?hid="+reBang.getNeedHelpId();
        new GetUsercuAsyncTask().execute(url);
    }

    private class GetUsercuAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUsercuAsyncTask() {
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
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
                //判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !TextUtils.isEmpty(s)) {
                try {
                    JSONObject results = new JSONObject(s);
                    usercur=new User();
                    System.out.println(s);
                    // 将数据封装为user对象
                    usercur.setId(results.getInt("id"));
                    usercur.setPhone(results.getString("phone"));
                    String s1 = results.get("age").toString();
                    if (s1.equals("null")) {
                        usercur.setAge(0);
                    } else {
                        usercur.setAge(results.getInt("age"));
                    }
                    if (results.get("gender").toString().equals("null")) {
                        usercur.setGender(" ");
                    } else {
                        usercur.setGender(results.getString("gender"));
                    }
                    if (results.get("nickname").toString().equals("null")) {
                        usercur.setNickname(" ");
                    } else {
                        usercur.setNickname(results.getString("nickname"));
                    }
                    System.out.println("---------------------" + usercur.getId() + "---------------------");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("获取当前正在帮助用户信息失败");
            }
        }
    }


    //     发起帮助用户终止整个帮助（帮助完全结束）
    private void quesfh() {
        String url = "http://119.23.226.102/aibangbang/v1/need-help/end";
        new QuesfhAsyncTask().execute(url, String.valueOf(reBang.getNeedHelpId()), String.valueOf(usercur.getId()),String.valueOf(flag) , String.valueOf(flag));
    }


    private class QuesfhAsyncTask extends AsyncTask<String, Integer, String> {
        public QuesfhAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("needHelpId", params[1]);
                json.put("userOfferHelpCurrentId", params[2]);
                json.put("endedFlag", params[3]);
                json.put("continueFlag", params[4]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
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
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
                    String coderesult = results.getString("code");
                    String data = results.getString("data");
                    JSONObject infoResult = new JSONObject(data);
                    String infer = infoResult.getString("info");
                    System.out.println(infer);
                    if ("200".equals(coderesult)) {
                        Toast.makeText(WHapplyActivity.this, "结束帮助成功", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("结果为空");
            }
        }
    }


    //    获取当前正在等待帮助的用户信息
    private void getUserofHpwait() {
        String url = "http://119.23.226.102/aibangbang/v1/help-waits/user?hid="+reBang.getNeedHelpId();
        new GetUserwtAsyncTask().execute(url);
    }

    private class GetUserwtAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUserwtAsyncTask() {
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
                    userwtList = new ArrayList<>();
                    for (int i = 0; i <results.length() ; i++) {
                        jsonObject= (JSONObject) results.get(i);
                        userwt= new User();
                        // 将获取到的等待帮助信息对象解析并添加到等待帮助信息对象中
                        userwt.setId(jsonObject.getInt("id"));
                        userwt.setPhone(jsonObject.getString("phone"));
                        String s1=jsonObject.get("age").toString();
                        if (s1.equals("null")){
                            userwt.setAge(0);
                        }else {
                            userwt.setAge(jsonObject.getInt("age"));
                        }
                        if (jsonObject.get("gender").toString().equals("null")){
                            userwt.setGender(" ");
                        }else {
                            userwt.setGender(jsonObject.getString("gender"));
                        }
                        if (jsonObject.get("nickname").toString().equals("null")){
                            userwt.setNickname(" ");

                        }else {
                            userwt.setNickname(jsonObject.getString("nickname"));

                        }
                        userwtList.add(userwt);
                        System.out.println(userwt+"userwt");
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
