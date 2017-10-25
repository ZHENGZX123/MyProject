package cn.kiway.mdm.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Contacts;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.View.viewPager.StereoPagerTransformer;
import cn.kiway.mdm.adapter.AppListAdapter;
import cn.kiway.mdm.adapter.MyViewPagerAdapter;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.dialog.CheckPassword;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.AppListUtils;
import cn.kiway.mdm.utils.LocationUtils;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.AppListUtils.isAppInstalled;
import static cn.kiway.mdm.utils.Constant.kiwayQiTa;

public class MainActivity extends BaseActivity implements CheckPassword.CheckPasswordCall {
    CheckPassword dialog;
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private boolean stop;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        Log.d("test", "Main onCreate");
        initView();
        //1.设置初始密码
        initPassword();
        //2.初始化界面
        initData();
        //3.华为推送
        //huaweiPush();
        //4.上报位置
        //uploadStatus();
        //5.拉取命令
        //getCommand();
        //6.判断跳到login
        //checkLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkLogin() {
        //只判断第一次
        boolean login = getSharedPreferences("kiway", 0).getBoolean("login", false);
        if (login) {
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initPassword() {
        String password = getSharedPreferences("kiway", 0).getString("password", "");
        if (TextUtils.isEmpty(password)) {
            dialog.setTitle("请设置初始密码");
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void initView() {
        stop = false;
        dialog = new CheckPassword(this, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        group = (LinearLayout) findViewById(R.id.points);
    }

    private void getCommand() {
        //TODO 没有登录的话，不会去拉取命令。
        Context context = this;
        String receive = "";
        //1.wifi电子围栏
        Utils.connectSSID(context, receive);
        //2.APP白名单、APP时间分段
        //AppListUtils
        //3.网页打开黑名单
        //MDMHelper.getAdapter().addNetworkAccessBlackList(null);
        //4.安装app
        Utils.installAPP(context, receive);
        //5.卸载app
        Utils.uninstallAPP(context, receive);
        //6.打开app
        Utils.openAPP(context, receive);
    }

    private void uploadStatus() {
        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    //1.上报位置：经纬度
                    Location location = LocationUtils.getInstance(MainActivity.this).showLocation();
                    if (location != null) {
                        String address = "纬度：" + location.getLatitude() + "经度：" + location.getLongitude();
                        Log.d("test", address);
                    }
                    //2.上报在线状态：已登录并且屏幕点亮
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
                    Log.d("test", "isScreenOn = " + isScreenOn);
                    //3.上报设备日志，异常日志
                    try {
                        sleep(10 * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void Camera(View view) {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivity(cameraIntent);
//        if (true) {
//            return;
//        }

        //改为测试用
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);

//        MDMHelper.getAdapter().setSilentActiveAdmin();

//        ArrayList<String> packageNames = new ArrayList<>();
//        packageNames.add("com.android.settings");
//        packageNames.add("com.hyphenate.chatuidemo");
//        MDMHelper.getAdapter().addDisallowedRunningApp(packageNames);
//        MDMHelper.getAdapter().removeDisallowedRunningApp(packageNames);
//        List<String> list = MDMHelper.getAdapter().getDisallowedRunningApp();
//        Log.d("test", "s.size = " + list.size());
//        for (String s : list) {
//            Log.d("test", "s = " + s);
//        }

//        MDMHelper.getAdapter().addInstallPackageBlackList(packageNames);

//        MDMHelper.getAdapter().setBluetoothDisabled(flag);
//        MDMHelper.getAdapter().setNetworkLocationDisabled(flag);

//        MDMHelper.getAdapter().setSystemUpdateDisabled(flag);
//        MDMHelper.getAdapter().setRestoreFactoryDisabled(flag);
//        MDMHelper.getAdapter().setWifiDisabled(flag);
//        MDMHelper.getAdapter().setSettingsApplicationDisabled(flag);
        //wifi围栏
//        MDMHelper.getAdapter().setScreenCaptureDisabled(flag);
//        Utils.connectSSID(this, "");

//        String path = "/mnt/sdcard/test.apk";
//        Log.d("test", "文件是否存在" + new File(path).exists());
//        MDMHelper.getAdapter().installPackage(path);

//        MDMHelper.getAdapter().uninstallPackage("cn.kiway.homework.student", false);

//        MDMHelper.getAdapter().shutdownDevice();

//        ArrayList<String> domains = new ArrayList<>();
//        domains.add("http://www.kiway.cn/");
//        MDMHelper.getAdapter().addNetworkAccessBlackList(domains);

//        MDMHelper.getAdapter().setWifiDisabled(flag);

//        MDMHelper.getAdapter().setBluetoothDisabled(flag);

//        MDMHelper.getAdapter().setDataConnectivityDisabled(flag);

//        MDMHelper.getAdapter().turnOnGPS(flag);

//        MDMHelper.getAdapter().setGPSDisabled(flag);

//        MDMHelper.getAdapter().setWifiApDisabled(flag);

//        MDMHelper.getAdapter().rebootDevice();

        flag = !flag;
    }

    private boolean flag = true;

    public void Call(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Contacts.People.CONTENT_URI);
        startActivity(intent);
    }


    public void SMS(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);//短信列表界面
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangePassWord(View view) {
        dialog.setView(null, 1);
        dialog.setCancelable(true);
        dialog.setTitle("请输入密码");
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.d("test", "Main onDestroy");
    }

    @Override
    public void success(View vx, int position) throws Exception {
        if (position == 1) {
            startActivity(new Intent(MainActivity.this, LockActivity.class));
        }
    }

    //设置页面数据
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initData() {
        totalPage = (int) Math.ceil(AppListUtils.getAppListData(this).size() * 1.0 / 20);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final int page = i;
            final GridView gridView = (GridView) View.inflate(this, R.layout.gird_view, null);
            gridView.setClipToPadding(false);
            gridView.setSelected(true);
            gridView.setSelector(android.R.color.transparent);
            gridView.setAdapter(new AppListAdapter(this, AppListUtils.getAppListData(this), i, 20));//添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    //跳转到其他APK
                    try {
                        final int pos = position + page * 20;//假设mPageSiez
                        App a = AppListUtils.getAppListData(MainActivity.this).get(pos);
                        String packageName = a.packageName;
                        if (packageName.equals(kiwayQiTa)) {//如果点击的是“其他应用”
                            clickButton4(null);
                            return;
                        }
                        if (TextUtils.isEmpty(packageName)) {
                            toast("包名错误");
                            return;
                        }
                        //1.判断app是否安装
                        boolean installed = isAppInstalled(getApplicationContext(), packageName);
                        if (!installed) {
                            toast("该APP未安装");
                            return;
                        }
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                        startActivity(intent);
                    } catch (Exception e) {
                        toast("启动异常");
                    }
                }
            });//每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
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
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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

    public void clickButton4(View v) {
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入其他应用");
            return;
        }
        startActivity(new Intent(this, AppListActivity2.class));
    }

    public void test(String text) {
        toast(text);
    }

    public void installationPush(final String token, final String imei) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    Log.d("test", "huaweitoken = " + token);
                    JSONObject param = new JSONObject();
                    param.put("appId", "c77b6c47dbcee47d7ffbc9461da0c82a");
                    param.put("type", Build.TYPE);
                    param.put("deviceId", token);
                    param.put("userId", imei);
                    param.put("module", "student");
                    Log.d("push", "param = " + param.toString());
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    String url = "http://192.168.8.161:8082/mdms/push/installation";
                    Log.d("test", "installationPush = " + url);
                    client.post(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "installationPush onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "installationPush onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }
}