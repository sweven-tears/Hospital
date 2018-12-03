package com.cdtc.hospital.local.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.local.DatabaseHelper;
import com.cdtc.hospital.util.LogUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * @author sweven
 * @version 1.0
 * 连接数据库
 */
public class BaseLocalDao {

    private Activity activity;
    private DatabaseHelper database_helper;
    private SQLiteDatabase db;
    private String dataBaseName;

    public static final int QUERY_DATABASE = 1;
    public static final int UPDATE_DATABASE = 2;


    /**
     * @param activity     上下文
     * @param dataBaseName 库名，默认 hospital.db
     * @param type         创建并读取库 or 更新库的数据
     */
    public BaseLocalDao(Activity activity, String dataBaseName, int type) {
        this.activity = activity;

        if (dataBaseName == null || dataBaseName.equals("")) {
            //库的名字为 hospital.db
            dataBaseName = App.DATA_BASE +".db";
        }
        this.dataBaseName = dataBaseName;

        if (type == QUERY_DATABASE) {
            // 查询数据库
            queryDataBase();
        } else if (type == UPDATE_DATABASE) {
            // 更新数据库
            updateDataBase();
        } else {
            new LogUtil(activity.getPackageName()).e("数据库参数错误！");
        }
    }

    /**
     * 创建数据库
     */
    private void queryDataBase() {
        database_helper = new DatabaseHelper(activity, dataBaseName);
        db = database_helper.getReadableDatabase();
    }


    /**
     * 更新数据库
     */
    private void updateDataBase() {
        database_helper = new DatabaseHelper(activity, dataBaseName);
        db = database_helper.getWritableDatabase();
    }


    public long insert(String tableName,Map<String, Object> map) {
        ContentValues values = new ContentValues();
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (o == null) {
                values.putNull(key);//注意值的类型要匹配
            }
            else if (o instanceof Byte) {
                values.put(key, (Byte) map.get(key));
            }
            else if (o instanceof Long) {
                values.put(key, (Long) map.get(key));
            }
            else if (o instanceof Float) {
                values.put(key, (Float) map.get(key));
            }
            else if (o instanceof Short) {
                values.put(key, (Short) map.get(key));
            }
            else if (o instanceof byte[]) {
                values.put(key, (byte[]) map.get(key));
            }
            else if (o instanceof Double) {
                values.put(key, (Double) map.get(key));
            }
            else if (o instanceof String) {
                values.put(key, (String) map.get(key));
            }
            else if (o instanceof Boolean) {
                values.put(key, (Boolean) map.get(key));
            }
            else if (o instanceof Integer) {
                values.put(key, (Integer) map.get(key));
            }
            else if (o instanceof Date){
                String date=map.get(key).toString();
                values.put(key,date);
            }

        }
        return db.insert(tableName, null, values);
    }

    public int update(String tableName,Map<String, Object> map, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (o == null) {
                values.putNull(key);//注意值的类型要匹配
            }
            else if (o instanceof Byte) {
                values.put(key, (Byte) map.get(key));
            }
            else if (o instanceof Long) {
                values.put(key, (Long) map.get(key));
            }
            else if (o instanceof Float) {
                values.put(key, (Float) map.get(key));
            }
            else if (o instanceof Short) {
                values.put(key, (Short) map.get(key));
            }
            else if (o instanceof byte[]) {
                values.put(key, (byte[]) map.get(key));
            }
            else if (o instanceof Double) {
                values.put(key, (Double) map.get(key));
            }
            else if (o instanceof String) {
                values.put(key, (String) map.get(key));
            }
            else if (o instanceof Boolean) {
                values.put(key, (Boolean) map.get(key));
            }
            else if (o instanceof Integer) {
                values.put(key, (Integer) map.get(key));
            }
            else if (o instanceof Date){
                String date=map.get(key).toString();
                values.put(key,date);
            }

        }
        return db.update(tableName, values, whereClause, whereArgs);
    }

    public Cursor query(String tableName,String[] columns, String selection, String[] selectionArgs) {
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public int delete(String tableName,String whereClause,String[] whereArgs) {
        return db.delete(tableName, whereClause, whereArgs);
    }
}
