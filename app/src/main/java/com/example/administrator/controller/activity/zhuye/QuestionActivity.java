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

public class QuestionActivity extends AppCompatActivity {
    private EditText questiondata;
    private TextView fabiao_text;
    private TextView quxiao_text;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
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
                qusetion();
            }
        });
    }

    private void qusetion() {
        String data = questiondata.getText().toString();//数据
        if (!"".equals(data)) {
//            Qusetion(questiondata.getText().toString(), MainActivity.user.getUserid());
        } else {
            Toast.makeText(QuestionActivity.this, "数据不能为空",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void Qusetion(String data, int userid) {
        String questionUrl = "http://10.0.2.2:8080/QuestionServlet";

        //  String questionUrl = "http://192.168.1.106:8080/QuestionServlet";
        new QuestionAsyncTask().execute(questionUrl, data, String.valueOf(userid));
    }

    private class QuestionAsyncTask extends AsyncTask<String, Integer, String> {
        public QuestionAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("data", params[1]);
                json.put("userid", params[2]);
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
           // System.out.println("=======================>" + s);
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
                    int questionresult = results.getInt("result");
                    //System.out.println("=======================>" + questionresult);
                    if (questionresult != 0) {
                        finish();
                    } else {
//                        Toast.makeText(QuestionActivity.this,
//                                "=======================>" + "输入有误",
//                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
//                System.out.println("结果为空");
//                Toast.makeText(QuestionActivity.this,
//                        "=======================>" + "输入有误",
//                        Toast.LENGTH_LONG)
//                        .show();

            }
        }
    }
}
