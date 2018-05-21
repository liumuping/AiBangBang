package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;

public class TuijianActivity extends AppCompatActivity {
    public static final String TUIJIAN_DATA="tuijian_data";
    private ImageButton im_tj_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian);
        Intent intent=getIntent();
        im_tj_btn=(ImageButton)findViewById(R.id.im_tj_btn);
        String tuijian_data=intent.getStringExtra(TUIJIAN_DATA);
        TextView tv_tuijian_data=(TextView)findViewById(R.id.tv_tuijian_data);
        tv_tuijian_data.setText(tuijian_data);
        Listenner();
    }
    private void Listenner() {
        im_tj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(TuijianActivity.this, ChatMessageActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
