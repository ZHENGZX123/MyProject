package com.zk.myweex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.zk.myweex.utils.ScreenUtil;

import cn.kiway.utils.SharedPreferencesUtil;
import cn.kwim.mqttcilent.app_ui.fragment.HomeSchoolAdapter;
import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class GroupListView extends WXComponent<ListView> {

    private ListView lv;
    private HomeSchoolAdapter adapter;

    public GroupListView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ListView initComponentHostView(@NonNull Context context) {
        Log.d("mqtt", "ListView initComponentHostView");
        this.lv = new ListView(context);
        this.lv.setBackgroundColor(Color.WHITE);
        this.lv.setFocusable(false);
        this.lv.setFocusableInTouchMode(false);
        this.adapter = new HomeSchoolAdapter(getContext());
        this.lv.setAdapter(this.adapter);
        addListener();
        initData();
        return this.lv;
    }

    private void addListener() {
        Realm realm = Realm.getDefaultInstance();
        RealmChangeListener<Realm> realmListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                Log.d("test", "mqtt onchange");
                refreshUI();
            }
        };
        realm.addChangeListener(realmListener);
    }

    private void initData() {
        refreshUI();

        RealmResults<MainList> s = MainListDao.getMainList();
        if (s != null) {
            for (int i = 0; i < s.size(); i++) {
                MainList groupList = s.get(i);
                String groupid = groupList.getId();
                String key = groupList.getKey();
                int sum = MessageDao.unreadCount(groupid, DaoType.SESSTIONTYPE.GROUP);
                Message message = MessageDao.getLastContent(groupid, DaoType.SESSTIONTYPE.GROUP);
                if (message != null) {
                    MainListDao.updateGroupListChat(sum + "", key, message.getMsg(), message.getMessageType(), message
                            .getSendName());
                }
            }
        }
    }

    private void refreshUI() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取到数据之后，刷新列表
                try {
                    Global.getInstance().setUserId(SharedPreferencesUtil.getString(getContext(), Global.GL_UID));
                    Global.getInstance().setNickName(SharedPreferencesUtil.getString(getContext(), Global.GL_NICKNAME));
                    Global.getInstance().setLogo(SharedPreferencesUtil.getString(getContext(), Global.GL_LOGO));

                    RealmResults<MainList> list = MainListDao.getMainList();
                    if (list != null) {
                        adapter.setList(list);
                        int width = ScreenUtil.getDisplayWidth((AppCompatActivity) getContext());
                        int height = ScreenUtil.getDisplayHeight((AppCompatActivity) getContext()) - getComponentSize().top;
                        GroupListView.this.setHostLayoutParams(getHostView(), width, height, 0, 0, 0, 0);
                    } else {
                        Log.d("mqtt", "list is null");
                    }
                } catch (Exception e) {
                }
            }
        });
    }


}
