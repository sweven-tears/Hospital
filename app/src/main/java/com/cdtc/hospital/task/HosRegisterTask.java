package com.cdtc.hospital.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.cdtc.hospital.network.dao.HosRegisterDao;
import com.cdtc.hospital.network.dao.impl.HosRegisterDaoImpl;
import com.cdtc.hospital.entity.HosRegister;
import com.cdtc.hospital.util.LogUtil;
import com.cdtc.hospital.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sweven on 2018/12/3.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterTask extends AsyncTask<Void, Void, Integer> {

    private int taskType;
    private static final int TASK_QUERY_BY_CONDITION = 0;
    public static final int TASK_INSERT = 1;
    public static final int TASK_UPDATE = 2;
    private static final int TASK_SELECT_BY_ID = 3;
    public static final int TASK_UPDATE_STATE = 4;

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private HosRegister hosRegister;
    private List<HosRegister> list;
    private ProgressDialog mProgressDialog;

    private Integer hosR_id;
    private String d_name;
    private String d_keshi;

    /**
     * 病历号+医生姓名+科室 查询
     *
     * @param activity 上下文
     * @param hosR_id  病历号
     * @param d_name   医生姓名
     * @param d_keshi  科室
     */
    public HosRegisterTask(Activity activity, Integer hosR_id, String d_name, String d_keshi) {
        this.activity = activity;
        this.hosR_id = hosR_id;
        this.d_name = d_name;
        this.d_keshi = d_keshi;
        this.taskType = TASK_QUERY_BY_CONDITION;
    }

    public HosRegisterTask(Activity activity, HosRegister hosRegister, int taskType) {
        this.activity = activity;
        this.hosRegister = hosRegister;
        this.taskType = taskType;
    }

    public HosRegisterTask(Activity activity, Integer hosR_id, Integer hosR_state, int taskType) {
        this.activity = activity;
        hosRegister = new HosRegister();
        hosRegister.setHosR_state(hosR_state);
        hosRegister.setHosR_id(hosR_id);
        this.taskType = taskType;
    }

    /**
     * 用于查询详情
     * @param activity 上下文
     * @param hosR_id 主键，病历号
     */
    public HosRegisterTask(Activity activity, Integer hosR_id) {
        this.activity = activity;
        this.hosR_id = hosR_id;
        this.taskType = TASK_SELECT_BY_ID;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(activity, null, "加载中......");
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            HosRegisterDao dao = new HosRegisterDaoImpl();
            if (taskType == TASK_QUERY_BY_CONDITION) {
                list = dao.selectByCondition(hosR_id, d_name, d_keshi);
                return 1;
            } else if (taskType == TASK_INSERT) {
                int result = dao.addHosRegister(hosRegister);
                if (result>0) {
                    int hosR_id=dao.getLastHosRId();
                    hosRegister=new HosRegister();
                    hosRegister.setHosR_id(hosR_id);
                }
                return result;
            } else if (taskType == TASK_UPDATE) {
                return dao.updateHosRegisterById(hosRegister);
            } else if (taskType == TASK_SELECT_BY_ID) {
                hosRegister = dao.queryHosRegisterByHosR_id(hosR_id);
                return 1;
            } else if (taskType == TASK_UPDATE_STATE) {
                return dao.updateStateById(hosRegister.getHosR_id(), hosRegister.getHosR_state());
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(final Integer result) {
        String tag =
                taskType == TASK_QUERY_BY_CONDITION ? "多条件查询" :
                        (taskType == TASK_INSERT ? "添加" :
                                (taskType == TASK_UPDATE ? "更新" :
                                        (taskType == TASK_SELECT_BY_ID ? "id查询" :
                                                "其他"))) +
                                "数据" + (result > 0 ? "成功" : "失败");
        new LogUtil(activity.getLocalClassName()).i(tag);
        if (result == -1) {
            new ToastUtil(activity).showError("网络开了点小差~");
        } else if (result > 0) {
            if (taskType == TASK_QUERY_BY_CONDITION) {
                this.onSuccessListener.onSuccess(list);
            } else if (taskType == TASK_SELECT_BY_ID) {
                list=new ArrayList<>();
                list.add(hosRegister);
                this.onSuccessListener.onSuccess(list);
            } else if (taskType == TASK_UPDATE){
                this.onSuccessListener.onSuccess(null);
            } else if (taskType==TASK_INSERT){
                list=new ArrayList<>();
                list.add(hosRegister);
                this.onSuccessListener.onSuccess(list);
            }
        }
        mProgressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
    }

    //创建接口，成功时候回调
    private OnSuccessListener onSuccessListener;

    public interface OnSuccessListener {
        void onSuccess(List<HosRegister> list);
    }

    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}
