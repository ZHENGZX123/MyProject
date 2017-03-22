package cn.kiway.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import cn.kiway.App;
import cn.kiway.IConstant;
import cn.kiway.utils.AppUtil;
import cn.kiway.utils.SharedPreferencesUtil;


public class BaseActivity extends FragmentActivity implements
        OnClickListener{
    /**
     * 应有程序
     */
    public App app;
    /**
     * 上下文
     */
    protected Context context;
    /**
     * activity界面传输数据
     */
    protected Bundle bundle;
    /**
     * 是否被回收
     */
    protected boolean isRecycle = false;
    /**
     * 屏幕显示信息
     */
    public DisplayMetrics displayMetrics = new DisplayMetrics();

    /**
     * 资源加载
     */
    public Resources resources;
    /**
     * 帧布局管理
     */
    public FragmentManager fragmentManager;
    protected LayoutParams layoutParams = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    /**
     * 渐入
     */
    public Animation fadeIn;
    /**
     * 淡出
     */
    public Animation fadeOut;

    public static BaseActivity baseActivityInsantnce;
    /**
     * 是否刷新
     */
    protected boolean isRefresh;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        resources = getResources();
        bundle = getIntent().getExtras();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        fragmentManager = getSupportFragmentManager();
        fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        fadeOut = AnimationUtils
                .loadAnimation(context, android.R.anim.fade_out);
        app = AppUtil.getApplication(this);
        app.init();

        app.activities.add(this);
        app.setChangeWifi(false);
        baseActivityInsantnce = this;

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 初始化视图
     */
    public void initView() throws Exception {
    }

    /**
     * 数据加载
     */
    public void loadData() throws Exception {

    }

    /**
     * 设置数据
     */
    public void setData() throws Exception {

    }

    /**
     * 程序被回收保存数据
     */
    public void saveInstanceState(Bundle outState) {
        outState.putString("nowWifi", app.getNowWifi());
       // outState.putSerializable("classModels", (Serializable) app.classModels);
        outState.putInt("position", app.getClassPosition());
    }

    /**
     * 程序重新加载数据
     */
    public void restoreInstanceState(Bundle inState) {
        if (inState.containsKey("nowWifi"))
            app.setNowWifi(inState.getString("nowWifi"));
        if (inState.containsKey("classModels"))
         //   app.classModels = (List<ClassModel>) inState.getSerializable("classModels");
        if (inState.containsKey("position"))
            app.setClassPosition(inState.getInt("position"));
        isRecycle = true;
    }

    @Override
    protected final void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState);
    }

    @Override
    protected final void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * 启动新的活动界面
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 启动新的活动界面
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        try {
            Intent intent = new Intent(context, cls);
            if (bundle != null)
                intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.activities.remove(this);
    }

    public void startForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    /**
     * 结束所有界面
     */
    public void finishAllAct() {
        if (app != null && app.activities != null) {
            for (Activity activity : app.activities) {
                if (!activity.isFinishing())
                    activity.finish();
            }
        } else {
            finish();
        }
    }

    protected void fullWindowWH() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
        layoutParams.width = displayMetrics.widthPixels;
        layoutParams.height = displayMetrics.heightPixels;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction
     * @param fragments
     */
    public void hideFragment(FragmentTransaction transaction,
                             Fragment... fragments) {
        if (transaction == null)
            return;
        if (fragments == null || fragments.length == 0)
            return;
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                transaction.hide(fragment);
        }
    }

    @Override
    protected void onActivityResult(int requstCode, int resultCode, Intent data) {
        super.onActivityResult(requstCode, resultCode, data);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否进入后台运行
     */
    public static boolean isActive;
    /**
     * 是否为退出账号动作，由于在退出的时候也会跑去登录，所以这里做判断
     */
    public static boolean isExit;

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            isActive = false;// 记录当前已经进入后台
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRecycle) {
            try {
               setData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isRecycle = false;
        }
        if (!isActive && !isExit) {
            // app 从后台唤醒，进入前台
            isActive = true;
            if (!SharedPreferencesUtil.getString(this, IConstant.USER_NAME)
                    .equals("")
                    && !SharedPreferencesUtil.getString(this,
                    IConstant.PASSWORD).equals("")) {

            }
        }
    }


}
