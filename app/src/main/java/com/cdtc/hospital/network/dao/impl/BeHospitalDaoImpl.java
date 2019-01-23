package com.cdtc.hospital.network.dao.impl;

import com.cdtc.hospital.entity.BeHospital;
import com.cdtc.hospital.network.dao.BaseDao;
import com.cdtc.hospital.network.dao.BeHospitalDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeHospitalDaoImpl extends BaseDao implements BeHospitalDao {

    @Override
    public List<BeHospital> selectBeHospitalByCondition(BeHospital beHospital) {
        List<BeHospital> beHospitals = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM "
                        + "(SELECT "
                        + " beH_id,"
                        + "  (SELECT hos.hosR_name FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS hosR_name,"
                        + " beH_patBed,"
                        + "(SELECT hos.hosR_phone FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS hosR_phone,"
                        + " beH_antecedent,"
                        + "(SELECT "
                        + "(SELECT doc.d_name FROM doctor AS doc WHERE doc.d_id=hos.d_id)"
                        + "FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS d_name,"
                        + "beH_createTime,"
                        + "(SELECT "
                        + "(SELECT doc.d_keshi FROM doctor AS doc WHERE doc.d_id=hos.d_id)"
                        + "FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS d_keshi,"
                        + "beH_state, "
                        + "(SELECT hos.hosR_state FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS hosR_state  "
                        + " FROM behospital AS be) AS beHospital WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (beHospital != null) {
            if (beHospital.getBeH_id() != null) {
                sql.append(" and beH_id like ? ");
                params.add("%" + beHospital.getBeH_id() + "%");
            }
            if (beHospital.getD_name() != null && !beHospital.getD_name().equals("")) {
                sql.append(" and d_name like ? ");
                params.add("%" + beHospital.getD_name() + "%");
            }
            if (beHospital.getD_keshi() != null && !beHospital.getD_keshi().equals("")) {
                sql.append(" and d_keshi like ? ");
                params.add("%" + beHospital.getD_keshi() + "%");
            }
        }

        rs = query(sql.toString(), params.toArray());
        try {
            while (rs.next()) {
                beHospital = new BeHospital();
                beHospital.setBeH_id(rs.getInt("beH_id"));
                beHospital.setHosR_name(rs.getString("hosR_name"));
                beHospital.setBeH_patBed(rs.getString("beH_patBed"));
                beHospital.setHosR_phone(rs.getString("hosR_phone"));
                beHospital.setBeH_antecedent(rs.getDouble("beH_antecedent"));
                beHospital.setD_name(rs.getString("d_name"));
                beHospital.setBeH_createTime(rs.getDate("beH_createTime"));
                beHospital.setD_keshi(rs.getString("d_keshi"));
                beHospital.setBeH_state(rs.getInt("beH_state"));
                beHospital.setHosR_state(rs.getInt("hosR_state"));
                beHospitals.add(beHospital);
            }
            return beHospitals;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Integer queryBeHospitalCount(BeHospital beHospital) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(*) count FROM "
                        + "(SELECT "
                        + " beH_id,"
                        + " beH_patBed,"
                        + "(SELECT hos.hosR_phone FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS hosR_phone,"
                        + " beH_antecedent,"
                        + "(SELECT "
                        + "(SELECT doc.d_name FROM doctor AS doc WHERE doc.d_id=hos.d_id)"
                        + "FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS d_name,"
                        + "beH_createTime,"
                        + "(SELECT "
                        + "(SELECT doc.d_keshi FROM doctor AS doc WHERE doc.d_id=hos.d_id)"
                        + "FROM hosregister hos WHERE hos.hosR_id=be.hosR_id) AS d_keshi,"
                        + "beH_state "
                        + " FROM behospital AS be) AS beHospital WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (beHospital != null) {
            if (beHospital.getBeH_id() != null) {
                sql.append(" and beH_id like ? ");
                params.add("%" + beHospital.getBeH_id() + "%");
            }
            if (beHospital.getD_name() != null && !beHospital.getD_name().equals("")) {
                sql.append(" and d_name like ? ");
                params.add("%" + beHospital.getD_name() + "%");
            }
            if (beHospital.getD_keshi() != null && !beHospital.getD_keshi().equals("")) {
                sql.append(" and d_keshi like ? ");
                params.add("%" + beHospital.getD_keshi() + "%");
            }
        }

        rs = query(sql.toString(), params.toArray());
        try {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    @Override
    public Integer updateHosR_StateById(Integer hosR_state, Integer beH_id) {
        sql = "UPDATE hosregister SET hosR_state=? WHERE  hosR_id=(SELECT beh.hosR_id FROM behospital beh WHERE beh.beH_id=?)";
        Object[] objects = {hosR_state, beH_id};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public Integer queryHosR_idById(Integer beH_id) {
        sql = "select hosR_id from behospital where beH_id=?";
        Object[] objects = {beH_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                return rs.getInt("hosR_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public BeHospital queryNurseBedById(Integer beH_id) {

        sql = "SELECT beH_nursePeople,beH_patBed FROM behospital WHERE beH_id=?";
        Object[] objects = {beH_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                BeHospital beHospital = new BeHospital();
                String beH_nursePeople = rs.getString("beH_nursePeople");
                String beH_patBed = rs.getString("beH_patBed");
                beHospital.setBeH_nursePeople(beH_nursePeople);
                beHospital.setBeH_patBed(beH_patBed);
                return beHospital;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateNurseBedById(String beH_nursePeople,
                                      String beH_patBed, Integer beH_id) {
        sql = "UPDATE behospital SET beH_nursePeople=? , beH_patBed=? WHERE beH_id=?";
        Object[] objects = {beH_nursePeople, beH_patBed, beH_id};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public Integer addBeHospital(BeHospital beHospital) {
        sql = "insert into behospital values(0,?,?,?,?,0,0,?,?)";
        Object[] objects = {beHospital.getBeH_nursePeople(),
                beHospital.getBeH_patBed(), beHospital.getBeH_antecedent(),
                beHospital.getBeH_illness(), beHospital.getHosR_id(), null};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public BeHospital queryBeHospitalByIdForAntecedent(Integer beH_id) {
        sql = " SELECT b.beH_id,b.beH_antecedent,"
                + "(SELECT h.hosR_name FROM hosregister h WHERE h.hosR_id=b.hosR_id ) AS hosR_name,"
                + " (SELECT h.hosR_idCard FROM hosregister h WHERE h.hosR_id=b.hosR_id ) AS hosR_idCard "
                + " FROM behospital b where beH_id=?;";
        Object[] objects = {beH_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                BeHospital beHospital = new BeHospital();
                beHospital.setBeH_id(rs.getInt("beH_id"));
                beHospital.setHosR_name(rs.getString("hosR_name"));
                beHospital.setHosR_idCard(rs.getString("hosR_idCard"));
                beHospital.setBeH_antecedent(rs.getDouble("beH_antecedent"));
                return beHospital;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Integer updateAntecedentById(Double beH_antecedent, Integer beH_id) {
        sql = "update behospital set beh_antecedent=? where beh_id=?";
        Object[] objects = {beH_antecedent, beH_id};
        int result = update(sql, objects);
        close();
        return result;
    }

}
