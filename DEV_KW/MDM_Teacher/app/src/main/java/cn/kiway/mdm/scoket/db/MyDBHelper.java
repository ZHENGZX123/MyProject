package cn.kiway.mdm.scoket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "se.db";//数据库名字
    public static final int DB_VERSION = 1;//数据库版本号

    public static final String TABLE_RESPOND = "respond";//表名字
    private static final String CREATE_TABLE_RESPOND = " create table  IF NOT EXISTS "
            + TABLE_RESPOND
            + "(id integer primary key autoincrement, title text, schoolId text,classId text,userId " +
            "text,type integer,createData integer)";//表结构


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPOND);
        db.execSQL(CREATE_TABLE_RESPOND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(CREATE_TABLE_RESPOND);
    }

}
