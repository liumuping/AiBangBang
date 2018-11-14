package com.example.administrator.controller.activity.zhuye;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.model.bean.User;
import com.example.administrator.utils.Regex;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/4/21.
 */

public class CommentOferActivity extends AppCompatActivity {
    private EditText questiondata;
    private TextView fabiao_text;
    private TextView quxiao_text;
    private String data;
    private ReBang reBang;
    private User user;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        reBang = (ReBang) getIntent().getSerializableExtra("reBang");
        user = (User) getIntent().getSerializableExtra("usercur");
        initView();
        Listener();
    }

    private void initView() {
        questiondata = (EditText) findViewById(R.id.tv_question_data);
        fabiao_text = (TextView) findViewById(R.id.fabiao_text);
        quxiao_text=(TextView) findViewById(R.id.quxiao_text);
    }

    private void Listener() {
        quxiao_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        questiondata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //如果输入文本，可以触发点击事件且背景由浅灰色变成黄色
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                    fabiao_text.setBackgroundColor(Color.parseColor("#fffb00"));//浅灰色
                    initListener();
                }
                else {
                    fabiao_text.setBackgroundColor(Color.parseColor("#b9b9b9"));//黄色
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }


    private void initListener() {
        fabiao_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data=questiondata.getText().toString();
                comment();
            }
        });
    }



    private void comment() {
        String url = "http://119.23.226.102/aibangbang/v1/help-histories/comment";
        new CommentAsyncTask().execute(url, String.valueOf(user.getId()),String.valueOf(reBang.getNeedHelpId()), data);
        System.out.println("----------------------"+user.getId());
        System.out.println("----------------------"+reBang.getNeedHelpId());
        System.out.println("----------------------"+data);
    }

    private class CommentAsyncTask extends AsyncTask<String, Integer, String> {
        public CommentAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("userOfferHelpHistoryId", params[1]);
                json.put("needHelpId", params[2]);
                json.put("userComment", params[3]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .put(requestBody)
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
                System.out.println(s);
                try {
                    JSONObject results = new JSONObject(s);
                    String questionresult = results.getString("code");
                    if ("200".equals(questionresult)) {
                        finish();
                    } else {
                        Toast.makeText(CommentOferActivity.this,"发起评论失败了哦，请等会再试吧",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("发起评论失败2");
            }
        }
    }
}
