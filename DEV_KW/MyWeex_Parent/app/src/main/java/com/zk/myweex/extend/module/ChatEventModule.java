package com.zk.myweex.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;

/**
 * Created by Administrator on 2017/3/24.
 */

public class ChatEventModule extends WXModule {
    @JSMethod()
    public void creatGroup(String str, final JSCallback callback) {
        Log.d("test", "creatGroup = " + str);
        // {"groupList":[{"nickname":"(╥﹏23","uid":"247"},{"nickname":"Mybaby","uid":"4637abf00aed11e78ad90959ab5c0b23"}],"groupName":"tt","classId":"56"}
        try {
            JSONObject obj = new JSONObject(str);
            final String groupName = obj.getString("groupName");
            String classId = obj.getString("classId");
            JSONArray groupList = obj.getJSONArray("groupList");
            String json = "[";
            for (int i = 0; i < groupList.length(); i++) {
                JSONObject temp = groupList.getJSONObject(i);
                if (i != groupList.length() - 1) {
                    json = json + "{'uid':'" + temp.getString("uid") + "','nickname':'" + temp.getString("nickname") + "'},";
                } else {
                    json = json + "{'uid':'" + temp.getString("uid") + "','nickname':'" + temp.getString("nickname") + "'}";
                }
            }
            json = json + "]";
            final String j = json;
            new Thread() {
                @Override
                public void run() {
                    String names = "";
                    names = "{'name':'" + groupName + "','icon':'','notice':'','intro':'','type':'','ispublic':'','isvalidate':'','maxnum':'1000'}";
                    String ret = MqttInstance.getInstance().getPushInterface().creatGroup(names, j);
                    Log.d("test", "ret = " + ret);
                    if (ret != null && ret.contains("200")) {
                        MainListDao.saveGroupList(MqttInstance.getInstance().getPushInterface().getGroupList(), DaoType.SESSTIONTYPE.GROUP);
                        //发起群聊成功，返回1
                        HashMap map = new HashMap();
                        map.put("result", "1");
                        callback.invoke(map);
                    } else {
                        toast("创建群组失败");
                    }
                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}