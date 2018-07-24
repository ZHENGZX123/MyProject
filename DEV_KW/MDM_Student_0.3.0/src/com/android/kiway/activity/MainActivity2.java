package com.android.kiway.activity;

import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anarchy.classify.ClassifyView;
import com.android.kiway.adapter.AppListAdapter;
import com.android.kiway.adapter.MyViewPagerAdapter;
import com.android.kiway.dialog.CheckPassword;
import com.android.kiway.entity.App;
import com.android.kiway.utils.AppListUtils;
import com.android.kiway.utils.AppReceiverIn;
import com.android.kiway.utils.FileACache;
import com.android.kiway.utils.Utils;
import com.android.kiway.view.viewPager.StereoPagerTransformer;
import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;

import static com.android.kiway.utils.AppListUtils.isAppInstalled;
import static com.android.kiway.utils.AppReceiverIn.INSTALL_SUCCESS;
import static com.android.kiway.utils.AppReceiverIn.PACKAGENAME;
import static com.android.kiway.utils.AppReceiverIn.REMOVE_SUCCESS;
import static com.android.kiway.utils.AppReceiverIn.REPLACE_SUCCESS;
import static com.android.kiway.utils.Constant.KIWAYSETTING;
import static com.android.kiway.utils.Constant.MARKETPLACE;
import static com.android.kiway.utils.Constant.PARENTMESSAGE;
import static com.android.kiway.utils.Constant.ZHIHUIKETANGPG;
import static com.android.kiway.utils.Constant._16;
import static com.android.kiway.utils.FileACache.ListFileName;


public class MainActivity2 extends MainActivity implements CheckPassword.CheckPasswordCall, SensorEventListener {


    public List<List<App>> allListData = new ArrayList<>();
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    public static MainActivity2 instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");
        instance = this;
        //2.初始化界面
        initView();
        initData(getListdata(AppListUtils.getAppListData(this)));
        //23.设置背景图片
        setBg();
        registerBroadcast();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        group = (LinearLayout) findViewById(R.id.points);
    }

    private void setBg() {//设置壁纸
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        //获取壁纸图片
        Drawable wallpaperDrawable = wallpaperManager.getFastDrawable();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        linearLayout.setBackground(wallpaperDrawable);
    }
    private void registerBroadcast() {
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
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    public List<List<App>> getListdata(List<List<App>> data1) {
        allListData.clear();
        if (FileACache.loadListCache(MainActivity2.this, ListFileName).size() > 0) {
            allListData.addAll(new ArrayList(FileACache.loadListCache(MainActivity2.this, ListFileName)));
        } else {
            allListData.addAll(new ArrayList(data1));
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
        if (isAppInstalled(this, pgName)) {
            ArrayList<App> apps = new ArrayList<>();
            App a = new App();
            a.name = Utils.getProgramNameByPackageName(this, pgName);
            a.packageName = pgName;
            apps.add(a);
            allListData.add(positon, apps);
        }
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
        allListData.add(apps);
        return allListData;
    }

    //设置页面数据
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void initData(List<List<App>> data1) {
        data1 = addMarketplace("应用市场", MARKETPLACE, 1);//判断有没有应用市场，没有的话添加到第二个
        data1 = addMarketplace("家长留言", PARENTMESSAGE, 2);
        data1 = addMarketplace("设置", KIWAYSETTING, 3);
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
            classifyView.setAdapter(new AppListAdapter(MainActivity2.this, data, allListData, i));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 广播接收
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String packageName = intent.getStringExtra(PACKAGENAME);
            Log.e(AppReceiverIn.TAG,  packageName);
            boolean b = intent.getBooleanExtra("boolean", false);
            if (action.equals(INSTALL_SUCCESS)) {
                if (!b) {
                    boolean in = Utils.checkInAppcharges(MainActivity2.this, packageName);
                    if (!in) {
                        return;
                    }
                }
                ArrayList<App> apps = new ArrayList<>();
                App a = new App();
                a.name = Utils.getProgramNameByPackageName(context, packageName);
                a.packageName = packageName;
                apps.add(a);
                Log.e(AppReceiverIn.TAG, "--------MainActivity安装成功1" + packageName);
                Log.e(AppReceiverIn.TAG, "--------MainActivity安装成功2" + allListData.toString());
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


}

