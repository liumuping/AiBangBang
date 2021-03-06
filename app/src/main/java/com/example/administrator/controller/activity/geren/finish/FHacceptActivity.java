package com.example.administrator.controller.activity.geren.finish;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.controller.adapter.zhuyeadapter.OfferHpHisAdapter;
import com.example.administrator.model.bean.Finish;
import com.example.administrator.model.bean.ReBang;
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
import okhttp3.Response;

public class FHacceptActivity extends AppCompatActivity {
    private ArrayList<UserOfferHelpHistory> reViewList = new ArrayList<>();
    private UserOfferHelpHistory userOfferHelpHistory;
    private StaggeredGridLayoutManager layoutManager;
    private OfferHpHisAdapter adapter;
    private TextView tv_rebang_data;
    private TextView tv_rebang_cre;
    private TextView tv_rebang_name;
    private TextView tv_rebang_wtime;
    private LinearLayout ly_fy;
    private ReBang reBang;
    private ImageView back;
    private RecyclerView recyclerView;
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
        tv_rebang_data = (TextView) findViewById(R.id.tv_datebase_data);
        tv_rebang_cre = (TextView) findViewById(R.id.dateshowbase_cre);
        tv_rebang_name = findViewById(R.id.dateshowbase_username);
        tv_rebang_wtime = findViewById(R.id.dateshowbase_waittime);
        ly_fy = findViewById(R.id.ly_fy);
        ly_fy.setVisibility(View.VISIBLE);
        back = (ImageView) findViewById(R.id.dateshowbase_im_back);
    }

    private void initdata() {
        reBang = (ReBang) getIntent().getSerializableExtra("reBang");
        tv_rebang_data.setText(reBang.getDetails());
        tv_rebang_name.setText(MainActivity.user.getNickname());
        tv_rebang_cre.setText(reBang.getCreateDateTime());
        tv_rebang_wtime.setText(reBang.getEndDateTime());
        getUserHpHty();
        Listenner();
    }


    private void Listenner() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    //    获取历史帮助用户的信息
    private void getUserHpHty() {
        String url = "http://119.23.226.102/aibangbang/v1/help-histories/help?uid="+reBang.getUserNeedHelpId();
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
            if (s != null ) {
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






}
