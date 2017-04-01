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

import com.kiway.yjpt.Teacher.R;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.entity.ZipPackage;
import com.zk.myweex.utils.MyDBHelper;
import com.zk.myweex.utils.Utils;
import com.zk.myweex.utils.VersionUpManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Service;


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
        Utils.checkNetWork(this);
        getDataFromServer();
    }

    private void getDataFromServer() {
        Log.d("test", "getDataFromServer");
        new Thread() {
            @Override
            public void run() {
                try {
                    int tabcount = new MyDBHelper(getApplicationContext()).getAllTabEntity().size();
                    //第一次，初始化tab
                    if (tabcount == 0) {
                        List<Service> services = new Service().find(new KWQuery().like("id", "tab%"));
                        for (Service s : services) {
                            //插入数据库
                            TabEntity tab = new TabEntity();
                            tab.idStr = s.get("id").toString();
                            tab.name = s.get("name").toString();
                            tab.image_default = "";
                            tab.image_selected = "";
                            new MyDBHelper(getApplicationContext()).addTabEntity(tab);
                        }
                    }

                    checkZipVersion();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "no net ... ");
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<TabEntity> tabs = new MyDBHelper(getApplicationContext()).getAllTabEntity();
                            Log.d("test", "main initView");
                            if (tabs.size() == 0) {
                                //第一次加载不成功的情况
                                ArrayList<TabEntity> temp = new ArrayList<>();
                                TabEntity tab0 = new TabEntity();
                                tab0.name = "首页";
                                tab0.idStr = "tab0";
                                TabEntity tab1 = new TabEntity();
                                tab1.name = "亲子圈";
                                tab1.idStr = "tab1";
                                TabEntity tab2 = new TabEntity();
                                tab2.name = "我的";
                                tab2.idStr = "tab2";
                                temp.add(tab0);
                                temp.add(tab1);
                                temp.add(tab2);
                                //插入数据库。。
                                new MyDBHelper(getApplicationContext()).addTabEntity(tab0);
                                new MyDBHelper(getApplicationContext()).addTabEntity(tab1);
                                new MyDBHelper(getApplicationContext()).addTabEntity(tab2);

                                ZipPackage zip0 = new ZipPackage();
                                zip0.name = "tab0.zip";
                                zip0.indexPath = "yjpt/weex_jzd/main.js";
                                zip0.version = "1.0.0";
                                zip0.patchs = "";
                                new MyDBHelper(getApplicationContext()).addZipPackage(zip0);

                                ZipPackage zip1 = new ZipPackage();
                                zip1.name = "tab1.zip";
                                zip1.indexPath = "yjpt/weex_jzd/qzq.js";
                                zip1.version = "1.0.0";
                                zip1.patchs = "";
                                new MyDBHelper(getApplicationContext()).addZipPackage(zip1);

                                ZipPackage zip2 = new ZipPackage();
                                zip2.name = "tab2.zip";
                                zip2.indexPath = "yjpt/weex_jzd/wd.js";
                                zip2.version = "1.0.0";
                                zip2.patchs = "";
                                new MyDBHelper(getApplicationContext()).addZipPackage(zip2);

                                tabs = new MyDBHelper(getApplicationContext()).getAllTabEntity();
                                initView(tabs);
                            } else {
                                //第一次加载成功
                                initView(tabs);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private void checkZipVersion() {
        new Thread() {
            @Override
            public void run() {
                VersionUpManager manager = new VersionUpManager();
                manager.init(getApplication());
                manager.getLocalVersion();
                manager.getRemoteVersion();
            }
        }.start();
    }

    private void initView(ArrayList<TabEntity> tabs) {
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
        Log.d("test", "refreshUI = " + position);
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

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

}
