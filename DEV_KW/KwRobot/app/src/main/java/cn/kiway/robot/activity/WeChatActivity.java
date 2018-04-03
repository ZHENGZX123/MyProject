package cn.kiway.robot.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.easy.wtool.sdk.WToolSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.util.WxUtils;

/**
 * Created by Administrator on 2018/4/3.
 */

public class WeChatActivity extends BaseActivity {
    public WToolSDK wToolSDK = new WToolSDK();
    JSONArray wxPeopleList = new JSONArray();
    JSONArray wxRoomList = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        wToolSDK.encodeValue("1");
        String s = wToolSDK.init("9999", "757533D0860F8CC0590B510BE2374F48C5750673");
        // String s = wToolSDK.init("17810096", "010112143ECFD10AC82DE363C837A7CBB45E0302");
        Log.e("zzx", s);
    }

    public void getFriend(View view) {
        //1.获取所有的好友
        //2.上报给易敏
        getWxPeople();
    }

    public void groupSendText(View view) {
        //群发文字、群发卡片给所有好友
        ArrayList<String> list = WxUtils.getCeWxLists();
        for (int i = 0; i < list.size(); i++) {
            sendWxMsg(1, list.get(i), "文字测试内容");
        }
    }

    public void groupSendImage(View view) {
        ArrayList<String> list = WxUtils.getCeWxLists();
        for (int i = 0; i < list.size(); i++) {
            sendWxMsg(2, list.get(i), "http://img2.imgtn.bdimg.com/it/u=2239146502,165013516&fm=27&gp=0.jpg");
        }
    }

    public void getFriendCircle(View view) {
        //1.获取所有好友的朋友圈
        //2.上报给易敏
    }

    private void getWxPeople() {
        try {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("type", 5);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("pageindex", 0);
            jsonTask.getJSONObject("content").put("pagecount", 0);
            String content = wToolSDK.sendTask(jsonTask.toString());
            JSONObject jsonObject = new JSONObject(content);

            if (jsonObject.getInt("result") == 0) {
                wxPeopleList = jsonObject.getJSONArray("content");
                for (int i = 0; i < wxPeopleList.length(); i++) {
                    JSONObject item = wxPeopleList.getJSONObject(i);
                    String nickname = wToolSDK.decodeValue(item.getString("nickname"));//解码
                    String remark = wToolSDK.decodeValue(item.getString("remark"));
                    String wxid = wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = wToolSDK.decodeValue(item.getString("wxno"));
                    item.put("nickname", nickname);//昵称
                    item.put("remark", remark);//备注
                    item.put("wxid", wxid);//微信id
                    item.put("wxno", wxno);//微信号
                    wxPeopleList.put(i, item);
                }
                new MyDBHelper(this).addWxPeople(wxPeopleList);
            }
            wxPeopleList = new MyDBHelper(this).getWxPeople();
            Log.e("zzx", wxPeopleList.toString());
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
            String content = wToolSDK.sendTask(jsonTask.toString());
            JSONObject jsonObject = new JSONObject(content);
            Log.e("zzx", jsonObject.toString());
            if (jsonObject.getInt("result") == 0) {
                wxRoomList = jsonObject.getJSONArray("content");
                for (int i = 0; i < wxRoomList.length(); i++) {
                    JSONObject item = wxRoomList.getJSONObject(i);
                    String displayname = wToolSDK.decodeValue(item.getString("displayname"));//解码
                    String nickname = wToolSDK.decodeValue(item.getString("nickname"));
                    String roomowner = wToolSDK.decodeValue(item.getString("roomowner"));
                    String wxid = wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = wToolSDK.decodeValue(item.getString("wxno"));
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

    public void sendWxMsg(int type, String wxid, String content) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("taskid", System.currentTimeMillis());
            jsonObject.put("content", new JSONObject());
            jsonObject.getJSONObject("content").put("talker", wxid);
            jsonObject.getJSONObject("content").put("timeout", -1);
            if (type == 1) {//文字
                jsonObject.getJSONObject("content").put("text", wToolSDK.encodeValue(content));
            } else if (type == 2) {//图片
                jsonObject.getJSONObject("content").put("imagefile", content);//content为图片地址
            } else if (type == 3) {//语音
                jsonObject.getJSONObject("content").put("voicefile", content);//content未语音地址
                jsonObject.getJSONObject("content").put("duration", 60);
            } else if (type == 4) {//视频
                //  jsonObject.getJSONObject("content").put("videofile",toVideoFile);//视频地址
                //    jsonObject.getJSONObject("content").put("videothumbfile", toVideoThumbFile);//视频缩略图地址
                //  jsonObject.getJSONObject("content").put("duration",60);
            }
            String result = wToolSDK.sendTask(jsonObject.toString());
            Log.e("----", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wToolSDK.unload();
    }
}
