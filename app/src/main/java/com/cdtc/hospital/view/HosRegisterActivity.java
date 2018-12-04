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
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.local.dao.impl.HosRegisterLocalDaoImpl;
import com.cdtc.hospital.network.entity.HosRegister;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HosRegisterActivity extends BaseActivity {

    private RecyclerView hosRegisterRecyclerView;
    private HosRegisterAdapter hosRegisterAdapter;

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

        hosRegisterRecyclerView = findViewById(R.id.hos_register_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        hosRegisterRecyclerView.setLayoutManager(manager);
        hosRegisterRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initData() {

        searchHosRegisterList();
        queryBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

    }

    /**
     * 获取 HosRegisterList （条件查询数据或查询所有数据）
     */
    private void searchHosRegisterList() {

        String hosR_idStr = searchHosR_id.getText().toString();
        String d_name = searchD_name.getText().toString();
        String d_keshi = searchD_keshi.getText().toString();

        HosRegisterLocalDao hosRegisterLocalDao = new HosRegisterLocalDaoImpl(activity, BaseLocalDao.QUERY_DATABASE);
        List<HosRegister> hosRegisters = new ArrayList<>();
        if (!hosR_idStr.equals("")) {
            Integer hosR_id;
            try {
                hosR_id = Integer.parseInt(hosR_idStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                toast.showError("病历号只能为数字");
                return;
            }

            hosRegisters.add(hosRegisterLocalDao.queryHosRegisterByHosR_id(hosR_id));
        } else {
            hosRegisters = hosRegisterLocalDao.queryHosRegisters();
        }
        if (!d_keshi.equals("")){

        }

        Set<HosRegister> set=new HashSet<>();


        hosRegisterAdapter = new HosRegisterAdapter(activity, hosRegisters);
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
            HosRegister hosRegister=new HosRegister();

            assert data != null;
            Bundle bundle=data.getExtras();

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
        }
    }
}
