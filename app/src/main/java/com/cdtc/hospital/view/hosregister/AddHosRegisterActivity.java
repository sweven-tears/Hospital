package com.cdtc.hospital.view.hosregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.cdtc.hospital.R;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.entity.Doctor;
import com.cdtc.hospital.entity.HosRegister;
import com.cdtc.hospital.service.DoctorTask;
import com.cdtc.hospital.service.HosRegisterTask;

import java.util.ArrayList;
import java.util.List;

public class AddHosRegisterActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    public static final int REQUEST = 1001;
    public static final int RESULT = 1002;
    private EditText hosR_name;
    private EditText hosR_idCard;
    private EditText hosR_medical;
    private EditText hosR_regPrice;
    private EditText hosR_phone;
    private RadioGroup hosR_selfPrice;
    private RadioGroup hosR_sex;
    private EditText hosR_age;
    private EditText hosR_work;
    private RadioGroup hosR_lookDoctor;
    private Spinner d_keshi;
    private Spinner d_name;
    private EditText hosR_remark;
    private List<Doctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hos_register);

        setActionBarTitle("门诊挂号");

        bindViewId();
        initData();

        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT, BTN_TYPE_TEXT, "取消");
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT, BTN_TYPE_TEXT, "添加");
        showLeftButton();
        showRightButton();
    }

    @Override
    protected void bindViewId() {
        hosR_name = findViewById(R.id.hos_r_name);
        hosR_idCard = findViewById(R.id.hos_r_id_card);
        hosR_medical = findViewById(R.id.hos_r_medical);
        hosR_regPrice = findViewById(R.id.hos_r_reg_price);
        hosR_phone = findViewById(R.id.hos_r_phone);
        hosR_selfPrice = findViewById(R.id.hos_r_self_price);
        hosR_sex = findViewById(R.id.hos_r_sex);
        hosR_age = findViewById(R.id.hos_r_age);
        hosR_work = findViewById(R.id.hos_r_work);
        hosR_lookDoctor = findViewById(R.id.hos_r_look_doctor);
        d_keshi = findViewById(R.id.d_keshi);
        d_name = findViewById(R.id.d_name);
        hosR_remark = findViewById(R.id.hos_r_remark);

    }

    @Override
    protected void initData() {
        DoctorTask doctorTask = new DoctorTask(activity, null, DoctorTask.QUERY_KeShi_LIST, DoctorTask.TYPE_PROGRESS);
        doctorTask.execute();
        doctorTask.setOnSuccessListener((keShiList, nameList) -> {
            ArrayAdapter<String> keShiAdapter = new ArrayAdapter<>(AddHosRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, keShiList);
            keShiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            d_keshi.setAdapter(keShiAdapter);
            d_keshi.setOnItemSelectedListener(this);
        });

    }

    @Override
    protected void rightDoWhat() {
        attemptHosRegister();
    }

    /**
     * 验证所有输入是否为空并将数据添加到云服务器的数据库中
     */
    private void attemptHosRegister() {
        boolean isEmpty = false;
        View emptyView = null;

        String hosR_name = null;
        String hosR_idCard = null;
        String hosR_medical = null;
        String hosR_regPrice = null;
        String hosR_phone = null;
        String hosR_selfPrice = null;
        String hosR_sex = null;
        String hosR_age = null;
        String hosR_work = null;
        String hosR_lookDoctor = null;
        String d_keshi;
        String d_name;
        String hosR_remark = null;
        try {
            hosR_name = this.hosR_name.getText().toString();
            hosR_idCard = this.hosR_idCard.getText().toString();
            hosR_medical = this.hosR_medical.getText().toString();
            hosR_regPrice = this.hosR_regPrice.getText().toString();
            hosR_phone = this.hosR_phone.getText().toString();

            RadioButton hosR_selfPriceRadioButton = findViewById(this.hosR_selfPrice.getCheckedRadioButtonId());
            hosR_selfPrice = hosR_selfPriceRadioButton.getText().toString();

            RadioButton hosR_sexRadioButton = findViewById(this.hosR_sex.getCheckedRadioButtonId());
            hosR_sex = hosR_sexRadioButton.getText().toString();

            hosR_work = this.hosR_work.getText().toString();
            hosR_age = this.hosR_age.getText().toString();

            RadioButton hosR_lookDoctorRadioButton = findViewById(this.hosR_lookDoctor.getCheckedRadioButtonId());
            hosR_lookDoctor = hosR_lookDoctorRadioButton.getText().toString();

            d_keshi = this.d_keshi.getSelectedItem().toString();

            d_name = this.d_name.getSelectedItem().toString();

            hosR_remark = this.hosR_remark.getText().toString();

            if (hosR_name.equals("")) {
                emptyView = this.hosR_name;
                throw new Exception();
            }
            if (hosR_idCard.equals("")) {
                emptyView = this.hosR_idCard;
                throw new Exception();
            }
            if (hosR_medical.equals("")) {
                emptyView = this.hosR_medical;
                throw new Exception();
            }
            if (hosR_regPrice.equals("")) {
                emptyView = this.hosR_regPrice;
                throw new Exception();
            }
            if (hosR_phone.equals("")) {
                emptyView = this.hosR_phone;
                throw new Exception();
            }
            if (hosR_selfPrice.equals("")) {
                emptyView = this.hosR_selfPrice;
                throw new Exception();
            }
            if (hosR_sex.equals("")) {
                emptyView = this.hosR_sex;
                throw new Exception();
            }
            if (hosR_age.equals("")) {
                emptyView = this.hosR_age;
                throw new Exception();
            }
            if (hosR_work.equals("")) {
                emptyView = this.hosR_work;
                throw new Exception();
            }
            if (hosR_lookDoctor.equals("")) {
                emptyView = this.hosR_lookDoctor;
                throw new Exception();
            }
            if (d_keshi.equals("")) {
                emptyView = this.d_keshi;
                throw new Exception();
            }
            if (d_name.equals("")) {
                emptyView = this.d_name;
                throw new Exception();
            }

        } catch (Exception e) {
            e.printStackTrace();
            isEmpty = true;
        }

        if (isEmpty) {
            toast.showShort("还有信息未填写！");
            if (emptyView != null) {
                emptyView.requestFocus();
            }
        } else {
            Integer d_id = doctorList.get(this.d_name.getSelectedItemPosition()).getD_id();
            saveHosRegister(hosR_name, hosR_idCard,
                    hosR_medical, Double.parseDouble(hosR_regPrice),
                    hosR_phone, hosR_selfPrice.equals("是") ? 0 : 1,
                    hosR_sex.equals("男") ? 0 : 1, Integer.parseInt(hosR_age),
                    hosR_work, hosR_lookDoctor.equals("初诊") ? 0 : 1,
                    hosR_remark, d_id);
        }

    }

    /**
     * 保存新的挂号信息
     *
     * @param hosR_name       病人姓名
     * @param hosR_idCard     病人身份证
     * @param hosR_medical    病人社保卡
     * @param hosR_regPrice   挂号费
     * @param hosR_phone      电话
     * @param hosR_selfPrice  是否自费
     * @param hosR_sex        性别
     * @param hosR_age        年龄
     * @param hosR_work       职业
     * @param hosR_lookDoctor 初诊or复诊
     * @param hosR_remark     备注
     * @param d_id            医生id
     */
    private void saveHosRegister(String hosR_name, String hosR_idCard, String hosR_medical, double hosR_regPrice, String hosR_phone, int hosR_selfPrice, int hosR_sex, int hosR_age, String hosR_work, int hosR_lookDoctor, String hosR_remark, Integer d_id) {
        HosRegister hosRegister = new HosRegister();
        hosRegister.setHosR_id(0);
        hosRegister.setHosR_name(hosR_name);
        hosRegister.setHosR_idCard(hosR_idCard);
        hosRegister.setHosR_medical(hosR_medical);
        hosRegister.setHosR_regPrice(hosR_regPrice);
        hosRegister.setHosR_phone(hosR_phone);
        hosRegister.setHosR_selfPrice(hosR_selfPrice);
        hosRegister.setHosR_sex(hosR_sex);
        hosRegister.setHosR_age(hosR_age);
        hosRegister.setHosR_work(hosR_work);
        hosRegister.setHosR_lookDoctor(hosR_lookDoctor);
        hosRegister.setHosR_createTime(null);
        hosRegister.setHosR_remark(hosR_remark);
        hosRegister.setHosR_state(0);
        hosRegister.setD_id(d_id);

        try {
            HosRegisterTask hosRegisterTask = new HosRegisterTask(activity, hosRegister, HosRegisterTask.TASK_INSERT);
            hosRegisterTask.setOnSuccessListener((List<HosRegister> list) -> {
                Integer hosR_id = list.get(0).getHosR_id();
                backList(hosR_id);
            });
            hosRegisterTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String keshi = d_keshi.getSelectedItem().toString();
        DoctorTask doctorTask = new DoctorTask(activity, keshi, DoctorTask.QUERY_NAME_LIST, DoctorTask.TYPE_NON_PROGRESS);
        doctorTask.execute();
        doctorTask.setOnSuccessListener((keShiList, doctorList) -> {
            AddHosRegisterActivity.this.doctorList = doctorList;
            List<String> nameList = new ArrayList<>();
            for (Doctor doctor : doctorList) {
                nameList.add(doctor.getD_name());
            }
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameList);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            d_name.setAdapter(nameAdapter);
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * 返回上一个activity
     *
     * @param hosR_id 挂号id，病历号
     */
    private void backList(Integer hosR_id) {

        Intent intent = new Intent(activity, HosRegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("hosR_id", hosR_id);
        bundle.putInt("d_id", doctorList.get(d_name.getSelectedItemPosition()).getD_id());
        bundle.putString("d_name", d_name.getSelectedItem().toString());
        bundle.putString("d_keshi", d_keshi.getSelectedItem().toString());
        intent.putExtras(bundle);
        setResult(RESULT, intent);
        finish();
    }


}
