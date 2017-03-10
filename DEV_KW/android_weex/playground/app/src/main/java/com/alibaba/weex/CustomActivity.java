package com.alibaba.weex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.wjc.R;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }


    public void clickButton(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();


        startActivity(new Intent(this, Custom2Activity.class).putExtra("file", text));
    }


}
