package cn.kiway.mdm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.QuestionActivity;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdmaidl.KiwayApplication;

import static cn.kiway.mdm.utils.HttpUtil.uploadUserFile;
import static cn.kiway.mdm.utils.HttpUtil.uploadUserInfo;

/**
 * Created by Administrator on 2017/12/4.
 */

public class App extends KiwayApplication {


    public static App instance;
    public boolean isAttenClass = false;
    public Activity currentActivity;

    public static final String ROOT = "/mnt/sdcard/kiway_mdm_kthd/";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "KTHD APP oncreate");
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

    public static final int MSG_HOME_DIS = MSG_CONNECT + 4;//禁用home
    public static final int MSG_HOME_TURE = MSG_CONNECT + 5;//开启home
    public static final int MSG_TIME_OUT = MSG_CONNECT + 6;//上课超时判断


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_HOME_DIS) {//禁用home
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
            isAttenClass = false;
            mHandler.removeMessages(MSG_TIME_OUT);
            mHandler.sendEmptyMessage(MSG_HOME_TURE);
            if (currentActivity != null)
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(currentActivity, "已经下课啦", Toast.LENGTH_SHORT).show();
                    }
                });
        }

        @Override
        public void accpterMessage(String msg, String token) throws RemoteException {
            Log.d("test", "accpterMessage msg = " + msg + "");
            isAttenClass = true;
            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
            try {
                JSONObject o = new JSONObject(msg);
                String command = o.optString("command");
                if (command.equals("question")) {//回答问题
                    ((BaseActivity) currentActivity).onQuestion(o);
                } else if (command.equals("sign")) {//签到
                    ((BaseActivity) currentActivity).onSign();
                } else if (command.equals("tongji")) {//知识点统计
                    String knowledge = o.optString("knowledge");
                    ((BaseActivity) currentActivity).onTongji(knowledge);
                } else if (command.equals("qiangda")) {
                    ((BaseActivity) currentActivity).onQiangda();
                } else if (command.equals("qiangdaResult")) {
                    int result = o.optInt("result");
                    String qiangdaStudentName = o.optString("qiangdaStudentName");
                    ((BaseActivity) currentActivity).onQiangdaResult(result, qiangdaStudentName);
                } else if (command.equals("wenjian")) {
                    ((BaseActivity) currentActivity).downloadFile(o);
                } else if (command.equals("collection")) {
                    if (!(currentActivity instanceof QuestionActivity)) {
                        Log.d("test", "学生把答题页面关闭了，不应该。。。");
                        return;
                    }
                    String collection = o.optString("collection");
                    ((QuestionActivity) currentActivity).setCollection(collection);
                } else if (command.equals("questionTimeup")) {
                    if (!(currentActivity instanceof QuestionActivity)) {
                        Log.d("test", "学生把答题页面关闭了，不应该。。。");
                        return;
                    }
                    ((QuestionActivity) currentActivity).toast("老师结束了这次答题");
                    ((QuestionActivity) currentActivity).questionTimeup(false);
                } else if (command.equals("questionEnd")) {
                    if (!(currentActivity instanceof QuestionActivity)) {
                        Log.d("test", "学生把答题页面关闭了，不应该。。。");
                        return;
                    }
                    ((QuestionActivity) currentActivity).toast("老师结束了这次答题");
                    ((QuestionActivity) currentActivity).finish();
                } else if (command.equals("tuiping")) {
                    int status = o.optInt("status");
                    String teacherUserId = o.optString("teacherUserId");
                    if (status == 1) {
                        //打开
                        ((BaseActivity) currentActivity).startPlayer(teacherUserId);
                    } else {
                        ((BaseActivity) currentActivity).stopPlayer();
                    }
                } else if (command.equals("chaping")) {
                    int chaping = o.optInt("chaping");
                    if (chaping == 1) {
                        MainActivity.instantce.startTuiping();
                    } else {
                        MainActivity.instantce.endTuiping();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void attendClass(String msg) throws RemoteException {
            //上课
            isAttenClass = true;
            if (currentActivity != null)
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(currentActivity, "开始上课啦", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    };

    //修改用户头像 ,名字
    public void uploadUserInfo(final String urlPath, final String name) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
        client.setTimeout(10000);
        RequestParams param = new RequestParams();
        if (urlPath != null && !urlPath.equals(""))
            param.put("avatar", urlPath);
        if (name != null && !name.equals(""))
            param.put("name", name);
        client.post(this, uploadUserInfo, param, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                Log.d("test", "course onSuccess = " + ret);
            }

            @Override
            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                Logger.log("::::::::::::onFailure" + ret);
                if (ret != null && !ret.equals("")) {
                    try {
                        JSONObject data = new JSONObject(ret);
                        if (data.optInt("statusCode") != 200) {
                            Utils.login(App.this, new Utils.ReLogin() {
                                @Override
                                public void onSuccess() {
                                    uploadUserInfo(urlPath, name);
                                }

                                @Override
                                public void onFailure() {
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(App.this, "请求失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void uploadUserFile(final String url, final int type, final String name, final String size) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
        client.setTimeout(10000);
        RequestParams param = new RequestParams();
        param.put("url", url);
        param.put("type", type);
        param.put("name", name);
        param.put("size", size);

        client.post(this, uploadUserFile, param, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                Log.d("test", "course onSuccess = " + ret);
            }

            @Override
            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                Logger.log("::::::::::::onFailure" + ret);
                if (ret != null && !ret.equals("")) {
                    try {
                        JSONObject data = new JSONObject(ret);
                        if (data.optInt("statusCode") != 200) {
                            Utils.login(App.this, new Utils.ReLogin() {
                                @Override
                                public void onSuccess() {
                                    uploadUserFile(url, type, name, size);
                                }

                                @Override
                                public void onFailure() {
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(App.this, "请求失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
