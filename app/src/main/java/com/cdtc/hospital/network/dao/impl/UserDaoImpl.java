package com.cdtc.hospital.network.dao.impl;

import com.cdtc.hospital.network.dao.BaseDao;
import com.cdtc.hospital.network.dao.UserDao;
import com.cdtc.hospital.network.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public User selectByLoginName(String u_loginName) {
		sql="select * from user where u_loginName=?";
		Object[] objects={u_loginName};
		rs=query(sql, objects);
		try {
			if (rs.next()){
				User user=new User();
				user.setU_loginName(rs.getString("u_loginName"));
				user.setU_passWord(rs.getString("u_passWord"));
				user.setU_trueName(rs.getString("u_trueName"));
				user.setU_email(rs.getString("u_email"));
				user.setU_state(rs.getInt("u_state"));
				user.setR_id(rs.getInt("r_id"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}

	@Override
	public List<User> queryAllUser() {
		List<User> users=new ArrayList<>();

		sql="select * from user";
		rs=query(sql, null);
		try {
			while (rs.next()){
				User user=new User();
				user.setU_loginName(rs.getString("u_loginName"));
				user.setU_passWord(rs.getString("u_passWord"));
				user.setU_trueName(rs.getString("u_trueName"));
				user.setU_email(rs.getString("u_email"));
				user.setU_state(rs.getInt("u_state"));
				user.setR_id(rs.getInt("r_id"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}

}
