package com.example.administrator.controller.adapter.zhuyeadapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.model.bean.ReBang;
import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.zhuye.RebangActivity;
import com.example.administrator.model.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ReBangAdapter extends RecyclerView.Adapter<ReBangAdapter.ViewHolder> {
    private List<ReBang> mReBangList;
    private User user = new User();
    private List<User> mUserList = new ArrayList<>();
    private Context context;

    public ReBangAdapter(List<ReBang> reBangList) {
        mReBangList = reBangList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tuijian_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.rebangView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ReBang reBang = mReBangList.get(position);
                Intent intent = new Intent(context, RebangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reBang", reBang);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReBang reBang = mReBangList.get(position);
        for (int i = 0; i < mUserList.size(); i++) {
            if (reBang.getUserNeedHelpId() == mUserList.get(i).getId()) {
                holder.rebanguserName.setText(mUserList.get(i).getNickname());
                reBang.setNickname(mUserList.get(i).getNickname());
            }
        }
        holder.rebangData.setText(reBang.getDetails());
        holder.rebangwaittime.setText(String.valueOf(reBang.getWillingToWaitTime()));
        holder.rebangcreatetime.setText(String.valueOf(reBang.getCreateDateTime()));
        showstates(reBang, holder);
        getUser(reBang.getUserNeedHelpId());
//    holder.rebangImage.setImageResource(reBang.getImageId());
    }

    private void showstates(ReBang tuiJian, ReBangAdapter.ViewHolder holder) {
        if (tuiJian.getStatus() == 1) {
            holder.ly_hw.setVisibility(View.VISIBLE);
            holder.ly_hp.setVisibility(View.GONE);
            holder.ly_fy.setVisibility(View.GONE);
            holder.ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 2) {
            holder.ly_hp.setVisibility(View.VISIBLE);
            holder.ly_hw.setVisibility(View.GONE);
            holder.ly_fy.setVisibility(View.GONE);
            holder.ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 3) {
            holder.ly_fy.setVisibility(View.VISIBLE);
            holder.ly_hw.setVisibility(View.GONE);
            holder.ly_hp.setVisibility(View.GONE);
            holder.ly_fn.setVisibility(View.GONE);
        } else if (tuiJian.getStatus() == 4) {
            holder.ly_fn.setVisibility(View.VISIBLE);
            holder.ly_hw.setVisibility(View.GONE);
            holder.ly_fy.setVisibility(View.GONE);
            holder.ly_hp.setVisibility(View.GONE);
        }

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
        TextView rebangwaittime;
        TextView rebangcreatetime;
        LinearLayout ly_hw;
        LinearLayout ly_hp;
        LinearLayout ly_fn;
        LinearLayout ly_fy;

        public ViewHolder(View view) {
            super(view);
            rebangView = view;
//            rebangImage=(ImageView)view.findViewById(R.id.rebang_image);
            rebanguserName = (TextView) view.findViewById(R.id.tuijian_username);
            rebangData = (TextView) view.findViewById(R.id.tuijian_data_text);
            rebangwaittime = (TextView) view.findViewById(R.id.tuijian_waittime);
            rebangcreatetime = view.findViewById(R.id.tuijian_creattime);
            ly_fn = view.findViewById(R.id.ly_fn);
            ly_fy = view.findViewById(R.id.ly_fy);
            ly_hw = view.findViewById(R.id.ly_hw);
            ly_hp = view.findViewById(R.id.ly_hp);
        }
    }

    public void getUser(int uid) {
        String registUrl = "http://119.23.226.102/aibangbang/v1/users/" + uid;
        System.out.println(registUrl);
        new GetUserAsyncTask().execute(registUrl);
    }

    private class GetUserAsyncTask extends AsyncTask<String, Integer, String> {
        public GetUserAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(params[0])
                        .get()
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
                //判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
//                    User user=new User();
                    user = new User();
                    // 将数据封装为user对象
                    user.setId(results.getInt("id"));
                    user.setPhone(results.getString("phone"));
                    if (results.get("age").toString().equals("null")) {
                        user.setAge(0);
                    } else {
                        user.setAge(results.getInt("age"));
                    }
                    if (results.get("gender").toString().equals("null")) {
                        user.setGender(" ");
                    } else {
                        user.setGender(results.getString("gender"));
                    }
                    if (results.get("nickname").toString().equals("null")) {
                        user.setNickname(" ");
                    } else {
                        user.setNickname(results.getString("nickname"));
                    }
                    mUserList.add(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("获取信息失败");
            }
        }
    }
}
