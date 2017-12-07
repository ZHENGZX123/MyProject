package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/12/7.
 */

public class SystemSetupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syssetting);
    }

    public void onWLAN(View view) {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        startActivity(intent);
    }

    public void onBlueB(View view) {
        Intent intent =  new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }

    public void onDisPlay(View view) {
        Intent intent =  new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        startActivity(intent);
    }

    public void onWarningTone(View view) {
        Intent intent =  new Intent(Settings.ACTION_SOUND_SETTINGS);
        startActivity(intent);
    }

    public void onLanguage(View view) {
        Intent intent =  new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        startActivity(intent);
    }

    public void onTime(View view) {
        Intent intent =  new Intent(Settings.ACTION_DATE_SETTINGS );
        startActivity(intent);
    }
    public void onMobileNetwork(View view){
        Intent intent =  new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS );
        startActivity(intent);
    }
    public void onSafe(View view){
        Intent intent =  new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivity(intent);
    }
    public void onStorage(View view){
        Intent intent =  new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }
    public void onPosition(View view){
        Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
    public void Before(View view) {
        finish();
    }
}
