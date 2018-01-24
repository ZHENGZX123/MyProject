package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import cn.kiway.mdm.App;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class KnowledgeDialog extends BaseDialog {

    public KnowledgeDialog(@NonNull Context context) {
        super(context);
    }

    public String title;

    public KnowledgeDialog(@NonNull Context context, String knowledge) {
        super(context);
        this.title = knowledge;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_knowledge);
        fullWindowCenter();
        findViewById(R.id.know).setOnClickListener(this);
        findViewById(R.id.unknow).setOnClickListener(this);
        ((TextView) findViewById(R.id.knowledge_txt)).setText("知识点\n    " + title);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.know:
                try {
                    App.instance.mRemoteInterface.callbackMessage("tongji_know");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.dismiss();
                break;
            case R.id.unknow:
                try {
                    App.instance.mRemoteInterface.callbackMessage("tongji_unknow");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.dismiss();
                break;
        }
    }
}
