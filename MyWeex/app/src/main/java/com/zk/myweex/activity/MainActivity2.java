package com.zk.myweex.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.myweex.R;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.VersionUpManager;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        new Thread() {
            @Override
            public void run() {
                try {
                    //第一次，初始化tab
                    int tabcount = Realm.getDefaultInstance().where(TabEntity.class).findAll().size();
                    if (tabcount == 0) {
                        List<Service> services = new Service().find(new KWQuery().like("id", "tab%"));
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
                        List<Service> services = new Service().find(new KWQuery().like("id", "tab%"));
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RealmResults<TabEntity> tabs = Realm.getDefaultInstance().where(TabEntity.class).findAll();
                            initView(tabs);
                        }
                    });

                    VersionUpManager manager = new VersionUpManager();
                    manager.init(getApplication());
                    manager.getLocalVersion();
                    manager.getRemoteVersion();
                } catch (Exception e) {
                    e.printStackTrace();
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

//    public void setTab(int position) {
//        switch (position) {
//            case 0:
//                ll1.performClick();
//                break;
//            case 1:
//                ll2.performClick();
//                break;
//            case 2:
//                ll3.performClick();
//                break;
//            case 3:
//                ll4.performClick();
//                break;
//
//
//            default:
//                break;
//        }
//    }

//    @Override
//    protected void onRestart() {
//        int position = tabhost.getCurrentTab();
//        if (position == 2 && !Utils.isLogin(this)) {
//            position = 0;
//        }
//        if (position == 4 && !Utils.isLogin(this)) {
//            position = 0;
//        }
//        setTab(position);
//        super.onRestart();
//    }
}
