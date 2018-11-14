package com.example.administrator.view;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.zhuye.QuestionActivity;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.utils.Regex;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class Fragmentcom extends BaseFragment {
    private TextView fabiao;
    private TextView quxiao;
    private EditText comment;
    private ReBang reBang;
    private String infer="";
    private int flag=1;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Nullable


    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.dialogfgcom,null);
        fabiao =view.findViewById(R.id.fabiao_text);
        comment=view.findViewById(R.id.tv_comment_data);
        quxiao=view.findViewById(R.id.quxiao_text);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle!=null) {
            reBang = (ReBang) bundle.getSerializable("reBang");
            System.out.println(reBang.getNeedHelpId());
        }
        initListener();
    }

    private void initListener() {
        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posthttpcom();

            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //如果输入文本，可以触发点击事件且背景由浅灰色变成黄色
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                    fabiao.setBackgroundColor(Color.parseColor("#fffb00"));//浅灰色
                    initListener();
                }
                else {
                    fabiao.setBackgroundColor(Color.parseColor("#b9b9b9"));//黄色
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }
    private void Listener() {

    }


    private void initLis() {
        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




    public void posthttpcom() {
        String loginUrl = "http://119.23.226.102/aibangbang/v1/need-help/offer";
        new HelpOffAsyncTask().execute(loginUrl, MainActivity.uid, String.valueOf(reBang.getNeedHelpId()),String.valueOf(flag));
    }

    private class HelpOffAsyncTask extends AsyncTask<String, Integer, String> {
        public HelpOffAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
//                 "userOfferHelpWaitId":1,
//                "needHelpId":4,
//                "willingToWaitFlag":1,
//                "willingToWaitTime":60
                json.put("userOfferHelpWaitId",params[1]);
                json.put("needHelpId",params[2]);
                json.put("willingToWaitFlag",params[3]);
                json.put("willingToWaitTime",params[4]);
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
            System.out.println(s);
            if (s != null){
                try {
                    JSONObject results = new JSONObject(s);
                    String coderesult = results.getString("code");
                    String data = results.getString("data");
                    JSONObject infoResult = new JSONObject(data);
                    infer = infoResult.getString("info");
                    System.out.println(infer);
                    if("200".equals(coderesult)){
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
            }
        }
    }


}

