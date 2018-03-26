package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class KnowledgeDialog extends BaseDialog {


    private String title;
    private Context context;

    public KnowledgeDialog(@NonNull Context context, String knowledge) {
        super(context);
        this.title = knowledge;
        this.context = context;
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
                Utils.sendToServer(context, "tongji_know");
                this.dismiss();
                break;
            case R.id.unknow:
                Utils.sendToServer(context, "tongji_unknow");
                this.dismiss();
                break;
        }
    }
}
