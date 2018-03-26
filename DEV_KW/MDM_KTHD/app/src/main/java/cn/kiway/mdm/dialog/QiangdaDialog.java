package cn.kiway.mdm.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class QiangdaDialog extends BaseDialog {

    public TextView qiangdaResult;
    public Button qiangdaBtn;
    public BaseActivity activity;

    public QiangdaDialog(BaseActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qiangda);
        qiangdaResult = (TextView) findViewById(R.id.qiangdaResult);
        qiangdaBtn = (Button) findViewById(R.id.answer_btn);
        qiangdaBtn.setOnClickListener(this);
        fullWindowCenter();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.answer_btn:
                Utils.sendToServer(this.activity, "qiangda");
                break;
        }
    }

    public void setQiangdaResult(String result) {
        qiangdaResult.setText(result);
        //2秒后自动关闭
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        }.start();
    }
}
