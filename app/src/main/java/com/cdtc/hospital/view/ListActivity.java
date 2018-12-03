package com.cdtc.hospital.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdtc.hospital.R;
import com.cdtc.hospital.base.App;
import com.cdtc.hospital.base.BaseActivity;

public class ListActivity extends BaseActivity {

    private ImageView guaHaoImageView;
    private ImageView zhuYuanImageView;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bindViewId();
        initData();
    }

    @Override
    protected void bindViewId() {
        guaHaoImageView = findViewById(R.id.gua_hao);
        zhuYuanImageView = findViewById(R.id.zhu_yuan);
        welcomeTextView = findViewById(R.id.welcome);
    }

    @Override
    protected void initData() {
        String welcome = "Welcome " + App.trueName + " to " + getString(R.string.app_name);
        welcomeTextView.setText(welcome);

        guaHaoImageView.setOnClickListener(this);
        zhuYuanImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gua_hao: {
                startActivity(HosRegisterActivity.class);
                break;
            }
            case R.id.zhu_yuan: {
                break;
            }
        }
    }
}
