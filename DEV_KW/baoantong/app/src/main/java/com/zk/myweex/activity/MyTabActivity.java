package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.MyDBHelper;

import cn.kiway.baoantong.R;

/**
 * Created by Administrator on 2017/3/7.
 */

//动态tab使用
public class MyTabActivity extends SubActivity {

    //0未加载 1加载中 2已加载
    private int status = 0;
    private ProgressBar pb;
    private Button reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pb = (ProgressBar) findViewById(R.id.pb);
        reload = (Button) findViewById(R.id.reload);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("test", "onResume");
        initView();
    }

    private void initView() {
        if (status == 1 || status == 2) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                showPB();
                try {
                    status = 1;
                    int position = getIntent().getIntExtra("position", 0);
                    TabEntity tab = new MyDBHelper(getApplicationContext()).getAllTabEntity().get(position);
                    load(tab.idStr + ".zip");
                    status = 2;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    toast("加载失败，请稍后再试");
                    showReload();
                } finally {
                    hidePB();
                }
            }
        }.start();
    }

    private void showReload() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reload.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideReload() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reload.setVisibility(View.GONE);
            }
        });
    }

    private void showPB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hidePB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.GONE);
            }
        });
    }

    public void clickReload(View v) {
        hideReload();
        initView();
    }
}
