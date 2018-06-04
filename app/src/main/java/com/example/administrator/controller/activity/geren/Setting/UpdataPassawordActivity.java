package com.example.administrator.controller.activity.geren.Setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.controller.R;

public class UpdataPassawordActivity extends AppCompatActivity {
    private ImageView udps_im_back;
    private EditText udps_new;
    private EditText udps_cofirm;
    private Button btn_udps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_passaword);
        initview();
        initdata();
    }

    private void initview() {
        udps_im_back=(ImageView)findViewById(R.id.udps_im_back);
        udps_new=(EditText)findViewById(R.id.udps_new);
        udps_cofirm=(EditText)findViewById(R.id.udps_cofirm);
        btn_udps=(Button)findViewById(R.id.btn_udps);
    }

    private void initdata() {
        listenner();
    }

    private void listenner() {
        udps_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_udps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
