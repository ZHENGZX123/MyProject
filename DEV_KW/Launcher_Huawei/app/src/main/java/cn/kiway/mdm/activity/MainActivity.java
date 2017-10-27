package cn.kiway.mdm.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anarchy.classify.ClassifyView;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.View.viewPager.StereoPagerTransformer;
import cn.kiway.mdm.adapter.AppListAdapter;
import cn.kiway.mdm.adapter.MyViewPagerAdapter;
import cn.kiway.mdm.dialog.CheckPassword;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.AppListUtils;
import cn.kiway.mdm.utils.AppReceiverIn;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.LocationUtils;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.AppReceiverIn.INSTALL_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.PACKAGENAME;
import static cn.kiway.mdm.utils.AppReceiverIn.REMOVE_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.REPLACE_SUCCESS;
import static cn.kiway.mdm.utils.Constant._16;

public class MainActivity extends BaseActivity implements CheckPassword.CheckPasswordCall {
    CheckPassword dialog;
    public List<List<App>> allListData = new ArrayList<>();
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");
        initView();
        //1.设置初始密码
        initPassword();
        //2.初始化界面
        initData(getListdata(AppListUtils.getAppListData(this)));
        //4.上报位置
        uploadStatus();
        //5.拉取命令
        getCommand();
        //6.检查命令
        checkCommand();

    }

    private void getCommand() {
        new Thread() {
            @Override
            public void run() {
                Utils.appCharge(MainActivity.this);
                Utils.networkDeviceCharge(MainActivity.this);
                Utils.wifi(MainActivity.this);
            }
        }.start();
    }

    private void checkCommand() {
        new Thread() {
            @Override
            public void run() {
                //10秒后开始检查
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (!stop) {
                    Log.d("test", "检查开始");
                    //1.检查wifi
                    Utils.checkWifis(MainActivity.this);
                    Utils.checkAppCharges(MainActivity.this);
                    Log.d("test", "检查结束");
                    try {
                        sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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
                        Utils.uploadLocation(MainActivity.this, location.getLongitude(), location.getLatitude());
                    }
                    //2.获取电量，如果电量1%，上报一下
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
//        Log.d("test", "开始安装");
//        MDMHelper.getAdapter().installPackage("/mnt/sdcard/test.apk");
//        Log.d("test", "安装结束");
        Utils.connectSSID(this, "", "");
    }

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
    public void success(View vx, int position) throws Exception {
        if (position == 1) {
            startActivityForResult(new Intent(MainActivity.this, LockActivity.class), 999);
        }
    }

    public List<List<App>> getListdata(List<List<App>> data1) {
        allListData.clear();
        totalPage = (int) Math.ceil(data1.size() * 1.0 / _16);
        for (int i = 0; i < totalPage; i++) {
            if (FileACache.loadListCache(MainActivity.this, i + "list.txt").size() > 0) {
                allListData.addAll(new ArrayList(FileACache.loadListCache(MainActivity.this, i + "list.txt")));
            } else {
                if (i * _16 + _16 >= data1.size())
                    allListData.addAll(new ArrayList(data1.subList(i * _16, data1.size() - 1)));
                else
                    allListData.addAll(new ArrayList(data1.subList(i * _16, i * _16 + _16)));
            }
        }
        Log.e("allListData", allListData.toString());
        return allListData;
    }

    //设置页面数据
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void initData(List<List<App>> data1) {
        viewPagerList = new ArrayList<View>();
        totalPage = (int) Math.ceil(data1.size() * 1.0 / _16);
        for (int i = 0; i < totalPage; i++) {
            final ClassifyView classifyView = (ClassifyView) View.inflate(this, R.layout.gird_view, null);
            classifyView.setClipToPadding(false);
            classifyView.setSelected(true);
            List<List<App>> data = new ArrayList<>();//截取数据到适配器
            if (i * _16 + _16 >= data1.size()) {
                data = new ArrayList(data1.subList(i * _16, data1.size() - 1));
            } else {
                data = new ArrayList(data1.subList(i * _16, i * _16 + _16));
            }
            classifyView.setAdapter(new AppListAdapter(MainActivity.this, data, i));
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

    public void clickButton4(View v) {
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入其他应用");
            return;
        }
        startActivity(new Intent(this, AppListActivity2.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 999) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(INSTALL_SUCCESS);
        filter.addAction(REPLACE_SUCCESS);
        filter.addAction(REMOVE_SUCCESS);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop = true;
        unregisterReceiver(mReceiver);
        Log.d("test", "Main onDestroy");
    }

    /**
     * 广播接收
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String packageName = intent.getStringExtra(PACKAGENAME);
            if (action.equals(INSTALL_SUCCESS)) {
                ArrayList<App> apps = new ArrayList<>();
                App a = new App();
                a.name = Utils.getProgramNameByPackageName(context, packageName);
                a.packageName = packageName;
                apps.add(a);
                Log.e(AppReceiverIn.TAG, "--------MainActivity安装成功" + packageName);
                if (allListData.toString().contains(a.packageName))
                    return;
                allListData.add(apps);
                initData(allListData);
            } else if (action.equals(REMOVE_SUCCESS)) {
                Log.e(AppReceiverIn.TAG, "--------MainActivity卸载成功" + packageName);
            } else if (action.equals(REPLACE_SUCCESS)) {
                Log.e(AppReceiverIn.TAG, "--------MainActivity替换成功" + packageName);
            }
        }
    };
}