package com.example.administrator.controller.adapter.gerenadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.MainActivity;
import com.example.administrator.controller.activity.geren.waitHelp.WHacceptActivity;
import com.example.administrator.model.bean.ReBang;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class WaitAcceptAdapter extends RecyclerView.Adapter<WaitAcceptAdapter.ViewHolder> {
    private List<ReBang> mwaitList;
    private Context context;


    public WaitAcceptAdapter(List<ReBang> waitList) {
        mwaitList = waitList;
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
                ReBang needHelp = mwaitList.get(position);
                Intent intent = new Intent(context, WHacceptActivity.class);
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
        ReBang needHelp = mwaitList.get(position);
        holder.tv_nick.setText(MainActivity.user.getNickname());
        holder.tv_data.setText(needHelp.getDetails());
        holder.tv_wtime.setText(String.valueOf(needHelp.getWillingToWaitTime()));
        holder.tv_cre.setText(String.valueOf(needHelp.getCreateDateTime()));

    }

    @Override
    public int getItemCount() {
        return mwaitList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View finishview;
        TextView tv_data;
        TextView tv_cre;
        TextView tv_nick;
        TextView tv_wtime;
        LinearLayout ly_hw;

        public ViewHolder(View view) {
            super(view);
            finishview = view;
            tv_data = view.findViewById(R.id.tv_datebase_data);
            tv_cre = view.findViewById(R.id.dateshowbase_cre);
            tv_nick = view.findViewById(R.id.dateshowbase_username);
            tv_wtime = view.findViewById(R.id.dateshowbase_waittime);
            ly_hw = view.findViewById(R.id.ly_hw);
            ly_hw.setVisibility(View.VISIBLE);
        }
    }
}
