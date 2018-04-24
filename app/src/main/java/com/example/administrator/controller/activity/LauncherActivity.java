package com.example.administrator.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.administrator.controller.R;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounch);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                startLoginActivity();
           }


       }, 2000);
    }
    private void startLoginActivity() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
