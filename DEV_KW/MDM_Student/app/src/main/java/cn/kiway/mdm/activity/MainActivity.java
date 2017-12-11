package cn.kiway.mdm.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anarchy.classify.ClassifyView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.R;
import cn.kiway.mdm.adapter.AppListAdapter;
import cn.kiway.mdm.adapter.MyViewPagerAdapter;
import cn.kiway.mdm.dialog.CheckPassword;
import cn.kiway.mdm.dialog.ShowMessageDailog;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.AppListUtils;
import cn.kiway.mdm.utils.AppReceiverIn;
import cn.kiway.mdm.utils.DESUtil;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.Utils;
import cn.kiway.mdm.view.viewPager.StereoPagerTransformer;
import cn.kiway.mdmsdk.MDMHelper;

import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.YUXUNFANWENJLU;
import static cn.kiway.mdm.utils.AppReceiverIn.INSTALL_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.PACKAGENAME;
import static cn.kiway.mdm.utils.AppReceiverIn.REMOVE_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.REPLACE_SUCCESS;
import static cn.kiway.mdm.utils.Constant.KIWAYSETTING;
import static cn.kiway.mdm.utils.Constant.MARKETPLACE;
import static cn.kiway.mdm.utils.Constant.ZHIHUIKETANGPG;
import static cn.kiway.mdm.utils.Constant._16;
import static cn.kiway.mdm.utils.FileACache.ListFileName;
import static cn.kiway.mdm.utils.Utils.huaweiPush;
import static cn.kiway.mdm.utils.Utils.oauth;


public class MainActivity extends BaseActivity implements CheckPassword.CheckPasswordCall, SensorEventListener {

    private CheckPassword dialog;
    public List<List<App>> allListData = new ArrayList<>();
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    public static MainActivity instance;
    private TelephonyManager telephonyManager;
    private MyPhoneStateListener myPhoneStateListener;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public static final int LOGOUT = 999;
    public static final int USAGE_STATS = 1101;
    public static final int SCREEN = 1102;

    private static final int MSG_CHECK_SETTING = 1;
    private static final int MSG_CHECK_COMMAND = 2;
    private static final int MSG_UPLOAD = 3;
    private static final int MSG_GET_COMMAND = 4;

