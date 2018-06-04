package com.example.administrator.controller.fragment.geren.review;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.FinishAdapter;
import com.example.administrator.controller.adapter.gerenadapter.ReviewAdapter;
import com.example.administrator.model.bean.Finish;
import com.example.administrator.model.bean.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RVhavefragment extends BaseFragment {
    private RecyclerView recyclerview;
    private List<Review> reviewList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.baserecycle,null);
        recyclerview=(RecyclerView)view.findViewById(R.id.baserecycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        ReviewAdapter adapter = new ReviewAdapter(reviewList,1);
        recyclerview.setAdapter(adapter);
        return view;
    }
    protected void initData() {
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
