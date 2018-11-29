package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.network.entity.User;

/**
 * 
 * @author sweven
 * @version 1.0
 *
 */
public interface UserDao {

	/*
	 * 根据登录名查询出User对象
	 */
	User selectByLoginName(String u_loginName);
	
}
