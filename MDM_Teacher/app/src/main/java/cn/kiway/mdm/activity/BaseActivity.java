package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.teacher.R;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.rtc.Constants;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {

    public TextView teacherName;
    public ImageView teacherIcon;
    public TextView titleName;
    public ImageButton videoBtn;

    public RelativeLayout toolsRL;
    public ImageButton gj;
    public ImageButton dm;
    public ImageButton sk;
    public ImageButton wdq;
    public ImageButton cp;

    public ImageButton rk;
    public ProgressDialog pd;
    private RelativeLayout retryRL;

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

        String url = getSharedPreferences("kiway", 0).getString("teacherAvatar", "");
        if (teacherIcon != null && !TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, teacherIcon, KWApplication.getLoaderOptions());
        }

        videoBtn = (ImageButton) findViewById(R.id.video);
        toolsRL = (RelativeLayout) findViewById(R.id.toolsRL);

        gj = (ImageButton) findViewById(R.id.gj);
        dm = (ImageButton) findViewById(R.id.dm);
        sk = (ImageButton) findViewById(R.id.sk);
        wdq = (ImageButton) findViewById(R.id.wdq);
        cp = (ImageButton) findViewById(R.id.cp);
        rk = (ImageButton) findViewById(R.id.rk);

        retryRL = (RelativeLayout) findViewById(R.id.retryRL);
    }

    public void showPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.show();
            }
        });
    }

    public void hidePD() {
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

        KWApplication.currentActivity = this;

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

    public void clickRetry(View view) {

    }

    public void showRetry() {
        retryRL.setVisibility(View.VISIBLE);
    }

    public void hideRetry() {
        retryRL.setVisibility(View.GONE);
    }

    public void startPlayer(String roomName) {
        //1.开始播放学生推屏
        Intent i = new Intent(BaseActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, roomName);
        startActivity(i);
    }

    public void stopPlayer() {
        if (LiveRoomActivity.instance == null) {
            return;
        }
        LiveRoomActivity.instance.finish();
    }

    public void hideTool(int index) {
        switch (index) {
            case 1:
                dm.setVisibility(View.GONE);
                break;
            case 2:
                sk.setVisibility(View.GONE);
                break;
            case 3:
                wdq.setVisibility(View.GONE);
                break;
            case 4:
                cp.setVisibility(View.GONE);
                break;
            case 5:
                rk.setVisibility(View.GONE);
                break;
        }
    }
}
