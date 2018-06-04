package com.example.administrator.controller.activity.geren.Mymessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.controller.R;

public class UpdataMyMessageActivity extends AppCompatActivity {
private ImageView updata_message_im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_my_message);
        initview();
        initdata();
    }

    private void initview() {
        updata_message_im_back=(ImageView)findViewById(R.id.updata_message_im_back);

    }

    private void initdata() {
        updata_message_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
