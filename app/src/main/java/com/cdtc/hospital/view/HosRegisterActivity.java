package com.cdtc.hospital.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cdtc.hospital.R;
import com.cdtc.hospital.adapter.HosRegisterAdapter;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.entity.HosRegister;
import com.cdtc.hospital.task.HosRegisterTask;

import java.util.ArrayList;

public class HosRegisterActivity extends BaseActivity {

    private RecyclerView hosRegisterRecyclerView;
    private HosRegisterAdapter hosRegisterAdapter;

    private EditText searchHosR_id;
    private EditText searchD_name;
    private EditText searchD_keshi;
    private Button queryBtn;
    private Button clearBtn;
    private Button selectBtn;
    private Button deleteBtn;
    private Button updateInfoBtn;

    private RelativeLayout totalPanel;
    private RelativeLayout searchPanel;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_register);

        setActionBarTitle("挂号信息管理");

        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT, BTN_TYPE_IMG, R.drawable.ic_keyboard_arrow_left_black_24dp);
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT, BTN_TYPE_TEXT, "门诊挂号");
        showLeftButton();
        showRightButton();
        bindViewId();
        initData();

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
        updateInfoBtn = findViewById(R.id.update_info_btn);

        totalPanel = findViewById(R.id.total_panel);
        searchPanel = findViewById(R.id.title_panel);

        hosRegisterRecyclerView = findViewById(R.id.hos_register_list);

        manager = new LinearLayoutManager(this);
        hosRegisterRecyclerView.setLayoutManager(manager);
        hosRegisterRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {

        searchHosRegisterList();
        queryBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateInfoBtn.setOnClickListener(this);

        updateInfoBtn.setVisibility(View.INVISIBLE);

        hosRegisterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (manager.findFirstVisibleItemPosition() > 0) {
                    totalPanel.removeView(searchPanel);
                } else {
                    if (totalPanel.getChildAt(0).getId() != R.id.title_panel) {
                        totalPanel.addView(searchPanel, 0);
                    }
                }
            }
        });

        hosRegisterAdapter = new HosRegisterAdapter(activity, new ArrayList<>());
        hosRegisterRecyclerView.setAdapter(hosRegisterAdapter);

        // checkBox选中监听
        hosRegisterAdapter.setOnSelectListener(() -> {
            if (hosRegisterAdapter.getSelectedCount() > 0) {
                deleteBtn.setText("退号");
            } else {
                deleteBtn.setText("刷新");
            }
            if (hosRegisterAdapter.getSelectedCount() == 1) {
                updateInfoBtn.setVisibility(View.VISIBLE);
            } else {
                updateInfoBtn.setVisibility(View.INVISIBLE);
            }
            if (hosRegisterAdapter.getSelectedCount() < hosRegisterAdapter.getItemCount()) {
                selectBtn.setText("全选");
            } else {
                selectBtn.setText("取消全选");
            }
        });
    }

    /**
     * 获取 HosRegisterList （条件查询数据或查询所有数据）
     */
    private void searchHosRegisterList() {

        String hosR_idStr = searchHosR_id.getText().toString();
        String d_name = searchD_name.getText().toString();
        String d_keshi = searchD_keshi.getText().toString();

        Integer hosR_id = null;
        if (!hosR_idStr.equals("")) {
            try {
                hosR_id = Integer.parseInt(hosR_idStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                toast.showError("病历号只能为数字");
                return;
            }
        }

        HosRegisterTask hosRegisterTask = new HosRegisterTask(activity, hosR_id, d_name, d_keshi);
        hosRegisterTask.execute();

        hosRegisterTask.setOnSuccessListener(list -> hosRegisterAdapter.insertAllHosRegisters(list));

    }

    @Override
    protected void rightDoWhat() {
        Intent intent = new Intent(activity, AddHosRegisterActivity.class);
        startActivityForResult(intent, AddHosRegisterActivity.REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AddHosRegisterActivity.REQUEST &&
                resultCode == AddHosRegisterActivity.RESULT) {
            HosRegister hosRegister = new HosRegister();

            assert data != null;
            Bundle bundle = data.getExtras();

            assert bundle != null;
            hosRegister.setHosR_id(bundle.getInt("hosR_id"));
            hosRegister.setD_id(bundle.getInt("d_id"));
            hosRegister.setD_name(bundle.getString("d_name"));
            hosRegister.setD_keshi(bundle.getString("d_keshi"));
            hosRegister.setHosR_state(0);
            hosRegisterAdapter.insertHosRegister(hosRegister);
        }
        if (requestCode == UpdateHosRegisterActivity.REQUEST &&
                resultCode == UpdateHosRegisterActivity.RESULT) {
            assert data != null;
            Bundle bundle = data.getExtras();
            assert bundle != null;
            hosRegisterAdapter.updateHosRegister(bundle.getString("d_name"), bundle.getString("d_keshi"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_btn:
                searchHosRegisterList();
                updateInfoBtn.setVisibility(View.INVISIBLE);
                break;
            case R.id.clear_btn:
                searchHosR_id.setText("");
                searchD_name.setText("");
                searchD_keshi.setText("");
                searchHosRegisterList();
                updateInfoBtn.setVisibility(View.INVISIBLE);
                break;
            case R.id.select_btn:
                String name = selectBtn.getText().toString();
                if (name.equals("全选")) {
                    hosRegisterAdapter.selectAll();
                    selectBtn.setText("取消全选");
                } else if (name.equals("取消全选")) {
                    hosRegisterAdapter.cancelSelectAll();
                    selectBtn.setText("全选");
                }
                break;
            case R.id.delete_btn:
                if (hosRegisterAdapter.getSelectedCount() > 0 && deleteBtn.getText().toString().equals("退号")) {
                    hosRegisterAdapter.updateState();
                } else if (deleteBtn.getText().toString().equals("刷新")) {
                    String hosR_idStr = searchHosR_id.getText().toString();
                    String d_name = searchD_name.getText().toString();
                    String d_keshi = searchD_keshi.getText().toString();

                    Integer hosR_id = null;
                    if (!hosR_idStr.equals("")) {
                        try {
                            hosR_id = Integer.parseInt(hosR_idStr);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            toast.showError("病历号只能为数字");
                            return;
                        }
                    }
                    HosRegisterTask task = new HosRegisterTask(activity, hosR_id, d_name, d_keshi);
                    task.setOnSuccessListener(list -> hosRegisterAdapter.updateAllData(list));
                    task.execute();

                }
                break;
            case R.id.update_info_btn:
                Intent intent = new Intent(activity, UpdateHosRegisterActivity.class);
                intent.putExtra("hosR_id", hosRegisterAdapter.getSelectedHosR_id());
                intent.putExtra("d_keshi", hosRegisterAdapter.getD_KeShi());
                startActivityForResult(intent, UpdateHosRegisterActivity.REQUEST);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (hosRegisterAdapter.getSelectedCount() > 0) {
                hosRegisterAdapter.cancelSelectAll();
                deleteBtn.setText("刷新");
                updateInfoBtn.setVisibility(View.INVISIBLE);
            } else {
                finish();
            }
        }
        return false;
    }
}
