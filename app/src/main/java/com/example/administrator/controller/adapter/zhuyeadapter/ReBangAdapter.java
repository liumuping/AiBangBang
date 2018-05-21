package com.example.administrator.controller.adapter.zhuyeadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.zhuye.RebangActivity;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ReBangAdapter extends RecyclerView.Adapter<ReBangAdapter.ViewHolder> {
   private List<ReBang>mReBangList;
   private Context context;
   public ReBangAdapter(List<ReBang> reBangList){
       mReBangList=reBangList;
   }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       context=parent.getContext();
       View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.rebang_item,parent,false);
      final ViewHolder holder=new ViewHolder(view);
       holder.rebangView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position=holder.getAdapterPosition();
               ReBang reBang=mReBangList.get(position);
               Intent intent=new Intent(context,RebangActivity.class);
               intent.putExtra(RebangActivity.REBANG_DATA,reBang.getData());
               context.startActivity(intent);
           }
       });
        return holder;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReBang reBang=mReBangList.get(position);
    holder.rebanguserName.setText(reBang.getName());
    holder.rebangData.setText(reBang.getData());
    holder.rebangImage.setImageResource(reBang.getImageId());
    }

    @Override
    public int getItemCount() {
        return mReBangList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View rebangView;
        ImageView rebangImage;
        TextView rebanguserName;
        TextView rebangData;
        public ViewHolder(View view){
            super(view);
            rebangView=view;
            rebangImage=(ImageView)view.findViewById(R.id.rebang_image);
            rebanguserName=(TextView)view.findViewById(R.id.rebang_username);
            rebangData=(TextView)view.findViewById(R.id.rebang_data_text);
        }
    }
}
