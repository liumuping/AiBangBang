package com.example.administrator.controller.fragment.zhuye;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.controller.adapter.zhuyeadapter.ReBangAdapter;
import com.example.administrator.model.bean.TuiJian;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.zhuyeadapter.TuiJianAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/10.
 */

public class TuiJianFragment extends BaseFragment {
    private List<TuiJian> tuiJianList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private TuiJianAdapter adapter;
    private SwipeRefreshLayout tj_swipe_refresh;
    @Override
    protected View initView() {
        View view = View.inflate(mcontext, R.layout.tuijian, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.tj_recycle_view);
        layoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new TuiJianAdapter(tuiJianList);
        recyclerView.setAdapter(adapter);
        tj_swipe_refresh=(SwipeRefreshLayout)view.findViewById(R.id.tj_swipe_refresh);
        return view;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 70; i++) {
            TuiJian yundong = new TuiJian("晴空万里", R.drawable.boy,getRandomLengthName("请问你可以帮我吗"));
            tuiJianList.add(yundong);
            super.initData();
            Listenner();

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
    private void Listenner() {
        tj_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        adapter.notifyDataSetChanged();
                        tj_swipe_refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}