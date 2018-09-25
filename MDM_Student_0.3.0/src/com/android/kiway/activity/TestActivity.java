package com.android.kiway.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.android.launcher3.R;

import cn.kiway.mdmsdk.MDMHelper;


/**
 * Created by Administrator on 2017/10/27.
 */

//救急页面
public class TestActivity extends BaseActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


//        MDMHelper.getAdapter().setBackButtonDisabled(false);
//        MDMHelper.getAdapter().setHomeButtonDisabled(false);
//        MDMHelper.getAdapter().setWifiDisabled(false);
        MDMHelper.getAdapter().setProximityEnable(true);
        MDMHelper.getAdapter().setProximityDistance(20);
        MDMHelper.getAdapter().setProximityDelay(1000);
        dialog = new AlertDialog.Builder(this).setTitle("自定义对话框").setMessage("自定义对话框消息").setNegativeButton("ok", null).create();
        registerSensor();
    }

    private void registerSensor() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void clickUnlock(View view) {
        unlock();
    }

    public void clickSetting(View view) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    boolean flag = false;

    public void clickTest(View view) {
        flag = !flag;
//        MDMHelper.getAdapter().setBackButtonDisabled(flag);
//        MDMHelper.getAdapter().setHomeButtonDisabled(flag);
//        MDMHelper.getAdapter().setWifiDisabled(flag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
