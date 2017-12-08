package cn.kiway.marketplace.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.marketplace.util.MarketApp;
import cn.kiway.marketplace.util.MarketClassify;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MarkePlaceDBHelper extends SQLiteOpenHelper {


    public static final String MARKEPLACEDB_NAME = "markerplace.db";
    public static final int MARKEPLACEDB_VERSION = 1;

    private static final String TABLE_CLASSIFY = "Classify";
    private static final String CREATE_TABLE_CLASSIFY = " create table  IF NOT EXISTS "
            + TABLE_CLASSIFY
            + "   (id integer primary key autoincrement, ids text,  name  text)";

    private static final String TABLE_APPLIST = "AppList";
    private static final String CREATE_TABLE_APPLIST = " create table  IF NOT EXISTS "
            + TABLE_APPLIST
            + "   (id integer primary key autoincrement, ids text,  url  text,  type  text , enable text)";

    private SQLiteDatabase db;


    public MarkePlaceDBHelper(Context context) {
        super(context, MARKEPLACEDB_NAME, null, MARKEPLACEDB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_CLASSIFY);
        db.execSQL(CREATE_TABLE_APPLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSIFY);
        db.execSQL(CREATE_TABLE_CLASSIFY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLIST);
        db.execSQL(CREATE_TABLE_APPLIST);
    }

    //--------------------------classify--------------
    public void addClassify(List<MarketClassify> marketClassifies) {
        if (db == null)
            db = getWritableDatabase();
        for (MarketClassify classify : marketClassifies) {
            ContentValues values = new ContentValues();
            values.put("ids", classify.classifyId);
            values.put("name", classify.classifyName);
            db.insert(TABLE_CLASSIFY, null, values);
        }
        db.close();
    }

    public void deleteAllClassify() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_CLASSIFY, null, null);
        db.close();
    }

    public void deleteClassify(String s) {
        if (db == null)
            db = getWritableDatabase();
        if (TextUtils.isEmpty(s)) {
            db.delete(TABLE_CLASSIFY, null, null);
        } else {
            db.delete(TABLE_CLASSIFY, "ids=?", new String[]{s});
        }
        db.close();
    }

    //获取所有分类列表
    public ArrayList<MarketClassify> getAllClassify() {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        cur = db.query(TABLE_CLASSIFY, null, null, null, null, null, null);
        ArrayList<MarketClassify> marketClassifies = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            MarketClassify classify = new MarketClassify();
            classify.classifyId = cur.getString(1);
            classify.classifyName = cur.getString(2);
            marketClassifies.add(classify);
        }
        cur.close();
        db.close();
        return marketClassifies;
    }
    //-------------------------appList-------------

    public void addAppList(List<MarketApp> list) {
        if (db == null)
            db = getWritableDatabase();
        for (MarketApp app : list) {
            ContentValues values = new ContentValues();

            db.insert(TABLE_APPLIST, null, values);
        }
        db.close();
    }
    public ArrayList<MarketApp> getAllAppList(){
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        cur = db.query(TABLE_CLASSIFY, null, null, null, null, null, null);
        ArrayList<MarketApp> marketApps = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            MarketApp marketApp = new MarketApp();
            marketApps.add(marketApp);
        }
        cur.close();
        db.close();
        return marketApps;
    }

    public void deleteAllAppList() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_APPLIST, null, null);
        db.close();
    }

    public void deleteAppList(String s) {
        if (db == null)
            db = getWritableDatabase();
        if (TextUtils.isEmpty(s)) {
            db.delete(TABLE_APPLIST, null, null);
        } else {
            db.delete(TABLE_APPLIST, "ids=?", new String[]{s});
        }
        db.close();
    }
}
