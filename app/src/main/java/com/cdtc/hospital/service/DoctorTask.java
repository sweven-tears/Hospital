package com.cdtc.hospital.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.cdtc.hospital.entity.Doctor;
import com.cdtc.hospital.network.dao.DoctorDao;
import com.cdtc.hospital.network.dao.impl.DoctorDaoImpl;
import com.cdtc.hospital.util.LogUtil;
import com.cdtc.hospital.util.ToastUtil;

import java.util.List;

/**
 * Created by Sweven on 2018/12/20.
 * Email:sweventears@Foxmail.com
 */
public class DoctorTask extends AsyncTask<Void, Void, Integer> {

    public final static int QUERY_KeShi_LIST = 1;
    public final static int QUERY_NAME_LIST = 2;
    public final static int TYPE_PROGRESS = 3;
    public final static int TYPE_NON_PROGRESS = 4;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private int taskType;
    private int progress;
    private String d_keshi;
    private List<String> keShiList;
    private List<Doctor> nameList;
    private ProgressDialog mProgressDialog;
    //创建接口，成功时候回调
    private OnSuccessListener onSuccessListener;

    public DoctorTask(Activity activity, String d_keshi, int query, int progress) {
        this.activity = activity;
        this.d_keshi = d_keshi;
        this.taskType = query;
        this.progress = progress;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("加载中......");
        if (progress == TYPE_PROGRESS) {
            mProgressDialog.show();
        } else if (progress == TYPE_NON_PROGRESS) {
            mProgressDialog = null;
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            DoctorDao dao = new DoctorDaoImpl();
            if (taskType == QUERY_KeShi_LIST) {
                keShiList = dao.queryKeshiList();
                return 1;
            } else if (taskType == QUERY_NAME_LIST) {
                nameList = dao.queryDoctorByKeshi(d_keshi);
                return 1;
            }
        } catch (Exception e) {
            new LogUtil(this.getClass().getName()).e("网络问题");
            return -1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        String tag = (taskType == QUERY_KeShi_LIST ? "科室" : "姓名") + "列表" + (result > 0 ? "成功" : "失败");
        new LogUtil(activity.getLocalClassName()).i(tag);

        if (result == -1) {
            new ToastUtil(activity).showError("网络错误，请稍后再试!");
        } else if (result > 0) {
            onSuccessListener.onSuccess(keShiList, nameList);
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }

    public interface OnSuccessListener {
        void onSuccess(List<String> keShiList, List<Doctor> nameList);
    }
}
