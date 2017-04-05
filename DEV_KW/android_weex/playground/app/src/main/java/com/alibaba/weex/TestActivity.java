package com.alibaba.weex;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wjc.R;

/**
 * Created by Administrator on 2017/4/5.
 */

public class TestActivity extends AppCompatActivity {

    private EditText et;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_test);
    }

    public void clickGo(View view) {
        String txt = et.getText().toString();
        Intent i = new Intent(this, TestActivity2.class);
        i.putExtra("url", txt);
        startActivity(i);
    }

}