    private Button button5;
    private Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");
        instance = this;
        initView();
        //2.初始化界面
        initView();
        initData(getListdata(AppListUtils.getAppListData(this)));
        //4.上报位置
        uploadStatus();
        //5.拉取命令
        getCommand();
        //6.检查命令
        checkCommand();
        //7.注册华为
        huaweiPush(this);
        //8.允许使用访问记录
        setUsageStats();
        //9.上报APP列表
        uploadApp();
        //10.判断有没有sim卡
        checkSimCard();
        //11.检查settings
        //checkSettings();
        //12.注册广播
        registerBroadcast();
        //13.监听来电
        checkIncomingCall();
        //14.距离传感器
        //registerSensor();
        //15.设置默认短信app
        //setDefaultSMSApp();
        //16.检查版本更新
        checkNewVersion();
        //17.检查通话功能
        checkTelephoney();
        //18.获取经纬度
        getLocation();
        //19.检查lock状态
        checkLock();
        //20.鉴权
        //oauth();
    }

    private void oauth() {
        //1.网络鉴权
        Utils.oauth(this, "9a9b01f8ab910e12422bcc0e88d95dff2f95f582", "cn.kiway.kthd");
        //2.本地鉴权
        try {
            DESUtil des = null;
            des = new DESUtil("cn.kiway.kthd");
            String ret = des.encrypt("f2ec1fb69b27c7ab5260d2eb7cd95dea" + "9a9b01f8ab910e12422bcc0e88d95dff2f95f582");
            Log.d("test", "encrypt ret = " + ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLock() {
        boolean locked = getSharedPreferences("kiway", 0).getBoolean("locked", false);
        if (locked) {
            lock();
        }
    }

    private void checkTelephoney() {
        PackageManager pm = getPackageManager();
        // 获取是否支持电话
        boolean telephony = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        if (telephony) {
            button5.setVisibility(View.VISIBLE);
            //button6.setVisibility(View.VISIBLE);
        } else {
            button5.setVisibility(View.GONE);
            //button6.setVisibility(View.GONE);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CHECK_SETTING: {
                    String runningAPP = Utils.getRunningAPP(MainActivity.this);
                    if (runningAPP.equals("com.android.settings")) {
                        //返回MDM桌面
                        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                        startActivity(intent);
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_CHECK_SETTING, 1000);
                }
                break;
                case MSG_CHECK_COMMAND: {
                    Log.d("test", "检查开始");
                    //1.检查wifi
                    Utils.checkWifis(MainActivity.this);
                    Utils.checkAppCharges(MainActivity.this);
                    Utils.checkTemperary(MainActivity.this);
                    Utils.checkShutDown(MainActivity.this);
                    Log.d("test", "检查结束");
                    mHandler.sendEmptyMessageDelayed(MSG_CHECK_COMMAND, 60 * 1000);
                }
                break;
                case MSG_UPLOAD: {
                    //1.获取电量，如果电量1%，上报一下
                    Intent intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    int level = intent.getIntExtra("level", 0);
                    if (level < 5) {
                        Utils.deviceRuntime(MainActivity.this, "2", true);
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_UPLOAD, 10 * 60 * 1000);
                }
                break;
                case MSG_GET_COMMAND: {
                    Utils.appCharge(MainActivity.this);
                    Utils.networkDeviceCharge(MainActivity.this);
                    Utils.wifi(MainActivity.this);
                    Utils.appFunction(MainActivity.this);
                    Utils.getCalls(MainActivity.this);
                    mHandler.sendEmptyMessageDelayed(MSG_GET_COMMAND, 60 * 60 * 1000);
                }
                break;
            }
        }
    };


    private void setDefaultSMSApp() {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
        startActivity(intent);
    }

    private void registerSensor() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //1.检查锁屏
        Utils.checkTemperary(this);
        //2.检查关机
        Utils.checkShutDown(this);
    }

    private void checkIncomingCall() {
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        myPhoneStateListener = new MyPhoneStateListener();
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void registerBroadcast() {
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(INSTALL_SUCCESS);
        filter.addAction(REPLACE_SUCCESS);
        filter.addAction(REMOVE_SUCCESS);
        registerReceiver(mReceiver, filter);
    }

    private void checkSettings() {
        mHandler.sendEmptyMessage(MSG_CHECK_SETTING);
    }

    private void checkSimCard() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String simSer = tm.getSimSerialNumber();
        if (TextUtils.isEmpty(simSer)) {
            Log.d("test", "没有sim卡");
        } else {
            Log.d("test", "有sim卡");
        }
    }

    private void uploadApp() {
        String today = Utils.getToday();
        if (getSharedPreferences("kiway", 0).getBoolean(today, false)) {
            return;
        }
        Utils.uploadApp(this);
    }

    private void setUsageStats() {
        if (Build.BRAND.equals("HUAWEI") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !hasPermission()) {
            showMessageDailog = new ShowMessageDailog(this);
            showMessageDailog.setShowMessage("请您到设置页面打开权限：选择开维教育桌面--允许访问使用记录--打开", YUXUNFANWENJLU);
            showMessageDailog.setCancelable(false);
            showMessageDailog.show();
        }
    }

    //检测用户是否对本app开启了“Apps with usage access”权限
    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void getCommand() {
        mHandler.sendEmptyMessage(MSG_GET_COMMAND);
    }

    private void checkCommand() {
        mHandler.sendEmptyMessageDelayed(MSG_CHECK_COMMAND, 10000);
    }

    private void initView() {
        dialog = new CheckPassword(this, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        group = (LinearLayout) findViewById(R.id.points);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
    }

    private void uploadStatus() {
        mHandler.sendEmptyMessage(MSG_UPLOAD);
    }

    public void Camera(View view) {
        Utils.childOperation(this, "useApp", "使用了相机APP");
        int flag_camera = getSharedPreferences("kiway", 0).getInt("flag_camera", 1);
        if (flag_camera == 0) {
            toast("相机功能当前不能使用");
            return;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
    }

    public void Call(View view) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Contacts.People.CONTENT_URI);
//        startActivity(intent);
        startActivity(new Intent(this, CallActivity.class));
    }

    boolean flag = false;

    public void SMS(View view) {
//        flag = !flag;
//        MDMHelper.getAdapter().setWifiDisabled(flag);

        startActivity(new Intent(this, WebViewActivity.class));
//        MDMHelper.getAdapter().installPackage("/mnt/sdcard/test.apk", true);
//        startActivity(new Intent(this, ComposeSmsActivity.class));
//        try {
//            Intent intent = new Intent(Intent.ACTION_MAIN);//短信列表界面
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.setType("vnd.android-dir/mms-sms");
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void ChangePassWord(View view) {
        //1.设置初始密码
        String password = getSharedPreferences("kiway", 0).getString("password", "");
        if (TextUtils.isEmpty(password)) {
            dialog.setTitle("请设置初始密码");
            dialog.setCancelable(false);
            dialog.show();
            return;
        }
        dialog.setTitle("请输入密码");
        dialog.setCancelable(true);
        dialog.setView(null,1);
        dialog.show();
        //startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), 999);
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void success(View vx, int position) throws Exception {
        if (position == 1) {
            startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), 999);
        }
    }

    public List<List<App>> getListdata(List<List<App>> data1) {
        allListData.clear();
        if (FileACache.loadListCache(MainActivity.this, ListFileName).size() > 0) {
            allListData.addAll(new ArrayList(FileACache.loadListCache(MainActivity.this, ListFileName)));
        } else {
            allListData.addAll(new ArrayList(data1));//不知道为啥不用data1.size()-1
        }
        Log.e("allListData", allListData.toString());
        return allListData;
    }

    public List<List<App>> checkList(String pgName, int positon) {
        if (allListData.toString().contains(pgName)) {//添加智慧课堂第一个，如果之前有了，先删除在添加
            k:
            for (int i = 0; i < allListData.size(); i++) {
                for (int j = 0; j < allListData.get(i).size(); j++) {
                    App app = allListData.get(i).get(j);
                    if (app.packageName.equals(pgName)) {//存在的
                        if (allListData.get(i).size() == 1) {//只有一个的时候移除大的
                            allListData.remove(i);
                        } else {
                            allListData.get(i).remove(j);
                        }
                        break k;
                    } else {
                        //不存咋的
                    }
                }
            }
        }
        ArrayList<App> apps = new ArrayList<>();
        App a = new App();
        a.name = Utils.getProgramNameByPackageName(this, pgName);
        a.packageName = pgName;
        apps.add(a);
        allListData.add(positon, apps);
        return allListData;
    }

    public List<List<App>> addMarketplace(String name, String pgName, int positon) {
        checkList(ZHIHUIKETANGPG, 0);//将智慧课堂放到第一个
        if (allListData.toString().contains(pgName)) {
            return allListData;
        }
        ArrayList<App> apps = new ArrayList<>();
        App a = new App();
        a.name = name;
        a.packageName = pgName;
        apps.add(a);
        allListData.add(positon, apps);
        return allListData;
    }

    //设置页面数据
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void initData(List<List<App>> data1) {
        data1 = addMarketplace("开维应用市场", MARKETPLACE, 1);//判断有没有应用市场，没有的话添加到第二个
        data1 = addMarketplace("设置", KIWAYSETTING, 2);
        viewPagerList = new ArrayList<View>();
        totalPage = (int) Math.ceil(data1.size() * 1.0 / _16);
        for (int i = 0; i < totalPage; i++) {
            final ClassifyView classifyView = (ClassifyView) View.inflate(this, R.layout.item_gird_view, null);
            classifyView.setClipToPadding(false);
            classifyView.setSelected(true);
            List<List<App>> data = new ArrayList<>();//截取数据到适配器
            if (i * _16 + _16 >= data1.size()) {
                data = new ArrayList(data1.subList(i * _16, data1.size()));
            } else {
                data = new ArrayList(data1.subList(i * _16, i * _16 + _16));
            }
            classifyView.setAdapter(new AppListAdapter(MainActivity.this, data, allListData, i));
            viewPagerList.add(classifyView);
        }
        viewPager.setPageTransformer(false, new StereoPagerTransformer());
        viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));//设置ViewPager适配器
        group.removeAllViews();
        ivPoints = new ImageView[totalPage];//添加小圆点
        for (int i = 0; i < totalPage; i++) {
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.ic_lens);
            } else {
                ivPoints[i].setImageResource(R.drawable.ic_panorama_fish_eye);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()

        {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.ic_lens);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.ic_panorama_fish_eye);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOGOUT) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (requestCode == USAGE_STATS) {
            if (!hasPermission()) {
                toast("请务必设置权限");
                //若用户未开启权限，则引导用户开启“Apps with usage access”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        USAGE_STATS);
            }
        } else if (requestCode == SCREEN) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        if (telephonyManager != null) {
            telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    /**
     * 广播接收
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            String packageName = intent.getStringExtra(PACKAGENAME);
            boolean b = intent.getBooleanExtra("boolean", false);
            if (action.equals(INSTALL_SUCCESS)) {
                if (!b) {
                    boolean in = Utils.checkInAppcharges(MainActivity.this, packageName);
                    if (!in) {
                        return;
                    }
                }
                ArrayList<App> apps = new ArrayList<>();
                App a = new App();
                a.name = Utils.getProgramNameByPackageName(context, packageName);
                a.packageName = packageName;
                apps.add(a);
                Log.e(AppReceiverIn.TAG, "--------MainActivity安装成功" + packageName);
                Log.e(AppReceiverIn.TAG, "--------MainActivity安装成功" + allListData.toString());
                if (!allListData.toString().contains(a.packageName)) {
                    allListData.add(apps);
                }
                initData(allListData);
            } else if (action.equals(REMOVE_SUCCESS)) {
                Log.e(AppReceiverIn.TAG, "--------" + allListData.toString());
                if (allListData.toString().contains(packageName)) {
                    k:
                    for (int i = 0; i < allListData.size(); i++) {
                        for (int j = 0; j < allListData.get(i).size(); j++) {
                            App app = allListData.get(i).get(j);
                            if (app.packageName.equals(packageName)) {//存在的
                                if (allListData.get(i).size() == 1) {//只有一个的时候移除大的
                                    allListData.remove(i);
                                } else {
                                    allListData.get(i).remove(j);
                                }
                                initData(allListData);
                                break k;
                            } else {
                                //不存咋的
                            }
                        }
                    }
                }
                Log.e(AppReceiverIn.TAG, "--------MainActivity卸载成功" + packageName);
            } else if (action.equals(REPLACE_SUCCESS)) {
                Log.e(AppReceiverIn.TAG, "--------MainActivity替换成功" + packageName);
                if (allListData.toString().contains(packageName)) {
                    initData(allListData);
                }
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] its = event.values;
        Log.d("test", "onSensorChanged its[0]:" + its[0]);
        if (its[0] == 0) {
            Utils.showCloserDialog(KWApp.instance.currentActivity);
        } else {
            Utils.hideCloserDialog();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("test", "onAccuracyChanged");
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("test", "onCallStateChanged state = " + state + " , incomingNumber = " + incomingNumber);
            // 如果是响铃状态,检测拦截模式是否是电话拦截,是挂断
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                boolean enable = Utils.checkCallEnable(getApplicationContext(), incomingNumber);
                Log.d("test", "checkCallEnable " + incomingNumber + " enable = " + enable);
                if (!enable) {
                    MDMHelper.getAdapter().hangupCalling();
                }
            }
        }
    }


    public LocationClient mLocationClient = null;

    public void getLocation() {
        //获取经纬度
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(
                new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation location) {
                        Log.d("test", "onReceiveLocation ：" + location.getLongitude() + " , " + location.getLatitude());
                        Utils.uploadLocation(MainActivity.this, location.getLongitude(), location.getLatitude());
                    }

                    @Override
                    public void onConnectHotSpotMessage(String s, int i) {

                    }
                }
        );
        initLocation();
        //start
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000 * 60 * 10;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }
}

