package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.entity.User;

import java.util.List;

/**
 * @author sweven
 * @version 1.0
 */
public interface UserDao {

    List<User> queryAllUser();

    String queryPassWordByLoginName(String loginName);

}
