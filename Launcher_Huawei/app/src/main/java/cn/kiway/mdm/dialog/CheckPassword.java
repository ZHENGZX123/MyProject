package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.R;

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

    public CheckPassword(Context context, CheckPasswordCall checkPasswordCall) {
        super(context, R.style.LoadingDialog);
        this.checkPasswordCall = checkPasswordCall;
        fullWindowCenter();
    }

    public void setView(View view, int position) {
        this.view = view;
        this.position = position;
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
        editText.setText("123456");
        findViewById(R.id.ok).setOnClickListener(this);
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void onClick(View view) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title != null && title.equals("请设置初始密码")) {//设置初始密码则不走回调了
            dismiss();
            getContext().getSharedPreferences("kiway", 0).edit().putString("password", editText.getText().toString())
                    .commit();
            return;
        }
        String password = getContext().getSharedPreferences("kiway", 0).getString("password", "");
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
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        TextView textView = (TextView) findViewById(R.id.title);
        if (title == null)
            title = "请输入密码";
        textView.setText(title);
    }

    public interface CheckPasswordCall {
        public void success(View vx, int position) throws Exception;
    }
}
