package com.example.administrator.controller.activity.zhuye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.model.bean.TuiJian;

public class TuijianActivity extends AppCompatActivity {
    private Button dateshowbase_btn;
    private TuiJian tuiJian;
    private TextView tv_tuijian_data;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datashowbase);
        initview();
        initdata();

    }

    private void initdata() {
        tuiJian= (TuiJian) getIntent().getSerializableExtra("tuiJian");
        String tuijian_data=tuiJian.getData();
        tv_tuijian_data.setText(tuijian_data);
        Listenner();
    }

    private void initview() {
        dateshowbase_btn=(Button)findViewById(R.id.dateshowbase_btn);
        tv_tuijian_data=(TextView)findViewById(R.id.tv_datebase_data);
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
