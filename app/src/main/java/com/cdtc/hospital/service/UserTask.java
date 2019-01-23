package com.cdtc.hospital.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.cdtc.hospital.entity.User;
import com.cdtc.hospital.network.dao.UserDao;
import com.cdtc.hospital.network.dao.impl.UserDaoImpl;
import com.cdtc.hospital.util.LogUtil;
import com.cdtc.hospital.util.ToastUtil;

import java.util.List;

/**
 * Created by Sweven on 2019/1/11.
 * Email:sweventears@Foxmail.com
 */
public class UserTask extends AsyncTask<Void, Void, Integer> {

    public final static int TASK_QUERY_ALL = 1;
    public final static int TASK_QUERY_PASSWORD_BY_NAME = 2;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private ProgressDialog mProgressDialog;
    private List<User> users;
    private int taskType;
    private String loginName;
    private String getPassWord;
    //创建接口，成功时候回调
    private OnResultListener onResultListener;

    public UserTask(Activity activity, int taskType, String loginName) {
        this.activity = activity;
        this.taskType = taskType;
        this.loginName = loginName;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("加载中......");
//        mProgressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            UserDao userDao = new UserDaoImpl();
            if (taskType == TASK_QUERY_ALL) {
                users = userDao.queryAllUser();
                return 1;
            } else if (taskType == TASK_QUERY_PASSWORD_BY_NAME) {
                getPassWord = userDao.queryPassWordByLoginName(loginName);
                return getPassWord == null ? 0 : 1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        String tag = (taskType == TASK_QUERY_ALL ? "查询列表" : "查询密码") + (result > 0 ? "成功" : "失败");
        new LogUtil(activity.getLocalClassName()).i(tag);

        if (result == -1) {
            new ToastUtil(activity).showError("网络错误，请稍后再试!");
        } else if (result > 0) {
            if (taskType == TASK_QUERY_ALL) {
                onResultListener.onSuccess(users);
            } else if (taskType == TASK_QUERY_PASSWORD_BY_NAME) {
                onResultListener.onSuccess(getPassWord);
            }

        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCancelled() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void setOnSuccessListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public interface OnResultListener {
        void onSuccess(List<User> users);

        void onSuccess(String passWord);

    }
}
