package com.example.administrator.controller.activity.geren.mymessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.utils.GetUserMessage;

public class MyMessageActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView  my_message_age;
    private TextView  my_message_phone;
    private TextView  my_message_nickname;
    private TextView  my_message_gender;
    private Button updata_my_message;
    private LinearLayout ly_headpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initview();
        initdata();
    }



    private void initview() {
        my_message_age = findViewById(R.id.my_message_age);
        my_message_nickname = findViewById(R.id.my_message_nickname);
        my_message_gender = findViewById(R.id.my_message_gender);
//        my_message_phone = findViewById(R.id.my_message_phone);
        imageView=(ImageView)findViewById(R.id.mymessage_im_back);
        updata_my_message=(Button) findViewById(R.id.updata_my_message);
        ly_headpicture=(LinearLayout)findViewById(R.id.ly_headpicture);
        GetUserMessage get = new GetUserMessage();
        get.getUser();
        my_message_age.setText(String.valueOf(MainActivity.user.getAge()));
        my_message_nickname.setText(MainActivity.user.getNickname());
        my_message_gender.setText(MainActivity.user.getGender());
//        my_message_phone.setText(MainActivity.user.getPhone());
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
                finish();
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
