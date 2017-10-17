package cn.kiway.mdm.activity;

import android.annotation.TargetApi;
import android.content.Context;
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

import com.huawei.android.pushagent.api.PushManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.View.viewPager.StereoPagerTransformer;
import cn.kiway.mdm.adapter.AppListAdapter;
import cn.kiway.mdm.adapter.MyViewPagerAdapter;
import cn.kiway.mdm.dialog.CheckPassword;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.AppListUtils;
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
        if (TextUtils.isEmpty(password)) {
            dialog.setTitle("请设置初始密码");
            dialog.setCancelable(false);
            dialog.show();
        }
        initData();
        //2.华为推送
        huaweiPush();
        //3.上报位置
        uploadStatus();
        //4.拉取命令
        getCommand();
    }

    private void getCommand() {
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
                while (true) {
                    //1.上报位置：经纬度

                    //2.使用APP日志

                    try {
                        sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());
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
}