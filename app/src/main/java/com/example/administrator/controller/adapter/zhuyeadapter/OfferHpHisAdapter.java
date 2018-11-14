package com.example.administrator.controller.adapter.zhuyeadapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.model.bean.User;
import com.example.administrator.model.bean.UserOfferHelpHistory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferHpHisAdapter extends RecyclerView.Adapter<OfferHpHisAdapter.ViewHolder> {
    private User user=new User();
    private List<User> mUserList=new ArrayList<>();
    private List<UserOfferHelpHistory> mUserOfferHelpHistoryList;
    private UserOfferHelpHistory userOfferHelpHistory=new UserOfferHelpHistory();
    public OfferHpHisAdapter(List<UserOfferHelpHistory> reviewList){
        mUserOfferHelpHistoryList=reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_offhelpitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserOfferHelpHistory userOfferHelpHistory = mUserOfferHelpHistoryList.get(position);
        for (int i = 0; i < mUserList.size(); i++) {
            if (userOfferHelpHistory.getUserOfferHelpHistoryId() == mUserList.get(i).getId()) {
                holder.nickName.setText(mUserList.get(i).getNickname());
                userOfferHelpHistory.setNickname(mUserList.get(i).getNickname());
            }
        }
        holder.comment.setText(userOfferHelpHistory.getUserComment());
        holder.endtime.setText(String.valueOf(userOfferHelpHistory.getUserCommentDateTime()));
        getUser(userOfferHelpHistory.getUserOfferHelpHistoryId());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View offHelpView;
        ImageView userImage;
        TextView nickName;
        TextView comment;
        TextView endtime;

        public ViewHolder(View view) {
            super(view);
            offHelpView = view;
            nickName=view.findViewById(R.id.offusername);
            comment=view.findViewById(R.id.tv_off_comment);
            endtime=view.findViewById(R.id.offendtime);
        }
    }

    @Override
    public int getItemCount() {
        return mUserOfferHelpHistoryList.size();
    }


    public void getUser(int uid) {
        String registUrl ="http://119.23.226.102/aibangbang/v1/users/"+uid;
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
            if (s != null && "".equals(s)) {
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
