package com.cdtc.hospital.network.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cdtc.hospital.network.dao.AccountDao;
import com.cdtc.hospital.network.dao.BaseDao;
import com.cdtc.hospital.network.entity.Account;

public class AccountDaoImpl extends BaseDao implements AccountDao {

    @Override
    public List<Object> selectAccountByPageCondition(Account account) {
        List<Object> accounts = new ArrayList<>();
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
                        + " FROM behospital AS be) AS account WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (account != null) {
            if (account.getBeH_id() != null) {
                sql.append(" and beH_id like ? ");
                params.add("%" + account.getBeH_id() + "%");
            }
            if (account.getD_name() != null && !account.getD_name().equals("")) {
                sql.append(" and d_name like ? ");
                params.add("%" + account.getD_name() + "%");
            }
            if (account.getD_keshi() != null && !account.getD_keshi().equals("")) {
                sql.append(" and d_keshi like ? ");
                params.add("%" + account.getD_keshi() + "%");
            }
        }

        rs = query(sql.toString(), params.toArray());
        try {
            while (rs.next()) {
                account = new Account();
                account.setBeH_id(rs.getInt("beH_id"));
                account.setHosR_name(rs.getString("hosR_name"));
                account.setBeH_patBed(rs.getString("beH_patBed"));
                account.setHosR_phone(rs.getString("hosR_phone"));
                account.setBeH_antecedent(rs.getDouble("beH_antecedent"));
                account.setD_name(rs.getString("d_name"));
                account.setBeH_createTime(rs.getDate("beH_createTime"));
                account.setD_keshi(rs.getString("d_keshi"));
                account.setBeH_state(rs.getInt("beH_state"));
                account.setHosR_state(rs.getInt("hosR_state"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Integer queryAccountCount(Account account) {
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
                        + " FROM behospital AS be) AS account WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (account != null) {
            if (account.getBeH_id() != null) {
                sql.append(" and beH_id like ? ");
                params.add("%" + account.getBeH_id() + "%");
            }
            if (account.getD_name() != null && !account.getD_name().equals("")) {
                sql.append(" and d_name like ? ");
                params.add("%" + account.getD_name() + "%");
            }
            if (account.getD_keshi() != null && !account.getD_keshi().equals("")) {
                sql.append(" and d_keshi like ? ");
                params.add("%" + account.getD_keshi() + "%");
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
    public Account queryNurseBedById(Integer beH_id) {

        sql = "SELECT beH_nursePeople,beH_patBed FROM behospital WHERE beH_id=?";
        Object[] objects = {beH_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                Account account = new Account();
                String beH_nursePeople = rs.getString("beH_nursePeople");
                String beH_patBed = rs.getString("beH_patBed");
                account.setBeH_nursePeople(beH_nursePeople);
                account.setBeH_patBed(beH_patBed);
                return account;
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
    public Integer addAccount(Account account) {
        sql = "insert into behospital values(0,?,?,?,?,0,0,?,?)";
        Object[] objects = {account.getBeH_nursePeople(),
                account.getBeH_patBed(), account.getBeH_antecedent(),
                account.getBeH_illness(), account.getHosR_id(), null};
        int result = update(sql, objects);
        close();
        return result;
    }

    @Override
    public Account queryAccountByIdForAntecedent(Integer beH_id) {
        sql = " SELECT b.beH_id,b.beH_antecedent,"
                + "(SELECT h.hosR_name FROM hosregister h WHERE h.hosR_id=b.hosR_id ) AS hosR_name,"
                + " (SELECT h.hosR_idCard FROM hosregister h WHERE h.hosR_id=b.hosR_id ) AS hosR_idCard "
                + " FROM behospital b where beH_id=?;";
        Object[] objects = {beH_id};
        rs = query(sql, objects);
        try {
            if (rs.next()) {
                Account account = new Account();
                account.setBeH_id(rs.getInt("beH_id"));
                account.setHosR_name(rs.getString("hosR_name"));
                account.setHosR_idCard(rs.getString("hosR_idCard"));
                account.setBeH_antecedent(rs.getDouble("beH_antecedent"));
                return account;
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
