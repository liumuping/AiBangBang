package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.controller.R;

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
    private EditText questiontitle;
    private EditText questiondata;
    private Button  qusetion_btn;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
        initListener();

    }

    private void initView() {
        questiontitle=(EditText) findViewById(R.id.tv_question_title);
        questiondata=(EditText)findViewById(R.id.tv_question_data);
        qusetion_btn=(Button)findViewById(R.id.question_btn);
    }
    private void initListener(){
        qusetion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qusetion();
            }
        });
    }

    private void qusetion() {
        String title = questiontitle.getText().toString();//标题
        String data = questiondata.getText().toString();//数据
        if (!"".equals(title) && !"".equals(data))
        {
            Qusetion(questiontitle.getText().toString(), questiondata.getText().toString());
        } else {
            Toast.makeText(QuestionActivity.this, "标题或者数据不能为空",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void Qusetion(String title, String data) {
        String questionUrl = "http://10.0.2.2:8080/QuestionServlet";
        new QuestionAsyncTask().execute(questionUrl, title, data);
    }

    private class QuestionAsyncTask extends AsyncTask<String, Integer, String> {
        public QuestionAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("title",params[1]);
                json.put("data",params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response=okHttpClient.newCall(request).execute();
                results=response.body().string();
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
            System.out.println("=======================>"+s);
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    int questionresult = results.getInt("result");
                    System.out.println("=======================>"+questionresult);
                    if(questionresult!=0){
                        Toast.makeText(QuestionActivity.this,
                                "=======================>"+"请等候",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(QuestionActivity.this,
                                RebangActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(QuestionActivity.this,
                                "=======================>"+"输入有误",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(QuestionActivity.this,
                        "=======================>"+"输入有误",
                        Toast.LENGTH_LONG)
                        .show();

            }
        }
    }
}
