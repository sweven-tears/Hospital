package com.cdtc.hospital.network.dao.impl;

import com.cdtc.hospital.network.dao.BaseDao;
import com.cdtc.hospital.network.dao.HosRegisterDao;
import com.cdtc.hospital.entity.HosRegister;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HosRegisterDaoImpl extends BaseDao implements HosRegisterDao {

    @Override
    public List<HosRegister> queryHosRegisters() {
        List<HosRegister> hosRegisters = new ArrayList<>();
        sql = "SELECT * FROM hosregister";

        rs = query(sql, null);
        try {
            while (rs.next()) {
                HosRegister hosRegister = new HosRegister();
                hosRegister.setHosR_id(rs.getInt("hosR_id"));
                hosRegister.setHosR_name(rs.getString("hosR_name"));
                hosRegister.setHosR_idCard(rs.getString("hosR_idCard"));
                hosRegister.setHosR_medical(rs.getString("hosR_medical"));
                hosRegister.setHosR_regPrice(rs.getDouble("hosR_regPrice"));
                hosRegister.setHosR_phone(rs.getString("hosR_phone"));
                hosRegister.setHosR_selfPrice(rs.getInt("hosR_selfPrice"));
                hosRegister.setHosR_sex(rs.getInt("hosR_sex"));
                hosRegister.setHosR_age(rs.getInt("hosR_age"));
                hosRegister.setHosR_work(rs.getString("hosR_work"));
                hosRegister.setHosR_lookDoctor(rs.getInt("hosR_lookDoctor"));
                hosRegister.setD_id(rs.getInt("d_id"));
                hosRegister.setHosR_createTime(rs.getDate("hosR_createTime"));
                hosRegister.setHosR_remark(rs.getString("hosR_remark"));
                hosRegister.setHosR_state(rs.getInt("hosR_state"));
                hosRegisters.add(hosRegister);
            }
            return hosRegisters;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Integer addHosRegister(HosRegister hosRegister) {
        sql = "insert into hosregister values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] objects = {0,
                hosRegister.getHosR_name(),
                hosRegister.getHosR_idCard(),
                hosRegister.getHosR_medical(),
                hosRegister.getHosR_regPrice(),
                hosRegister.getHosR_phone(),
                hosRegister.getHosR_selfPrice(),
                hosRegister.getHosR_sex(),
                hosRegister.getHosR_age(),
                hosRegister.getHosR_work(),
                hosRegister.getHosR_lookDoctor(),
                hosRegister.getD_id(),
                null,
                hosRegister.getHosR_remark(),
                0};
        return update(sql, objects);
    }

    @Override
    public Integer updateHosRegisterById(HosRegister hosRegister) {
        sql = "UPDATE hosregister SET " +
                "hosR_name=?," +
                "hosR_idCard=?," +
                "hosR_medical=?," +
                "hosR_regPrice=?," +
                "hosR_phone=?," +
                "hosR_selfPrice=?," +
                "hosR_sex=?," +
                "hosR_age=?," +
                "hosR_work=?," +
                "hosR_lookDoctor=?," +
                "d_id=?," +
                "hosR_remark=? " +
                " WHERE hosR_id=?";
        Object[] objects = {
                hosRegister.getHosR_name(),
                hosRegister.getHosR_idCard(),
                hosRegister.getHosR_medical(),
                hosRegister.getHosR_regPrice(),
                hosRegister.getHosR_phone(),
                hosRegister.getHosR_selfPrice(),
                hosRegister.getHosR_sex(),
                hosRegister.getHosR_age(),
                hosRegister.getHosR_work(),
                hosRegister.getHosR_lookDoctor(),
                hosRegister.getD_id(),
                hosRegister.getHosR_remark(),
                hosRegister.getHosR_id()};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public Integer updateStateById(Integer hosR_id, Integer hosR_state) {
        sql = "UPDATE hosregister SET hosR_state=? WHERE hosR_id=?";
        Object[] objects = {hosR_state, hosR_id};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public Integer deleteHosRegisterById(Integer hosR_id) {
        sql = "delete from hosregister where hosR_id=?";
        Object[] objects = {hosR_id};
        int result = update(sql, objects);
        close();
        return result;
    }



    /*---------------------------------------以下为从未使用过的方法--------------------------------------------*/

    @Override
    public List<HosRegister> selectByCondition(Integer hosR_id, String d_name, String d_keshi) {
        List<HosRegister> hosRegisters = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM " + "(SELECT hos.hosR_id,"
                + "(SELECT d.d_name FROM doctor d WHERE d.d_id=hos.d_id) AS d_name," + "hos.hosR_createTime,"
                + "(SELECT d.d_keshi FROM doctor d WHERE d.d_id=hos.d_id) AS d_keshi," + "hos.hosR_state"
                + " FROM hosregister AS hos)  " + " hosregister " + "WHERE 1=1 ");
        if (hosR_id != null) {
            sql.append(" and hosR_id like ? ");
            params.add("%" + hosR_id + "%");
        }
        if (d_name != null && !d_name.equals("")) {
            sql.append(" and d_name like ? ");
            params.add("%" + d_name + "%");
        }
        if (d_keshi != null && !d_keshi.equals("")) {
            sql.append(" and d_keshi like ? ");
            params.add("%" + d_keshi + "%");
        }

        rs = query(sql.toString(), params.toArray());
        try {
            while (rs.next()) {
                HosRegister hosregister = new HosRegister();
                hosregister.setHosR_id(rs.getInt("hosR_id"));
                hosregister.setD_name(rs.getString("d_name"));
                hosregister.setHosR_createTime(rs.getDate("hosR_createTime"));
                hosregister.setD_keshi(rs.getString("d_keshi"));
                hosregister.setHosR_state(rs.getInt("hosR_state"));
                hosRegisters.add(hosregister);
            }
            return hosRegisters;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Integer getHosRegisterCount(Integer hosR_id, String d_name, String d_keshi) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM " + "(SELECT hos.hosR_id,"
                + "(SELECT d.d_name FROM doctor d WHERE d.d_id=hos.d_id) AS d_name," + "hos.hosR_createTime,"
                + "(SELECT d.d_keshi FROM doctor d WHERE d.d_id=hos.d_id) AS d_keshi," + "hos.hosR_state"
                + " FROM hosregister AS hos)  " + "hosregister " + "WHERE 1=1 ");
        if (hosR_id != null) {
            sql.append(" and hosR_id like ? ");
            params.add("%" + hosR_id + "%");
        }
        if (d_name != null && !d_name.equals("")) {
            sql.append(" and d_name like ? ");
            params.add("%" + d_name + "%");
        }
        if (d_keshi != null && !d_keshi.equals("")) {
            sql.append(" and d_keshi like ? ");
            params.add("%" + d_keshi + "%");
        }

        rs = query(sql.toString(), params.toArray());
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    @Override
    public HosRegister queryHosRegisterByHosR_id(Integer hosR_id) {
        sql = "SELECT " + "h.hosR_id," + "h.hosR_name," + "hosR_idCard," + "hosR_regPrice," + "hosR_medical,"
                + "hosR_phone," + "hosR_selfPrice," + "hosR_sex," + "hosR_age," + "hosR_work," + "hosR_lookDoctor,"
                + "hosR_createTime," + "d_id," + "(SELECT d.d_keshi FROM doctor d WHERE d.d_id=h.d_id) AS d_keshi,"
                + "(SELECT d.d_name FROM doctor d WHERE d.d_id=h.d_id) AS d_name," + "hosR_remark," + "hosR_state "
                + "FROM hosregister h WHERE hosR_id=?";
        Object[] objects = {hosR_id};
        HosRegister hosRegister = null;
        rs = query(sql, objects);
        try {
            while (rs.next()) {
                hosRegister = new HosRegister();
                hosRegister.setHosR_id(rs.getInt("hosR_id"));
                hosRegister.setHosR_name(rs.getString("hosR_name"));
                hosRegister.setHosR_idCard(rs.getString("hosR_idCard"));
                hosRegister.setHosR_regPrice(rs.getDouble("hosR_regPrice"));
                hosRegister.setHosR_medical(rs.getString("hosR_medical"));
                hosRegister.setHosR_phone(rs.getString("hosR_phone"));
                hosRegister.setHosR_selfPrice(rs.getInt("hosR_selfPrice"));
                hosRegister.setHosR_sex(rs.getInt("hosR_sex"));
                hosRegister.setHosR_age(rs.getInt("hosR_age"));
                hosRegister.setHosR_work(rs.getString("hosR_work"));
                hosRegister.setHosR_lookDoctor(rs.getInt("hosR_lookDoctor"));
                hosRegister.setHosR_createTime(rs.getDate("hosR_createTime"));
                hosRegister.setD_id(rs.getInt("d_id"));
                hosRegister.setD_keshi(rs.getString("d_keshi"));
                hosRegister.setD_name(rs.getString("d_name"));
                hosRegister.setHosR_state(rs.getInt("hosR_state"));
                hosRegister.setHosR_remark(rs.getString("hosR_remark"));
            }
            return hosRegister;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

}
