package com.example.administrator.controller.activity.geren.WaitHelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.controller.R;
import com.example.administrator.controller.activity.chat.ChatMessageActivity;
import com.example.administrator.controller.activity.geren.Finish.FHacceptActivity;
import com.example.administrator.model.bean.Finish;

public class WHacceptActivity extends AppCompatActivity {

    private Button dateshowbase_btn;
    private TextView tv_whaccept_data;
    private Finish finish;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datashowbase);
        initview();
        initdata();

    }

    private void initdata() {
        finish= (Finish) getIntent().getSerializableExtra("finish");
        String fhaccept_data=finish.getData();
        tv_whaccept_data.setText(fhaccept_data);
        Listenner();
    }

    private void initview() {
        dateshowbase_btn=(Button)findViewById(R.id.dateshowbase_btn);
        tv_whaccept_data=(TextView)findViewById(R.id.tv_datebase_data);
        back=(ImageView)findViewById(R.id.dateshowbase_im_back);
    }

    private void Listenner() {
        dateshowbase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(WHacceptActivity.this, ChatMessageActivity.class);
                startActivity(intent1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
