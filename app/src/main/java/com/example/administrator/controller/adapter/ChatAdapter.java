package com.example.administrator.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.controller.activity.zhuye.RebangActivity;
import com.example.administrator.model.bean.Chat;
import com.example.administrator.controller.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
   private List<Chat>mChatList;
    private Context context;
   public ChatAdapter(List<Chat> chatList){
       mChatList=chatList;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
         View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.chat_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
            holder.chatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getAdapterPosition();
                    Chat chat=mChatList.get(position);
                    Intent intent=new Intent(context,ChatMessageActivity.class);
                    context.startActivity(intent);
                }
            });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat=mChatList.get(position);
    holder.chatuserName.setText(chat.getName());
    holder.chatData.setText(chat.getData());
    holder.chatImage.setImageResource(chat.getImageId());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View chatView;
        ImageView chatImage;
        TextView chatuserName;
        TextView chatData;
        public ViewHolder(View view){
            super(view);
            chatView=view;
            chatImage=(ImageView)view.findViewById(R.id.chat_image);
            chatuserName=(TextView)view.findViewById(R.id.chat_name);
            chatData=(TextView)view.findViewById(R.id.chat_data);
        }
    }
}
