package com.cdtc.hospital.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cdtc.hospital.R;
import com.cdtc.hospital.base.App;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.local.SQLite;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("挂号信息管理");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> search());
    }

    private void search() {

    }
}
