package com.cdtc.hospital.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cdtc.hospital.base.App;

/**
 * Created by Sweven on 2018/11/28.
 * Email:sweventears@Foxmail.com
 */
public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int VERSION = 1;//默认的数据库版本

    //继承SQLiteOpenHelper类的类必须有自己的构造函数
    //该构造函数4个参数，直接调用父类的构造函数。其中第一个参数为该类本身；第二个参数为数据库的名字；
    //第3个参数是用来设置游标对象的，这里一般设置为null；参数四是数据库的版本号。
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    //该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
    DatabaseHelper(Context context, String name, int version){
        this(context, name, null, version);
    }

    //该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
    public DatabaseHelper(Context context, String name){
        this(context, name, VERSION);
    }

    //该函数在数据库第一次被建立时调用
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        System.out.println("create a sqlite database");
        //execSQL()为执行参数里面的SQL语句，因此参数中的语句需要符合SQL语法,这里是创建一个表

        // user表
        arg0.execSQL("CREATE TABLE "+App.TABLE_USER +" (" +
                "  u_loginName VARCHAR(32) NOT NULL," +
                "  u_passWord VARCHAR(80) NOT NULL," +
                "  u_trueName VARCHAR(80) NOT NULL," +
                "  u_email VARCHAR(80) DEFAULT NULL," +
                "  u_state INT(1)  NOT NULL DEFAULT '0'," +
                "  PRIMARY KEY (u_loginName)" +
                ")");

        // doctor表
        arg0.execSQL("CREATE TABLE "+App.TABLE_DOCTOR +" (" +
                "  d_id integer(11) NOT NULL primary key," +
                "  d_name varchar(32) NOT NULL ," +
                "  d_idCard varchar(32) NOT NULL ," +
                "  d_phone varchar(32) DEFAULT NULL ," +
                "  d_telPhone varchar(32) DEFAULT NULL ," +
                "  d_sex int(1) NOT NULL DEFAULT '0' ," +
                "  d_birthday varchar(32) NOT NULL ," +
                "  d_age int(3) NOT NULL ," +
                "  d_email varchar(32) DEFAULT NULL ," +
                "  d_keshi varchar(32) NOT NULL ," +
                "  d_xueli varchar(32) DEFAULT NULL ," +
                "  d_descr varchar(255) DEFAULT '' ," +
                "  d_inTime varcahr(32) NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  d_state int(1) NOT NULL DEFAULT '0' " +
                ")");

        // hosRegister表
        arg0.execSQL("CREATE TABLE "+App.TABLE_HOS_REGISTER +" (" +
                "  hosR_id Integer(11) NOT NULL PRIMARY KEY," +
                "  hosR_name varchar(80) NOT NULL ," +
                "  hosR_idCard varchar(255) NOT NULL ," +
                "  hosR_medical varchar(255) DEFAULT NULL," +
                "  hosR_regPrice double NOT NULL ," +
                "  hosR_phone varchar(255) DEFAULT NULL ," +
                "  hosR_selfPrice int(1) NOT NULL DEFAULT '0' ," +
                "  hosR_sex int(1) NOT NULL DEFAULT '0' ," +
                "  hosR_age int(3) NOT NULL ," +
                "  hosR_work varchar(255) DEFAULT NULL ," +
                "  hosR_lookDoctor int(1) NOT NULL DEFAULT '0' ," +
                "  d_id int(11) references doctor(d_id)," +
                "  hosR_createTime varcahr(32) DEFAULT CURRENT_TIMESTAMP," +
                "  hosR_remark varchar(255) DEFAULT NULL  ," +
                "  hosR_state int(1) NOT NULL DEFAULT '0'" +
                ")");

        // beHospital表
        arg0.execSQL("CREATE TABLE "+App.TABLE_BE_HOSPITAL +" (" +
                "  beH_id int(11) NOT NULL PRIMARY KEY," +
                "  beH_nursePeople varchar(255) DEFAULT NULL ," +
                "  beH_patBed varchar(255) NOT NULL ," +
                "  beH_antecedent double NOT NULL ," +
                "  beH_illness varchar(255) DEFAULT NULL ," +
                "  beH_closePrice int(10) NOT NULL DEFAULT '0' ," +
                "  beH_state int(10) NOT NULL DEFAULT '0' ," +
                "  hosR_id int(11) references hosregister(hosR_id)," +
                "  beH_createTime varcahr(32) NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        System.out.println("update a sqlite database");
    }
}
