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

public class UpdateHosRegisterActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    public static final int REQUEST = 2001;
    public static final int RESULT = 2002;
    private EditText hosR_name;
    private EditText hosR_idCard;
    private EditText hosR_medical;
    private EditText hosR_regPrice;
    private EditText hosR_phone;
    private RadioGroup hosR_selfPrice;
    private RadioButton hosR_selfPrice_No;
    private RadioButton hosR_selfPrice_Yes;
    private RadioGroup hosR_sex;
    private RadioButton hosR_sex_man;
    private RadioButton hosR_sex_woman;
    private EditText hosR_age;
    private EditText hosR_work;
    private RadioGroup hosR_lookDoctor;
    private RadioButton hosR_lookDoctor_first;
    private RadioButton hosR_lookDoctor_double;
    private Spinner d_keshi;
    private Spinner d_name;
    private EditText hosR_remark;
    private List<Doctor> doctorList;
    private Integer hosR_id;
    private boolean isFirstLaunch = true;
    private HosRegister hosRegister;
    private List<String> keShiList;
    private int success = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hos_register);

        setActionBarTitle("修改信息");

        bindViewId();
        initData();

        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT, BTN_TYPE_TEXT, "取消");
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT, BTN_TYPE_TEXT, "更新");
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

        hosR_selfPrice_No = findViewById(R.id.hos_r_self_price_no);
        hosR_selfPrice_Yes = findViewById(R.id.hos_r_self_price_yes);
        hosR_sex_man = findViewById(R.id.hos_r_sex_man);
        hosR_sex_woman = findViewById(R.id.hos_r_sex_woman);
        hosR_lookDoctor_first = findViewById(R.id.hos_r_look_doctor_first);
        hosR_lookDoctor_double = findViewById(R.id.hos_r_look_doctor_double);

    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        hosR_id = intent.getIntExtra("hosR_id", 1001);
        String d_keshi = intent.getStringExtra("d_keshi");


        HosRegisterTask hosRegisterTask = new HosRegisterTask(activity, hosR_id);
        hosRegisterTask.execute();
        hosRegisterTask.setOnSuccessListener(list -> {
            hosRegister = list.get(0);
            success++;
            addInfo(success);
        });

        DoctorTask doctorTask = new DoctorTask(activity, null, DoctorTask.QUERY_KeShi_LIST, DoctorTask.TYPE_PROGRESS);
        doctorTask.execute();
        doctorTask.setOnSuccessListener((keShiList, nameList) -> {
            UpdateHosRegisterActivity.this.keShiList = keShiList;
            success++;
            addInfo(success);
        });

        DoctorTask doctorTask2 = new DoctorTask(activity, d_keshi, DoctorTask.QUERY_NAME_LIST, DoctorTask.TYPE_PROGRESS);
        doctorTask2.execute();
        doctorTask2.setOnSuccessListener((keShiList, nameList) -> {
            UpdateHosRegisterActivity.this.doctorList = nameList;
            success++;
            addInfo(success);
        });


    }

    /**
     * @param success task success num
     */
    private void addInfo(int success) {
        if (success >= 3) {
            ArrayAdapter<String> keShiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, keShiList);
            keShiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            d_keshi.setAdapter(keShiAdapter);
            d_keshi.setOnItemSelectedListener(this);


            hosR_name.setText(hosRegister.getHosR_name());
            hosR_idCard.setText(hosRegister.getHosR_idCard());
            hosR_medical.setText(hosRegister.getHosR_medical());
            hosR_regPrice.setText(String.valueOf(hosRegister.getHosR_regPrice()));
            hosR_phone.setText(hosRegister.getHosR_phone());

            if (hosRegister.getHosR_selfPrice() == 1) {
                hosR_selfPrice_No.setChecked(true);
            } else {
                hosR_selfPrice_Yes.setChecked(true);
            }

            if (hosRegister.getHosR_sex() == 0) {
                hosR_sex_man.setChecked(true);
            } else {
                hosR_sex_woman.setChecked(true);
            }
            hosR_age.setText(String.valueOf(hosRegister.getHosR_age()));
            hosR_work.setText(hosRegister.getHosR_work());

            if (hosRegister.getHosR_lookDoctor() == 0) {
                hosR_lookDoctor_first.setChecked(true);
            } else {
                hosR_lookDoctor_double.setChecked(true);
            }

            int position = 0;
            for (int i = 0; i < keShiList.size(); i++) {
                if (keShiList.get(i).equals(hosRegister.getD_keshi())) {
                    position = i;
                }
            }
            d_keshi.setSelection(position);

            List<String> nameList = new ArrayList<>();
            for (Doctor doctor2 : doctorList) {
                nameList.add(doctor2.getD_name());
            }
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameList);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            d_name.setAdapter(nameAdapter);

            for (int i = 0; i < nameList.size(); i++) {
                if (nameList.get(i).equals(hosRegister.getD_name())) {
                    position = i;
                    break;
                }
            }
            d_name.setSelection(position);

            hosR_remark.setText(hosRegister.getHosR_remark());
        }
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
            updateHosRegister(hosR_name, hosR_idCard,
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
     * @param hosR_name       姓名
     * @param hosR_idCard     身份证号
     * @param hosR_medical    医保卡号
     * @param hosR_regPrice   押金
     * @param hosR_phone      电话
     * @param hosR_selfPrice  是否自费
     * @param hosR_sex        性别
     * @param hosR_age        年龄
     * @param hosR_work       职业
     * @param hosR_lookDoctor 初诊or复诊
     * @param hosR_remark     备注
     * @param d_id            医生id
     */
    private void updateHosRegister(String hosR_name, String hosR_idCard, String hosR_medical, double hosR_regPrice, String hosR_phone, int hosR_selfPrice, int hosR_sex, int hosR_age, String hosR_work, int hosR_lookDoctor, String hosR_remark, Integer d_id) {
        HosRegister hosRegister = new HosRegister();
        hosRegister.setHosR_id(hosR_id);
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
        hosRegister.setHosR_remark(hosR_remark);
        hosRegister.setD_id(d_id);

        try {
            HosRegisterTask hosRegisterTask = new HosRegisterTask(activity, hosRegister, HosRegisterTask.TASK_UPDATE);
            hosRegisterTask.setOnSuccessListener(list -> finish());
            hosRegisterTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        backList();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!isFirstLaunch) {
            String keshi = d_keshi.getSelectedItem().toString();
            DoctorTask doctorTask = new DoctorTask(activity, keshi, DoctorTask.QUERY_NAME_LIST, DoctorTask.TYPE_NON_PROGRESS);
            doctorTask.execute();
            doctorTask.setOnSuccessListener((keShiList, doctorList) -> {
                UpdateHosRegisterActivity.this.doctorList = doctorList;
                List<String> nameList = new ArrayList<>();
                for (Doctor doctor : doctorList) {
                    nameList.add(doctor.getD_name());
                }
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameList);
                nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                d_name.setAdapter(nameAdapter);
            });

        } else {
            isFirstLaunch = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * 返回上一个activity
     */
    private void backList() {
        Intent intent = new Intent(activity, HosRegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("d_name", (String) d_name.getSelectedItem());
        bundle.putString("d_keshi", (String) d_keshi.getSelectedItem());
        intent.putExtras(bundle);
        setResult(RESULT, intent);
    }
}
