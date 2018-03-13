
package cn.kiway.mdm.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cn.kiway.mdm.entity.UploadTask;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "teacher.db";

    private static final String TABLE_TASK = "Task";
    private static final String CREATE_TABLE_TASK = " create table  IF NOT EXISTS "
            + TABLE_TASK
            + " (id integer primary key autoincrement,  filepath  text , courseID text,  status text , progress text , url text)";

    private SQLiteDatabase db;

    public MyDBHelper(Context c) {
        super(c, DB_NAME, null, 8);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_TASK);
    }

    //-----------------Task-----------------------------

    public void addTask(UploadTask a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("filepath", a.filepath);
        values.put("courseID", a.courseID);
        values.put("status", a.status);
        values.put("progress", a.progress);
        values.put("url", a.url);
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    public ArrayList<UploadTask> getTasksByStatus(int status) {
        if (db == null)
            db = getWritableDatabase();
        ArrayList<UploadTask> uts = new ArrayList<>();
        Cursor cur;
        if (status == -1) {
            cur = db.query(TABLE_TASK, null, null, null, null, null, null);
        } else {
            cur = db.query(TABLE_TASK, null, "status=?", new String[]{status + ""}, null, null, null);
        }
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            UploadTask ut = new UploadTask();
            ut.id = cur.getString(cur.getColumnIndex("id"));
            ut.filepath = cur.getString(cur.getColumnIndex("filepath"));
            ut.courseID = cur.getString(cur.getColumnIndex("courseID"));
            ut.status = cur.getInt(cur.getColumnIndex("status"));
            ut.progress = cur.getInt(cur.getColumnIndex("progress"));
            ut.url = cur.getString(cur.getColumnIndex("url"));
            uts.add(ut);
        }
        cur.close();
        db.close();
        return uts;
    }

    public ArrayList<UploadTask> getTasksByCourseID(String courseID) {
        if (db == null)
            db = getWritableDatabase();
        ArrayList<UploadTask> uts = new ArrayList<>();
        Cursor cur = db.query(TABLE_TASK, null, "courseID=?", new String[]{courseID + ""}, null, null, null);
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            UploadTask ut = new UploadTask();
            ut.id = cur.getString(cur.getColumnIndex("id"));
            ut.filepath = cur.getString(cur.getColumnIndex("filepath"));
            ut.courseID = cur.getString(cur.getColumnIndex("courseID"));
            ut.status = cur.getInt(cur.getColumnIndex("status"));
            ut.progress = cur.getInt(cur.getColumnIndex("progress"));
            ut.url = cur.getString(cur.getColumnIndex("url"));
            uts.add(ut);
        }
        cur.close();
        db.close();
        return uts;
    }

    public UploadTask getTasksByUrl(String url) {
        if (db == null)
            db = getWritableDatabase();
        UploadTask ut = new UploadTask();
        Cursor cur = db.query(TABLE_TASK, null, "url=?", new String[]{url + ""}, null, null, null);
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            ut.id = cur.getString(cur.getColumnIndex("id"));
            ut.filepath = cur.getString(cur.getColumnIndex("filepath"));
            ut.courseID = cur.getString(cur.getColumnIndex("courseID"));
            ut.status = cur.getInt(cur.getColumnIndex("status"));
            ut.progress = cur.getInt(cur.getColumnIndex("progress"));
            ut.url = cur.getString(cur.getColumnIndex("url"));
        }
        cur.close();
        db.close();
        return ut;
    }

    public void deleteTask() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_TASK, null, null);
        db.close();
    }

    public void setTaskStatus(UploadTask ut, int newStatus) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", newStatus);
        String[] args = {"" + ut.id};
        db.update(TABLE_TASK, cv, "id=?", args);
        db.close();
    }

    public void setTaskProgress(UploadTask ut, int newProgress) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("progress", newProgress);
        String[] args = {"" + ut.id};
        db.update(TABLE_TASK, cv, "id=?", args);
        db.close();
    }

    public void setTaskUrl(UploadTask ut, String url) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("url", url);
        String[] args = {"" + ut.id};
        db.update(TABLE_TASK, cv, "id=?", args);
        db.close();
    }


}