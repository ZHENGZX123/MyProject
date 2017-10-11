package com.sonix.oidbluetooth;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.sonix.util.ApplicationResources;
import com.tqltech.tqlpencomm.Dot;
import com.tqltech.tqlpencomm.PenCommAgent;
import com.tqltech.tqlpencomm.listener.TQLPenSignal;


public class BluetoothLEService extends Service {
    private final static String TAG = "USB_HOST";

    private String mBluetoothDeviceAddress;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_PEN_STATUS_CHANGE =
            "ACTION_PEN_STATUS_CHANGE";

    private PenCommAgent bleManager;
    private int mConnectionState;
    private String mAddress;
    private String mName;

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    public static int unsignedToBytes(byte a) {
        int b = ((a & 0xFF));
        return ((b));
    }

    public class LocalBinder extends Binder {
        public BluetoothLEService getService() {
            return BluetoothLEService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {

        bleManager = PenCommAgent.GetInstance(getApplication());
        bleManager.setTQLPenSignalListener(mPenSignalCallback);

        if (!bleManager.isSupportBluetooth()) {
            Log.e(TAG, "Unable to Support Bluetooth");
            return false;
        }

        if (!bleManager.isSupportBLE()) {
            Log.e(TAG, "Unable to Support BLE.");
            return false;
        }

        return true;
    }

    public boolean connect(final String address, final String name) {
        if (address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && bleManager.isConnect(address)
            ///&& mBluetoothGatt != null
                ) {
            Log.d(TAG, "Trying to use an existing pen for connection.");
            return true;
        }

        Log.d(TAG, "Trying to create a new connection.");
        mAddress = address;
        if (!TextUtils.isEmpty(name)) {
            mName = name;
        }
        bleManager.connect(address);
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;

        OidActivity.gCurPageID = -1;
        return true;
    }

    public void disconnect() {
        bleManager.disconnect(mBluetoothDeviceAddress);
    }

    public void close() {
        if (bleManager == null) {
            return;
        }
        Log.w(TAG, "mBluetoothGatt closed");
        bleManager.disconnect(mBluetoothDeviceAddress);
        mBluetoothDeviceAddress = null;
        bleManager = null;
    }

    private OnDataReceiveListener onDataReceiveListener = null;

    public interface OnDataReceiveListener {
        void onDataReceive(Dot dot);

        void onStartOfflineDownload(boolean isSuccess);

        void onOfflineDataReceive(Dot dot);

        void onFinishedOfflineDownload();

        void onConnected(String address, String penName);

        void onDisconnected();

        void onPenNameSetupResponse(boolean isSuccess);

        void onReceivePenStatus(int battery);
    }

    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

    private TQLPenSignal mPenSignalCallback = new TQLPenSignal() {

        @Override
        public void onConnected(int forceMax, String fwVersion) {
            Log.d(TAG, "TQLPenSignal had Connected");
            String intentAction;

            intentAction = ACTION_GATT_CONNECTED;
            mConnectionState = STATE_CONNECTED;
            broadcastUpdate(intentAction);
            Log.i(TAG, "Connected to GATT server.");
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onConnected(mAddress, mName);
            }
        }

        @Override
        public void onDisconnected() {
            String intentAction;
            Log.d(TAG, "TQLPenSignal had onDisconnected");
            intentAction = ACTION_GATT_DISCONNECTED;
            mConnectionState = STATE_DISCONNECTED;
            Log.i(TAG, "C.");
            broadcastUpdate(intentAction);
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onDisconnected();
            }
        }

        @Override
        public void onOfflineDataList(int offlineNotes) {
            Log.d("zk", "onOfflineDataList offlineNotes = " + offlineNotes);
        }

        @Override
        public void onStartOfflineDownload(boolean isSuccess) {
            Log.d("zk", "onStartOfflineDownload = " + isSuccess);
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onStartOfflineDownload(isSuccess);
            }
        }

        @Override
        public void onFinishedOfflineDownload(boolean isSuccess) {
            Log.d("zk", "onFinishedOfflineDownload = " + isSuccess);
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onFinishedOfflineDownload();
            }
        }

        @Override
        public void onReceiveOfflineStrokes(Dot dot) {
            Log.d("zk", "离线的点" + dot.toString());
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onOfflineDataReceive(dot);
            }
        }

        @Override
        public void onPenAuthenticated() {
        }

        @Override
        public void onPenAutoPowerOnSetUpResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mPowerOnMode = ApplicationResources.tmp_mPowerOnMode;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
        }

        @Override
        public void onPenAutoShutdownSetUpResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mPowerOffTime = ApplicationResources.tmp_mPowerOffTime;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
        }

        @Override
        public void onPenBeepSetUpResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mBeep = ApplicationResources.tmp_mBeep;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
        }

        @Override
        public void onPenSensitivitySetUpResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mPenSens = ApplicationResources.tmp_mPenSens;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
        }

        @Override
        public void onPenNameSetupResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mPenName = ApplicationResources.tmp_mPenName;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onPenNameSetupResponse(isSuccess);
            }
        }

        @Override
        public void onPenTimetickSetupResponse(boolean isSuccess) {
            if (isSuccess) {
                ApplicationResources.mTimer = ApplicationResources.tmp_mTimer;
            }
            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);
        }

        @Override
        public void onReceiveDot(Dot dot) {
            Log.d("zk", "在线的点" + dot.toString());
            if (onDataReceiveListener != null) {
                onDataReceiveListener.onDataReceive(dot);
            }
        }

        @Override
        public void onReceivePenStatus(
                long timetick,
                int forcemax,
                int battery,
                int usedmem,
                boolean autopowerMode,
                boolean beep,
                short autoshutdownTime,
                short penSensitivity
        ) {
            Log.d(TAG, "PenStatus {timetick:" + timetick
                    + ", forcemax:" + forcemax
                    + ", battery:" + battery
                    + ", usedmem:" + usedmem
                    + ", autopowerMode:" + autopowerMode
                    + ", beep:" + beep
                    + ", autoshutdownTime:" + autoshutdownTime
                    + ", penSensitivity:" + penSensitivity
                    + "}");

            ApplicationResources.mBattery = battery;
            ApplicationResources.mUsedMem = usedmem;
            ApplicationResources.mTimer = timetick;
            ApplicationResources.mPowerOnMode = autopowerMode;
            ApplicationResources.mPowerOffTime = autoshutdownTime;
            ApplicationResources.mBeep = beep;
            ApplicationResources.mPenSens = penSensitivity;

            ApplicationResources.mPenName = bleManager.getPenName();
            ApplicationResources.mBTMac = bleManager.getPenMAC();
            ApplicationResources.mFirmWare = bleManager.getPenFirmWare();

            String intentAction = ACTION_PEN_STATUS_CHANGE;
            Log.i(TAG, "Disconnected from GATT server.");
            broadcastUpdate(intentAction);

            if (onDataReceiveListener != null) {
                onDataReceiveListener.onReceivePenStatus(battery);
            }
        }

        @Override
        public void onUpDown(boolean isUp) {

        }
    };
}

