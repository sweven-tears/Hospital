package com.cdtc.hospital.network.dao.impl;

import com.cdtc.hospital.network.dao.BaseDao;
import com.cdtc.hospital.network.dao.DoctorDao;
import com.cdtc.hospital.network.entity.Doctor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl extends BaseDao implements DoctorDao {

    @Override
    public List<Doctor> queryDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        sql = "select * from doctor";
        ResultSet rs = query(sql, null);
        try {
            while (rs.next()) {
                Integer d_id = rs.getInt("d_id");
                String d_name = rs.getString("d_name");
                String d_idCard = rs.getString("d_idCard");
                String d_phone = rs.getString("d_phone");
                String d_telPhone = rs.getString("d_telPhone");
                Integer d_sex = rs.getInt("d_sex");
                Date d_birthday = rs.getDate("d_birthday");
                Integer d_age = rs.getInt("d_age");
                String d_email = rs.getString("d_email");
                String d_keshi = rs.getString("d_keshi");
                String d_xueli = rs.getString("d_xueli");
                String d_descr = rs.getString("d_descr");
                Date d_inTime = rs.getDate("d_inTime");
                Integer d_state = rs.getInt("d_state");
                Doctor doctor = new Doctor(d_id, d_name, d_idCard, d_phone, d_telPhone, d_sex, d_birthday, d_age,
                        d_email, d_keshi, d_xueli, d_descr, d_inTime, d_state);
                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*---------------------------------------以下为从未使用过的方法--------------------------------------------*/

    @Override
    public List<String> queryKeshiList() {
        List<String> doctors = new ArrayList<>();
        sql = "SELECT DISTINCT d_keshi FROM doctor";
        ResultSet rs = query(sql, null);
        try {
            while (rs.next()) {
                String d_keshi = rs.getString("d_keshi");
                doctors.add(d_keshi);
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> queryDoctorByKeshi(String d_keshi) {
        List<Doctor> doctors = new ArrayList<>();
        sql = "select * from doctor where d_keshi=?";
        Object[] objects = {d_keshi};
        rs = query(sql, objects);
        try {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                Integer d_id = rs.getInt("d_id");
                String d_name = rs.getString("d_name");
                doctor.setD_id(d_id);
                doctor.setD_name(d_name);
                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public String queryNameById(Integer d_id) {
        sql = "select d_name from doctor where d_id=?";
        Object[] objects = {d_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                return rs.getString("d_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

}
