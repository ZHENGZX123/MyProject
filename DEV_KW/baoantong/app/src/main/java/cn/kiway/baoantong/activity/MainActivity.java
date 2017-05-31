package cn.kiway.baoantong.activity;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.module.Module;
import cn.kiway.baoantong.R;
import cn.kiway.baoantong.WXApplication;
import cn.kiway.baoantong.entity.TabEntity;
import cn.kiway.baoantong.entity.ZipPackage;
import cn.kiway.baoantong.utils.FileUtils;
import cn.kiway.baoantong.utils.MyDBHelper;
import cn.kiway.baoantong.utils.ScreenManager;
import cn.kiway.baoantong.utils.Utils;
import cn.kiway.baoantong.utils.VersionUpManager;


public class MainActivity extends TabActivity {

    private TabHost tabhost;
    public LinearLayout bottom;
    private ArrayList<LinearLayout> lls = new ArrayList<>();
    public static MainActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        PackageManager p = getPackageManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenManager.getScreenManager().pushActivity(this);
        main = this;
    }

    interface IPermission {
        //权限被允许时的回调
        void onGranted();

        //权限被拒绝时的回调， 参数：被拒绝权限的集合
        void onDenied(List<String> deniedPermissions);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRunTimePermission(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                mListener);
    }

    private final int REQUEST_CODE = 1;
    private IPermission mListener = new IPermission() {
        @Override
        public void onGranted() {
            toast("所有权限被接受");
            checkIsFirst();
            init();
        }

        @Override
        public void onDenied(List<String> deniedPermissions) {
            toast("有权限被拒绝");
            finish();
        }
    };

    //申请权限的方法
    public void requestRunTimePermission(String[] permissions, IPermission listener) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);
        } else {
            checkIsFirst();
            init();
        }
    }

    private void init() {
        Utils.checkNetWork(this);
        checkPackageService();
        checkZipVersion();

        ArrayList<TabEntity> tabs = new MyDBHelper(getApplicationContext()).getAllTabEntity();
        Log.d("test", "main initView");
        initView(tabs);
    }

    //当用户拒绝或者同意权限时的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void checkIsFirst() {
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();

            //1.mkdirs
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.PATH).exists()) {
                new File(WXApplication.PATH).mkdirs();
            }
            if (!new File(WXApplication.PATH_BACKUP).exists()) {
                new File(WXApplication.PATH_BACKUP).mkdirs();
            }
            if (!new File(WXApplication.PATH_TMP).exists()) {
                new File(WXApplication.PATH_TMP).mkdirs();
            }
            if (!new File(WXApplication.PATH_APK).exists()) {
                new File(WXApplication.PATH_APK).mkdirs();
            }
            //2.拷贝。。。
            FileUtils.copyRawToSdcard(this, R.raw.bat, "batTab0.zip");
            //3.插入数据库
            TabEntity tab0 = new TabEntity();
            tab0.name = "首页";
            tab0.idStr = "batTab0";
            new MyDBHelper(getApplicationContext()).addTabEntity(tab0);
            ZipPackage zip0 = new ZipPackage();
            zip0.name = "batTab0.zip";
            zip0.indexPath = "bat/dist/tab0.js";
            zip0.version = "1.0.0";
            zip0.patchs = "";
            new MyDBHelper(getApplicationContext()).addZipPackage(zip0);
        }
    }

    private void checkPackageService() {
        new Thread() {
            @Override
            public void run() {
                List<Module> services = null;
                try {
                    services = new Module().find(new KWQuery().like("id", "batTab%"));
                    Log.d("test", "services count  = " + services.size());
                    for (Module s : services) {
                        Log.d("test", "service  = " + s.toString());
                        TabEntity tab = new TabEntity();
                        tab.idStr = s.get("id").toString();
                        tab.name = s.get("name").toString();
                        try {
                            tab.image_default = s.get("icon").toString();
                            tab.image_selected = s.get("iconActive").toString();
                        } catch (Exception e) {
                        }
                        new MyDBHelper(getApplicationContext()).updateTabEntity(tab);

                        String baseUrl = s.get("url").toString();
                        new MyDBHelper(getApplicationContext()).updateZipPackageBaseUrl(baseUrl, tab.idStr + ".zip");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkZipVersion() {
        new Thread() {
            @Override
            public void run() {
                VersionUpManager manager = new VersionUpManager();
                manager.init(getApplication());
                manager.getLocalVersion();
                manager.getRemoteVersion();
            }
        }.start();
    }

    private void initView(final ArrayList<TabEntity> tabs) {
        if (tabs == null) {
            return;
        }
        int tabcount = tabs.size();
        if (tabcount == 0) {
            return;
        }
        bottom = (LinearLayout) findViewById(R.id.bottom);
        bottom.setWeightSum(tabcount);
        tabhost = getTabHost();
        for (int i = 0; i < tabcount; i++) {
            final int ii = i;
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_tab, null);
            bottom.addView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            lls.add(ll);
            Intent tab = new Intent(this, MyTabActivity.class);
            tab.putExtra("position", ii);
            tabhost.addTab(tabhost
                    .newTabSpec("tab" + ii)
                    .setIndicator("tab" + ii,
                            getResources().getDrawable(R.mipmap.ic_launcher))
                    .setContent(tab));
            ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    tabhost.setCurrentTab(ii);
                }
            });
        }
    }


    protected void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    protected void toast(final int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
            int arg1 = msg.arg1;
            if (arg1 == 0) {
                rl_nonet.setVisibility(View.VISIBLE);
            } else {
                rl_nonet.setVisibility(View.GONE);
            }
        }
    };

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenManager.getScreenManager().popActivity(this);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }


}
