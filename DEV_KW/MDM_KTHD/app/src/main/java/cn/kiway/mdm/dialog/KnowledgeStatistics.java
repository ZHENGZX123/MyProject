package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class KnowledgeStatistics extends BaseDialog {
    public KnowledgeStatistics(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_knowledge);
        fullWindowCenter();
        findViewById(R.id.know).setOnClickListener(this);
        findViewById(R.id.unknow).setOnClickListener(this);
    }
}
