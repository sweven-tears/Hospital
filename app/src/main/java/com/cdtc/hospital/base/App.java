package com.cdtc.hospital.base;

/**
 * Created by Sweven on 2018/11/28.
 * Email:sweventears@Foxmail.com
 */
public class App {
    public static final String DATA_BASE = "hospital";
    public static final String TABLE_USER = "user";

    // 登录状态码,error:密码已修改,lost:长时间未登录,out:登出,in:登入
    public static final int LOG_ERROR = -2;
    public static final int LOG_LOST = -1;
    public static final int LOG_OUT = 0;
    public static final int LOG_IN = 1;

    public static int loginState = LOG_OUT;
    public static String trueName;
    public static String loginName;

    public class TableUser {
        public static final String LOGIN_NAME = "u_loginName";
        public static final String PASSWORD = "u_passWord";
        public static final String TRUE_NAME = "u_trueName";
        public static final String EMAIL = "u_email";
        public static final String STATE = "u_state";
    }
}
