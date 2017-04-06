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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.MyDBHelper;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.Utils;
import com.zk.myweex.utils.VersionUpManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.Yjptj.R;
import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Service;

import static uk.co.senab.photoview.sample.ViewPagerActivity.getLoaderOptions;


public class MainActivity2 extends TabActivity {

    private TabHost tabhost;
    private LinearLayout bottom;
    private ArrayList<LinearLayout> lls = new ArrayList<>();
    public static MainActivity2 main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ScreenManager.getScreenManager().pushActivity(this);
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
                    List<Service> services = new Service().find(new KWQuery().like("id", "ParentTab%"));
                    for (Service s : services) {
                        Log.d("test", "s = " + s.toString());
                        //修改service的值
                        TabEntity tab = new TabEntity();
                        tab.idStr = s.get("id").toString();
                        tab.name = s.get("name").toString();
                        try {
                            tab.image_default = s.get("icon").toString();
                            tab.image_selected = s.get("iconActive").toString();
                        } catch (Exception e) {
                        }
                        new MyDBHelper(getApplicationContext()).updateTabEntity(tab);
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
                            initView(tabs);

                        }
                    });
                }
            }
        }.

                start();
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

    private void initView(final ArrayList<TabEntity> tabs) {
        if (tabs == null) {
            return;
        }
        int tabcount = tabs.size();
        if (tabcount == 0) {
            return;
        }
        bottom = (LinearLayout) findViewById(R.id.bottom);
        bottom.setWeightSum(tabcount);
        tabhost = getTabHost();
        for (int i = 0; i < tabcount; i++) {
            final int ii = i;
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_tab, null);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
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

                    refreshUI(ii, tabs);
                }
            });
        }
        refreshUI(0, tabs);
    }

    private void refreshUI(int position, ArrayList<TabEntity> tabs) {
        Log.d("test", "refreshUI = " + position);
        for (int i = 0; i < lls.size(); i++) {
            LinearLayout ll = lls.get(i);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
            tv.setText(tabs.get(i).name.replace("1", ""));

            if (i == position) {
                tv.setTextColor(getResources().getColor(R.color.orange));
                ImageLoader.getInstance().displayImage(tabs.get(i).image_selected, iv, getLoaderOptions());
            } else {
                tv.setTextColor(getResources().getColor(R.color.lightblack));
                ImageLoader.getInstance().displayImage(tabs.get(i).image_default, iv, getLoaderOptions());
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

    public void setCurrentTab(int tab) {
        tabhost.setCurrentTab(tab);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenManager.getScreenManager().popActivity(this);
    }
}
