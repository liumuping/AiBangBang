package com.example.administrator.controller.activity.zhuye;

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
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.controller.adapter.zhuyeadapter.OfferHpHisAdapter;
import com.example.administrator.controller.interfc.InputStringtime;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.model.bean.User;
import com.example.administrator.model.bean.UserOfferHelpHistory;
import com.example.administrator.utils.DateChange;
import com.example.administrator.view.MydialogFragment;


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



/**
 * Created by Administrator on 2018/4/16.
 */

public class RebangActivity extends AppCompatActivity implements InputStringtime {
    private ArrayList<UserOfferHelpHistory> reViewList = new ArrayList<>();
    private ArrayList<User> userwtList = new ArrayList<>();
    private UserOfferHelpHistory userOfferHelpHistory;
//    private UserOfferHelpWait userwt = new UserOfferHelpWait();
    private StaggeredGridLayoutManager layoutManager;
    private OfferHpHisAdapter adapter;
    private User usercur=new User();
    private User userwt=new User();
    private TextView tv_rebang_data;
    private TextView tv_rebang_cre;
    private TextView tv_rebang_name;
    private TextView tv_rebang_wtime;
    private Button chat_btn;
    private Button off_btn;
    private Button cancel_btn;
    private Button ofercancel_btn;
    private Button update_btn;
    private Button quesfh_btn;
    private Button oferfh_btn;
    private ReBang reBang;
    private ImageView back;
    private RecyclerView recyclerView;
    private LinearLayout ly_hw;
    private LinearLayout ly_hp;
    private LinearLayout ly_fn;
    private LinearLayout ly_fy;
    private int myflag=1;
    private int flag = 1;
    private int endflag = 2;
    private int waittime = 60;
    private int state;
    private MydialogFragment fragment;
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
        fragment = new MydialogFragment();
        fragment.setInputStringtime(this);
        chat_btn = (Button) findViewById(R.id.chat_btn);
        off_btn = (Button) findViewById(R.id.offer_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        update_btn = findViewById(R.id.update_btn);
        oferfh_btn = findViewById(R.id.oferfh_btn);
        ofercancel_btn = findViewById(R.id.ofercancel_btn);
        quesfh_btn = findViewById(R.id.quesfh_btn);
        tv_rebang_data = (TextView) findViewById(R.id.tv_datebase_data);
        tv_rebang_cre = (TextView) findViewById(R.id.dateshowbase_cre);
        tv_rebang_name = findViewById(R.id.dateshowbase_username);
        tv_rebang_wtime = findViewById(R.id.dateshowbase_waittime);
        ly_fn = findViewById(R.id.ly_fn);
        ly_fy = findViewById(R.id.ly_fy);
        ly_hw = findViewById(R.id.ly_hw);
        ly_hp = findViewById(R.id.ly_hp);
        back = (ImageView) findViewById(R.id.dateshowbase_im_back);
    }

    private void initdata() {
        reBang = (ReBang) getIntent().getSerializableExtra("reBang");
        state = reBang.getStatus();
        tv_rebang_data.setText(reBang.getDetails());
        tv_rebang_name.setText(reBang.getNickname());
        tv_rebang_cre.setText(reBang.getCreateDateTime());
        tv_rebang_wtime.setText(reBang.getWillingToWaitTime());
        getUserHpHty();
        getUserofHpcurrent();
        getUserofHpwait();
        showstates(reBang);
        showbtn();
        Listenner();
//        System.out.println(userwt.getId());
    }


    private void Listenner() {
        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RebangActivity.this, ChatMessageActivity.class);
                startActivity(intent1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //      提供帮助
        off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("1+++++++++++++++++++++++++"+usercur.getId()+"+++++++++++++++++++++++++"+MainActivity.uid);
                if (usercur.getId() != Integer.valueOf(MainActivity.uid)) {
                    if (userwt.getId() == Integer.valueOf(MainActivity.uid)){
                        ofercancel_btn.setVisibility(View.VISIBLE);
                        off_btn.setVisibility(View.GONE);
                    }else {
                        off_btn.setVisibility(View.VISIBLE);
                        postoffer();
                    }
                } else {
//                    ofercancel_btn.setVisibility(View.GONE);
                    off_btn.setVisibility(View.GONE);
                    oferfh_btn.setVisibility(View.VISIBLE);
                    chat_btn.setVisibility(View.VISIBLE);
                }
            }
        });
