package com.zk.myweex.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.myweex.R;

import java.util.List;

import cn.kiway.baas.sdk.Configure;
import cn.kiway.baas.sdk.model.service.Package;
import cn.kiway.baas.sdk.model.service.Service;


public class MainActivity extends TabActivity {

    private TabHost tabhost;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Configure.getInstance().setHost("192.168.8.28");
        Configure.getInstance().setPort(4000);
        Configure.getInstance().setRoot("admin");
        new Thread() {
            @Override
            public void run() {
                try {
                    List<Service> services = new Service().find(null);
                    Log.d("test", "services count = " + services.size());
                    for (Service s : services) {
                        Log.d("test", "s = " + s.toString());
                        List<Package> packages = s.findPackageList(null);
                        Log.d("test", "p size = " + packages.size());
                        for (Package p : packages) {
                            Log.d("test", "p = " + p.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        tabhost = getTabHost();
        Intent tab0 = new Intent(this, Tab0Activity.class);
        tabhost.addTab(tabhost
                .newTabSpec("tab0")
                .setIndicator("tab0",
                        getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(tab0));

        Intent tab1 = new Intent(this, Tab1Activity.class);
        tabhost.addTab(tabhost
                .newTabSpec("tab1")
                .setIndicator("tab1",
                        getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(tab1));

        Intent tab2 = new Intent(this, Tab2Activity.class);
        tabhost.addTab(tabhost
                .newTabSpec("tab2")
                .setIndicator("tab2",
                        getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(tab2));

        Intent tab3 = new Intent(this, Tab3Activity.class);
        tabhost.addTab(tabhost
                .newTabSpec("tab3")
                .setIndicator("tab3",
                        getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(tab3));


        ll1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tabhost.setCurrentTab(0);
                iv1.setBackgroundResource(R.drawable.tab12);
                iv2.setBackgroundResource(R.drawable.tab21);
                iv3.setBackgroundResource(R.drawable.tab31);
                iv4.setBackgroundResource(R.drawable.tab41);
                tv1.setTextColor(getResources().getColor(R.color.orange));
                tv2.setTextColor(getResources().getColor(R.color.lightblack));
                tv3.setTextColor(getResources().getColor(R.color.lightblack));
                tv4.setTextColor(getResources().getColor(R.color.lightblack));
            }
        });

        ll2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tabhost.setCurrentTab(1);
                iv1.setBackgroundResource(R.drawable.tab11);
                iv2.setBackgroundResource(R.drawable.tab22);
                iv3.setBackgroundResource(R.drawable.tab31);
                iv4.setBackgroundResource(R.drawable.tab41);
                tv1.setTextColor(getResources().getColor(R.color.lightblack));
                tv2.setTextColor(getResources().getColor(R.color.orange));
                tv3.setTextColor(getResources().getColor(R.color.lightblack));
                tv4.setTextColor(getResources().getColor(R.color.lightblack));
            }
        });

        ll3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tabhost.setCurrentTab(2);
                iv1.setBackgroundResource(R.drawable.tab11);
                iv2.setBackgroundResource(R.drawable.tab21);
                iv3.setBackgroundResource(R.drawable.tab32);
                iv4.setBackgroundResource(R.drawable.tab41);
                tv1.setTextColor(getResources().getColor(R.color.lightblack));
                tv2.setTextColor(getResources().getColor(R.color.lightblack));
                tv3.setTextColor(getResources().getColor(R.color.orange));
                tv4.setTextColor(getResources().getColor(R.color.lightblack));
            }
        });


        ll4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tabhost.setCurrentTab(3);
                iv1.setBackgroundResource(R.drawable.tab11);
                iv2.setBackgroundResource(R.drawable.tab21);
                iv3.setBackgroundResource(R.drawable.tab31);
                iv4.setBackgroundResource(R.drawable.tab42);
                tv1.setTextColor(getResources().getColor(R.color.lightblack));
                tv2.setTextColor(getResources().getColor(R.color.lightblack));
                tv3.setTextColor(getResources().getColor(R.color.lightblack));
                tv4.setTextColor(getResources().getColor(R.color.orange));
            }
        });
    }

    protected void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    protected void toast(final int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void setTab(int position) {
        switch (position) {
            case 0:
                ll1.performClick();
                break;
            case 1:
                ll2.performClick();
                break;
            case 2:
                ll3.performClick();
                break;
            case 3:
                ll4.performClick();
                break;


            default:
                break;
        }
    }

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
