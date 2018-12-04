package com.cdtc.hospital.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.hospital.R;
import com.cdtc.hospital.adapter.HosRegisterAdapter;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.DoctorLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.local.dao.impl.DoctorLocalDaoImpl;
import com.cdtc.hospital.local.dao.impl.HosRegisterLocalDaoImpl;
import com.cdtc.hospital.network.dao.HosRegisterDao;
import com.cdtc.hospital.network.dao.impl.HosRegisterDaoImpl;
import com.cdtc.hospital.network.entity.Doctor;
import com.cdtc.hospital.network.entity.HosRegister;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HosRegisterActivity extends BaseActivity {

    private RecyclerView hosRegisterRecyclerView;
    private HosRegisterAdapter hosRegisterAdapter;
    private QueryHosRegisterTask mAuthTask;

    private static List<HosRegister> list;

    private EditText searchHosR_id;
    private EditText searchD_name;
    private EditText searchD_keshi;
    private Button queryBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_register);

        setActionBarTitle("挂号信息管理");

        bindViewId();
        initData();
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT,BTN_TYPE_IMG,R.drawable.ic_keyboard_arrow_left_black_24dp);
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT,BTN_TYPE_TEXT,"门诊挂号");
        showLeftButton();
        showRightButton();

    }

    @Override
    protected void bindViewId() {

        searchHosR_id=findViewById(R.id.search_hos_r_id);
        searchD_name=findViewById(R.id.search_d_name);
        searchD_keshi=findViewById(R.id.search_d_keshi);

        queryBtn=findViewById(R.id.query_btn);
        clearBtn=findViewById(R.id.clear_btn);

        hosRegisterRecyclerView = findViewById(R.id.hos_register_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        hosRegisterRecyclerView.setLayoutManager(manager);

    }

    @Override
    protected void initData() {

        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchHosR_id.getWindowToken(),0);

        searchHosRegisterList();

    }

    /**
     * 获取 HosRegisterList （条件查询数据或查询所有数据）
     */
    private void searchHosRegisterList() {
        if (mAuthTask != null) {
            return;
        }

        String hosR_idStr = searchHosR_id.getText().toString();
        String d_name = searchD_name.getText().toString();
        String d_keshi = searchD_keshi.getText().toString();

        boolean isInteger = true;


        Integer hosR_id = null;
        try {
            hosR_id = Integer.parseInt(hosR_idStr);
        } catch (NumberFormatException e) {
            isInteger = false;
        }

        if (!isInteger) {
            searchHosR_id.requestFocus();
        } else {
            mAuthTask = new QueryHosRegisterTask(hosR_id, d_name, d_keshi);
            mAuthTask.execute();
        }
        HosRegisterLocalDao hosRegisterLocalDao=new HosRegisterLocalDaoImpl(activity,BaseLocalDao.QUERY_DATABASE);
        List<HosRegister> hosRegisters = hosRegisterLocalDao.queryHosRegisters();
        hosRegisterAdapter = new HosRegisterAdapter(activity, hosRegisters);
        hosRegisterRecyclerView.setAdapter(hosRegisterAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    public class QueryHosRegisterTask extends AsyncTask<Void, List<HosRegister>, List<HosRegister>> {

        private Integer hosR_id;
        private String d_name;
        private String d_keshi;
        private List<HosRegister> hosRegisters;

        QueryHosRegisterTask(Integer hosR_id, String d_name, String d_keshi) {
            this.hosR_id = hosR_id;
            this.d_name = d_name;
            this.d_keshi = d_keshi;
        }

        @Override
        protected List<HosRegister> doInBackground(Void... params) {
            HosRegisterDao dao = new HosRegisterDaoImpl();
            hosRegisters = dao.selectByCondition(hosR_id, d_name, d_keshi);
            return hosRegisters;
        }

        @Override
        protected void onPostExecute(final List<HosRegister> hosRegisters) {
            list=hosRegisters;
        }

        @Override
        protected void onCancelled() {
        }
    }

}
