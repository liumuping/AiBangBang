package com.example.administrator.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.zhuye.RebangActivity;
import com.example.administrator.controller.interfc.InputStringtime;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.utils.Animal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MydialogFragment extends DialogFragment {
    View mView;
    private Button submit;
    private EditText th;
    private EditText tm;
    private ReBang reBang;
    private InputStringtime minputStringtime;
    private String infer="";
    private int flag=1;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public MydialogFragment() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.layout,container,false);
        submit =(Button)mView.findViewById(R.id.submit);
        th = mView.findViewById(R.id.time_hours);
        tm = mView.findViewById(R.id.time_minutes);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            reBang = (ReBang) bundle.getSerializable("reBang");
            System.out.println(reBang.getNeedHelpId());
        }
        System.out.println("---------------------");
        initlistenner();
        return mView;

    }

    private void initlistenner() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posthttpoffer();
                minputStringtime.inputInforCompleted(infer);
                dismiss();
            }
        });
    }
    private String getwaittime(){
        int  s =Integer.valueOf(th.getText().toString())*60+Integer.valueOf(tm.getText().toString());
        return ""+s;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        //设置dialog相应属性
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //必须设定的属性，否则无法使dialog铺满屏幕，设置其他颜色会出现黑边
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Animal.slideToUp(mView);
    }

    public void setInputStringtime(InputStringtime inputStringtime) {
        minputStringtime = inputStringtime;
    }

    public void posthttpoffer() {
        String loginUrl = "http://119.23.226.102/aibangbang/v1/need-help/offer";
        new HelpOffAsyncTask().execute(loginUrl, MainActivity.uid, String.valueOf(reBang.getNeedHelpId()),String.valueOf(flag),getwaittime());
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

