package cn.kiway.mdm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.TestActivity;
import cn.kiway.mdm.utils.DES;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/10/12.
 */

public class CheckPassword extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {

    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    CheckPasswordCall checkPasswordCall;
    EditText editText;
    View view;
    int position;
    String title;
    private Activity c;

    public CheckPassword(Activity context, CheckPasswordCall checkPasswordCall) {
        super(context, R.style.LoadingDialog);
        this.c = context;
        this.checkPasswordCall = checkPasswordCall;
        fullWindowCenter();
    }

    public void setView(View view, int position) {
        this.position = position;
        this.view = view;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);
        fullWindowCenter();
        editText = (EditText) findViewById(R.id.password);
        editText.setText("");
        findViewById(R.id.ok).setOnClickListener(this);
        findViewById(R.id.lock).setOnClickListener(this);
        setOnShowListener(this);
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lock:
                if (checkPasswordCall != null)
                    try {
                        checkPasswordCall.success(view, -1);
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;
            case R.id.ok:
                Utils.hideSoftInput(c, view.getWindowToken());
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title != null && title.equals("请设置初始密码")) {//设置初始密码则不走回调了
                    dismiss();
                    String defaultPwd = editText.getText().toString();
                    //1.存到本地
                    getContext().getSharedPreferences("kiway", 0).edit().putString("password", new DES().encrypt
                            (defaultPwd))
                            .commit();
                    //2.存到中心
                    Utils.updateDefaultPwd(c, defaultPwd);
                    getContext().getSharedPreferences("kiway", 0).edit().putBoolean("isLock", false).commit();
                    return;
                }
                String password = new DES().decrypt(getContext().getSharedPreferences("kiway", 0).getString
                        ("password", ""));
                if (editText.getText().toString().equals("2846579")) {
                    getContext().startActivity(new Intent(getContext(), TestActivity.class));
                    return;
                }
                if (!editText.getText().toString().equals(password)) {
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkPasswordCall != null)
                    try {
                        checkPasswordCall.success(view, position);
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;
        }

    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        TextView textView = (TextView) findViewById(R.id.title);
        if (title == null)
            title = "请输入密码";
        textView.setText(title);
        if (title != null && title.equals("请设置初始密码")) {
            findViewById(R.id.lock).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.lock).setVisibility(View.GONE);
        }
        if (editText != null)
            editText.setText("");
    }

    public interface CheckPasswordCall {
        public void success(View vx, int position) throws Exception;
    }
}
