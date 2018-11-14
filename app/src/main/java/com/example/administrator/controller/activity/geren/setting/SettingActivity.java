package com.example.administrator.controller.activity.geren.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.ResetRegistActivity;

/**
 * Created by Administrator on 2018/4/22.
 */

public class SettingActivity extends AppCompatActivity {
    private ImageView setting_im_back;
    private LinearLayout ly_setting_location;
    private LinearLayout ly_setting_updatapassword;
    private LinearLayout ly_setting_myguanzhu;
    private LinearLayout ly_setting_feedback;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initview();
        initListen();



    }
    private void initview() {
        setting_im_back=(ImageView) findViewById(R.id.setting_im_back);
        ly_setting_location=(LinearLayout)findViewById(R.id.ly_setting_location);
        ly_setting_updatapassword=(LinearLayout)findViewById(R.id.ly_setting_updatapassword);
        ly_setting_myguanzhu=(LinearLayout)findViewById(R.id.ly_setting_guanzhu);
        ly_setting_feedback=findViewById(R.id.ly_setting_feedback);
    }
    private void initListen() {
        setting_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ly_setting_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,LocationActivity.class);
                startActivity(intent);
            }
        });
        ly_setting_updatapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,UpdataPassawordActivity.class);
                startActivity(intent);
            }
        });
        ly_setting_myguanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,MyGZActivity.class);
                startActivity(intent);
            }
        });
        ly_setting_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,FeedBackActivity.class);
                startActivity(intent);
            }
        });
    }


}
