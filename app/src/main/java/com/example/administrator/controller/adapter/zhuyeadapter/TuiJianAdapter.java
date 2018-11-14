package com.example.administrator.controller.adapter.zhuyeadapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.controller.activity.zhuye.TuijianActivity;
import com.example.administrator.controller.R;
import com.example.administrator.model.bean.NeedHelp;
import com.example.administrator.model.bean.User;
import com.example.administrator.utils.GetUser;
import com.example.administrator.utils.GetUserMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.ViewHolder> {
    private List<NeedHelp> mTuiJianList;
    private User user = new User();
    private List<User> mUserList = new ArrayList<>();
    //   private HashMap<Integer,User> mUserHashMap=new HashMap<>();
    private Context context;

    //   public TuiJianAdapter(List<NeedHelp> tuiJianList, HashMap<String,User> userHashMap){
    public TuiJianAdapter(List<NeedHelp> tuiJianList) {
        mTuiJianList = tuiJianList;
        //       mUserHashMap=userHashMap;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tuijian_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.tuijianView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                NeedHelp tuiJian = mTuiJianList.get(position);
                Intent intent = new Intent(context, TuijianActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tuiJian", tuiJian);
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
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
        NeedHelp tuiJian = mTuiJianList.get(position);
        System.out.println("============================"+mTuiJianList.get(position));
//        User user=mUserList.get(position);
        User newuser;
        for (int i = 0; i < mUserList.size(); i++) {
            if (tuiJian.getUserNeedHelpId() == mUserList.get(i).getId()) {
                holder.tuijianuserName.setText(mUserList.get(i).getNickname());
                System.out.println("============================"+mUserList.get(i).getNickname());
                newuser = mUserList.get(i);
            }
        }
        holder.tuijianData.setText(tuiJian.getDetails());
        holder.tuijianwaittime.setText(String.valueOf(tuiJian.getWillingToWaitTime()));
        holder.tuijiancreatetime.setText(String.valueOf(tuiJian.getCreateDateTime()));
        showstates(tuiJian, holder);
        getUser(tuiJian.getUserNeedHelpId());
        System.out.println("============================"+tuiJian.getUserNeedHelpId());
//        holder.tuijianuserName.setText(mUserList.get(tuiJian.getUserNeedHelpId()).getNickname());
//        holder.tuijianuserName.setText(user.getNickname());
//        User user = new User();
//        user = mUserHashMap.get(tuiJian.getNeedHelpId());
//        holder.tuijianuserName.setText(mUserHashMap.get(tuiJian.getUserNeedHelpId()));
//        holder.tuijianImage.setImageResource(tuiJian.getImageId());
    }

    private void showstates(NeedHelp tuiJian, TuiJianAdapter.ViewHolder holder) {
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        View tuijianView;
        //        ImageView tuijianImage;
        TextView tuijianuserName;
        TextView tuijianData;
        TextView tuijianwaittime;
        TextView tuijiancreatetime;
        LinearLayout ly_hw;
        LinearLayout ly_hp;
        LinearLayout ly_fn;
        LinearLayout ly_fy;

        public ViewHolder(View view) {
            super(view);
            tuijianView = view;
//            tuijianImage=(ImageView)view.findViewById(R.id.tuijian_image);
            tuijianuserName = (TextView) view.findViewById(R.id.tuijian_username);
            tuijianData = (TextView) view.findViewById(R.id.tuijian_data_text);
            tuijianwaittime = (TextView) view.findViewById(R.id.tuijian_waittime);
            tuijiancreatetime = view.findViewById(R.id.tuijian_creattime);
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
//                    mUserHashMap.put(user.getId(),user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("获取信息失败");
            }
        }
    }

}
