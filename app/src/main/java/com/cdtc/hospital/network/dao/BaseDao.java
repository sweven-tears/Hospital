package com.cdtc.hospital.network.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author sweven
 * @version 1.0
 * 连接数据库
 */
public class BaseDao {

    private static final String uri = "139.199.5.186";
    private static final String port = "3306";
    private static final String dataBase = "hospital";
    private static final String extra = "serverTimezone=UTC";
    private static final String encoding = "useUnicode=true&characterEncoding=utf-8";
    private static final String user = "sweven";
    private static final String password = "luoluna";
    private static final String url = "jdbc:mysql://" + uri + ":" + port + "/" + dataBase + "?" + extra + "&" + encoding;


    private Connection conn;
    private PreparedStatement pre;
    protected ResultSet rs;

    protected String sql;

    /**
     * make mysql connection
     *
     * @return 连接
     */
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * update table. Ex. update,delete,insert
     *
     * @param sql     执行语句
     * @param objects 参数
     * @return 成功的行数
     */
    public int update(String sql, Object[] objects) {
        conn = getConnection();

        pre = null;
        try {
            // cancel auto commit sql
            conn.setAutoCommit(false);
            pre = conn.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    pre.setObject(i + 1, objects[i]);
                }

            }
            int result = pre.executeUpdate();

            // commit sql
            conn.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }

    /**
     * get ResultSet . Ex. select * from user
     *
     * @param sql     执行语句
     * @param objects 参数
     * @return 结果集
     */
    public ResultSet query(String sql, Object[] objects) {
        conn = getConnection();
        pre = null;
        try {
            pre = conn.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    pre.setObject(i + 1, objects[i]);
                }
            }
            rs = pre.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
