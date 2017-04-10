package yjpty.teaching.acitivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import yjpty.teaching.R;
import yjpty.teaching.adpater.SpinnerAdapter;
import yjpty.teaching.dialog.LoginDialog;
import yjpty.teaching.tcpudp.Client;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.GlobeVariable;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.SharedPreferencesUtil;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.wifi.WifiAdmin;

import static android.content.Context.WIFI_SERVICE;


//, NettyMessageCallBack
public class SendWifiNameActivity extends BaseActivity implements
        OnCheckedChangeListener, Client.TcpMessageCallBack {
    private Spinner spinner;
    private SpinnerAdapter adapter;
    private WifiManager wm;
    private boolean cracking;
    private WifiReceiver wifiReceiver;
    private IntentFilter intentFilter;
    private String wifiName;
    private boolean isConnect = false;
    private boolean isOpenConnect = false;
    private CheckBox box;
    public static String IS_NOTIFY = "is_notify";
    private LoginDialog dialog;
    private boolean isStart = true;
    private boolean isRightPs = false;
    private HandlerClient client;
    WifiAdmin admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_send_wifiname);
        admin=new WifiAdmin(this);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() throws Exception {
        super.initView();
        cracking = false;
        wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        spinner = ViewUtil.findViewById(this, R.id.wifiTp);// 下来列表
        box = ViewUtil.findViewById(this, R.id.check);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.no_send).setOnClickListener(this);
        box.setOnCheckedChangeListener(this);
        adapter = new SpinnerAdapter(this, -1);
        //  NettyClientHandler.setCallBack(this);
        dialog = new LoginDialog(this);
        spinner.setAdapter(adapter);
        IConstant.executorService.execute(new Runnable() {
            public void run() {
                ViewUtil.setContent(SendWifiNameActivity.this, R.id.wifiName,
                        AppUtil.getConnectWifiSsid(SendWifiNameActivity.this));
                spinner.setSelection(AppUtil.getCipherType(
                        SendWifiNameActivity.this, ViewUtil.getContent(
                                SendWifiNameActivity.this, R.id.wifiName)) - 1);
                wifiReceiver = new WifiReceiver();
                intentFilter = new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                intentFilter
                        .addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
                registerReceiver(wifiReceiver, intentFilter);
            }
        });
        wifiName = SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                + app.classModels.get(app.getClassPosition()).getId());
        SharedPreferencesUtil.save(this, IConstant.IS_ON_CLASS, false);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.login) {
            if (spinner.getSelectedItemPosition() != 0
                    && ViewUtil
                    .getContent(this, R.id.wifiPs).isEmpty()) {
                ViewUtil.showMessage(this, R.string.password);
                return;
            }
            if (ViewUtil.getContent(this, R.id.wifiName).isEmpty()
                    || ViewUtil.getContent(this, R.id.wifiName).equals("")) {
                ViewUtil.showMessage(this, R.string.network_no_k);
                return;
            }
            if (AppUtil.getCipherType(this,
                    ViewUtil.getContent(this, R.id.wifiName)) != spinner
                    .getSelectedItemPosition() + 1) {
                ViewUtil.showMessage(this, R.string.jmle_error);
                return;
            }
            isConnect = false;
            isOpenConnect = true;
            IConstant.executorService.execute(new Runnable() {
                public void run() {
                    //   PushClient.create();
                    admin.addNetwork(admin.CreateWifiInfo(ViewUtil
                            .getContent(SendWifiNameActivity.this,
                                    R.id.wifiName), ViewUtil.getContent(
                            SendWifiNameActivity.this, R.id.wifiPs), spinner
                            .getSelectedItemPosition() + 1));
                }
            });
            if (dialog != null) {
                dialog.setTitle(resources.getString(R.string.run_yz_ps));
                dialog.show();
            }

        } else if (i == R.id.no_send) {
            app.setHotSesson(true);
            Bundle bundle = new Bundle();
            bundle.putBoolean(IConstant.BUNDLE_PARAMS, true);// 1上课， 2看备课
            startActivity(TeachingPlansActivity.class, bundle);
            finish();

        }
    }

    @Override
    public void accpetMessage(String message) throws Exception {
        handler.removeMessages(0);
        if (dialog.isShowing() && dialog != null)
            dialog.close();
        if (isConnect == true)
            return;
        admin.disconnectWifi();
        SharedPreferencesUtil.save(SendWifiNameActivity.this,
                IConstant.WIFI_NEME + app.classModels.get(app.getClassPosition()).getId(),
                ViewUtil.getContent(SendWifiNameActivity.this, R.id.wifiName));// 连接成功，更新保存wifi信息
        finish();
        Bundle bundle = new Bundle();
        bundle.putString(IConstant.BUNDLE_PARAMS, ViewUtil.getContent(SendWifiNameActivity.this, R.id.wifiName));
        bundle.putString(IConstant.BUNDLE_PARAMS1, ViewUtil.getContent(
                SendWifiNameActivity.this, R.id.wifiPs));
        bundle.putInt(IConstant.BUNDLE_PARAMS2,spinner
                .getSelectedItemPosition() + 1);
        startActivity(HeizInfoActivity.class, bundle);// 我要上课
        ViewUtil.showMessage(this, R.string.fasongchengg);
        isConnect = true;
        isStart = false;
        Log.e("Wifi:::", "连接成功：：进去到上课");
    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION
                    .equals(action)) {
                WifiInfo info = wm.getConnectionInfo();
                SupplicantState state = info.getSupplicantState();
                String str = null;
                if (state == SupplicantState.ASSOCIATED) {
                    str = "关联AP完成";
                    Log.e("Wifi:::", str);
                } else if (state.toString().equals("AUTHENTICATING")) {
                    str = "正在验证";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.ASSOCIATING) {
                    str = "正在关联AP...";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.COMPLETED) {
                    if (cracking) {
                        str = "不知道是啥东西：：：：";
                    } else {
                        str = "已连接";
                        if (AppUtil.getConnectWifiSsid(
                                SendWifiNameActivity.this).equals(
                                ViewUtil.getContent(SendWifiNameActivity.this,
                                        R.id.wifiName))) {
                            if (isConnect && !isStart && isRightPs) {
                            } else {
                                if (isOpenConnect) {
                                    isRightPs = true;
                                   admin
                                            .addNetwork(admin.CreateWifiInfo(
                                                    wifiName,
                                                    "12345678",
                                                    spinner.getSelectedItemPosition() + 1));
                                    Log.e("Wifi:::", "开始连接盒子Wifi了");
                                    dialog.setTitle("密码正确，开始连接盒子wifi");
                                }
                            }
                        } else if (AppUtil.getConnectWifiSsid(
                                SendWifiNameActivity.this).equals(
                                wifiName)
                                && isOpenConnect) {
                            Log.e("Wifi:::", "该发送密码了：：：：：");
                            handler.sendEmptyMessageDelayed(0, 1500);
                        }
                    }
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.DISCONNECTED) {
                    str = "已断开";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.DORMANT) {
                    str = "暂停活动";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.FOUR_WAY_HANDSHAKE) {
                    str = "四路握手中...";
                } else if (state == SupplicantState.GROUP_HANDSHAKE) {
                    str = "GROUP_HANDSHAKE";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.INACTIVE) {
                    str = "休眠中...";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.INVALID) {
                    str = "无效";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.SCANNING) {
                    str = "";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.UNINITIALIZED) {
                    str = "未初始化";
                    Log.e("Wifi:::", str);
                }
                final int errorCode = intent.getIntExtra(// 验证失败
                        WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                if (errorCode == WifiManager.ERROR_AUTHENTICATING) {
                    Log.e("Wifi:::", "验证失败");
                    dialog.close();
                    ViewUtil.showMessage(SendWifiNameActivity.this, "密码错误");
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (client == null || !client.isConnect()) {
                if (client == null)
                    client = new HandlerClient();
                client.connectTCP(resources.getString(R.string.ip), SendWifiNameActivity.this);
            } else {
                client.sendTCP(GlobeVariable.SEND_WIFI
                        + ViewUtil.getContent(SendWifiNameActivity.this,
                        R.id.wifiName)
                        + ":::"
                        + ViewUtil.getContent(SendWifiNameActivity.this,
                        R.id.wifiPs) + ":::"
                        + (spinner.getSelectedItemPosition() + 1));
                dialog.setTitle(resources.getString(R.string.send_wifi_info));
            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }

        ;

    };

    // 暂停监听
    @Override
    protected void onStop() {
        super.onStop();
        if (null != client) {
            client.close();
            client.destory();
        }
        unregisterReceiver(wifiReceiver);
    }

    // 重启监听
    @Override
    protected void onResume() {
        if (wifiReceiver != null && intentFilter != null)
            IConstant.executorService.execute(new Runnable() {
                public void run() {
                    registerReceiver(wifiReceiver, intentFilter);
                }
            });
        super.onResume();
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        if (arg1) {
            SharedPreferencesUtil.save(this, IS_NOTIFY
                    + app.classModels.get(app.getClassPosition()).getHezi_code(), true);
        } else {
            SharedPreferencesUtil.save(this, IS_NOTIFY
                    + app.classModels.get(app.getClassPosition()).getHezi_code(), false);
        }
    }
}
