package com.zk.myweex.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.entity.ZipPackage;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "kw.db";

    private static final String TABLE_TABENTITY = "TabEntity";
    private static final String CREATE_TABLE_TABENTITY = " create table  IF NOT EXISTS "
            + TABLE_TABENTITY
            + "   (id integer primary key autoincrement,  idstr  text, name text , image_default text , image_selected text) ";


    private static final String TABLE_ZIPPACKAGE = "ZipPackage";
    private static final String CREATE_TABLE_ZIPPACKAGE = " create table  IF NOT EXISTS "
            + TABLE_ZIPPACKAGE
            + "   (id integer primary key autoincrement,  name  text, indexPath  text , version text , patchs text ) ";

    private SQLiteDatabase db;

    public MyDBHelper(Context c) {
        super(c, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_TABENTITY);
        db.execSQL(CREATE_TABLE_ZIPPACKAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABENTITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZIPPACKAGE);

        db.execSQL(CREATE_TABLE_TABENTITY);
        db.execSQL(CREATE_TABLE_ZIPPACKAGE);
    }

    public ArrayList<TabEntity> getAllTabEntity() {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_TABENTITY, null, null, null, null, null, null);
        ArrayList<TabEntity> temp = new ArrayList<TabEntity>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String idstr = cur.getString(cur.getColumnIndex("idstr"));
            String name = cur.getString(cur.getColumnIndex("name"));
            String image_default = cur.getString(cur.getColumnIndex("image_default"));
            String image_selected = cur.getString(cur.getColumnIndex("image_selected"));
            TabEntity a = new TabEntity();
            a.idStr = idstr;
            a.name = name;
            a.image_default = image_default;
            a.image_selected = image_selected;
            temp.add(a);
        }
        cur.close();
        db.close();
        return temp;
    }


    public void addTabEntity(TabEntity a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idstr", a.idStr);
        values.put("name", a.name);
        values.put("image_default", a.image_default);
        values.put("image_selected", a.image_selected);
        db.insert(TABLE_TABENTITY, null, values);
        db.close();
    }

    public ArrayList<ZipPackage> getAllZipPackages() {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_ZIPPACKAGE, null, null, null, null, null, null);
        ArrayList<ZipPackage> temp = new ArrayList<ZipPackage>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String name = cur.getString(cur.getColumnIndex("name"));
            String indexPath = cur.getString(cur.getColumnIndex("indexPath"));
            String version = cur.getString(cur.getColumnIndex("version"));
            String patchs = cur.getString(cur.getColumnIndex("patchs"));
            ZipPackage a = new ZipPackage();
            a.name = name;
            a.indexPath = indexPath;
            a.version = version;
            a.patchs = patchs;
            temp.add(a);
        }
        cur.close();
        db.close();
        return temp;
    }


    public void addZipPackage(ZipPackage a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", a.name);
        values.put("indexPath", a.indexPath);
        values.put("version", a.version);
        values.put("patchs", a.patchs);
        db.insert(TABLE_ZIPPACKAGE, null, values);
        db.close();
    }


    public ZipPackage getAllZipPackageByName(String zipName) {
        ArrayList<ZipPackage> packages = getAllZipPackages();
        for (ZipPackage p : packages) {
            if (p.name.equals(zipName)) {
                return p;
            }
        }
        return null;
    }

    public void updateZipPackageVersion(String version, String zipName) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("version", version);
        String[] args = {zipName};
        db.update(TABLE_ZIPPACKAGE, cv, "name=?", args);
        db.close();
    }

    public void updateTabEntity(TabEntity tab) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", tab.name);
        cv.put("image_default", tab.image_default);
        cv.put("image_selected", tab.image_selected);

        String[] args = {tab.idStr};
        db.update(TABLE_TABENTITY, cv, "idstr=?", args);
        db.close();
    }

    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }
}