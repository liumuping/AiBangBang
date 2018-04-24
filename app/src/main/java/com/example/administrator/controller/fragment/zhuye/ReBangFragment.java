package com.example.administrator.controller.fragment.zhuye;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.zhuyeadapter.ReBangAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ReBangFragment extends BaseFragment {
    private List<ReBang> reBangList = new ArrayList<>();

    @Override
    protected View initView() {
        View view = View.inflate(mcontext, R.layout.rebang, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rb_recycle_view);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ReBangAdapter adapter = new ReBangAdapter(reBangList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 70; i++) {
            ReBang yundong = new ReBang("风雨不堪", R.drawable.boy,getRandomLengthName("请问你可以帮我吗"));
            reBangList.add(yundong);
            super.initData();

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