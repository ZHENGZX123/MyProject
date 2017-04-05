package com.alibaba.weex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().hide();
        et = (EditText) findViewById(R.id.et);
    }

    public void clickGo(View view) {
        String txt = et.getText().toString();
        Intent i = new Intent(this, TestActivity2.class);
        i.putExtra("url", txt);
        startActivity(i);
    }

}
