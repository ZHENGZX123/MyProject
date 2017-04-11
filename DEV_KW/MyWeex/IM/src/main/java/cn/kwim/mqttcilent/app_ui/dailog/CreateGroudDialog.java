package cn.kwim.mqttcilent.app_ui.dailog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import cn.kiway.Yjpty.R;
import cn.kiway.activity.BaseActivity;
import cn.kiway.dialog.BaseDialog;
import cn.kiway.utils.ViewUtil;

public class CreateGroudDialog extends BaseDialog {
    BaseActivity activity;
    CreateGroup createGroup;
    EditText editText;
    int viewId;

    public CreateGroudDialog(Context context, CreateGroup createGroup) {
        super(context);
        activity = (BaseActivity) context;
        view = ViewUtil.inflate(context, R.layout.im_dialog_create_groud);
        fullWindowCenter(context);
        setContentView(view, layoutParams);
        editText = ViewUtil.findViewById(view, R.id.home_notify_content);
        view.findViewById(R.id.send).setOnClickListener(this);
        this.createGroup = createGroup;
    }

    public void setTitle(String title, int viewId) {
        ViewUtil.setContent(view, R.id.title, title);
        this.viewId = viewId;
        if (editText != null)
            editText.setText("");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.send) {
            if (editText.getText().length() <= 0) {
                ViewUtil.showMessage(activity, "不能为空,请重新输入");
                return;
            }
            if (createGroup != null)
                try {
                    createGroup.createGroupCallBack(editText.getText().toString(), viewId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public interface CreateGroup {
        public void createGroupCallBack(String message, int viewId) throws Exception;
    }
}
