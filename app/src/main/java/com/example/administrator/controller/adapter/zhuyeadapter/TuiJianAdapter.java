package com.example.administrator.controller.adapter.zhuyeadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.activity.zhuye.RebangActivity;
import com.example.administrator.controller.activity.zhuye.TuijianActivity;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.model.bean.TuiJian;
import com.example.administrator.controller.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.ViewHolder> {
   private List<TuiJian>mTuiJianList;
    private Context context;
   public TuiJianAdapter(List<TuiJian> tuiJianList){
       mTuiJianList=tuiJianList;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
       View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.tuijian_item,parent,false);
        final  ViewHolder holder=new ViewHolder(view);
        holder.tuijianView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                TuiJian tuiJian=mTuiJianList.get(position);
                Intent intent=new Intent(context,TuijianActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }



    @Override
    public int getItemCount() {
        return mTuiJianList.size();
    }

    @Override
    public void onBindViewHolder(TuiJianAdapter.ViewHolder holder, int position) {
        TuiJian tuiJian=mTuiJianList.get(position);
        holder.tuijianuserName.setText(tuiJian.getName());
        holder.tuijianData.setText(tuiJian.getData());
        holder.tuijianImage.setImageResource(tuiJian.getImageId());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View tuijianView;
        ImageView tuijianImage;
        TextView tuijianuserName;
        TextView tuijianData;
        public ViewHolder(View view){
            super(view);
            tuijianView=view;
            tuijianImage=(ImageView)view.findViewById(R.id.tuijian_image);
            tuijianuserName=(TextView)view.findViewById(R.id.tuijian_username);
            tuijianData=(TextView)view.findViewById(R.id.tuijian_data_text);
        }
    }
}
