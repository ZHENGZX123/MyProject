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

import static cn.kiway.marketplace.util.IUrContant.BASE_URL;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MarkePlaceDBHelper extends SQLiteOpenHelper {


    public static final String MARKEPLACEDB_NAME = "markerplace.db";
    public static final int MARKEPLACEDB_VERSION = 4;

    private static final String TABLE_CLASSIFY = "Classify";
    private static final String CREATE_TABLE_CLASSIFY = " create table  IF NOT EXISTS "
            + TABLE_CLASSIFY
            + "   (id integer primary key autoincrement, ids text,  name  text,type text,description text,parentId " +
            "text,hasSub text,delFlag text,startDate text,endDate text,createDate text,label text)";


    private static final String TABLE_APPLIST = "AppList";
    private static final String CREATE_TABLE_APPLIST = " create table  IF NOT EXISTS "
            + TABLE_APPLIST
            + "   (id integer primary key autoincrement, remoteUpdate text,  iocn  text,  description  text , " +
            " language text , packages text , systemRequirement text , isPublish text ,version text,platform text,url" +
            " text," +
            "removeApp text,supportPlatform text,size text,name text,ids text,deviceRequirement text,pushInstall " +
            "text," +
            "downloadCount text,appClassify text,createDate text,page text,appClassifyName text)";


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
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
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
            values.put("ids", classify.id);
            values.put("name", classify.name);
            values.put("type", classify.type);
            values.put("description", classify.description);
            values.put("parentId", classify.parentId);
            values.put("hasSub", classify.hasSub);
            values.put("delFlag", classify.delFlag);
            values.put("startDate", classify.startDate);
            values.put("endDate", classify.endDate);
            values.put("createDate", classify.createDate);
            values.put("label", classify.label);
            // values.put("type", classify.type);
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
            classify.id = cur.getString(1);
            classify.name = cur.getString(2);
            classify.type = cur.getString(3);
            classify.description = cur.getString(4);
            classify.parentId = cur.getString(5);
            classify.hasSub = cur.getString(6);
            classify.delFlag = cur.getString(7);
            classify.startDate = cur.getString(8);
            classify.endDate = cur.getString(9);
            classify.createDate = cur.getString(10);
            classify.label = cur.getString(11);
            //  classify.type = cur.getString(12);
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
        for (MarketApp marketApp : list) {
            if (!marketApp.url.equals("")){
            ContentValues values = new ContentValues();
            values.put("remoteUpdate", marketApp.remoteUpdate);
            values.put("iocn", BASE_URL + marketApp.iocn);
            values.put("description", marketApp.description);
            values.put("language", marketApp.language);
            values.put("packages", marketApp.packages);
            values.put("systemRequirement", marketApp.systemRequirement);
            values.put("isPublish", marketApp.isPublish);
            values.put("version", marketApp.version);
            values.put("platform", marketApp.platform);
            values.put("url", marketApp.url);
            values.put("removeApp", marketApp.removeApp);
            values.put("supportPlatform", marketApp.supportPlatform);
            values.put("size", marketApp.size);
            values.put("name", marketApp.name);
            values.put("ids", marketApp.id);
            values.put("deviceRequirement", marketApp.deviceRequirement);
            values.put("pushInstall", marketApp.pushInstall);
            values.put("downloadCount", marketApp.downloadCount);
            values.put("appClassify", marketApp.appClassify);
            values.put("createDate", marketApp.createDate);
            values.put("page", marketApp.page);
            values.put("appClassifyName",marketApp.appClassifyName);
            db.insert(TABLE_APPLIST, null, values);}
        }
        db.close();
    }

    public ArrayList<MarketApp> getAllAppList(String appClassify,String page) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        cur = db.query(TABLE_APPLIST, null,"appClassify=? and page=?", new String[] { appClassify, page}, null, null, null);
        ArrayList<MarketApp> marketApps = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            MarketApp marketApp = new MarketApp();
            marketApp.remoteUpdate = cur.getString(1);
            marketApp.iocn = cur.getString(2);
            marketApp.description = cur.getString(3);
            marketApp.language = cur.getString(4);
            marketApp.packages = cur.getString(5);
            marketApp.systemRequirement = cur.getString(6);
            marketApp.isPublish = cur.getString(7);
            marketApp.version = cur.getString(8);
            marketApp.platform = cur.getString(9);
            marketApp.url = cur.getString(10);
            marketApp.removeApp = cur.getString(11);
            marketApp.supportPlatform = cur.getString(12);
            marketApp.size = cur.getString(13);
            marketApp.name = cur.getString(14);
            marketApp.id = cur.getString(15);
            marketApp.deviceRequirement = cur.getString(16);
            marketApp.pushInstall = cur.getString(17);
            marketApp.downloadCount = cur.getString(18);
            marketApp.appClassify = cur.getString(19);
            marketApp.createDate = cur.getString(20);
            marketApp.page = cur.getString(21);
            marketApp.appClassifyName=cur.getString(22);
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

    public void deleteAppList(String appClassify, String page) {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_APPLIST, "appClassify=? and page=?", new String[]{appClassify, page});
        db.close();
    }
}
