package com.example.administrator.controller.fragment.zhuye;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.model.bean.GuanZhu;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.zhuyeadapter.GuanZhuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class GuanZhuFragment extends BaseFragment {
    private List<GuanZhu> guanZhuList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected View initView() {
        View view=View.inflate(mcontext, R.layout.guanzhu,null);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.gz_recycle_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager
               (1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        GuanZhuAdapter adapter=new GuanZhuAdapter(guanZhuList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initData() {
        for (int i=0;i<2;i++){
            GuanZhu yundong=new GuanZhu( "运动",R.drawable.circle4);
            guanZhuList.add(yundong);
            GuanZhu chuxing=new GuanZhu( "出行",R.drawable.circle1);
            guanZhuList.add(chuxing);
            GuanZhu youxi=new GuanZhu( "游戏",R.drawable.circle2);
            guanZhuList.add(youxi);
            GuanZhu meisi=new GuanZhu( "美食",R.drawable.circle3);
            guanZhuList.add(meisi);
        super.initData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

}


}
