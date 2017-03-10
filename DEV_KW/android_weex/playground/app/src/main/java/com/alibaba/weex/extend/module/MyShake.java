package com.alibaba.weex.extend.module;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import static android.content.Context.SENSOR_SERVICE;

public class MyShake extends WXModule implements SensorEventListener {

    private JSCallback cb;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;

    @JSMethod(uiThread = true)
    public void doListen(JSCallback callback) {
        this.cb = callback;
        this.cb.invokeAndKeepAlive("监听中...");

        mSensorManager = ((SensorManager) mWXSDKInstance.getContext().getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }


    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        Log.d("test", "onActivityDestroy");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17)) {
                Log.d("test", "onSensorChanged: 摇动");
                Vibrator vibrator = (Vibrator) mWXSDKInstance.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
