package com.cdtc.hospital.base;

/**
 * Created by Sweven on 2018/11/28.
 * Email:sweventears@Foxmail.com
 */
public class App {
    public static final String DATA_BASE="hospital";
    public static final String TABLE_USER="user";
    public static final String TABLE_HOS_REGISTER="hosregister";
    public static final String TABLE_BE_HOSPITAL="behospital";
    public static final String TABLE_DOCTOR="doctor";

    public static final int LOG_IN=1;
    public static final int LOG_OUT=0;

    // 登录状态
    public static int loginState=LOG_OUT;
    public static String trueName;
    public static String loginName;
}
