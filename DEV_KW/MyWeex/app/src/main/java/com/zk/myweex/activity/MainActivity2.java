package com.zk.myweex.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.myweex.R;
import com.zk.myweex.WXApplication;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.FileUtils;
import com.zk.myweex.utils.VersionUpManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity2 extends TabActivity {

    private TabHost tabhost;
    private LinearLayout bottom;
    private ArrayList<LinearLayout> lls = new ArrayList<>();
    public static MainActivity2 main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        main = this;

        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
        }

        getDataFromServer();
    }

    private void getDataFromServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int tabcount = Realm.getDefaultInstance().where(TabEntity.class).findAll().size();
                    List<Service> services = new Service().find(new KWQuery().like("id", "tab%"));
                    //第一次，初始化tab
                    if (tabcount == 0) {
                        for (Service s : services) {
                            Realm.getDefaultInstance().beginTransaction();
                            TabEntity tab = Realm.getDefaultInstance().createObject(TabEntity.class);
                            tab.id = s.get("id").toString();
                            tab.name = s.get("name").toString();
                            tab.image_default = "";
                            tab.image_selected = "";
                            Realm.getDefaultInstance().commitTransaction();
                        }
                    } else {
                        for (Service s : services) {
                            Realm.getDefaultInstance().beginTransaction();
                            TabEntity tab = Realm.getDefaultInstance().where(TabEntity.class).equalTo("id", s.get("id").toString()).findFirst();
                            tab.id = s.get("id").toString();
                            tab.name = s.get("name").toString();
                            tab.image_default = "";
                            tab.image_selected = "";
                            Realm.getDefaultInstance().commitTransaction();
                        }
                    }


                    VersionUpManager manager = new VersionUpManager();
                    manager.init(getApplication());
                    manager.getLocalVersion();
                    manager.getRemoteVersion();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "no net ... ");
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RealmResults<TabEntity> tabs = Realm.getDefaultInstance().where(TabEntity.class).findAll();
                            initView(tabs);
                        }
                    });
                }
            }
        }.start();
    }

    private void initView(RealmResults<TabEntity> tabs) {
        if (tabs == null) {
            return;
        }
        int tabcount = tabs.size();
        if (tabcount == 0) {
            return;
        }

//FIXME hardcode
        tabcount = 3;

        bottom = (LinearLayout) findViewById(R.id.bottom);
        bottom.setWeightSum(tabcount);
        tabhost = getTabHost();
        for (int i = 0; i < tabcount; i++) {
            TabEntity tabEntity = tabs.get(i);

            final int ii = i;
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_tab, null);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
            tv.setText(tabEntity.name);//名字

            bottom.addView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            lls.add(ll);

            Intent tab = new Intent(this, MyTabActivity.class);
            tab.putExtra("position", ii);
            tabhost.addTab(tabhost
                    .newTabSpec("tab" + ii)
                    .setIndicator("tab" + ii,
                            getResources().getDrawable(R.mipmap.ic_launcher))
                    .setContent(tab));
            ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    tabhost.setCurrentTab(ii);

                    refreshUI(ii);
                }
            });
        }

        refreshUI(0);
    }

    private void refreshUI(int position) {
        for (int i = 0; i < lls.size(); i++) {
            LinearLayout ll = lls.get(i);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
            if (i == position) {
                tv.setTextColor(getResources().getColor(R.color.orange));
//                iv.setBackgroundResource(R.drawable.tab12);
            } else {
                tv.setTextColor(getResources().getColor(R.color.lightblack));
//                iv.setBackgroundResource(R.drawable.tab12);
            }
        }
    }

    protected void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity2.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    protected void toast(final int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity2.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
            int arg1 = msg.arg1;
            if (arg1 == 0) {
                rl_nonet.setVisibility(View.VISIBLE);
            } else {
                rl_nonet.setVisibility(View.GONE);
            }
        }
    };


}
