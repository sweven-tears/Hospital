package com.cdtc.hospital.local.dao.impl;

import android.app.Activity;
import android.database.Cursor;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.DoctorLocalDao;
import com.cdtc.hospital.network.entity.Doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sweven on 2018/12/1.
 * Email:sweventears@Foxmail.com
 */
public class DoctorLocalDaoImpl extends BaseLocalDao implements DoctorLocalDao {

    /**
     * @param activity 上下文
     * @param type     创建并读取库 or 更新库的数据
     */
    public DoctorLocalDaoImpl(Activity activity, int type) {
        super(activity, App.DATA_BASE, type);
    }

    @Override
    public List<Doctor> queryDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String[] columns = new String[]{
                "d_id", "d_name",
                "d_idCard", "d_phone",
                "d_telPhone", "d_sex",
                "d_birthday", "d_age",
                "d_email", "d_keshi",
                "d_xueli", "d_descr",
                "d_inTime", "d_state"};
        Cursor cursor = query(App.TABLE_DOCTOR, columns, null, null);
        while (cursor.moveToNext()) {
            Doctor doctor = new Doctor();
            Integer d_id = cursor.getInt(cursor.getColumnIndex(columns[0]));
            String d_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String d_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String d_phone = cursor.getString(cursor.getColumnIndex(columns[3]));
            String d_telPhone = cursor.getString(cursor.getColumnIndex(columns[4]));
            Integer d_sex = cursor.getInt(cursor.getColumnIndex(columns[5]));
            String d_birthdayStr = cursor.getString(cursor.getColumnIndex(columns[6]));
            Integer d_age = cursor.getInt(cursor.getColumnIndex(columns[7]));
            String d_email = cursor.getString(cursor.getColumnIndex(columns[8]));
            String d_keshi = cursor.getString(cursor.getColumnIndex(columns[9]));
            String d_xueli = cursor.getString(cursor.getColumnIndex(columns[10]));
            String d_descr = cursor.getString(cursor.getColumnIndex(columns[11]));
            String d_inTimeStr = cursor.getString(cursor.getColumnIndex(columns[12]));
            Integer d_state = cursor.getInt(cursor.getColumnIndex(columns[13]));

            doctor.setD_id(d_id);
            doctor.setD_name(d_name);
            doctor.setD_idCard(d_idCard);
            doctor.setD_phone(d_phone);
            doctor.setD_telPhone(d_telPhone);
            doctor.setD_sex(d_sex);
            doctor.setD_birthday(Date.valueOf(d_birthdayStr));
            doctor.setD_age(d_age);
            doctor.setD_email(d_email);
            doctor.setD_keshi(d_keshi);
            doctor.setD_xueli(d_xueli);
            doctor.setD_descr(d_descr);
            doctor.setD_inTime(Date.valueOf(d_inTimeStr));
            doctor.setD_state(d_state);
            doctors.add(doctor);
        }
        return doctors;
    }

    @Override
    public List<String> queryKeshiList() {
        Set<String> set=new HashSet<>();
        String[] columns=new String[]{"d_keshi"};
        Cursor cursor=query(App.TABLE_DOCTOR,columns,null,null);
        while (cursor.moveToNext()){
            set.add(cursor.getString(cursor.getColumnIndex(columns[0])));
        }
        return new ArrayList<>(set);
    }

    @Override
    public List<String> queryNameByKeshi(String d_keshi) {
        Set<String> set=new HashSet<>();
        String[] columns=new String[]{"d_name"};
        String selection="d_keshi=?";
        String[] selectionArgs=new String[]{d_keshi};
        Cursor cursor=query(App.TABLE_DOCTOR,columns,selection,selectionArgs);
        while (cursor.moveToNext()){
            set.add(cursor.getString(cursor.getColumnIndex(columns[0])));
        }
        return new ArrayList<>(set);
    }

    @Override
    public List<Doctor> queryDoctorByKeshi(String d_keshi) {
        List<Doctor> doctors = new ArrayList<>();
        String[] columns = new String[]{
                "d_id", "d_name",
                "d_idCard", "d_phone",
                "d_telPhone", "d_sex",
                "d_birthday", "d_age",
                "d_email", "d_keshi",
                "d_xueli", "d_descr",
                "d_inTime", "d_state"};
        String selection = "d_keshi=?";
        String[] selectionArgs = new String[]{d_keshi};
        Cursor cursor = query(App.TABLE_DOCTOR, columns, selection, selectionArgs);
        while (cursor.moveToNext()) {
            Doctor doctor = new Doctor();
            Integer d_id = cursor.getInt(cursor.getColumnIndex(columns[0]));
            String d_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String d_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String d_phone = cursor.getString(cursor.getColumnIndex(columns[3]));
            String d_telPhone = cursor.getString(cursor.getColumnIndex(columns[4]));
            Integer d_sex = cursor.getInt(cursor.getColumnIndex(columns[5]));
            String d_birthdayStr = cursor.getString(cursor.getColumnIndex(columns[6]));
            Integer d_age = cursor.getInt(cursor.getColumnIndex(columns[7]));
            String d_email = cursor.getString(cursor.getColumnIndex(columns[8]));
            String d_xueli = cursor.getString(cursor.getColumnIndex(columns[10]));
            String d_descr = cursor.getString(cursor.getColumnIndex(columns[11]));
            String d_inTimeStr = cursor.getString(cursor.getColumnIndex(columns[12]));
            Integer d_state = cursor.getInt(cursor.getColumnIndex(columns[13]));

            doctor.setD_id(d_id);
            doctor.setD_name(d_name);
            doctor.setD_idCard(d_idCard);
            doctor.setD_phone(d_phone);
            doctor.setD_telPhone(d_telPhone);
            doctor.setD_sex(d_sex);
            doctor.setD_birthday(Date.valueOf(d_birthdayStr));
            doctor.setD_age(d_age);
            doctor.setD_email(d_email);
            doctor.setD_keshi(d_keshi);
            doctor.setD_xueli(d_xueli);
            doctor.setD_descr(d_descr);
            doctor.setD_inTime(Date.valueOf(d_inTimeStr));
            doctor.setD_state(d_state);
            doctors.add(doctor);
        }
        return doctors;
    }

    @Override
    public String queryNameById(Integer d_id) {
        String d_name = null;
        String[] columns = new String[]{"d_name"};
        String selection = "d_id=?";
        String[] selectionArg = new String[]{String.valueOf(d_id)};
        Cursor cursor = query(App.TABLE_DOCTOR, columns, selection, selectionArg);
        while (cursor.moveToNext()) {
            d_name = cursor.getString(cursor.getColumnIndex(columns[0]));
        }
        return d_name;
    }

    @Override
    public Doctor queryDoctorById(Integer d_id) {
        String[] columns = new String[]{
                "d_id", "d_name",
                "d_idCard", "d_phone",
                "d_telPhone", "d_sex",
                "d_birthday", "d_age",
                "d_email", "d_keshi",
                "d_xueli", "d_descr",
                "d_inTime", "d_state"};
        String selection = "d_id=?";
        String[] selectionArgs = new String[]{String.valueOf(d_id)};
        Cursor cursor = query(App.TABLE_DOCTOR, columns, selection, selectionArgs);
        if (cursor.moveToNext()) {
            Doctor doctor = new Doctor();
            String d_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String d_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String d_phone = cursor.getString(cursor.getColumnIndex(columns[3]));
            String d_telPhone = cursor.getString(cursor.getColumnIndex(columns[4]));
            Integer d_sex = cursor.getInt(cursor.getColumnIndex(columns[5]));
            String d_birthdayStr = cursor.getString(cursor.getColumnIndex(columns[6]));
            Integer d_age = cursor.getInt(cursor.getColumnIndex(columns[7]));
            String d_email = cursor.getString(cursor.getColumnIndex(columns[8]));
            String d_keshi = cursor.getString(cursor.getColumnIndex(columns[9]));
            String d_xueli = cursor.getString(cursor.getColumnIndex(columns[10]));
            String d_descr = cursor.getString(cursor.getColumnIndex(columns[11]));
            String d_inTimeStr = cursor.getString(cursor.getColumnIndex(columns[12]));
            Integer d_state = cursor.getInt(cursor.getColumnIndex(columns[13]));

            doctor.setD_id(d_id);
            doctor.setD_name(d_name);
            doctor.setD_idCard(d_idCard);
            doctor.setD_phone(d_phone);
            doctor.setD_telPhone(d_telPhone);
            doctor.setD_sex(d_sex);
            doctor.setD_birthday(Date.valueOf(d_birthdayStr));
            doctor.setD_age(d_age);
            doctor.setD_email(d_email);
            doctor.setD_keshi(d_keshi);
            doctor.setD_xueli(d_xueli);
            doctor.setD_descr(d_descr);
            doctor.setD_inTime(Date.valueOf(d_inTimeStr));
            doctor.setD_state(d_state);
            return doctor;
        }
        return null;
    }

    @Override
    public long insertDoctor(Doctor doctor) {
        Map<String, Object> map = new HashMap<>();
        map.put("d_id", doctor.getD_id());
        map.put("d_name", doctor.getD_name());
        map.put("d_idCard", doctor.getD_idCard());
        map.put("d_phone", doctor.getD_phone());
        map.put("d_telPhone", doctor.getD_telPhone());
        map.put("d_sex", doctor.getD_sex());
        map.put("d_birthday", doctor.getD_birthday());
        map.put("d_age", doctor.getD_age());
        map.put("d_email", doctor.getD_email());
        map.put("d_keshi", doctor.getD_keshi());
        map.put("d_xueli", doctor.getD_xueli());
        map.put("d_descr", doctor.getD_descr());
        map.put("d_inTime", doctor.getD_inTime());
        map.put("d_state", doctor.getD_state());
        return insert(App.TABLE_DOCTOR, map);
    }
}
