package cn.kiway.mdm.scoket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static cn.kiway.mdm.scoket.db.MyDBHelper.TABLE_RESPOND;


/**
 * Created by Administrator on 2017/11/6.
 */

public class DbUtils {

    public static void addResponse(Context c, String s) {
        if (getResponse(c).contains(s)) {//判断数据库是否有一样的数据
            return;
        }
        SQLiteDatabase db = new MyDBHelper(c).getWritableDatabase();
        try {
            JSONObject da = new JSONObject(s);
            ContentValues values = new ContentValues();
            values.put("title", da.optString("title"));
            values.put("schoolId", da.optString("schoolId"));
            values.put("classId", da.optString("classId"));
            values.put("userId", da.optString("userId"));
            values.put("type", da.optInt("type"));
            values.put("createData", da.optLong("createData"));
            db.insert(TABLE_RESPOND, null, values);
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteResponse(Context c) {
        SQLiteDatabase db = new MyDBHelper(c).getWritableDatabase();
        db.delete(TABLE_RESPOND, null, null);
        db.close();
    }

    public static String getResponse(Context c) {
        JSONArray array = new JSONArray();
        SQLiteDatabase db = new MyDBHelper(c).getWritableDatabase();
        Cursor cur = db.query(TABLE_RESPOND, null, null, null, null, null, null);
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            JSONObject da = new JSONObject();
            try {
                // da.put("ids", cur.getString(1));
                da.put("title", cur.getString(1));
                da.put("schoolId", cur.getString(2));
                da.put("classId", cur.getString(3));
                da.put("userId", cur.getString(4));
                da.put("type", cur.getInt(5));
                da.put("createData", cur.getLong(6));
                array.put(da);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cur.close();
        db.close();
        return array.toString();
    }
}
