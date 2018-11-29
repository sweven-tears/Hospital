package com.cdtc.hospital.base;

import android.database.Cursor;

import com.cdtc.hospital.local.SQLite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sweven on 2018/11/28.
 * Email:sweventears@Foxmail.com
 */
public class App {
    public static final String DATA_BASE="hospital";
    public static final String TABLE_USER="user";

    public static final int LOG_IN=1;
    public static final int LOG_OUT=0;

    // 登录状态
    public static int loginState=LOG_OUT;
    public static String trueName;
}
