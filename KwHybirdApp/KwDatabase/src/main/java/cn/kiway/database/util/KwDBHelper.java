package cn.kiway.database.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import cn.kiway.database.entity.HTTPCache;
import cn.kiway.database.entity.KV;


public class KwDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sdk.db";

    private static final String TABLE_HTTPCACHE = "HTTPCache";
    private static final String CREATE_TABLE_HTTPCACHE = " create table  IF NOT EXISTS "
            + TABLE_HTTPCACHE
            + "   (id integer primary key autoincrement,  request  text,  response  text ,  requesttime text  , tagname text) ";


    private static final String TABLE_KV = "KV";
    private static final String CREATE_TABLE_KV = " create table  IF NOT EXISTS "
            + TABLE_KV
            + "   (id integer primary key autoincrement,  k  text,  v  text ) ";

    private SQLiteDatabase db;

    public KwDBHelper(Context c) {
        super(c, DB_NAME, null, 10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HTTPCACHE);
        db.execSQL(CREATE_TABLE_HTTPCACHE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KV);
        db.execSQL(CREATE_TABLE_KV);
        db.execSQL("CREATE UNIQUE INDEX index_k ON KV (k)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_HTTPCACHE);
        db.execSQL(CREATE_TABLE_KV);
        db.execSQL("CREATE UNIQUE INDEX index_k ON KV (k)");
    }

    //-------------------------------httpcache-----------------------------
    public HTTPCache getHttpCacheByRequest(String request, int time) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_HTTPCACHE, null, "request=?", new String[]{request}, null, null, null);
        HTTPCache a = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex("id"));
            request = cur.getString(cur.getColumnIndex("request"));
            String response = cur.getString(cur.getColumnIndex("response"));
            String requesttime = cur.getString(cur.getColumnIndex("requesttime"));
            String tagname = cur.getString(cur.getColumnIndex("tagname"));

            long current = System.currentTimeMillis();
            if (current - Long.parseLong(requesttime) > time * 60 * 1000) {
                return null;
            }
            a = new HTTPCache();
            a.id = id;
            a.request = request;
            a.response = response;
            a.requesttime = requesttime;
            a.tagname = tagname;
        }
        cur.close();
        db.close();
        return a;
    }

    public HTTPCache getHttpCacheByRequest(String request) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_HTTPCACHE, null, "request=?", new String[]{request}, null, null, null);
        HTTPCache a = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex("id"));
            request = cur.getString(cur.getColumnIndex("request"));
            String response = cur.getString(cur.getColumnIndex("response"));
            String requesttime = cur.getString(cur.getColumnIndex("requesttime"));
            String tagname = cur.getString(cur.getColumnIndex("tagname"));


            a = new HTTPCache();
            a.id = id;
            a.request = request;
            a.response = response;
            a.requesttime = requesttime;
            a.tagname = tagname;
        }
        cur.close();
        db.close();
        return a;
    }

    public void addHTTPCache(HTTPCache a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("request", a.request);
        values.put("response", a.response);
        values.put("requesttime", a.requesttime);
        values.put("tagname", a.tagname);
        db.insert(TABLE_HTTPCACHE, null, values);
        db.close();
    }

    public void updateHTTPCache(HTTPCache cache) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("request", cache.request);
        cv.put("response", cache.response);
        cv.put("requesttime", cache.requesttime);
        cv.put("tagname", cache.tagname);
        String[] args = {cache.id};
        db.update(TABLE_HTTPCACHE, cv, "id=?", args);
        db.close();
    }

    public void deleteHttpCache(String tagname) {
        if (TextUtils.isEmpty(tagname)) {
            return;
        }
        if (db == null)
            db = getWritableDatabase();
        Log.d("test", "删除记录:" + tagname);

        if (tagname.contains(",")) {
            String[] tagnames = tagname.split(",");
            for (String s : tagnames) {
                db.delete(TABLE_HTTPCACHE, "tagname=?", new String[]{s});
            }
        } else {
            db.delete(TABLE_HTTPCACHE, "tagname=?", new String[]{tagname});
        }

        db.close();
    }

    //------------------------------------------kv----------------

    public void addKV(KV a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("k", a.k);
        values.put("v", a.v);
        db.insert(TABLE_KV, null, values);
        db.close();
    }

    public KV getKVByK(String k) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_KV, null, "k=?", new String[]{k}, null, null, null);
        KV a = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex("id"));
            k = cur.getString(cur.getColumnIndex("k"));
            String v = cur.getString(cur.getColumnIndex("v"));

            a = new KV();
            a.id = id;
            a.k = k;
            a.v = v;

        }
        cur.close();
        db.close();
        return a;
    }

    public void deleteAllHttpCache() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_HTTPCACHE, null, null);
        db.close();
    }

}