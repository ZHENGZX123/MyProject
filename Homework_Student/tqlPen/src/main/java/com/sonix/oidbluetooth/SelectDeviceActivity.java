package com.sonix.oidbluetooth;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tqltech.tqlpencomm.BLEException;
import com.tqltech.tqlpencomm.BLEScanner;
import com.tqltech.tqlpencomm.PenCommAgent;

import java.util.ArrayList;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import android.graphics.Color;


public class SelectDeviceActivity extends ListActivity {

    //private final static String TAG = SelectDeviceActivity.class.getSimpleName();
    private final static String TAG = "USB_HOST";
    private LeDeviceListAdapter mLeDeviceListAdapter;

    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private PenCommAgent bleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on Create start");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create start1");
        Log.d(TAG, "on Create start2");
        mHandler = new Handler();

        Log.d(TAG, "on Create start3");
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        bleManager = PenCommAgent.GetInstance(getApplication());
        Log.d(TAG, "on Create end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_scan) {
            mLeDeviceListAdapter.clear();
            scanLeDevice(true);
        } else if (i == R.id.menu_stop) {
            scanLeDevice(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "on resume start");
        super.onResume();

        if (bleManager != null) {
            bleManager.init();
        }
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter(this);
        setListAdapter(mLeDeviceListAdapter);
        Log.i(TAG, "onResume-------------");
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "on pause start");
        super.onPause();
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        bleManager.stopFindAllDevices();
        Bundle b = new Bundle();
        b.putString(BluetoothDevice.EXTRA_DEVICE, mLeDeviceListAdapter.getDevice(position).getAddress());
        b.putString(BluetoothDevice.EXTRA_NAME, mLeDeviceListAdapter.getDevice(position).getName());

        Intent result = new Intent();
        result.putExtras(b);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    private void scanLeDevice(final boolean enable) {
        if (bleManager == null) {
            Toast.makeText(this, "请打开蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }
        if (enable) {
            bleManager.FindAllDevices(new BLEScanner.OnBLEScanListener() {

                                          @Override
                                          public void onScanResult(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                                              runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      mLeDeviceListAdapter.addDevice(device);
                                                      mLeDeviceListAdapter.notifyDataSetChanged();
                                                  }
                                              });
                                          }

                                          @Override
                                          public void onScanFailed(BLEException bleException) {
                                          }
                                      }
            );
            mScanning = true;
        } else {
            mScanning = false;
            bleManager.stopFindAllDevices();
        }
        invalidateOptionsMenu();
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter(Context context) {
            super();
            mLeDevices = new ArrayList<>();
            mInflator = LayoutInflater.from(context);
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.item_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}
