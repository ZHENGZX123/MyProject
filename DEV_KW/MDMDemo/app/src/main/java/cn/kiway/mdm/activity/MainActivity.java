package cn.kiway.mdm.activity;

import android.os.Bundle;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }

    @Override
    public void onBackPressed() {

    }
}
