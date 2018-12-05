package com.cdtc.hospital.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.local.dao.impl.HosRegisterLocalDaoImpl;
import com.cdtc.hospital.network.dao.HosRegisterDao;
import com.cdtc.hospital.network.dao.impl.HosRegisterDaoImpl;
import com.cdtc.hospital.entity.HosRegister;
import com.cdtc.hospital.util.LogUtil;
import com.cdtc.hospital.util.ToastUtil;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Sweven on 2018/12/3.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterTask extends AsyncTask<Void, Void, Integer> {

    private int taskType;
    public static final int TASK_INSERT = 1;
    public static final int TASK_UPDATE = 2;
    public static final int TASK_DELETE = 3;
    public static final int TASK_SYNC_DATA = 4;
    public static final int TASK_UPDATE_STATE=5;

    private Activity activity;
    private HosRegister hosRegister;
    private List<HosRegister> list;



    public HosRegisterTask(Activity activity, HosRegister hosRegister, int taskType) {
        this.activity = activity;
        this.hosRegister = hosRegister;
        this.taskType = taskType;
    }

    public HosRegisterTask(Activity activity,Integer hosR_id,Integer hosR_state,int taskType){
        this.activity=activity;
        hosRegister=new HosRegister();
        hosRegister.setHosR_state(hosR_state);
        hosRegister.setHosR_id(hosR_id);
        this.taskType=taskType;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            HosRegisterDao dao = new HosRegisterDaoImpl();
            if (taskType == TASK_INSERT) {
                int result = dao.addHosRegister(hosRegister);
                Calendar cal = Calendar.getInstance();
                int mYear = cal.get(Calendar.YEAR); // 获取当前年份
                int mMonth = cal.get(Calendar.MONTH) + 1;// 获取当前月份
                int mDay = cal.get(Calendar.DAY_OF_MONTH);// 获取当日期
                hosRegister.setHosR_createTime(new Date(mYear, mMonth, mDay));
                return result;
            } else if (taskType == TASK_UPDATE) {
                return dao.updateHosRegisterById(hosRegister);
            } else if (taskType == TASK_DELETE) {
                return dao.deleteHosRegisterById(hosRegister.getHosR_id());
            } else if (taskType == TASK_SYNC_DATA){
                list=dao.queryHosRegisters();
                return 1;
            } else if (taskType == TASK_UPDATE_STATE){
                return dao.updateStateById(hosRegister.getHosR_id(),hosRegister.getHosR_state());
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(final Integer result) {
        if (result == -1) {
            new ToastUtil(activity).showError("网络开了点小差~");
            return;
        }
        String tag = taskType == TASK_INSERT ? "添加" : (taskType == TASK_UPDATE ? "更新" : (taskType == TASK_DELETE ? "删除" : taskType==TASK_SYNC_DATA?"同步":"错误操作")) + "数据" + (result > 0 ? "成功" : "失败");
        new LogUtil(activity.getLocalClassName()).i(tag);
        if (result > 0) {
            HosRegisterLocalDao hosRegisterLocalDao = new HosRegisterLocalDaoImpl(activity, BaseLocalDao.UPDATE);
            if (taskType == TASK_INSERT) {
                HosRegisterTask task=new HosRegisterTask(activity,null,TASK_SYNC_DATA);
                task.execute();
            } else if (taskType == TASK_UPDATE) {
                hosRegisterLocalDao.updateHosRegisterById(hosRegister);
            } else if (taskType == TASK_DELETE) {
                hosRegisterLocalDao.deleteHosRegisterById(hosRegister.getHosR_id());
            }else if (taskType==TASK_SYNC_DATA){
                for (HosRegister hosRegister:list){
                    hosRegisterLocalDao.addHosRegister(hosRegister);
                }
            }else if (taskType==TASK_UPDATE_STATE){
                hosRegisterLocalDao.updateStateById(hosRegister.getHosR_id(),hosRegister.getHosR_state());
            }
        }

    }

    @Override
    protected void onCancelled() {
    }
}
