package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.R;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/9/4.
 */

public class EulaActivity extends Activity {
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mAdminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_eula);

        String filename = "huawei_permission_statement.html";
        String content = Utils.getStringFromHtmlFile(this, filename);
        TextView permissionText = (TextView) findViewById(R.id.content_permissions);
        permissionText.setText(Html.fromHtml(content));

        TextView statementText = (TextView) findViewById(R.id.read_statement);
        statementText.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = statementText.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan();
                style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            statementText.setText(style);
        }

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAdminName = new ComponentName(this, SampleDeviceReceiver.class);

        if (isActiveMe()) {
            jump();
        }
    }

    private class MyURLSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            widget.setBackgroundColor(Color.parseColor("#00000000"));
            Intent intent = new Intent(EulaActivity.this, LicenseActivity.class);
            EulaActivity.this.startActivity(intent);
        }
    }

    public void clickButton1(View view) {
        activeProcess();
    }

    public void clickButton2(View view) {
        finish();
    }

    protected void activeProcess() {
        if (mDevicePolicyManager != null
                && !mDevicePolicyManager.isAdminActive(mAdminName)) {
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isActiveMe()) {
            Toast.makeText(this, "请先激活", Toast.LENGTH_SHORT).show();
        } else {
            jump();
        }
    }

    private void jump() {
        //判断是否登录
        boolean login = getSharedPreferences("kiway", 0).getBoolean("login", false);
        if (login) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

    private boolean isActiveMe() {
        if (mDevicePolicyManager == null || !mDevicePolicyManager.isAdminActive(mAdminName)) {
            return false;
        } else {
            return true;
        }
    }
}
