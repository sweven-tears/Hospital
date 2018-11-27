package com.cdtc.hospital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.cdtc.hospital.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity {

    /**
     * [延迟启动时间设置,直接启动]
     */
    private static final int LAUNCH_APP_TIME = 5000, DIRECT_LAUNCH_APP = 0;

    private final Handler mLaunchHandler = new Handler();

    private boolean isDirect;

    /**
     * 线程启动MainActivity
     */
    private final Runnable launchRun = () -> {
        if (!isDirect) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            isDirect=true;
        }
    };

    private TextView countDown;
    private int time = LAUNCH_APP_TIME / 1000;

    protected static final int MSG_WHAT = 0;
    private Timer timer;


    /**
     * 利用Handler设计倒计时
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countDown.setText(time + " s");
            switch (msg.what) {
                case MSG_WHAT:
                    if (time > 0) {
                        time--;
                    } else {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        countDown = findViewById(R.id.count_down);

        countDown.setBackgroundResource(R.drawable.border_round_corner);
        countDown.setOnClickListener
                (v -> {
                    mLaunchHandler.postDelayed(launchRun, DIRECT_LAUNCH_APP);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                });

        hide();
    }

    /**
     * 隐藏actionBar
     */
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 定时器Timer设置
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(MSG_WHAT);
                }
            }, 0, 1000);
        }
        mLaunchHandler.postDelayed(launchRun, LAUNCH_APP_TIME);
    }

}
