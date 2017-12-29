package cn.kiway.mdm.activity;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private boolean expandTool = true;

    public void gj(View view) {
        expandTool = !expandTool;
        if (expandTool) {
            dm.setVisibility(View.VISIBLE);
            sk.setVisibility(View.VISIBLE);
            wdq.setVisibility(View.VISIBLE);
            cp.setVisibility(View.VISIBLE);
            rk.setVisibility(View.VISIBLE);
        } else {
            dm.setVisibility(View.GONE);
            sk.setVisibility(View.GONE);
            wdq.setVisibility(View.GONE);
            cp.setVisibility(View.GONE);
            rk.setVisibility(View.GONE);
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
