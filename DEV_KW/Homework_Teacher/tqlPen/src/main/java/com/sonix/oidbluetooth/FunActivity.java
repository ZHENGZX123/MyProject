package com.sonix.oidbluetooth;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sonix.util.ApplicationResources;
import com.tqltech.tqlpencomm.PenCommAgent;

public class FunActivity extends Activity {

    private final static String TAG = "USB_HOST";

    private TextView tvMac;
    private TextView tvVer;
    private TextView tvRtcTime;
    private TextView tvBat;
    private TextView tvUsedMem;

    private EditText edPenName;
    private Button btPenName;

    private EditText edSensi;
    private Button btSensi;

    private EditText edofftime;
    private Button btofftime;

    private Switch swPowerMode;
    private Switch swBeepMode;

    private PenCommAgent bleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create start");

        setContentView(R.layout.pen_status);

        tvMac = (TextView) findViewById(R.id.mac);
        tvVer = (TextView) findViewById(R.id.ver);
        tvRtcTime = (TextView) findViewById(R.id.timer);
        tvBat = (TextView) findViewById(R.id.battery);
        tvUsedMem = (TextView) findViewById(R.id.usedmem);

        edPenName = (EditText) findViewById(R.id.penname);
        btPenName = (Button) findViewById(R.id.pennamebt);

        edPenName = (EditText) findViewById(R.id.penname);
        btPenName = (Button) findViewById(R.id.pennamebt);

        edofftime = (EditText) findViewById(R.id.offtime);
        btofftime = (Button) findViewById(R.id.offtimebt);

        edSensi = (EditText) findViewById(R.id.sensi);
        btSensi = (Button) findViewById(R.id.sensibt);

        swPowerMode = (Switch) findViewById(R.id.powermodesw);
        swBeepMode = (Switch) findViewById(R.id.beepsw);

        bleManager = PenCommAgent.GetInstance(getApplication());

        Log.d(TAG, "on Create end");
        setStatusVal();

        btPenName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((bleManager != null) && (bleManager.isConnect())) {
                    String name = edPenName.getText().toString();
                    ApplicationResources.tmp_mPenName = name;
                    bleManager.ReqSetupPenName(name);
                }
            }
        });

        btSensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((bleManager != null) && (bleManager.isConnect())) {
                    int sens = Integer.parseInt(edSensi.getText().toString());
                    ApplicationResources.tmp_mPenSens = sens;
                    bleManager.ReqSetupPenSensitivity((short) sens);
                }
            }
        });

        btofftime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((bleManager != null) && (bleManager.isConnect())) {
                    int offtime = Integer.parseInt(edofftime.getText().toString());
                    ApplicationResources.tmp_mPowerOffTime = offtime;
                    bleManager.ReqSetupPenAutoShutdownTime((short) offtime);
                }
            }
        });

        swPowerMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((bleManager != null) && (bleManager.isConnect())) {
                    ApplicationResources.tmp_mPowerOnMode = isChecked;
                    bleManager.ReqSetupPenAutoPowerOn(isChecked);
                }
            }
        });

        swBeepMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((bleManager != null) && (bleManager.isConnect())) {
                    ApplicationResources.tmp_mBeep = isChecked;
                    bleManager.ReqSetupPenBeep(isChecked);
                }
            }
        });

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLEService.ACTION_PEN_STATUS_CHANGE);
        registerReceiver(mPenStatuUpdateReceiver, intentFilter);


    }

    private void setStatusVal() {
        tvRtcTime.setText(Integer.toString((int) ApplicationResources.mTimer));
        tvMac.setText(ApplicationResources.mBTMac);
        tvVer.setText(ApplicationResources.mFirmWare);
        tvBat.setText(Integer.toString(ApplicationResources.mBattery));
        tvUsedMem.setText(Integer.toString(ApplicationResources.mUsedMem));

        edPenName.setText(ApplicationResources.mPenName);
        edofftime.setText(Integer.toString(ApplicationResources.mPowerOffTime));
        edSensi.setText(Integer.toString(ApplicationResources.mPenSens));

        if (ApplicationResources.mPowerOnMode == true) {
            swPowerMode.setChecked(true);
        } else {
            swPowerMode.setChecked(false);
        }

        if (ApplicationResources.mBeep == true) {
            swBeepMode.setChecked(true);
        } else {
            swBeepMode.setChecked(false);
        }

    }

    private final BroadcastReceiver mPenStatuUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLEService.ACTION_PEN_STATUS_CHANGE.equals(action)) {
                setStatusVal();
            }
        }
    };

    @Override
    protected void onResume() {
        Log.d(TAG, "on resume start");
        super.onResume();
        if ((bleManager != null) && (bleManager.isConnect())) {
            Log.d(TAG, "Get Pen ALL Status.....");
            bleManager.ReqPenStatus();
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "on pause start");
        super.onPause();
    }

}
