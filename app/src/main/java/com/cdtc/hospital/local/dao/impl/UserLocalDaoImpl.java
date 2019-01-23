package com.cdtc.hospital.local.dao.impl;

import android.app.Activity;
import android.database.Cursor;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.entity.User;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.UserLocalDao;

import java.util.HashMap;
import java.util.Map;

public class UserLocalDaoImpl extends BaseLocalDao implements UserLocalDao {


    /**
     * @param activity 上下文
     * @param type     创建并读取库 or 更新库的数据
     */
    public UserLocalDaoImpl(Activity activity, int type) {
        super(activity, App.DATA_BASE, type);
    }

    @Override
    public User queryByLoginName(String u_loginName) {
        String[] columns = new String[]{App.TableUser.LOGIN_NAME, App.TableUser.PASSWORD, App.TableUser.TRUE_NAME, App.TableUser.EMAIL, App.TableUser.STATE};
        Cursor cursor = query(App.TABLE_USER, columns, App.TableUser.LOGIN_NAME + whereArg, new String[]{u_loginName});
        if (cursor.moveToNext()) {
            User user = new User();
            user.setU_loginName(cursor.getString(cursor.getColumnIndex(columns[0])));
            user.setU_passWord(cursor.getString(cursor.getColumnIndex(columns[1])));
            user.setU_trueName(cursor.getString(cursor.getColumnIndex(columns[2])));
            user.setU_email(cursor.getString(cursor.getColumnIndex(columns[3])));
            user.setU_state(cursor.getInt(cursor.getColumnIndex(columns[4])));
            return user;
        }
        return null;
    }

    @Override
    public String queryLocalLogSate() {
        String[] columns = new String[]{App.TableUser.LOGIN_NAME, App.TableUser.TRUE_NAME, App.TableUser.STATE};
        Cursor cursor = query(App.TABLE_USER, columns, App.TableUser.STATE + whereArg, new String[]{String.valueOf(App.LOG_IN)});
        if (cursor.moveToNext()) {
            App.loginState = App.LOG_IN;
            App.trueName = cursor.getString(cursor.getColumnIndex(columns[1]));
            return App.loginName = cursor.getString(cursor.getColumnIndex(columns[0]));
        }
        return null;
    }

    @Override
    public int insertUser(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(App.TableUser.LOGIN_NAME, user.getU_loginName());
        map.put(App.TableUser.PASSWORD, user.getU_passWord());
        map.put(App.TableUser.TRUE_NAME, user.getU_trueName());
        map.put(App.TableUser.EMAIL, user.getU_email());
        map.put(App.TableUser.STATE, App.LOG_OUT);
        return (int) insert(App.TABLE_USER, map);
    }

    @Override
    public int updateUser(User user) {
        if (user == null) {
            return -1;
        }
        Map<String, Object> map = new HashMap<>();
        if (user.getU_loginName() != null) {
            map.put(App.TableUser.LOGIN_NAME, user.getU_loginName());
        }
        if (user.getU_passWord() != null) {
            map.put(App.TableUser.PASSWORD, user.getU_passWord());
        }
        if (user.getU_trueName() != null) {
            map.put(App.TableUser.TRUE_NAME, user.getU_trueName());
        }
        if (user.getU_email() != null) {
            map.put(App.TableUser.EMAIL, user.getU_email());
        }
        if (user.getU_state() != null) {
            map.put(App.TableUser.STATE, user.getU_state());
        }
        String whereClause = App.TableUser.LOGIN_NAME + whereArg;
        String[] whereArgs = new String[]{user.getU_loginName()};

        return update(App.TABLE_USER, map, whereClause, whereArgs);
    }

    @Override
    public int updateLogSate(int logState, String u_loginName) {
        Map<String, Object> map = new HashMap<>();
        map.put(App.TableUser.STATE, logState);
        return update(App.TABLE_USER, map, App.TableUser.LOGIN_NAME + whereArg, new String[]{u_loginName});
    }

    @Override
    public boolean queryExistByPassWordLoginName(String loginName, String passWord) {
        Cursor cursor = rawQuery(select + xin + from +
                        App.TABLE_USER + where +
                        App.TableUser.LOGIN_NAME + whereArg + and + App.TableUser.PASSWORD + whereArg,
                new String[]{loginName, passWord});
        return cursor.moveToNext();
    }
}
