package com.cdtc.hospital.local.dao.impl;

import android.app.Activity;
import android.database.Cursor;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.network.entity.Doctor;
import com.cdtc.hospital.network.entity.HosRegister;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sweven on 2018/12/1.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterLocalDaoImpl extends BaseLocalDao implements HosRegisterLocalDao {
    /**
     * @param activity 上下文
     * @param type     创建并读取库 or 更新库的数据
     */
    public HosRegisterLocalDaoImpl(Activity activity, int type) {
        super(activity, App.DATA_BASE, type);
    }

    @Override
    public List<HosRegister> queryHosRegisters() {
        List<HosRegister> hosRegisters = new ArrayList<>();
        String[] columns = new String[]{
                "hosR_id", "hosR_name",
                "hosR_idCard", "hosR_medical",
                "hosR_regPrice", "hosR_phone",
                "hosR_selfPrice", "hosR_sex",
                "hosR_age", "hosR_work",
                "hosR_lookDoctor", "d_id",
                "hosR_createTime", "hosR_remark",
                "hosR_state"};

        Cursor cursor = query(App.TABLE_HOS_REGISTER, columns, null, null);
        while (cursor.moveToNext()) {
            HosRegister hosRegister = new HosRegister();
            Integer hosR_id = cursor.getInt(cursor.getColumnIndex(columns[0]));
            String hosR_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String hosR_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String hosR_medical = cursor.getString(cursor.getColumnIndex(columns[3]));
            double hosR_regPrice = cursor.getDouble(cursor.getColumnIndex(columns[4]));
            String hosR_phone = cursor.getString(cursor.getColumnIndex(columns[5]));
            Integer hosR_selfPrice = cursor.getInt(cursor.getColumnIndex(columns[6]));
            Integer hosR_sex = cursor.getInt(cursor.getColumnIndex(columns[7]));
            Integer hosR_age = cursor.getInt(cursor.getColumnIndex(columns[8]));
            String hosR_work = cursor.getString(cursor.getColumnIndex(columns[9]));
            Integer hosR_lookDoctor = cursor.getInt(cursor.getColumnIndex(columns[10]));
            Integer d_id = cursor.getInt(cursor.getColumnIndex(columns[11]));
            String hosR_createTime = cursor.getString(cursor.getColumnIndex(columns[12]));
            String hosR_remark = cursor.getString(cursor.getColumnIndex(columns[13]));
            Integer hosR_state = cursor.getInt(cursor.getColumnIndex(columns[14]));
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
            hosRegister.setHosR_createTime(Date.valueOf(hosR_createTime));
            hosRegister.setHosR_remark(hosR_remark);
            hosRegister.setHosR_state(hosR_state);

            hosRegister.setD_id(d_id);
//            Cursor cursorChild = query(App.TABLE_DOCTOR, new String[]{"d_name", "d_keshi"}, "d_id=?", new String[]{String.valueOf(d_id)});
//            while (cursorChild.moveToNext()) {
//                hosRegister.setD_name(cursor.getString(cursor.getColumnIndex("d_name")));
//                hosRegister.setD_keshi(cursor.getString(cursor.getColumnIndex("d_keshi")));
//            }
            hosRegisters.add(hosRegister);

        }
        return hosRegisters;
    }

    @Override
    public List<HosRegister> selectByCondition(Integer d_id) {
        List<HosRegister> hosRegisters = new ArrayList<>();
        String[] columns = new String[]{
                "hosR_id", "hosR_name",
                "hosR_idCard", "hosR_medical",
                "hosR_regPrice", "hosR_phone",
                "hosR_selfPrice", "hosR_sex",
                "hosR_age", "hosR_work",
                "hosR_lookDoctor", "d_id",
                "hosR_createTime", "hosR_remark",
                "hosR_state"};
        StringBuilder builder = new StringBuilder(" 1=1 ");
        if (d_id != null) {
            builder.append(" and d_id=? ");
        }
        String[] selectionArgs = new String[]{String.valueOf(d_id)};

        Cursor cursor = query(App.TABLE_HOS_REGISTER, columns, builder.toString(), selectionArgs);
        while (cursor.moveToNext()) {
            HosRegister hosRegister = new HosRegister();
            Integer hosR_id = cursor.getInt(cursor.getColumnIndex(columns[0]));
            String hosR_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String hosR_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String hosR_medical = cursor.getString(cursor.getColumnIndex(columns[3]));
            double hosR_regPrice = cursor.getDouble(cursor.getColumnIndex(columns[4]));
            String hosR_phone = cursor.getString(cursor.getColumnIndex(columns[5]));
            Integer hosR_selfPrice = cursor.getInt(cursor.getColumnIndex(columns[6]));
            Integer hosR_sex = cursor.getInt(cursor.getColumnIndex(columns[7]));
            Integer hosR_age = cursor.getInt(cursor.getColumnIndex(columns[8]));
            String hosR_work = cursor.getString(cursor.getColumnIndex(columns[9]));
            Integer hosR_lookDoctor = cursor.getInt(cursor.getColumnIndex(columns[10]));
            d_id = cursor.getInt(cursor.getColumnIndex(columns[11]));
            String hosR_createTime = cursor.getString(cursor.getColumnIndex(columns[12]));
            String hosR_remark = cursor.getString(cursor.getColumnIndex(columns[13]));
            Integer hosR_state = cursor.getInt(cursor.getColumnIndex(columns[14]));
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
            hosRegister.setHosR_createTime(Date.valueOf(hosR_createTime));
            hosRegister.setHosR_remark(hosR_remark);
            hosRegister.setHosR_state(hosR_state);

            hosRegister.setD_id(d_id);
            Cursor cursorChild = query(App.TABLE_DOCTOR, new String[]{"d_name", "d_keshi"}, "d_id=?", new String[]{String.valueOf(d_id)});
            while (cursorChild.moveToNext()) {
                hosRegister.setD_name(cursor.getString(cursor.getColumnIndex("d_name")));
                hosRegister.setD_keshi(cursor.getString(cursor.getColumnIndex("d_keshi")));
            }
            hosRegisters.add(hosRegister);

        }
        return hosRegisters;
    }

    @Override
    public HosRegister queryHosRegisterByHosR_id(Integer hosR_id) {
        String[] columns = new String[]{
                "hosR_id", "hosR_name",
                "hosR_idCard", "hosR_medical",
                "hosR_regPrice", "hosR_phone",
                "hosR_selfPrice", "hosR_sex",
                "hosR_age", "hosR_work",
                "hosR_lookDoctor", "d_id",
                "hosR_createTime", "hosR_remark",
                "hosR_state"};
        String selection = "hosR_id=?";
        String[] selectionArgs = new String[]{String.valueOf(hosR_id)};

        Cursor cursor = query(App.TABLE_HOS_REGISTER, columns, selection, selectionArgs);
        if (cursor.moveToNext()) {
            HosRegister hosRegister = new HosRegister();
            String hosR_name = cursor.getString(cursor.getColumnIndex(columns[1]));
            String hosR_idCard = cursor.getString(cursor.getColumnIndex(columns[2]));
            String hosR_medical = cursor.getString(cursor.getColumnIndex(columns[3]));
            double hosR_regPrice = cursor.getDouble(cursor.getColumnIndex(columns[4]));
            String hosR_phone = cursor.getString(cursor.getColumnIndex(columns[5]));
            Integer hosR_selfPrice = cursor.getInt(cursor.getColumnIndex(columns[6]));
            Integer hosR_sex = cursor.getInt(cursor.getColumnIndex(columns[7]));
            Integer hosR_age = cursor.getInt(cursor.getColumnIndex(columns[8]));
            String hosR_work = cursor.getString(cursor.getColumnIndex(columns[9]));
            Integer hosR_lookDoctor = cursor.getInt(cursor.getColumnIndex(columns[10]));
            Integer d_id = cursor.getInt(cursor.getColumnIndex(columns[11]));
            String hosR_createTime = cursor.getString(cursor.getColumnIndex(columns[12]));
            String hosR_remark = cursor.getString(cursor.getColumnIndex(columns[13]));
            Integer hosR_state = cursor.getInt(cursor.getColumnIndex(columns[14]));
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
            hosRegister.setHosR_createTime(Date.valueOf(hosR_createTime));
            hosRegister.setHosR_remark(hosR_remark);
            hosRegister.setHosR_state(hosR_state);

            hosRegister.setD_id(d_id);
            Cursor cursorChild = query(App.TABLE_DOCTOR, new String[]{"d_name", "d_keshi"}, "d_id=?", new String[]{String.valueOf(d_id)});
            while (cursorChild.moveToNext()) {
                hosRegister.setD_name(cursor.getString(cursor.getColumnIndex("d_name")));
                hosRegister.setD_keshi(cursor.getString(cursor.getColumnIndex("d_keshi")));
            }
            return hosRegister;

        }
        return null;
    }

    @Override
    public Integer addHosRegister(HosRegister hosRegister) {
        Map<String, Object> map = new HashMap<>();
        map.put("hosR_id", hosRegister.getHosR_id());
        map.put("hosR_name", hosRegister.getHosR_name());
        map.put("hosR_idCard", hosRegister.getHosR_idCard());
        map.put("hosR_medical", hosRegister.getHosR_medical());
        map.put("hosR_regPrice", hosRegister.getHosR_regPrice());
        map.put("hosR_phone", hosRegister.getHosR_phone());
        map.put("hosR_selfPrice", hosRegister.getHosR_selfPrice());
        map.put("hosR_sex", hosRegister.getHosR_sex());
        map.put("hosR_age", hosRegister.getHosR_age());
        map.put("hosR_work", hosRegister.getHosR_work());
        map.put("hosR_lookDoctor", hosRegister.getHosR_lookDoctor());
        map.put("d_id", hosRegister.getD_id());
        map.put("hosR_createTime", hosRegister.getHosR_createTime());
        map.put("hosR_remark", hosRegister.getHosR_remark());
        map.put("hosR_state", hosRegister.getHosR_state());
        return (int) insert(App.TABLE_HOS_REGISTER, map);
    }

    @Override
    public Integer updateHosRegisterById(HosRegister hosRegister) {
        Map<String, Object> map = new HashMap<>();
        map.put("hosR_name", hosRegister.getHosR_name());
        map.put("hosR_idCard", hosRegister.getHosR_idCard());
        map.put("hosR_medical", hosRegister.getHosR_medical());
        map.put("hosR_regPrice", hosRegister.getHosR_regPrice());
        map.put("hosR_phone", hosRegister.getHosR_phone());
        map.put("hosR_selfPrice", hosRegister.getHosR_selfPrice());
        map.put("hosR_sex", hosRegister.getHosR_sex());
        map.put("hosR_age", hosRegister.getHosR_age());
        map.put("hosR_work", hosRegister.getHosR_work());
        map.put("hosR_lookDoctor", hosRegister.getHosR_lookDoctor());
        map.put("d_id", hosRegister.getD_id());
        map.put("hosR_createTime", hosRegister.getHosR_createTime());
        map.put("hosR_remark", hosRegister.getHosR_remark());
        map.put("hosR_state", hosRegister.getHosR_state());

        String whereClause="hosR_id=?";
        String[] whereArgs=new String[]{String.valueOf(hosRegister.getHosR_id())};

        return update(App.TABLE_BE_HOSPITAL,map,whereClause,whereArgs);
    }

    @Override
    public Integer deleteHosRegisterById(Integer hosR_id) {
        String whereClause="hosR_id=?";
        String[] whereArgs=new String[]{String.valueOf(hosR_id)};

        return delete(App.TABLE_BE_HOSPITAL,whereClause,whereArgs);
    }
}
