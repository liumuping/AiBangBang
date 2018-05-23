package com.example.administrator.controller.activity.geren.Review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.FinishAdapter;
import com.example.administrator.controller.adapter.gerenadapter.ReviewAdapter;
import com.example.administrator.model.bean.Finish;
import com.example.administrator.model.bean.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity {
    private RecyclerView review_recyclerView;
    private List<Review>reviewList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initData();
        review_recyclerView=(RecyclerView)findViewById(R.id.review_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        review_recyclerView.setLayoutManager(layoutManager);
        ReviewAdapter adapter = new ReviewAdapter(reviewList);
        review_recyclerView.setAdapter(adapter);

    }


    private void initData() {
        for (int i = 0; i < 2; i++) {
            Review yundong = new Review("风清云净",R.drawable.boy,"2018-05-18",
                    getRandomLengthName("谢谢你的帮忙"),getRandomLengthName("如果可以的话，请帮帮我"));
            reviewList.add(yundong);


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

