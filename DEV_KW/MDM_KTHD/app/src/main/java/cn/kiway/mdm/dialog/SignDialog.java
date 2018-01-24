package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.View;

import cn.kiway.mdm.App;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class SignDialog extends BaseDialog {

    public SignDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign);
        fullWindowCenter();
        findViewById(R.id.sing_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sing_btn:
                try {
                    App.instance.mRemoteInterface.callbackMessage("sign");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.dismiss();
                break;
        }
    }
}
