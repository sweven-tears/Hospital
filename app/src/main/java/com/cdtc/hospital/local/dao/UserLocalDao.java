package com.cdtc.hospital.local.dao;

import com.cdtc.hospital.network.entity.User;

/**
 * 
 * @author sweven
 * @version 1.0
 *
 */
public interface UserLocalDao {

	/*
	 * 根据登录名查询出User对象
	 */
	User selectByLoginName(String u_loginName);

	/**
	 * 获取本地登录状态
	 */
	void queryLocalLogSate();

	int insertUser(User user);

	int updateUser(User user);

	int updateLogSate(int logState,String u_loginName);
	
}
