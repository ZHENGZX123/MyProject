package cn.kiway.autoreply.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.adpater.LvCarIdsDailogAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wx);
        getWxPeople();
        getWxRoom();
        initView();
        setData();
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
        findViewById(R.id.gr_zf).setOnClickListener(this);
        findViewById(R.id.room_zf).setOnClickListener(this);
        findViewById(R.id.room_mb).setOnClickListener(this);
        findViewById(R.id.friend).setOnClickListener(this);
    }

    private void setData() {
        try {
            String item1 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFORWARDSERVIDE, "");
            String item2 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFOREARD_ROOM, "");
            String item3 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString
                    (SHE_MSG_TOFORDARD_ROOMMB, "");
            String item4 = getSharedPreferences("kiway", Activity.MODE_WORLD_READABLE).getString(SHE_FREIEND_ROOM, "");
            if (!item1.equals("")) {
                JSONObject jbItem1 = new JSONObject(item1);
                if (!jbItem1.optString("remark").equals("")) {//备注
                    gr_zf.setText(jbItem1.optString("remark"));
                } else {
                    gr_zf.setText(jbItem1.optString("nickname"));
                }
            }
            if (!item2.equals("")) {
                JSONObject jbItem2 = new JSONObject(item2);
                if (!jbItem2.optString("nickname").equals("")) {//备注
                    room_zf.setText(jbItem2.optString("nickname"));
                } else {
                    room_zf.setText(jbItem2.optString("displayname"));
                }
            }
            if (!item3.equals("")) {
                JSONObject jbItem3 = new JSONObject(item3);
                if (!jbItem3.optString("remark").equals("")) {//备注
                    room_mb.setText(jbItem3.optString("remark"));
                } else {
                    room_mb.setText(jbItem3.optString("nickname"));
                }
            }
            if (!item4.equals("")) {
                JSONObject jbItem4 = new JSONObject(item4);
                if (!jbItem4.optString("nickname").equals("")) {//备注
                    friend.setText(jbItem4.optString("nickname"));
                } else {
                    friend.setText(jbItem4.optString("displayname"));
                }
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
            Log.e("zzx", jsonObject.toString());
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
            }
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
            Log.e("zzx", jsonObject.toString());
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
            }
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
}
