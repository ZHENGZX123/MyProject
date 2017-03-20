package yjpty.teaching.acitivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.zk.myweex.R;
import com.zk.myweex.WXApplication;

import java.util.List;

import yjpty.teaching.http.BaseHttpHandler;
import yjpty.teaching.http.HttpHandler;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.util.ACache;
import yjpty.teaching.util.AppUtil;

public class BaseActivity extends FragmentActivity implements HttpHandler,
        OnClickListener {
    /**
     * 应有程序
     */
    public WXApplication app;
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
     * 请求回调
     */
    protected BaseHttpHandler activityHandler = new BaseHttpHandler(this) {
    };
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
    /**
     * 缓存
     */
    public ACache mCache;
    /**
     * 刷新
     */
    protected boolean isRefresh;
    public static BaseActivity baseActivityInsantnce;
    /**
     * 应用是否在活动
     */
    public static boolean isActive;
    /**
     * 应用是否退出
     */
    public static boolean isExit;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        resources = getResources();
        app = AppUtil.getApplication(this);
        bundle = getIntent().getExtras();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        fragmentManager = getSupportFragmentManager();
        fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        fadeOut = AnimationUtils
                .loadAnimation(context, android.R.anim.fade_out);
        mCache = ACache.get(this);
        baseActivityInsantnce = this;
    }
    /**
     * 初始化视图
     */
    public void initView() throws Exception {
    }


    /**
     * 设置数据
     */
    public void setData() throws Exception {

    }
    @Override
    protected void onStart() {
        super.onStart();
        View previous = findViewById(R.id.previos);
        if (previous != null)
            previous.setOnClickListener(this);
    }

    /**
     * 数据加载
     */
    public void loadData() throws Exception {
    }


    /**
     * 启动新的活动界面
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

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
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void startForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previos:
                finish();
                break;
        }
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



    @Override
    public void httpErr(HttpResponseModel message) throws Exception {
    }

    @Override
    public void HttpError(HttpResponseModel message) throws Exception {
        // if (AppUtil.isNetworkAvailable(this))
        // ViewUtil.showMessage(this, R.string.no_network);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground())
            isActive = false;// 记录当前已经进入后台
    }

}

