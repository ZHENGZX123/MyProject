
package cn.kiway.robot.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Filter;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.entity.Moment;
import cn.kiway.robot.entity.ServerMsg;
import cn.kiway.robot.moment.SnsInfo;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "kiwaywx.db";

    private static final String TABLE_ADDFRIEND = "AddFriend";
    private static final String CREATE_TABLE_ADDFRIEND = " create table  IF NOT EXISTS "
            + TABLE_ADDFRIEND
            + "   (id integer primary key autoincrement,  requesttime text , phone  text ,  remark text , status  text   ) ";

    private static final String TABLE_MESSAGE = "message";
    private static final String CREATE_TABLE_MESSAGE = " create table IF NOT EXISTS "
            + TABLE_MESSAGE
            + "   (id integer primary key autoincrement,  talker text , remark text , content text , createTime text  ) ";

    //朋友圈
    private static final String TABLE_WX_MOMENT = "WX_MOMENT";
    private static final String CREATE_TABLE_WX_MOMENT = " create table  IF NOT EXISTS "
            + TABLE_WX_MOMENT
            + "   (id integer primary key autoincrement,  momentID text , description text)";

    //评论表:使用author content toUser createDate做对比。
    private static final String TABLE_WX_COMMENT = "WX_COMMENT";
    private static final String CREATE_TABLE_WX_COMMNET = " create table  IF NOT EXISTS "
            + TABLE_WX_COMMENT
            + "   (id integer primary key autoincrement,  momentID text , author text , content text , toUser text , createDate text , uploaded text)";


    private static final String TABLE_FILTER = "filter";
    private static final String CREATE_TABLE_FILTER = " create table IF NOT EXISTS "
            + TABLE_FILTER
            + "   (id integer primary key autoincrement,  type text ,  wxNo text,  name text ) ";
    //type：0转发使者 1卧底

    private static final String TABLE_SERVER_MSG = "servermsg";
    private static final String CREATE_TABLE_SERVERMSG = " create table IF NOT EXISTS "
            + TABLE_SERVER_MSG
            + "   (id integer primary key autoincrement,  indexs text ,  content text , replyContent text ,  status text , time text , type text  ) ";

    private SQLiteDatabase db;

    public MyDBHelper(Context c) {
        super(c, DB_NAME, null, 47);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDFRIEND);
        db.execSQL(CREATE_TABLE_ADDFRIEND);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_MESSAGE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WX_MOMENT);
        db.execSQL(CREATE_TABLE_WX_MOMENT);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WX_COMMENT);
        db.execSQL(CREATE_TABLE_WX_COMMNET);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER);
        db.execSQL(CREATE_TABLE_FILTER);

        //数据库升级，不要重置该表
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER_MSG);
//        db.execSQL(CREATE_TABLE_SERVERMSG);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_ADDFRIEND);
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_WX_MOMENT);
        db.execSQL(CREATE_TABLE_WX_COMMNET);
        db.execSQL(CREATE_TABLE_FILTER);
        db.execSQL(CREATE_TABLE_SERVERMSG);
    }

    //-------------------------------AddFriend-----------------------------
    public ArrayList<AddFriend> getAllAddFriends() {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_ADDFRIEND, null, null, null, null, null, null);

        ArrayList<AddFriend> addFriends = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String requesttime = cur.getString(cur.getColumnIndex("requesttime"));
            String phone = cur.getString(cur.getColumnIndex("phone"));
            String remark = cur.getString(cur.getColumnIndex("remark"));
            int status = cur.getInt(cur.getColumnIndex("status"));

            AddFriend af = new AddFriend();
            af.id = id;
            af.phone = phone;
            af.remark = remark;
            af.requesttime = requesttime;
            af.status = status;

            addFriends.add(af);

        }
        cur.close();
        db.close();
        return addFriends;
    }

    public AddFriend getAddFriendByPhone(String phone) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_ADDFRIEND, null, "phone=?", new String[]{phone + ""}, null, null, null);

        AddFriend af = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String requesttime = cur.getString(cur.getColumnIndex("requesttime"));
            phone = cur.getString(cur.getColumnIndex("phone"));
            String remark = cur.getString(cur.getColumnIndex("remark"));
            int status = cur.getInt(cur.getColumnIndex("status"));

            af = new AddFriend();
            af.id = id;
            af.phone = phone;
            af.remark = remark;
            af.requesttime = requesttime;
            af.status = status;
        }
        cur.close();
        db.close();

        return af;
    }

    public AddFriend getAddFriendByRemark(String remark) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_ADDFRIEND, null, "remark like ?", new String[]{"%" + remark + "%"}, null, null, null);

        AddFriend af = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String requesttime = cur.getString(cur.getColumnIndex("requesttime"));
            String phone = cur.getString(cur.getColumnIndex("phone"));
            remark = cur.getString(cur.getColumnIndex("remark"));
            int status = cur.getInt(cur.getColumnIndex("status"));

            af = new AddFriend();
            af.id = id;
            af.phone = phone;
            af.remark = remark;
            af.requesttime = requesttime;
            af.status = status;
        }
        cur.close();
        db.close();

        return af;
    }

    public void addAddFriend(AddFriend a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("requesttime", a.requesttime);
        values.put("phone", a.phone);
        values.put("remark", a.remark);
        values.put("status", a.status);
        db.insert(TABLE_ADDFRIEND, null, values);
        db.close();
    }

    public void updateAddFriend(AddFriend a) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("requesttime", a.requesttime);
        cv.put("phone", a.phone);
        cv.put("remark", a.remark);
        cv.put("status", a.status);
        String[] args = {a.id + ""};
        db.update(TABLE_ADDFRIEND, cv, "id=?", args);
        db.close();
    }

    public void deleteAddFriends() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_ADDFRIEND, null, null);
        db.close();
    }

    //------------------------message------------------------------------
    public void addMessage(String remark, String content, String createTime) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("remark", remark);
        values.put("content", content);
        values.put("createTime", createTime);
        db.insert(TABLE_MESSAGE, null, values);
        db.close();
    }

    public ArrayList<Message> getMessagesIn1Hour() {
        if (db == null)
            db = getWritableDatabase();
        long current = System.currentTimeMillis();
        long before1hour = current - 60 * 60 * 1000;
        Cursor cur = db.query(TABLE_MESSAGE, null, "createTime>?", new String[]{before1hour + ""}, null, null, null);
        ArrayList<Message> messages = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String remark = cur.getString(cur.getColumnIndex("remark"));
            String content = cur.getString(cur.getColumnIndex("content"));
            String createTime = cur.getString(cur.getColumnIndex("createTime"));
            Message m = new Message();
            m.remark = remark;
            m.content = content;
            m.createTime = Long.parseLong(createTime);
            messages.add(m);
        }
        cur.close();
        db.close();
        return messages;
    }

    //---------------------------moment------------------

    public void addMoment(Moment m) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("momentID", m.momentID);
        values.put("description", m.description);
        db.insert(TABLE_WX_MOMENT, null, values);
        db.close();
    }

    public Moment getMomentByDescription(String description) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_WX_MOMENT, null, "description=?", new String[]{description}, null, null, null);
        Moment m = null;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String momentID = cur.getString(cur.getColumnIndex("momentID"));
            description = cur.getString(cur.getColumnIndex("description"));
            m = new Moment(momentID, description);
        }
        cur.close();
        db.close();
        return m;
    }

    public ArrayList<Moment> getAllMoments() {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_WX_MOMENT, null, null, null, null, null, null);
        ArrayList<Moment> moments = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String momentID = cur.getString(cur.getColumnIndex("momentID"));
            String description = cur.getString(cur.getColumnIndex("description"));
            Moment m = new Moment(momentID, description);
            moments.add(m);
        }
        cur.close();
        db.close();
        return moments;
    }

    public void deleteMoment(String momentID) {
        if (db == null)
            db = getWritableDatabase();
        db.execSQL("delete from WX_MOMENT where momentID = '" + momentID + "'");
        db.close();
    }

    //------------------------------comment-----------------------
    public boolean checkCommentExisted(String momentID, String author, String content, long time) {
        if (db == null)
            db = getWritableDatabase();
        String sql = "select * from WX_COMMENT where momentID = '" + momentID + "' and  author = '" + author + "' and content = '" + content + "' and createDate = " + time;
        Log.d("test", "sql = " + sql);
        Cursor cur = db.rawQuery(sql, null);
        boolean existed = false;
        if (cur.moveToNext()) {
            existed = true;
        }
        cur.close();
        db.close();
        return existed;
    }

    public void addComment(SnsInfo.Comment m) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("momentID", m.momentID);
        values.put("author", m.authorName);
        values.put("content", m.content);
        values.put("toUser", m.toUser);
        values.put("createDate", m.time);
        values.put("uploaded", 0);

        db.insert(TABLE_WX_COMMENT, null, values);
        db.close();
    }

    public ArrayList<SnsInfo.Comment> getCommentsByMomentID(String momentID) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        if (momentID == null) {
            cur = db.query(TABLE_WX_COMMENT, null, null, null, null, null, null);
        } else {
            cur = db.query(TABLE_WX_COMMENT, null, "momentID=?", new String[]{momentID}, null, null, null);
        }
        ArrayList<SnsInfo.Comment> comments = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            momentID = cur.getString(cur.getColumnIndex("momentID"));
            String author = cur.getString(cur.getColumnIndex("author"));
            String content = cur.getString(cur.getColumnIndex("content"));
            String toUser = cur.getString(cur.getColumnIndex("toUser"));
            long createDate = cur.getLong(cur.getColumnIndex("createDate"));
            int uploaded = cur.getInt(cur.getColumnIndex("uploaded"));
            SnsInfo.Comment c = new SnsInfo.Comment(momentID, author, content, toUser, createDate, uploaded);
            comments.add(c);
        }
        cur.close();
        db.close();
        return comments;
    }

    public void setCommentUploaded(String momentID, String author, String content, long time, int uploaded) {
        if (db == null)
            db = getWritableDatabase();
        String sql = "update WX_COMMENT set uploaded = " + uploaded + " where momentID = '" + momentID + "' and  author = '" + author + "' and content = '" + content + "' and createDate = " + time;
        Log.d("test", "sql = " + sql);
        db.execSQL(sql);
        db.close();
    }

    public void deleteComment(String momentID) {
        if (db == null)
            db = getWritableDatabase();
        db.execSQL("delete from WX_COMMENT where momentID = '" + momentID + "'");
        db.close();
    }


    //-------------------------filter----------------------

    public void addFilter(Filter filter) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", filter.type);
        values.put("name", filter.name);
        values.put("wxNo", filter.wxNo);
        db.insert(TABLE_FILTER, null, values);
        db.close();
    }

    //0转发使者==>全部 1卧底 2其他
    public ArrayList<Filter> getAllFilters(int type) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        if (type == 0) {
            cur = db.query(TABLE_FILTER, null, null, null, null, null, null);
        } else {
            cur = db.query(TABLE_FILTER, null, "type=?", new String[]{type + ""}, null, null, null);
        }
        ArrayList<Filter> groups = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String name = cur.getString(cur.getColumnIndex("name"));
            String wxNo = cur.getString(cur.getColumnIndex("wxNo"));
            type = cur.getInt(cur.getColumnIndex("type"));
            Filter filter = new Filter(id, name, wxNo, type);
            groups.add(filter);
        }
        cur.close();
        db.close();

        return groups;
    }

    public void deleteFilter(int id) {
        if (db == null)
            db = getWritableDatabase();
        db.execSQL("delete from filter where id = " + id);
        db.close();
    }


    //-------------------------servermsg----------------------

    public void addServerMsg(ServerMsg sm) {
        if (sm.index == 0) {
            return;
        }
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("indexs", sm.index);
        values.put("content", sm.content);
        values.put("replyContent", sm.replyContent);
        values.put("status", sm.status);
        values.put("time", sm.time);
        values.put("type", sm.type);
        db.insert(TABLE_SERVER_MSG, null, values);
        db.close();
    }

    public ArrayList<ServerMsg> getAllServerMsg(int getType) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = null;
        if (getType == 0) {
            cur = db.query(TABLE_SERVER_MSG, null, null, null, null, null, null);
        } else if (getType == 3) {
            cur = db.query(TABLE_SERVER_MSG, null, "status!=? and status!=?", new String[]{"1", "3"}, null, null, null);
        } else if (getType == 1) {
            cur = db.query(TABLE_SERVER_MSG, null, "status=?", new String[]{"1"}, null, null, null);
        }
        ArrayList<ServerMsg> groups = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            int index = cur.getInt(cur.getColumnIndex("indexs"));
            String content = cur.getString(cur.getColumnIndex("content"));
            String replyContent = cur.getString(cur.getColumnIndex("replyContent"));
            int status = cur.getInt(cur.getColumnIndex("status"));
            long time = cur.getLong(cur.getColumnIndex("time"));
            int type = cur.getInt(cur.getColumnIndex("type"));
            ServerMsg sm = new ServerMsg(id, index, content, replyContent, status, time, type);
            groups.add(sm);
        }
        cur.close();
        db.close();

        return groups;
    }

    public void updateServerMsgStatusByIndex(int index, int status) {
        Log.d("test", "updateServerMsgStatusByIndex index = " + index + " , status = " + status);
        if (index == 0) {
            return;
        }
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        String[] args = {index + ""};
        db.update(TABLE_SERVER_MSG, cv, "indexs=?", args);
        db.close();
    }

    public void updateServerMsgReplyContentByIndex(int index, String replyContent) {
        Log.d("test", "updateServerMsgStatusByIndex index = " + index + " , replyContent = " + replyContent);
        if (index == 0) {
            return;
        }
        if (db == null)
            db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("replyContent", replyContent);
        String[] args = {index + ""};
        db.update(TABLE_SERVER_MSG, cv, "indexs=?", args);
        db.close();
    }

    public boolean isIndexExisted(int index) {
        if (db == null)
            db = getWritableDatabase();
        Cursor cur = db.query(TABLE_SERVER_MSG, null, "indexs=?", new String[]{index + ""}, null, null, null);
        int count = 0;
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            count++;
        }
        cur.close();
        db.close();
        return count > 0;
    }


    public void deleteServerMsg() {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TABLE_SERVER_MSG, null, null);
        db.close();
    }
}