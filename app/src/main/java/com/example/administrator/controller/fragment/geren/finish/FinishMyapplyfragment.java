package com.example.administrator.controller.fragment.geren.finish;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.gerenadapter.FinishAdapter;
import com.example.administrator.model.bean.Finish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FinishMyapplyfragment extends BaseFragment {
    private RecyclerView recyclerview;
    private List<Finish> finishList=new ArrayList<>();
    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.baserecycle,null);
        recyclerview=(RecyclerView)view.findViewById(R.id.baserecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        FinishAdapter adapter = new FinishAdapter(finishList,2);
        recyclerview.setAdapter(adapter);
        return view;
    }
    protected void initData() {
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
