package com.example.administrator.controller.adapter.gerenadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.geren.finish.FHacceptActivity;
import com.example.administrator.controller.activity.geren.waitHelp.WHacceptActivity;
import com.example.administrator.model.bean.NeedHelp;
import com.example.administrator.model.bean.ReBang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class FinishAcceptAdapter extends RecyclerView.Adapter<FinishAcceptAdapter.ViewHolder> {
    private List<ReBang> mList;
    private List<ReBang> finishList=new ArrayList<>();
    private Context context;


    public FinishAcceptAdapter(List<ReBang> List) {
        mList = finishList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.finish_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
            holder.finishview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    ReBang needHelp = finishList.get(position);
                    Intent intent = new Intent(context, FHacceptActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reBang", needHelp);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return finishList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View finishview;
        TextView tv_data;
        TextView tv_cre;
        TextView tv_nick;
        TextView tv_wtime;

        public ViewHolder(View view) {
            super(view);
            finishview = view;
            tv_data = view.findViewById(R.id.tv_datebase_data);
            tv_cre = view.findViewById(R.id.dateshowbase_cre);
            tv_nick = view.findViewById(R.id.dateshowbase_username);
            tv_wtime = view.findViewById(R.id.dateshowbase_waittime);
        }
    }
}
