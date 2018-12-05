package com.cdtc.hospital.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.hospital.R;
import com.cdtc.hospital.adapter.HosRegisterAdapter;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.entity.Doctor;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.DoctorLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.local.dao.impl.DoctorLocalDaoImpl;
import com.cdtc.hospital.local.dao.impl.HosRegisterLocalDaoImpl;
import com.cdtc.hospital.entity.HosRegister;

import java.util.ArrayList;
import java.util.List;

public class HosRegisterActivity extends BaseActivity {

    private RecyclerView hosRegisterRecyclerView;
    private HosRegisterAdapter hosRegisterAdapter;

    private static List<HosRegister> list;

    private EditText searchHosR_id;
    private EditText searchD_name;
    private EditText searchD_keshi;
    private Button queryBtn;
    private Button clearBtn;
    private Button selectBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_register);

        setActionBarTitle("挂号信息管理");

        bindViewId();
        initData();
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT, BTN_TYPE_IMG, R.drawable.ic_keyboard_arrow_left_black_24dp);
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT, BTN_TYPE_TEXT, "门诊挂号");
        showLeftButton();
        showRightButton();

    }

    @Override
    protected void bindViewId() {

        searchHosR_id = findViewById(R.id.search_hos_r_id);
        searchD_name = findViewById(R.id.search_d_name);
        searchD_keshi = findViewById(R.id.search_d_keshi);

        queryBtn = findViewById(R.id.query_btn);
        clearBtn = findViewById(R.id.clear_btn);

        selectBtn = findViewById(R.id.select_btn);
        deleteBtn = findViewById(R.id.delete_btn);

        hosRegisterRecyclerView = findViewById(R.id.hos_register_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        hosRegisterRecyclerView.setLayoutManager(manager);
        hosRegisterRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initData() {

        searchHosRegisterList();
        queryBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

    }

    /**
     * 获取 HosRegisterList （条件查询数据或查询所有数据）
     */
    private void searchHosRegisterList() {

        list = new ArrayList<>();

        String hosR_idStr = searchHosR_id.getText().toString();
        String d_name = searchD_name.getText().toString();
        String d_keshi = searchD_keshi.getText().toString();

        HosRegisterLocalDao hosRegisterLocalDao = new HosRegisterLocalDaoImpl(activity, BaseLocalDao.QUERY);
        while (true) {
            if (hosR_idStr.equals("") && d_keshi.equals("") && d_name.equals("")) {
                list.addAll(hosRegisterLocalDao.queryHosRegisters());
                break;
            }
            if (!hosR_idStr.equals("")) {
                Integer hosR_id;
                try {
                    hosR_id = Integer.parseInt(hosR_idStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    toast.showError("病历号只能为数字");
                    return;
                }

                list.add(hosRegisterLocalDao.queryHosRegisterByHosR_id(hosR_id));
            } else if (!d_keshi.equals("")) {
                DoctorLocalDao doctorLocalDao = new DoctorLocalDaoImpl(activity, BaseLocalDao.QUERY);
                List<Doctor> doctors = doctorLocalDao.queryDoctorByKeshi(d_keshi);
                for (Doctor doctor : doctors) {
                    list.addAll(hosRegisterLocalDao.queryByCondition(doctor.getD_id()));
                }
            } else if (!d_name.equals("")) {
                DoctorLocalDao doctorLocalDao = new DoctorLocalDaoImpl(activity, BaseLocalDao.QUERY);
                List<Doctor> doctors = doctorLocalDao.queryDoctorByName(d_name);
                for (Doctor doctor : doctors) {
                    list.addAll(hosRegisterLocalDao.queryByCondition(doctor.getD_id()));
                }
            }
            break;
        }

        hosRegisterAdapter = new HosRegisterAdapter(activity, list);
        hosRegisterRecyclerView.setAdapter(hosRegisterAdapter);
    }

    @Override
    protected void rightDoWhat() {
        Intent intent = new Intent(activity, AddHosRegisterActivity.class);
        startActivityForResult(intent, AddHosRegisterActivity.REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AddHosRegisterActivity.REQUEST && resultCode == AddHosRegisterActivity.RESULT) {
            HosRegister hosRegister = new HosRegister();

            assert data != null;
            Bundle bundle = data.getExtras();

            assert bundle != null;
            hosRegister.setHosR_id(bundle.getInt("hosR_id"));
            hosRegister.setD_id(bundle.getInt("d_id"));
            hosRegister.setHosR_state(0);
            hosRegisterAdapter.insertHosRegister(hosRegister);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_btn:
                searchHosRegisterList();
                break;
            case R.id.clear_btn:
                searchHosR_id.setText("");
                searchD_name.setText("");
                searchD_keshi.setText("");
                searchHosRegisterList();
                break;
            case R.id.select_btn:
                String name=selectBtn.getText().toString();
                if (name.equals("全选")) {
                    hosRegisterAdapter.selectAll();
                    selectBtn.setText("取消全选");
                }
                else if (name.equals("取消全选")){
                    hosRegisterAdapter.cancelSelectAll();
                    selectBtn.setText("全选");
                }
                break;
            case R.id.delete_btn:
                hosRegisterAdapter.updateState();
                break;
        }
    }
}
