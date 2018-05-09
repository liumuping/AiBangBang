package com.example.administrator.controller.fragment.zhuye;


import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


import com.example.administrator.controller.Base.BaseFragment;
import com.example.administrator.model.bean.ReBang;
import com.example.administrator.controller.R;
import com.example.administrator.controller.adapter.zhuyeadapter.ReBangAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ReBangFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    @Override
    protected View initView() {
        View view = View.inflate(mcontext, R.layout.rebang, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rb_recycle_view);
        layoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        return view;
    }

    @Override
    protected void initData() {
      String rbUrl = "http://10.0.2.2:8080/ReBangServlet";
        //    String rbUrl = "http://192.168.1.106:8080/ReBangServlet";
        new ReBangAsyncTask().execute(rbUrl);
            super.initData();

        }


    private class ReBangAsyncTask extends AsyncTask<String, Integer, String> {
        public ReBangAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response=okHttpClient.newCall(request).execute();
                results=response.body().string();
                //判断请求是否成功
            } catch (IOException e) {
                e.printStackTrace();

            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
            List<ReBang> mdata = new ArrayList<>();
            if (s != null){
                try {
                    JSONArray results = new JSONArray(s);
                    for(int i=0;i<results.length();i++){
                        ReBang reBang = new ReBang();
                        JSONObject js = results.getJSONObject(i);
                        reBang.setName(js.getString("rbusername"));
                        reBang.setData(js.getString("rbdata"));
                        mdata.add(reBang);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            ReBangAdapter adapter = new ReBangAdapter(mdata);
            recyclerView.setAdapter(adapter);
        }
    }


}