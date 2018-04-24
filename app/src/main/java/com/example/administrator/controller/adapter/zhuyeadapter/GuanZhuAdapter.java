package com.example.administrator.controller.adapter.zhuyeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.model.bean.GuanZhu;
import com.example.administrator.controller.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class GuanZhuAdapter extends RecyclerView.Adapter<GuanZhuAdapter.ViewHolder> {
   private List<GuanZhu>mGuanZhuList;
   public  GuanZhuAdapter(List<GuanZhu> guanZhuList){
       mGuanZhuList=guanZhuList;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.guanzhu_item,parent,false);
       ViewHolder holder=new ViewHolder(view);
        return holder;
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
        TextView guanzhuName;
        public ViewHolder(View view){
            super(view);
            guanzhuImage=(ImageView)view.findViewById(R.id.guanzhu_image);
            guanzhuName=(TextView)view.findViewById(R.id.guanzhu_name);
        }
    }
}
