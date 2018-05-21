package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;

/**
 * Created by Administrator on 2018/4/16.
 */

public class RebangActivity extends AppCompatActivity{
public static final String REBANG_DATA="rebang_data";
private ImageButton im_rb_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebang);
        Intent intent=getIntent();
        im_rb_btn=(ImageButton)findViewById(R.id.im_rb_btn);
        String rebang_data=intent.getStringExtra(REBANG_DATA);
        TextView tv_rebang_data=(TextView)findViewById(R.id.tv_rebang_data);
        tv_rebang_data.setText(rebang_data);
        Listenner();

    }

    private void Listenner() {
        im_rb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RebangActivity.this, ChatMessageActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
