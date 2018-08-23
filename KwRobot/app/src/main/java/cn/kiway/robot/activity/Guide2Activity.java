package cn.kiway.robot.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import cn.kiway.robot.R;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.MyListener;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Guide2Activity extends BaseActivity {

    private boolean success = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);
    }

    public void clickStart(View view) {
        Intent i = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        startActivity(i);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (success) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Guide2Activity.this, Guide3Activity.class));
                    finish();
                }
            }, 1000);
            return;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(Guide2Activity.this, R.style.popupDialog);
                dialog.setContentView(R.layout.dialog_alert);
                dialog.show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent i = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                        startActivity(i);
                        //这是个异步请求。。。
                        AutoReplyService.instance.checkWechatLogin(new MyListener() {
                            @Override
                            public void onResult(boolean success) {
                                Log.d("test", "onResult success =" + success);
                                Guide2Activity.this.success = success;
                                if (success) {
                                    toast("检测成功");
                                } else {
                                    toast("检测失败，请确保微信登录并保持微信在首页");
                                }
                            }
                        });
                    }
                }, 5000);
            }
        }, 3000);
    }
}
