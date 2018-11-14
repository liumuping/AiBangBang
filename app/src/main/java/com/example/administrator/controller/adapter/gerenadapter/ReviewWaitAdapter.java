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
import com.example.administrator.controller.activity.geren.finish.FHapplyActivity;
import com.example.administrator.controller.activity.geren.review.RVwaitActivity;
import com.example.administrator.model.bean.ReBang;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ReviewWaitAdapter extends RecyclerView.Adapter<ReviewWaitAdapter.ViewHolder> {
    private List<ReBang> mfinishList;
    private Context context;



    public ReviewWaitAdapter(List<ReBang> finishList) {
        mfinishList = finishList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.review_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
            holder.finishview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    ReBang needHelp = mfinishList.get(position);
                    Intent intent = new Intent(context, RVwaitActivity.class);
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
        ReBang needHelp = mfinishList.get(position);
        holder.tv_nick.setText(MainActivity.user.getNickname());
        holder.tv_data.setText(needHelp.getDetails());
        holder.tv_wtime.setText(String.valueOf(needHelp.getEndDateTime()));
        holder.tv_cre.setText(String.valueOf(needHelp.getCreateDateTime()));
        holder.tv_rev_data.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mfinishList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View finishview;
        TextView tv_data;
        TextView tv_cre;
        TextView tv_nick;
        TextView tv_wtime;
        TextView tv_rev_data;
        LinearLayout ly_review;



        public ViewHolder(View view) {
            super(view);
            finishview = view;
            tv_data = view.findViewById(R.id.tv_con_data);
            tv_cre = view.findViewById(R.id.rev_cretime);
            tv_nick = view.findViewById(R.id.rev_nickname);
            tv_wtime = view.findViewById(R.id.review_time);
            tv_rev_data=view.findViewById(R.id.tv_rev_data);
            ly_review=view.findViewById(R.id.ly_review);
        }
    }
}
