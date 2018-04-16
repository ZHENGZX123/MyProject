package cn.kiway.autoreply.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.easy.wtool.sdk.MessageEvent;
import com.easy.wtool.sdk.OnMessageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.adpater.LvCarIdsDailogAdapter;
import cn.kiway.autoreply.db.MyDBHelper;
import cn.kiway.autoreply.util.Utils;

import static cn.kiway.autoreply.util.Constant.SHE_FREIEND_ROOM;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFORDARD_ROOMMB;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFOREARD_ROOM;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFORWARDSERVIDE;

/**
 * Created by Administrator on 2018/3/29.
 */

public class WxSettingActivity extends BaseActivity implements View.OnClickListener {
    JSONArray wxPeopleList = new JSONArray();
    JSONArray wxRoomList = new JSONArray();
    View chooseView;
    TextView gr_zf, room_zf, room_mb, friend;
    CheckBox checkBox;
    String item1, item2, item3, item4;
    String wxid1,wxid2,wxid3,wxid4;
    String toForwradTalker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wx);
        getWxPeople();
        getWxRoom();
        initView();
        setData();
        app.wToolSDK.setOnMessageListener(new OnMessageListener() {
            @Override
            public void messageEvent(MessageEvent event) {
                try {
                    String talker = event.getTalker();
                    JSONObject wxMsg = new JSONObject(event.getContent());
                    String content = app.wToolSDK.decodeValue(wxMsg.getString("content"));
                    String msgid = wxMsg.getString("msgid");
                    if (talker.endsWith("@chatroom")) {
                        Log.e("zzx", "这是群消息");
                        if (talker.equals(wxid4)) {//转发到朋友圈
                            Log.e("zzx", "朋友圈群");     //TODO: 2018/4/2
                        } else if (talker.equals(wxid2)) {//群内消息做转发
                            if (content.startsWith("toForward")) {//做转发给谁的消息
                                if (content.split(":")[1].length() > 2)
                                    toForwradTalker = content.split(":")[1];
                                Log.e("zzx", "转发群：转发到目标人" + toForwradTalker);
                            } else {
                                Log.e("zzx", "转发群：转发的内容");
                                if (!toForwradTalker.equals(""))
                                    forWard(toForwradTalker, event.getMsgType(), msgid);
                                toForwradTalker = "";
                            }
                        } else {
                            Log.e("zzx", "其他群");// TODO: 2018/4/2
                        }
                    } else {
                        // TODO: 2018/4/2
                        if (Utils.isForwrad(event.getMsgType())) {//是否需要转发
                            forWard(wxid1, event.getMsgType(), msgid);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void forWard(String talker, int msgType, String msgId) {
        JSONObject jsonTask = new JSONObject();
        try {//开发消息监听后这里做转发回去
            jsonTask.put("type", 12);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("talker", talker);//转发给谁
            jsonTask.getJSONObject("content").put("msgtype", msgType);//转发的消息类型
            jsonTask.getJSONObject("content").put("msgid", msgId);//转发的msgId
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s = app.wToolSDK.sendTask(jsonTask.toString());
        Log.e("---", s);
    }

    @Override
    public void onClick(View view) {
        chooseView = (View) view;
        switch (view.getId()) {
            case R.id.gr_zf:
                showDialog(wxPeopleList, false);
                break;
            case R.id.room_zf:
                showDialog(wxRoomList, true);
                break;
            case R.id.room_mb:
                showDialog(wxPeopleList, false);
                break;
            case R.id.friend:
                showDialog(wxRoomList, true);
                break;
        }
    }

    private void initView() {
        gr_zf = (TextView) findViewById(R.id.gr_zf);
        room_zf = (TextView) findViewById(R.id.room_zf);
        room_mb = (TextView) findViewById(R.id.room_mb);
        friend = (TextView) findViewById(R.id.friend);
        checkBox = (CheckBox) findViewById(R.id.wxReply);
        findViewById(R.id.gr_zf).setOnClickListener(this);
        findViewById(R.id.room_zf).setOnClickListener(this);
        findViewById(R.id.room_mb).setOnClickListener(this);
        findViewById(R.id.friend).setOnClickListener(this);
        if (getSharedPreferences("kiway", 0).getBoolean("wxReply", false)) {
            checkBox.setChecked(true);
            openMessageReply();
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBox.setText("关闭自动回复");
                    getSharedPreferences("kiway", 0).edit().putBoolean("wxReply", true).commit();
                    openMessageReply();
                } else {
                    checkBox.setText("开启自动回复");
                    getSharedPreferences("kiway", 0).edit().putBoolean("wxReply", false).commit();
                }
            }
        });
    }


    private void setData() {
        try {
            item1 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFORWARDSERVIDE, "");
            item2 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFOREARD_ROOM, "");
            item3 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFORDARD_ROOMMB, "");
            item4 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString(SHE_FREIEND_ROOM, "");
            if (!item1.equals("")) {
                JSONObject jbItem1 = new JSONObject(item1);
                if (!jbItem1.optString("remark").equals("")) {//备注
                    gr_zf.setText(jbItem1.optString("remark"));
                } else {
                    gr_zf.setText(jbItem1.optString("nickname"));
                }
                wxid1=jbItem1.optString("wxid");
            }
            if (!item2.equals("")) {
                JSONObject jbItem2 = new JSONObject(item2);
                if (!jbItem2.optString("nickname").equals("")) {//备注
                    room_zf.setText(jbItem2.optString("nickname"));
                } else {
                    room_zf.setText(jbItem2.optString("displayname"));
                }
                wxid2=jbItem2.optString("wxid");
            }
            if (!item3.equals("")) {
                JSONObject jbItem3 = new JSONObject(item3);
                if (!jbItem3.optString("remark").equals("")) {//备注
                    room_mb.setText(jbItem3.optString("remark"));
                } else {
                    room_mb.setText(jbItem3.optString("nickname"));
                }
                wxid3=jbItem3.optString("wxid");
            }
            if (!item4.equals("")) {
                JSONObject jbItem4 = new JSONObject(item4);
                if (!jbItem4.optString("nickname").equals("")) {//备注
                    friend.setText(jbItem4.optString("nickname"));
                } else {
                    friend.setText(jbItem4.optString("displayname"));
                }
                wxid4=jbItem4.optString("wxid");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logout(View v) {
        app.wToolSDK.unload();
        finish();
    }

    private void getWxPeople() {
        try {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("type", 5);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("pageindex", 0);
            jsonTask.getJSONObject("content").put("pagecount", 0);
            String content = app.wToolSDK.sendTask(jsonTask.toString());
            JSONObject jsonObject = new JSONObject(content);
            Log.e("people", jsonObject.toString());
            if (jsonObject.getInt("result") == 0) {
                wxPeopleList = jsonObject.getJSONArray("content");
                for (int i = 0; i < wxPeopleList.length(); i++) {
                    JSONObject item = wxPeopleList.getJSONObject(i);
                    String nickname = app.wToolSDK.decodeValue(item.getString("nickname"));//解码
                    String remark = app.wToolSDK.decodeValue(item.getString("remark"));
                    String wxid = app.wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = app.wToolSDK.decodeValue(item.getString("wxno"));
                    item.put("nickname", nickname);//昵称
                    item.put("remark", remark);//备注
                    item.put("wxid", wxid);//微信id
                    item.put("wxno", wxno);//微信号
                    wxPeopleList.put(i, item);
                }
                new MyDBHelper(this).addWxPeople(wxPeopleList);
            }
            wxPeopleList = new MyDBHelper(this).getWxPeople();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWxRoom() {
        try {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("type", 6);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("pageindex", 0);
            jsonTask.getJSONObject("content").put("pagecount", 0);
            jsonTask.getJSONObject("content").put("ismembers", 0);
            String content = app.wToolSDK.sendTask(jsonTask.toString());
            JSONObject jsonObject = new JSONObject(content);
            Log.e("wxRoom", jsonObject.toString());
            if (jsonObject.getInt("result") == 0) {
                wxRoomList = jsonObject.getJSONArray("content");
                for (int i = 0; i < wxRoomList.length(); i++) {
                    JSONObject item = wxRoomList.getJSONObject(i);
                    String displayname = app.wToolSDK.decodeValue(item.getString("displayname"));//解码
                    String nickname = app.wToolSDK.decodeValue(item.getString("nickname"));
                    String roomowner = app.wToolSDK.decodeValue(item.getString("roomowner"));
                    String wxid = app.wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = app.wToolSDK.decodeValue(item.getString("wxno"));
                    item.put("displayname", displayname);//群名
                    item.put("nickname", nickname);//备注
                    item.put("roomowner", roomowner);//谁创建
                    item.put("wxid", wxid);//微信id
                    item.put("wxno", wxno);//微信号
                    wxRoomList.put(i, item);
                }
                new MyDBHelper(this).addWxRoom(wxRoomList);
            }
            wxRoomList = new MyDBHelper(this).getWxRoom();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(final JSONArray array, boolean isRoom) {
        View bottomView = View.inflate(WxSettingActivity.this, R.layout.carids_dialog, null);//填充ListView布局
        ListView lvCarIds = (ListView) bottomView.findViewById(R.id.lv_carids);//初始化ListView控件
        AlertDialog parkIdsdialog = new AlertDialog.Builder(this)
                .setTitle(isRoom ? "选择群" : "选择个人微信号").setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        parkIdsdialog.show();
        lvCarIds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("-----", array
                        .optJSONObject(i).toString());
                if (chooseView != null) {
                    switch (chooseView.getId()) {
                        case R.id.gr_zf:
                            getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).edit().putString
                                    (SHE_MSG_TOFORWARDSERVIDE, array
                                            .optJSONObject(i).toString()).commit();
                            break;
                        case R.id.room_zf:
                            getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).edit().putString
                                    (SHE_MSG_TOFOREARD_ROOM, array
                                            .optJSONObject(i).toString()).commit();
                            break;
                        case R.id.room_mb:
                            getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).edit().putString
                                    (SHE_MSG_TOFORDARD_ROOMMB, array
                                            .optJSONObject(i).toString()).commit();
                            break;
                        case R.id.friend:
                            getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).edit().putString
                                    (SHE_FREIEND_ROOM, array
                                            .optJSONObject(i).toString()).commit();
                            break;
                    }
                }
                setData();
                if (parkIdsdialog != null && parkIdsdialog.isShowing())
                    parkIdsdialog.dismiss();
            }
        });
        lvCarIds.setAdapter(new LvCarIdsDailogAdapter(this, array, isRoom));//ListView设置适配器
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.wToolSDK.unload();
    }

    public void openMessageReply() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(1);//个人
            jsonArray.put(2);//群聊
            jsonObject.put("talkertypes", jsonArray);//设置监听的消息来源
            jsonObject.put("froms", new JSONArray());//可以设置监听某个人或者群聊的消息  wxid roomid
            jsonArray = new JSONArray();
//            jsonArray.put(1);//文字 设置监听的消息类型
//            jsonArray.put(3);//图片
//            jsonArray.put(34);//语音
//            jsonArray.put(42);//名片
//            jsonArray.put(43);//视频
//            jsonArray.put(49);//图文链接
//            jsonArray.put(62);//不知道是啥
            for (int i=0;i<100;i++){
                jsonArray.put(i);
            }
            jsonObject.put("msgtypes", jsonArray);
            jsonObject.put("msgfilters", new JSONArray());
            String result = app.wToolSDK.startMessageListener(jsonObject.toString());
            Log.e("----", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
