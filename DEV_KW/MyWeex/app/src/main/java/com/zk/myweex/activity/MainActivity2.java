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
import com.zk.myweex.utils.VersionManager;


public class MainActivity2 extends TabActivity {

    private TabHost tabhost;
    private LinearLayout bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();

        new Thread() {
            @Override
            public void run() {
                try {
                    VersionManager manager = new VersionManager();
                    manager.init(getApplication());
                    manager.getLocalVersion();
                    manager.getRemoteVersion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        bottom = (LinearLayout) findViewById(R.id.bottom);
        int num = 4;
        bottom.setWeightSum(num);
        tabhost = getTabHost();

        for (int i = 0; i < num; i++) {
            final int ii = i;
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_tab, null);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
            tv.setText("tab" + ii);

            bottom.addView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

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
                }
            });
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
