package com.example.administrator.controller.activity.zhuye;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.controller.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
private ImageView icon_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        icon_back=(ImageView)findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_back:
                finish();
                break;
        }
    }
}
