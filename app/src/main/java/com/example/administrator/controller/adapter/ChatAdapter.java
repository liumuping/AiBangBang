package com.example.administrator.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.model.bean.Chat;
import com.example.administrator.controller.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
   private List<Chat>mChatList;
   public ChatAdapter(List<Chat> chatList){
       mChatList=chatList;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.chat_item,parent,false);
       ViewHolder holder=new ViewHolder(view);
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
        ImageView chatImage;
        TextView chatuserName;
        TextView chatData;
        public ViewHolder(View view){
            super(view);
            chatImage=(ImageView)view.findViewById(R.id.chat_image);
            chatuserName=(TextView)view.findViewById(R.id.chat_name);
            chatData=(TextView)view.findViewById(R.id.chat_data);
        }
    }
}
