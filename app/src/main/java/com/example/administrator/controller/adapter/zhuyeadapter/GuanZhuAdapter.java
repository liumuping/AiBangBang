package com.example.administrator.controller.adapter.zhuyeadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.model.bean.GuanZhu;
import com.example.administrator.controller.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class GuanZhuAdapter extends RecyclerView.Adapter<GuanZhuAdapter.ViewHolder> {
   private List<GuanZhu>mGuanZhuList;
   private Context context;
   public  GuanZhuAdapter(List<GuanZhu> guanZhuList){
       mGuanZhuList=guanZhuList;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       context=parent.getContext();
       View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.guanzhu_item,parent,false);
       final   ViewHolder holder=new ViewHolder(view);
       holder.ly_gz.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               int position=holder.getAdapterPosition();
               removeItem(position);
               return false;
           }
       });
        return holder;
    }
    public void removeItem(int pos){
        this.mGuanZhuList.remove(pos);
        notifyItemRemoved(pos);
        if(pos != mGuanZhuList.size()){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(pos, mGuanZhuList.size() - pos);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    GuanZhu guanZhu=mGuanZhuList.get(position);
    holder.guanzhuName.setText(guanZhu.getName());
    holder.guanzhuImage.setImageResource(guanZhu.getImageId());
    }

    @Override
    public int getItemCount() {
        return mGuanZhuList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView guanzhuImage;
        View guanzhuView;
        TextView guanzhuName;
        LinearLayout ly_gz;
        public ViewHolder(View view){
            super(view);
            guanzhuView=view;
            ly_gz=(LinearLayout)view.findViewById(R.id.ly_gz);
            guanzhuImage=(ImageView)view.findViewById(R.id.guanzhu_image);
            guanzhuName=(TextView)view.findViewById(R.id.guanzhu_name);
        }
    }
}
