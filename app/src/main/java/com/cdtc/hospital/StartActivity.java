package com.cdtc.hospital;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.entity.User;
import com.cdtc.hospital.local.DatabaseHelper;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.UserLocalDao;
import com.cdtc.hospital.local.dao.impl.UserLocalDaoImpl;
import com.cdtc.hospital.service.UserTask;
import com.cdtc.hospital.view.ListActivity;
import com.cdtc.hospital.view.LoginActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity {

    protected static final int MSG_WHAT = 0;
    /**
     * [延迟启动时间设置,直接启动]
     */
    private static final int LAUNCH_APP_TIME = 5000, DIRECT_LAUNCH_APP = 0;
    private final Handler mLaunchHandler = new Handler();
    private boolean isDirect;
    private TextView countDown;
    private int time = LAUNCH_APP_TIME / 1000;
    private Timer timer;
    private UserTask userTask;
    private boolean isQuery = false;
    /**
     * 线程启动MainActivity
     */
    private final Runnable launchRun = this::run;
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
        new DatabaseHelper(activity, App.DATA_BASE);

        countDown.setBackgroundResource(R.drawable.border_round_corner);
        countDown.setOnClickListener
                (v -> {
                    userTask = new UserTask(activity, UserTask.TASK_QUERY_PASSWORD_BY_NAME, App.loginName);
                    userTask.execute();
                    userTask.setOnSuccessListener(new UserTask.OnResultListener() {
                        @Override
                        public void onSuccess(List<User> users) {
                        }

                        @Override
                        public void onSuccess(String passWord) {
                            UserLocalDao userLocalDao = new UserLocalDaoImpl(activity, BaseLocalDao.QUERY);
                            boolean isExist = userLocalDao.queryExistByPassWordLoginName(App.loginName, passWord);
                            if (!isExist) {
                                User user = new User();
                                user.setU_loginName(App.trueName);
                                user.setU_passWord(passWord);
                                user.setU_state(App.loginState = App.LOG_ERROR);
                                userLocalDao.updateUser(user);
                            }
                            isQuery = true;
                        }
                    });
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

//        task=new QueryHosRegisterTask();
//        task.execute();
        UserLocalDao userLocalDao = new UserLocalDaoImpl(activity, BaseLocalDao.UPDATE);
        userLocalDao.queryLocalLogSate();
        if (App.loginName == null) {
            userTask = new UserTask(activity, UserTask.TASK_QUERY_ALL, null);
            userTask.execute();
            userTask.setOnSuccessListener(new UserTask.OnResultListener() {
                @Override
                public void onSuccess(List<User> users) {
                    for (User user : users) {
                        int result = userLocalDao.insertUser(user);
                        if (result < 0) {
                            userLocalDao.updateUser(user);
                        }
                        log.i("add user " + user + (result > 0 ? " success" : " fail"));
                    }
                }

                @Override
                public void onSuccess(String passWord) {
                }
            });
            return;
        }
        userTask = new UserTask(activity, UserTask.TASK_QUERY_PASSWORD_BY_NAME, App.loginName);
        userTask.execute();
        userTask.setOnSuccessListener(new UserTask.OnResultListener() {
            @Override
            public void onSuccess(List<User> users) {
            }

            @Override
            public void onSuccess(String passWord) {
                boolean isExist = userLocalDao.queryExistByPassWordLoginName(App.loginName, passWord);
                if (!isExist) {
                    User user = new User();
                    user.setU_loginName(App.loginName);
                    user.setU_passWord(passWord);
                    user.setU_state(App.LOG_ERROR);
                    userLocalDao.updateUser(user);
                    App.loginState = App.LOG_ERROR;
                }
                isQuery = true;
            }
        });

    }

    private void run() {
        if (!isDirect) {
            finish();
            if (!isQuery) {
                UserLocalDao userLocalDao = new UserLocalDaoImpl(activity, BaseLocalDao.QUERY);
                userLocalDao.queryLocalLogSate();
            }
            if (App.loginState == App.LOG_OUT) {
                startActivity(LoginActivity.class);
            } else if (App.loginState == App.LOG_LOST) {
                startActivity(LoginActivity.class);
                toast.showShort("登录状态失效，请重新登录");
            } else if (App.loginState == App.LOG_ERROR) {
                startActivity(LoginActivity.class);
                toast.showShort("您的账号存在异常，请重新登录");
            } else if (App.loginState == App.LOG_IN) {
                startActivity(ListActivity.class);
            }
//            toast.showShort(App.loginState + "");
            isDirect = true;
        }
    }

//    QueryHosRegisterTask task;

//    @SuppressLint("StaticFieldLeak")
//    public class QueryHosRegisterTask extends AsyncTask<Void, Void, Integer> {
//
//        private List<TableUser> users;
//
//        QueryHosRegisterTask() {
//        }
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//            try {
//                UserDao userDao=new UserDaoImpl();
//                users=userDao.queryAllUser();
//            } catch (Exception e) {
//                return -1;
//            }
//            return 0;
//        }
//
//        @Override
//        protected void onPostExecute(final Integer integer) {
//            if (integer==-1){
//                toast.showError("网络开了点小差~");
//                return;
//            }
//            UserLocalDao userLocalDao=new UserLocalDaoImpl(activity,BaseLocalDao.UPDATE);
//            for (TableUser user:users){
//                int result= userLocalDao.insertUser(user);
//                if (result<0){
//                    userLocalDao.updateUser(user);
//                }
//                log.i("add user "+user+(result>0?" success":" fail"));
//            }
//            userLocalDao.queryLocalLogSate();
//        }
//
//        @Override
//        protected void onCancelled() {
//        }
//    }

}
