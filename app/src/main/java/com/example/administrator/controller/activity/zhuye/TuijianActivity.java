package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.model.bean.NeedHelp;
import com.example.administrator.model.bean.User;

public class TuijianActivity extends AppCompatActivity {
    private Button dateshowbase_btn;
    private NeedHelp tuiJian;
    private User user;
    private TextView tv_tuijian_data;
    private TextView tv_tuijian_cre;
    private TextView tv_tuijian_name;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datashowbase);
        initview();
        initdata();

    }

    private void initdata() {
        tuiJian=(NeedHelp) getIntent().getSerializableExtra("tuiJian");
        user=(User) getIntent().getSerializableExtra("user");
        tv_tuijian_data.setText(tuiJian.getDetails());
        tv_tuijian_cre.setText(tuiJian.getCreateDateTime());
        tv_tuijian_name.setText(user.getNickname());
        Listenner();
    }

    private void initview() {
        dateshowbase_btn=(Button)findViewById(R.id.chat_btn);
        tv_tuijian_data=(TextView)findViewById(R.id.tv_datebase_data);
        tv_tuijian_cre=(TextView)findViewById(R.id.dateshowbase_cre);
        tv_tuijian_name=findViewById(R.id.dateshowbase_username);
        back=(ImageView)findViewById(R.id.dateshowbase_im_back);
    }

    private void Listenner() {
        dateshowbase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(TuijianActivity.this, ChatMessageActivity.class);
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
