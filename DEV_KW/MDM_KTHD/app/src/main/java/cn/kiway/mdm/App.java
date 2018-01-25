package cn.kiway.mdm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.json.JSONObject;
import org.xutils.x;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.ScreenActivity;
import studentsession.kiway.cn.mdmaidl.KiwayApplication;

/**
 * Created by Administrator on 2017/12/4.
 */

public class App extends KiwayApplication {


    public static App instance;
    public boolean isAttenClass = false;
    public Activity currentActivity;

    public static final String PATH = "/mnt/sdcard/kiway_mdm_kthd/";
    public static final String serverUrl = "http://192.168.8.161:8083/";
    public static final String clientUrl = "http://192.168.8.161:8085/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //xutils
        x.Ext.init(this);
        //aidl
        connectService(mClientCallback);
        //init
        initImageCache();
    }

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        //设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.ARGB_8888);
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.NONE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(displayMetrics.widthPixels,
                        displayMetrics.heightPixels)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(10)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(200, 200)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                // .memoryCacheSize(2 * 1024 * 1024)
                // .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
                // .discCache(new UnlimitedDiscCache(cacheDir))
                // 自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .defaultDisplayImageOptions(displayImageOptionsBuilder.build())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                /* .writeDebugLogs() */.build(); // Remove for release app
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getLoaderOptions() {
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        displayImageOptionsBuilder.cacheInMemory(false);
        displayImageOptionsBuilder.cacheOnDisc(true);
//         RoundedBitmapDisplayer displayer = new RoundedBitmapDisplayer(10);
//         displayImageOptionsBuilder.displayer(displayer);
        displayImageOptionsBuilder.displayer(new SimpleBitmapDisplayer());
        DisplayImageOptions defaultDisplayImageOptions = displayImageOptionsBuilder
                .build();
        return defaultDisplayImageOptions;
    }


    public static final int MSG_CONNECT = 0x000;//上课连接
    public static final int MSG_XIAKE = MSG_CONNECT + 1;//下课

    public static final int MSG_LOCKONCLASS = MSG_CONNECT + 2;//上课锁屏
    public static final int MSG_UNLOCK = MSG_CONNECT + 3;//解锁
    public static final int MSG_HOME_DIS = MSG_CONNECT + 4;//禁用home
    public static final int MSG_HOME_TURE = MSG_CONNECT + 5;//开启home
    public static final int MSG_TIME_OUT = MSG_CONNECT + 6;//上课超时判断


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOCKONCLASS) {//锁屏
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    return;
                }
                startActivity(new Intent(getApplicationContext(), ScreenActivity.class).addFlags(Intent
                        .FLAG_ACTIVITY_NEW_TASK));
            } else if (msg.what == MSG_UNLOCK) {//解锁
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    currentActivity.finish();
                }
            } else if (msg.what == MSG_HOME_DIS) {//禁用home
                try {
                    if (mRemoteInterface != null)
                        mRemoteInterface.setHomeButtonDisabled(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == MSG_HOME_TURE) {//开启home
                try {
                    if (mRemoteInterface != null)
                        mRemoteInterface.setHomeButtonDisabled(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    //    客户端的ClientCallback对象
    //    在服务端注册后服务端可以调用客户端方法
    public ClientCallback.Stub mClientCallback = new ClientCallback.Stub() {
        @Override
        public void goOutClass() throws RemoteException {
            //下课
            mHandler.removeMessages(MSG_TIME_OUT);
            mHandler.sendEmptyMessage(MSG_HOME_TURE);
        }

        @Override
        public void accpterMessage(String msg, String token) throws RemoteException {
            Log.d("test", "accpterMessage msg = " + msg + "");
            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
            try {
                JSONObject data = new JSONObject(msg);
                String command = data.optString("command");
                if (command.equals("question")) {//回答问题的
                    ((BaseActivity) currentActivity).onQuestion(data);
                } else if (command.equals("sign")) {//签到
                    ((BaseActivity) currentActivity).onSign();
                } else if (command.equals("tongji")) {//知识点统计
                    String knowledge = data.optString("knowledge");
                    ((BaseActivity) currentActivity).onTongji(knowledge);
                } else if (command.equals("qiangda")) {
                    ((BaseActivity) currentActivity).onQiangda();
                } else if (command.equals("qiangdaResult")) {
                    int result = data.optInt("result");
                    String qiangdaStudentName = data.optString("qiangdaStudentName");
                    ((BaseActivity) currentActivity).onQiangdaResult(result, qiangdaStudentName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void attendClass(String msg) throws RemoteException {
            //上课
        }
    };
}
