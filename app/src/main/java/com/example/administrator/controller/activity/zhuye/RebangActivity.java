package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.model.bean.ReBang;

/**
 * Created by Administrator on 2018/4/16.
 */

public class RebangActivity extends AppCompatActivity{
private ImageButton im_rb_btn;
private ReBang reBang;
private TextView tv_rebang_data;
private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebang);
        initview();
        initdata();
    }

    private void initview() {
        tv_rebang_data=(TextView)findViewById(R.id.tv_rebang_data);
        im_rb_btn=(ImageButton)findViewById(R.id.im_rb_btn);
        back=(ImageView)findViewById(R.id.rebang_im_back);
    }

    private void initdata() {
        reBang = (ReBang) getIntent().getSerializableExtra("reBang");

        String rebang_data=reBang.getData();
        tv_rebang_data.setText(rebang_data);
        Listenner();
    }



    private void Listenner() {
        im_rb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RebangActivity.this, ChatMessageActivity.class);
                startActivity(intent1);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
