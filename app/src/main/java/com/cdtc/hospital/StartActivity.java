package com.cdtc.hospital;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.local.DatabaseHelper;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.UserLocalDao;
import com.cdtc.hospital.network.dao.UserDao;
import com.cdtc.hospital.local.dao.impl.UserLocalDaoImpl;
import com.cdtc.hospital.network.dao.impl.UserDaoImpl;
import com.cdtc.hospital.entity.User;
import com.cdtc.hospital.view.ListActivity;
import com.cdtc.hospital.view.LoginActivity;

import java.util.List;
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
            finish();
            UserLocalDao userLocalDao=new UserLocalDaoImpl(activity,BaseLocalDao.QUERY);
            userLocalDao.queryLocalLogSate();
            if (App.loginState==App.LOG_OUT){
                startActivity(LoginActivity.class);
            }else if(App.loginState==App.LOG_IN){
                startActivity(ListActivity.class);
            }
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

//        deleteDatabase(App.DATA_BASE);
        new DatabaseHelper(activity,App.DATA_BASE);

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

        task=new QueryHosRegisterTask();
        task.execute();

        mLaunchHandler.postDelayed(launchRun, LAUNCH_APP_TIME);
    }

    QueryHosRegisterTask task;

    @SuppressLint("StaticFieldLeak")
    public class QueryHosRegisterTask extends AsyncTask<Void, Void, Integer> {

        private List<User> users;

        QueryHosRegisterTask() {
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                UserDao userDao=new UserDaoImpl();
                users=userDao.queryAllUser();
            } catch (Exception e) {
                return -1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(final Integer integer) {
            if (integer==-1){
                toast.showError("网络开了点小差~");
                return;
            }
            UserLocalDao userLocalDao=new UserLocalDaoImpl(activity,BaseLocalDao.UPDATE);
            for (User user:users){
                int result= userLocalDao.insertUser(user);
                if (result<0){
                    userLocalDao.updateUser(user);
                }
                log.i("add user "+user+(result>0?" success":" fail"));
            }
            userLocalDao.queryLocalLogSate();
        }

        @Override
        protected void onCancelled() {
        }
    }

}
