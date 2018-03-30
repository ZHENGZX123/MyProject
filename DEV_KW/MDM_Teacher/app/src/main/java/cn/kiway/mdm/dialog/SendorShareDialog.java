package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
import cn.kiway.mdm.teacher.R;

import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_WENJIAN;

/**
 * Created by Administrator on 2018/3/28.
 */

public class SendorShareDialog extends Dialog implements View.OnClickListener {
    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    BaseActivity activity;
    String filePath;

    public SendorShareDialog(@NonNull Context context, String filePath) {
        super(context, R.style.LoadingDialog);
        this.activity = (BaseActivity) context;
        this.filePath = filePath;
        Log.e("----", filePath);
        fullWindowCenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.share:
                //需要上传
                activity.shareImgUrl(filePath);
                break;
            case R.id.send:
                activity.toast(R.string.chooseStudent);
                //2.再选择学生
                activity.startActivity(new Intent(activity, StudentGridActivity.class).putExtra("type", TYPE_WENJIAN)
                        .putExtra("filePath",
                                filePath));
                break;
        }
    }
}