//        发起用户主动取消帮助
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelHelp();
            }
        });
//        发起用户修改帮助内容
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RebangActivity.this, UpdateQuestionActivity.class);
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
                if (usercur.getId()!=0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RebangActivity.this);
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
                                Intent intent = new Intent(RebangActivity.this, CommentQuesActivity.class);
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
                }else {
                    Toast.makeText(RebangActivity.this,"还在等待帮助哦", Toast.LENGTH_LONG).show();
                }
            }
        });
//        等待帮助用户取消等待
        ofercancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < userwtList.size(); i++) {
                    if (Integer.valueOf(MainActivity.uid) == userwtList.get(i).getId()) {
                        ofcancelHelp();
                    }
                }
            }
        });
//        提供帮助用户评论帮助
        oferfh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reBang.getStatus()==3) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RebangActivity.this);
                    {
                        dialog.setMessage("是否要进行评价或提供书面帮助");
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RebangActivity.this, CommentOferActivity.class);
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
                }else {
                    Toast.makeText(RebangActivity.this,"需要帮助的用户还未结束帮助哦！", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    //    用户确认是否提供帮助功能
    private void postoffer() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            {
                dialog.setMessage("现在有人正在帮助，你愿意等待吗");
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("reBang", reBang);
                        fragment.setArguments(bundle);
                        fragment.show(getSupportFragmentManager(), "DialogFragment");
                    }
                });
                dialog.show();
            }

    }

    //    将确认帮助的结果显示出来
    @Override
    public void inputInforCompleted(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    //    根据状态显示按钮
    private void showbtn() {
        //  如果帮助状态处于未结束显示按钮
        if (reBang.getStatus() == 1 || reBang.getStatus() == 2) {
            //  当前用户是当前帮助正在进行的用户,就显示正在帮助用户的按钮
            if (Integer.valueOf(MainActivity.uid) != reBang.getUserNeedHelpId()  ) {
                if (usercur.getId() != Integer.valueOf(MainActivity.uid)) {
                    if (userwt.getId() == Integer.valueOf(MainActivity.uid)){
                        ofercancel_btn.setVisibility(View.VISIBLE);
                        System.out.println("如果当前登录用户是当前事件的等待帮助用户，则显示取消等待按钮");
                    }else {
                        ofercancel_btn.setVisibility(View.GONE);
                        off_btn.setVisibility(View.VISIBLE);
                        System.out.println("如果当前登录用户仅仅是浏览当前事件的用户，则显示帮助按钮");
                    }
                }
                else {
                    ofercancel_btn.setVisibility(View.GONE);
                    chat_btn.setVisibility(View.VISIBLE);
                    oferfh_btn.setVisibility(View.VISIBLE);
                    System.out.println("如果当前登录用户是当前事件的正在帮助用户，则显示聊天和结束事件按钮，但如果发起者未结束，不能评价");
                }
//            off_btn.setVisibility(View.VISIBLE);
            } else {
                cancel_btn.setVisibility(View.VISIBLE);
                update_btn.setVisibility(View.VISIBLE);
                quesfh_btn.setVisibility(View.VISIBLE);
                System.out.println("当前用户是发起帮助的用户，显示结束事件按钮和修改按钮");
            }
        }else {
            System.out.println("帮助状态已经结束，全部按钮都不显示");
        }
    }

    //    根据状态显示状态图标
    private void showstates(ReBang tuiJian) {
        if (tuiJian.getStatus() == 1) {
            ly_hw.setVisibility(View.VISIBLE);
            ly_hp.setVisibility(View.GONE);
            ly_fy.setVisibility(View.GONE);
            ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 2) {
            ly_hp.setVisibility(View.VISIBLE);
            ly_hw.setVisibility(View.GONE);
            ly_fy.setVisibility(View.GONE);
            ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 3) {
            ly_fy.setVisibility(View.VISIBLE);
            ly_hw.setVisibility(View.GONE);
            ly_hp.setVisibility(View.GONE);
            ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 4) {
            ly_fn.setVisibility(View.VISIBLE);
            ly_hw.setVisibility(View.GONE);
            ly_fy.setVisibility(View.GONE);
            ly_hp.setVisibility(View.GONE);
        }

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

    //    等待用户主动取消等待帮助
    private void ofcancelHelp() {
        String url = "http://119.23.226.102/aibangbang/v1/help-waits/cancel";
        new OfCancelHelpAsyncTask().execute(url, String.valueOf(reBang.getNeedHelpId()),String.valueOf(userwt.getId()));
    }

    private class OfCancelHelpAsyncTask extends AsyncTask<String, Integer, String> {
        public OfCancelHelpAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("needHelpId", params[1]);
                json.put("userOfferHelpWaitId",params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .delete(requestBody)
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
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
                    if ("200".equals(results.getString("code"))){
                        System.out.println("取消帮助成功");
                        finish();
                    }else {
                        System.out.println("取消帮助成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("取消帮助失败");
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

    //    提供用户用户发起帮助
    public void posthttpoffer() {
        String loginUrl = "http://119.23.226.102/aibangbang/v1/need-help/offer";
        new HelpOffAsyncTask().execute(loginUrl, MainActivity.uid, String.valueOf(reBang.getNeedHelpId()), String.valueOf(flag), String.valueOf(waittime));
    }

    private class HelpOffAsyncTask extends AsyncTask<String, Integer, String> {
        public HelpOffAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
//                 "userOfferHelpWaitId":1,
//                "needHelpId":4,
//                "willingToWaitFlag":1,
//                "willingToWaitTime":60
                json.put("userOfferHelpWaitId", params[1]);
                json.put("needHelpId", params[2]);
                json.put("willingToWaitFlag", params[3]);
                json.put("willingToWaitTime", params[4]);
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
                        Toast.makeText(RebangActivity.this, "帮助成功", Toast.LENGTH_LONG).show();
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

    //     发起帮助用户终止整个帮助（帮助完全结束）
    private void quesfh() {
        String url = "http://119.23.226.102/aibangbang/v1/need-help/end";
        System.out.println("@"+String.valueOf(reBang.getNeedHelpId())+"@"+ String.valueOf(usercur.getId())+"@"+String.valueOf(flag)+"@"+String.valueOf(flag)+"@");
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
            System.out.println("@"+s+"@");
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
                    String coderesult = results.getString("code");
                    if ("200".equals(coderesult)) {
                        Toast.makeText(RebangActivity.this, "结束帮助成功了", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RebangActivity.this, "结束帮助失败了！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RebangActivity.this, "结束帮助失败了！", Toast.LENGTH_SHORT).show();
                }
            } else {
                System.out.println("结束帮助失败了");
            }
        }
    }


    //    正在帮助用户结束帮助
    private void oferfh() {
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


//    请求帮助用户结束正在帮助（帮助仍继续）
    private void cancelHelp() {
        String url = "http://119.23.226.102/aibangbang/v1/need-help/end";
        new QuesCancelAsyncTask().execute(url, String.valueOf(reBang.getNeedHelpId()), String.valueOf(usercur.getId()), String.valueOf(endflag), String.valueOf(flag));
    }


    private class QuesCancelAsyncTask extends AsyncTask<String, Integer, String> {
        public QuesCancelAsyncTask() {
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
                        Toast.makeText(RebangActivity.this, "取消当前用户帮助成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RebangActivity.this, "正在等待帮助哦！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RebangActivity.this, "正在等待帮助哦！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RebangActivity.this, "正在等待帮助哦！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
