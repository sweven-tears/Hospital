package com.cdtc.hospital.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.cdtc.hospital.entity.BeHospital;

import java.util.List;

/**
 * Created by Sweven on 2019/1/17.
 * Email:sweventears@Foxmail.com
 */
public class BeHospitalTask extends AsyncTask<Void, Void, Integer> {

    public Activity activity;
    private int taskType;
    private ProgressDialog mProgressDialog;
    private int beH_id;
    private String d_name;
    private String d_keshi;
    private BeHospital beHospital;
    private List<BeHospital> list;

    public BeHospitalTask(Activity activity) {
        this(new Builder());
        this.activity = activity;
    }

    BeHospitalTask(Builder builder) {
        this.activity = builder.activity;
        this.taskType = builder.taskType;
        this.beH_id = builder.beH_id;
        this.d_name = builder.d_name;
        this.d_keshi = builder.d_keshi;
        this.beHospital = builder.beHospital;
        this.list = builder.list;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("加载中......");
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    @Override
    protected void onCancelled() {
        if (!isCancelled()){
            cancel(true);
        }
        mProgressDialog.dismiss();
        mProgressDialog=null;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {

        public Activity activity;
        private int taskType;
        private int beH_id;
        private String d_name;
        private String d_keshi;
        private BeHospital beHospital;
        private List<BeHospital> list;

        public static final int TASK_QUERY = 1;
        public static final int TASK_UPDATE = 2;
        public static final int TASK_INSERT = 3;

        public Builder() {
        }

        Builder(BeHospitalTask beHospitalTask) {
            this.activity = beHospitalTask.activity;
            this.taskType = beHospitalTask.taskType;
            this.beH_id = beHospitalTask.beH_id;
            this.d_name = beHospitalTask.d_name;
            this.d_keshi = beHospitalTask.d_keshi;
            this.beHospital = beHospitalTask.beHospital;
            this.list = beHospitalTask.list;
        }

        public Builder task(int type) {
            this.taskType = type;
            return this;
        }

        public Builder setID(int beH_id) {
            this.beH_id = beH_id;
            return this;
        }

        public Builder setName(String name) {
            this.d_name = name;
            return this;
        }

        public Builder setKeShi(String keShi) {
            this.d_keshi = keShi;
            return this;
        }

        public Builder setBeHospital(BeHospital beHospital) {
            this.beHospital = beHospital;
            return this;
        }

        public Builder setList(List<BeHospital> list) {
            this.list = list;
            return this;
        }

        public BeHospitalTask build(){
            return new BeHospitalTask(this);
        }
    }
}
