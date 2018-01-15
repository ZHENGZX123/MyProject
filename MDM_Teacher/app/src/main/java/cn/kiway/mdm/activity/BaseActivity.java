package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {

    public TextView teacherName;
    public ImageView teacherIcon;
    public TextView titleName;
    public ImageButton videoBtn;

    public ImageButton gj;
    public ImageButton dm;
    public ImageButton sk;
    public ImageButton wdq;
    public ImageButton cp;
    public ImageButton rk;

    public ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");
    }

    public void initView() {
        titleName = (TextView) findViewById(R.id.titleName);
        teacherName = (TextView) findViewById(R.id.teacherName);
        teacherIcon = (ImageView) findViewById(R.id.teacherIcon);
        videoBtn = (ImageButton) findViewById(R.id.video);

        gj = (ImageButton) findViewById(R.id.gj);
        dm = (ImageButton) findViewById(R.id.dm);
        sk = (ImageButton) findViewById(R.id.sk);
        wdq = (ImageButton) findViewById(R.id.wdq);
        cp = (ImageButton) findViewById(R.id.cp);
        rk = (ImageButton) findViewById(R.id.rk);
    }

    public void showPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.show();
            }
        });
    }

    public void dismissPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        WXApplication.currentActivity = this;

        boolean expandTool = getSharedPreferences("kiway", 0).getBoolean("expandTool", true);
        if (expandTool) {
            showTool();
        } else {
            hideTool();
        }
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    //----------------------------------工具栏点击事件------------------------------


    public void gj(View view) {
        boolean expandTool = getSharedPreferences("kiway", 0).getBoolean("expandTool", true);
        expandTool = !expandTool;
        if (expandTool) {
            showTool();
        } else {
            hideTool();
        }
        getSharedPreferences("kiway", 0).edit().putBoolean("expandTool", expandTool).commit();
    }

    private void showTool() {
        try {
            dm.setVisibility(View.VISIBLE);
            sk.setVisibility(View.VISIBLE);
            wdq.setVisibility(View.VISIBLE);
            cp.setVisibility(View.VISIBLE);
            rk.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    private void hideTool() {
        try {
            dm.setVisibility(View.GONE);
            sk.setVisibility(View.GONE);
            wdq.setVisibility(View.GONE);
            cp.setVisibility(View.GONE);
            rk.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    public void dm(View view) {

    }

    public void sk(View view) {

    }

    public void wdq(View view) {

    }

    public void cp(View view) {

    }

    public void rk(View view) {

    }

    public void clickBack(View view) {
        finish();
    }

}