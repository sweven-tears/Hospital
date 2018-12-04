package com.cdtc.hospital.local.dao.impl;

import android.app.Activity;
import android.database.Cursor;

import com.cdtc.hospital.base.App;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.UserLocalDao;
import com.cdtc.hospital.network.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserLocalDaoImpl extends BaseLocalDao implements UserLocalDao {



	/**
	 * @param activity     上下文
	 * @param type         创建并读取库 or 更新库的数据
	 */
	public UserLocalDaoImpl(Activity activity, int type) {
		super(activity, App.DATA_BASE, type);
	}

	@Override
	public User selectByLoginName(String u_loginName) {
        String[] columns=new String[]{"u_loginName","u_passWord","u_trueName","u_email","u_state"};
		Cursor cursor=query(App.TABLE_USER,columns,"u_loginName=?",new String[]{u_loginName});
		if (cursor.moveToNext()){
		    User user=new User();
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
    public void queryLocalLogSate() {
        String[] columns=new String[]{"u_loginName","u_trueName","u_state"};
        Cursor cursor =query(App.TABLE_USER,columns,"u_state=?",new String[]{String.valueOf(App.LOG_IN)});
        while (cursor.moveToNext()){
            App.loginState=App.LOG_IN;
            App.trueName=cursor.getString(cursor.getColumnIndex(columns[1]));
        }
    }

    @Override
    public int insertUser(User user) {
        Map<String,Object> map=new HashMap<>();
        map.put("u_loginName",user.getU_loginName());
        map.put("u_passWord",user.getU_passWord());
        map.put("u_trueName",user.getU_trueName());
        map.put("u_email",user.getU_email());
        map.put("u_state",App.LOG_OUT);
        return (int) insert(App.TABLE_USER,map);
    }

    @Override
    public int updateUser(User user) {
        Map<String,Object> map=new HashMap<>();
        map.put("u_loginName",user.getU_loginName());
        map.put("u_passWord",user.getU_passWord());
        map.put("u_trueName",user.getU_trueName());
        map.put("u_email",user.getU_email());
        String whereClause="u_loginName=?";
        String[] whereArgs=new String[]{user.getU_loginName()};

        return  update(App.TABLE_USER,map,whereClause,whereArgs);
    }

    @Override
    public int updateLogSate(int logState,String u_loginName) {
        Map<String,Object> map=new HashMap<>();
        map.put("u_state",logState);
        return update(App.TABLE_USER,map,"u_loginName=?",new String[]{u_loginName});
    }
}
