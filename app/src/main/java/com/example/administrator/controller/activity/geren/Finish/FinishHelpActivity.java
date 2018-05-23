package com.example.administrator.controller.activity.geren.Finish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.FinishAdapter;
import com.example.administrator.model.bean.Finish;
import com.example.administrator.model.bean.TuiJian;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FinishHelpActivity extends AppCompatActivity {
    private RecyclerView finish_recycleview;
    private List<Finish>finishList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_help);
        initData();
        finish_recycleview=(RecyclerView)findViewById(R.id.finish_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        finish_recycleview.setLayoutManager(layoutManager);
        FinishAdapter adapter = new FinishAdapter(finishList);
        finish_recycleview.setAdapter(adapter);


    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Finish yundong = new Finish("你好",R.drawable.boy,"2018-05-18",getRandomLengthName("如果可以的话，请帮帮我"));
            finishList.add(yundong);


        }
    }
    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(10)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(name);
        }
        return builder.toString();
    }
}
