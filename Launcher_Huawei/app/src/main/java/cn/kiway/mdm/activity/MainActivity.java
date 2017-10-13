package cn.kiway.mdm.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

import static cn.kiway.mdm.utils.AppListUtils.isAppInstalled;
import static cn.kiway.mdm.utils.Constant.kiwayQiTa;

public class MainActivity extends BaseActivity implements CheckPassword.CheckPasswordCall {
    CheckPassword dialog;
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");
        dialog = new CheckPassword(this, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        group = (LinearLayout) findViewById(R.id.points);
        //1.设置初始密码
        String password = getSharedPreferences("kiway", 0).getString("password", "");
        Log.d("test", "pasword = " + password);
        if (TextUtils.isEmpty(password)) {
            dialog.setTitle("请设置初始密码");
            dialog.setCancelable(false);
            dialog.show();
//            final EditText et = new EditText(this);
//            et.setText("123456");
//            et.setSelection(et.getText().toString().length());
//            et.setSingleLine();
//            new AlertDialog.Builder(this).setTitle("请设置初始密码").setIcon(
//                    android.R.drawable.ic_dialog_info).setView(et
//            ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    String input = et.getText().toString();
//                    if (input.equals("")) {
//                        toast("密码不能为空");
//                        return;
//                    }
//                    getSharedPreferences("kiway", 0).edit().putString("password", input).commit();
//                }
//            }).setCancelable(false).show();
        }
        initData();
    }

    public void clickButton2(View v) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("当前已锁定");
            return;
        }
        toast("开始锁定");
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();

        //初始化adapter
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
        //1.设置默认桌面
        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.MainActivity");
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //5.禁止USB，慎用！！！
        MDMHelper.getAdapter().setUSBDataDisabled(true);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        MDMHelper.getAdapter().setHomeButtonDisabled(true);
        //MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
        //7.禁止修改时间
        MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);
    }

    public void clickButton3(View v) {
        //进入APP列表
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入学习园地");
            return;
        }
        startActivity(new Intent(this, AppListActivity.class));
    }

    public void clickButton4(View v) {
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入其他应用");
            return;
        }
        startActivity(new Intent(this, AppListActivity2.class));
    }

    public void clickButton5(View v) {
        dialog.setView(null, 0);
        dialog.setCancelable(true);
        dialog.setTitle("请输入密码");
        dialog.show();
//        //弹出密码框，密码正确就解锁
//        final EditText et = new EditText(this);
//        et.setText("123456");
//        et.setSingleLine();
//        new AlertDialog.Builder(this).setTitle("请输入密码").setIcon(
//                android.R.drawable.ic_dialog_info).setView(et
//        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String input = et.getText().toString();
//                if (input.equals("")) {
//                    toast("密码不能为空");
//                    return;
//                }
//                String password = getSharedPreferences("kiway", 0).getString("password", "");
//                if (!input.equals(password)) {
//                    toast("密码错误");
//                    return;
//                }
//                toast("已经解锁");
//                getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();
//
//                //初始化adapter
//                ComponentName mAdminName = new ComponentName(MainActivity.this, SampleDeviceReceiver.class);
//                MDMHelper.getAdapter().init(MainActivity.this, mAdminName);
//                //1.设置默认桌面
//                MDMHelper.getAdapter().clearDefaultLauncher();
//                //2.关闭settings.慎用！！！
//                //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
//                //3.设置不可卸载
//                List<String> packages = new ArrayList<>();
//                packages.add("cn.kiway.mdm");
//                MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
//                MDMHelper.getAdapter().addPersistentApp(packages);
//                //4.禁止下拉状态栏
//                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
//                //5.禁止USB，慎用！！！
//                MDMHelper.getAdapter().setUSBDataDisabled(false);
//                //6.禁用一些物理键盘
//                MDMHelper.getAdapter().setTaskButtonDisabled(false);
//                MDMHelper.getAdapter().setHomeButtonDisabled(false);
//                //MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
//
//            }
//        }).show();
    }

    public void Camera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
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
//        Intent intent = new Intent(Intent.ACTION_VIEW);//发送短信的界面
//        intent.putExtra("address", "");
//        intent.putExtra("sms_body", "");
//        intent.setType("vnd.android-dir/mms-sms");
//        startActivity(intent);
    }

    public void ChangePassWord(View view) {
//        final EditText et = new EditText(this);
//        et.setText("123456");
//        et.setSingleLine();
//        new AlertDialog.Builder(this).setTitle("请输入密码").setIcon(
//                android.R.drawable.ic_dialog_info).setView(et
//        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String input = et.getText().toString();
//                if (input.equals("")) {
//                    toast("密码不能为空");
//                    return;
//                }
//                String password = getSharedPreferences("kiway", 0).getString("password", "");
//                if (!input.equals(password)) {
//                    toast("密码错误");
//                    return;
//                }
//                startActivity(new Intent(MainActivity.this, ChangePassWordActivity.class));
//            }
//        }).show();
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
        Log.d("test", "Main onDestroy");
    }

    @Override
    public void success(View vx, int position) throws Exception {
        if (position == 0) {
            toast("已经解锁");
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();

            //初始化adapter
            ComponentName mAdminName = new ComponentName(MainActivity.this, SampleDeviceReceiver.class);
            MDMHelper.getAdapter().init(MainActivity.this, mAdminName);
            //1.设置默认桌面
            MDMHelper.getAdapter().clearDefaultLauncher();
            //2.关闭settings.慎用！！！
            //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
            //3.设置不可卸载
            List<String> packages = new ArrayList<>();
            packages.add("cn.kiway.mdm");
            MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
            MDMHelper.getAdapter().addPersistentApp(packages);
            //4.禁止下拉状态栏
            MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
            //5.禁止USB，慎用！！！
            MDMHelper.getAdapter().setUSBDataDisabled(false);
            //6.禁用一些物理键盘
            MDMHelper.getAdapter().setTaskButtonDisabled(false);
            MDMHelper.getAdapter().setHomeButtonDisabled(false);
            //MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
            //7.禁止修改时间
            MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);
        } else if (position == 1) {
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
                        if (packageName.equals(kiwayQiTa)) {//如果点击的是其他应用
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
        viewPager.setPageTransformer(false,new StereoPagerTransformer());
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
}