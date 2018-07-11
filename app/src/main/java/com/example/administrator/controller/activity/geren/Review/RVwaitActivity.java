package com.example.administrator.controller.activity.geren.Review;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.controller.R;

public class RVwaitActivity extends AppCompatActivity {
    private ImageView rv_im_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        rv_im_back=(ImageView)findViewById(R.id.rv_im_back);
        rv_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
