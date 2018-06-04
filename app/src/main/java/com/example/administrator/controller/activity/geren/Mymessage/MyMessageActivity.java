package com.example.administrator.controller.activity.geren.Mymessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.controller.R;

public class MyMessageActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView updata_my_message;
    private LinearLayout ly_headpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initview();
        initdata();
    }



    private void initview() {
        imageView=(ImageView)findViewById(R.id.mymessage_im_back);
        updata_my_message=(TextView)findViewById(R.id.updata_my_message);
        ly_headpicture=(LinearLayout)findViewById(R.id.ly_headpicture);
    }

    private void initdata() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updata_my_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyMessageActivity.this,UpdataMyMessageActivity.class);
                startActivity(intent);
            }
        });
        ly_headpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pintent =new Intent(MyMessageActivity.this,UpdataPhoto.class);
                startActivity(pintent);
            }
        });
    }
}
