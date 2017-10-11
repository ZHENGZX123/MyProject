package cn.kiway.homework.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import com.sonix.oid.Dots;
import com.sonix.oidbluetooth.BluetoothLEService;
import com.sonix.oidbluetooth.SelectDeviceActivity;
import com.tqltech.tqlpencomm.Dot;
import com.tqltech.tqlpencomm.PenCommAgent;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.kiway.homework.WXApplication;
import cn.kiway.homework.entity.HTTPCache;
import cn.kiway.homework.student.R;
import cn.kiway.homework.util.BadgeUtil;
import cn.kiway.homework.util.CountlyUtil;
import cn.kiway.homework.util.FileUtils;
import cn.kiway.homework.util.HttpDownload;
import cn.kiway.homework.util.MyDBHelper;
import cn.kiway.homework.util.NetworkUtil;
import cn.kiway.homework.util.ResourceUtil;
import cn.kiway.homework.util.UploadUtil;
import cn.kiway.homework.util.Utils;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static android.content.ContentValues.TAG;
import static cn.kiway.homework.WXApplication.ceshiUrl;
import static cn.kiway.homework.WXApplication.url;
import static cn.kiway.homework.WXApplication.zhengshiUrl;


public class MainActivity extends BaseActivity {

    private static final String currentPackageVersion = "0.4.6";

    private WebView wv;
    private LinearLayout layout_welcome;
    private boolean isSuccess = false;
    private boolean isJump = false;
    private boolean checking = false;
    private Dialog dialog_download;
    protected ProgressDialog pd;
    private int lastProgress;
    public static MainActivity instance;
    private Button kill;
    private String mPage;
    private int mAction = -1;
    private String mNewName;
    private String mMac;
    private int mStatus;//0断开 1连接

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "onCreate");
        instance = this;
        initView();
        Utils.checkNetWork(this, false);
        checkIsFirst();
        initData();
        load();
        checkNewVersion();
        huaweiPush();
        initPen();
    }

    private void initPen() {
        Intent gattServiceIntent = new Intent(this, BluetoothLEService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (WebView) findViewById(R.id.wv);
        kill = (Button) findViewById(R.id.kill);
        layout_welcome = (LinearLayout) findViewById(R.id.layout_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BadgeUtil.sendBadgeNumber(getApplicationContext(), "0");
        Log.d("test", "onresume checking = " + checking);
        if (checking) {
            new Thread() {
                @Override
                public void run() {
                    while (checking) {
                        Log.d("test", "checking loop...");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("test", "checking loop end");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    checkNotification();
                }
            }.start();
        } else {
            //如果当前打开，直接跳转
            checkNotification();
        }
    }

    private synchronized void checkNotification() {
        final String event = getSharedPreferences("kiway", 0).getString("event", "");
        Log.d("test", "取了一个event = " + event);
        if (TextUtils.isEmpty(event)) {
            return;
        }
        getSharedPreferences("kiway", 0).edit().putString("event", "").commit();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //告诉前端做动作。
                Log.d("test", "notificationCallback");
                wv.loadUrl("javascript:notificationCallback('" + event + "')");
            }
        });
    }

    private void load() {
        wv.clearCache(true);
        wv.loadUrl("file://" + WXApplication.ROOT + WXApplication.HTML);
    }

    private void initData() {
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = wv.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        if (Utils.isTabletDevice(this)) {
            settings.setTextSize(WebSettings.TextSize.NORMAL);
        } else {
            settings.setTextSize(WebSettings.TextSize.NORMAL);
        }

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("test", "shouldInterceptRequest url = " + url);
                //只有http图片才缓存
                if ((url.startsWith("http") || url.startsWith("https"))
                        && (url.endsWith("jpg") || url.endsWith("JPG") || url.endsWith("jpeg") || url.endsWith("JPEG") || url.endsWith("png") || url.endsWith("PNG"))) {
                    InputStream is = getStreamByUrl(url);
                    if (is == null) {
                        return super.shouldInterceptRequest(view, url);
                    }
                    return new WebResourceResponse(getMimeType(url), "utf-8", is);
                }
                //des解密用
                //else if (url.endsWith("js") || url.endsWith("css") || url.endsWith("html")) {
                //InputStream is = getStreamByUrl2(url.replace("file://", ""));
                // return new WebResourceResponse(getMimeType(url), "utf-8", is);
                //}
                return super.shouldInterceptRequest(view, url);
            }
        });

        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "wx");
    }

    private long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            if (url.endsWith("login") || url.endsWith("ctb/error") || url.endsWith("msdx") || url.endsWith("mine") || url.endsWith("index")) {
                doFinish();
                return true;
            }
            if (url.contains("msdx_detail")) {
                wv.loadUrl("javascript:clickback()");
            }
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            }
            doFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doFinish() {
        long t = System.currentTimeMillis();
        if (t - time >= 2000) {
            time = t;
            Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    private static final int SNAPSHOT = 9999;
    private static final int SELECT_PHOTO = 8888;
    private static final int SAOMAWANG = 7777;
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;
    private String snapshotFile;
    private BluetoothLEService mService = null;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((BluetoothLEService.LocalBinder) rawBinder).getService();
            if (!mService.initialize()) {
                finish();
            }
            mService.setOnDataReceiveListener(new BluetoothLEService.OnDataReceiveListener() {
                @Override
                public void onDataReceive(final Dot dot) {
                    Log.d("test", "onDataReceive dot = " + dot.toString());
                    //TODO 在线的点要不要存到离线数据库里面去。
                }

                @Override
                public void onStartOfflineDownload(final boolean isSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSuccess) {
                                pd = new ProgressDialog(MainActivity.this);
                                pd.setMessage("正在获取离线数据。。。");
                                pd.show();
                            } else {
                                //没有离线数据，直接返回给前端
                                Log.d("test", "prepareOfflineCallback");
                                wv.loadUrl("javascript:prepareOfflineCallback()");
                            }
                        }
                    });
                }

                @Override
                public void onOfflineDataReceive(final Dot dot) {
                    Log.d("test", "onOfflineDataReceive dot = " + dot.toString());
                    ProcessEachDot2(dot);
                }

                @Override
                public void onFinishedOfflineDownload() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //1.删除所有缓存
                            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
                            bleManager.WritePenOffLineDelet();
                            pd.dismiss();

                            //有离线数据，下载完了，返回给前端
                            Log.d("test", "prepareOfflineCallback");
                            wv.loadUrl("javascript:prepareOfflineCallback()");
                        }
                    });
                }

                private synchronized void ProcessEachDot2(Dot dot) {
                    int counter = 0;
                    int pointZ = dot.force;
                    if (pointZ < 0) {
                        Log.i(TAG, "Counter=" + counter + ", Pressure=" + pointZ + "  Cut!!!!!");
                        return;
                    }
                    int tmpx = dot.x;
                    float pointX = dot.fx;
                    pointX /= 100.0;
                    pointX += tmpx;

                    int tmpy = dot.y;
                    float pointY = dot.fy;
                    pointY /= 100.0;
                    pointY += tmpy;

                    //int PageID = CheckXYLocation(gpointX, gpointY);
                    int PageID = dot.PageID;
                    int type = 0;
                    Log.d("test", "dot.type = " + dot.type.ordinal());
                    if (dot.type == Dot.DotType.PEN_DOWN) {
                        type = 0;
                    } else if (dot.type == Dot.DotType.PEN_MOVE) {
                        type = 1;
                    } else if (dot.type == Dot.DotType.PEN_UP) {
                        type = 2;
                    }
                    Dots dots = new Dots(dot.SectionID, dot.OwnerID, dot.BookID, PageID, pointX, pointY, pointZ, type, 0, 0, dot.Counter, dot.angle);
                    new com.sonix.util.MyDBHelper(getApplicationContext()).addDots(dots);
                }

                @Override
                public void onConnected(final String address, final String penName) {
                    Log.d("test", "onConnected isCalled");
                    mStatus = 1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast("连接点阵笔成功");
                            if (mAction == 0) {
                                JSONObject obj = new JSONObject();
                                try {
                                    obj.put("address", address);
                                    obj.put("penName", penName);
                                    wv.loadUrl("javascript:bindPenCallback(" + obj.toString() + ")");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mAction = -1;
                            } else {
                                //sdk bug sleep 1000.
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(1500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        if (mAction == 1) {
                                            new JsAndroidInterface().renamePen(mMac, mNewName);
                                        } else if (mAction == 2) {
                                            new JsAndroidInterface().getPenBattery(mMac);
                                        } else if (mAction == 3) {
                                            new JsAndroidInterface().prepareOffline(mMac, mPage);
                                        }
                                        mAction = -1;
                                    }
                                }.start();
                            }
                        }
                    });
                }

                @Override
                public void onDisconnected() {
                    Log.d("test", "onDisconnected is called");
                    mStatus = 0;
                }

                @Override
                public void onPenNameSetupResponse(final boolean isSuccess) {
                    Log.d("test", "改名" + isSuccess);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSuccess) {
                                wv.loadUrl("javascript:renamePenCallback(1)");
                            } else {
                                wv.loadUrl("javascript:renamePenCallback(0)");
                            }
                        }
                    });
                }

                @Override
                public void onReceivePenStatus(final int battery) {
                    Log.d("test", "onReceivePenStatus = " + battery);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            wv.loadUrl("javascript:getPenBatteryCallback(" + battery + ")");
                        }
                    });
                }
            });
        }

        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
        }
    };

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        @JavascriptInterface
        public void bindPen() {
            Log.d("test", "bindPen");
            mAction = 0;
            //连接笔，绑定笔
            if (checkBLEEnable()) {
                Intent serverIntent = new Intent(MainActivity.this, SelectDeviceActivity.class);
                startActivityForResult(serverIntent, REQUEST_SELECT_DEVICE);
            }
        }

        @JavascriptInterface
        public void renamePen(String mac, String newName) {
            mNewName = newName;
            mMac = mac;
            Log.d("test", "renamePen mac = " + mac + " , newName = " + newName);
            //1.判断笔有没有连接。
            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
            if (bleManager != null && bleManager.isConnect()) {
                bleManager.ReqSetupPenName(newName);
            } else {
                mAction = 1;
                mService.connect(mac, null);
            }
        }

        @JavascriptInterface
        public void getPenBattery(String mac) {
            Log.d("test", "getPenBattery mac = " + mac);
            mMac = mac;
            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
            if (bleManager != null && bleManager.isConnect()) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bleManager.ReqPenStatus();
            } else {
                mAction = 2;
                mService.connect(mac, null);
            }
        }

        @JavascriptInterface
        public String getPenStatus(String mac) {
            Log.d("test", "getPenStatus mac = " + mac);
            return mStatus + "";
        }

        @JavascriptInterface
        public void connectPen(String mac) {
            Log.d("test", "connectPen mac = " + mac);
            mMac = mac;
            mService.connect(mac, null);
        }

        @JavascriptInterface
        public void disconnectPen(String mac) {
            Log.d("test", "disconnectPen mac = " + mac);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        toast("请长按蓝牙笔上的开关");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            //mService.close();
            //试试disconnect()
        }

        @JavascriptInterface
        public void unbindPen(String mac) {
            Log.d("test", "unbindPen mac = " + mac);
            //mService.close();
            //试试disconnect()
        }

        @JavascriptInterface
        public void prepareOffline(String mac, String page) {
            Log.d("test", "prepareOffline , mac = " + mac + " , page = " + page);
            mMac = mac;
            mPage = page;//现在用不到。
            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
            if (bleManager != null && bleManager.isConnect()) {
                bleManager.ReqOfflineDataTransfer(true);
            } else {
                mAction = 3;
                mService.connect(mac, null);
            }
        }

        @JavascriptInterface
        public void getOffline(String mac, final String page) {
            Log.d("test", "getOffline , mac = " + mac + " , page = " + page);
            String pages[] = page.replace("[", "").replace("]", "").split(",");
            String ret = "";
            for (String p : pages) {
                ret += drawOffline(p) + "_";
            }
            ret = ret.substring(0, ret.length() - 1);
            final String finalRet = ret;
            Log.d("test", "finalRet = " + finalRet);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wv.loadUrl("javascript:getOfflineCallback('" + finalRet + "')");
                }
            });
        }

        private String drawOffline(String p) {
            ArrayList<Dots> dots = new com.sonix.util.MyDBHelper(getApplicationContext()).getDotsByPageID(p);
            int size = dots.size();
            Log.d("test", "drawOffline page = " + p + " size = " + size);
            if (size < 10) {
                Log.d("test", "page = " + p + " , param = [[41.47,152.64,41.51,152.74,41.48,152.64]]");
                return "[[0,0,50,50,100,100,60,40]]";
            }
            for (int i = 0; i < size; i++) {
                Dots d = dots.get(i);
                //规则1：如果第一个点不是0，强制为0
                if (i == 0) {
                    d.ntype = 0;
                    continue;
                }
                //规则2：如果0前面不是2，强制为2
                if (d.ntype == 0) {
                    Dots prevDot = dots.get(i - 1);
                    prevDot.ntype = 2;
                }
                //规则3：2的后面一定是0
                if (i != size - 1 && d.ntype == 2) {
                    Dots nextDot = dots.get(i + 1);
                    nextDot.ntype = 0;
                }
                //规则4:如果最后一个不是2，强制为2
                if (i == size - 1) {
                    d.ntype = 2;
                }
            }
            //规则5 如果倒数第2个是2，把最后一个删掉
            Dots temp = dots.get(size - 2);
            if (temp.ntype == 2) {
                dots.remove(size - 1);
            }
            String param = "";
            for (Dots d : dots) {
                Log.d("test", "d = " + d.toString());
                if (d.ntype == 0) {
                    param += "[" + d.pointX + "," + d.pointY + ",";
                } else if (d.ntype == 1) {
                    param += d.pointX + "," + d.pointY + ",";
                } else {
                    param += d.pointX + "," + d.pointY + "],";
                }
            }
            param = param.substring(0, param.length() - 1);
            param = "[" + param + "]";
            Log.d("test", "page = " + p + " , param = " + param);
            return param;
        }

        @JavascriptInterface
        public String getOS() {
            Log.d("test", "getOS is called");
            return "Android";
        }

        @JavascriptInterface
        public String isTest() {
            Log.d("test", "isTest is called");
            return WXApplication.isTest ? "1" : "0";
        }

        @JavascriptInterface
        public void login(String param) {
            Log.d("test", "login param = " + param);
            try {
                String accessToken = new JSONObject(param).getString("accessToken");
                String userId = new JSONObject(param).getString("userId");
                getSharedPreferences("kiway", 0).edit().putString("accessToken", accessToken).commit();
                getSharedPreferences("kiway", 0).edit().putString("userId", userId).commit();
                installationPush();
                //getBooks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //注销的时候要调用这个
        @JavascriptInterface
        public void logout() {
            Log.d("test", "logout");
            getSharedPreferences("kiway", 0).edit().putString("accessToken", "").commit();
            getSharedPreferences("kiway", 0).edit().putString("userId", "").commit();
            new MyDBHelper(getApplicationContext()).deleteAllHttpCache();
            //TODO 注销推送平台。
            //unInstallationPush();
        }

        @JavascriptInterface
        public String getCacheSize() {
            return "1.5M";
        }

        @JavascriptInterface
        public void clearCache() {
            toast("清理完毕");
        }

        @JavascriptInterface
        public void showPhoto(String param1, String param2) {
            try {
                Log.d("test", "showPhoto param1 = " + param1);
                Log.d("test", "showPhoto param2 = " + param2);
                ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
                //ViewPagerActivity.sDrawables = new String[]{"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1897461257,834967576&fm=80&w=179&h=119&img.JPEG"};
                //ViewPagerActivity.sDrawables = new String[]{"file:///mnt/sdcard/aaa2.jpg", "file:///mnt/sdcard/aaa.jpg"};本地要有file://
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", Integer.parseInt(param2));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void fileUpload(String filepath) {
            filepath = filepath.replace("file://", "");
            Log.d("test", "fileUpload , filepath = " + filepath);
            //选择图片
            final String finalFilepath = filepath;
            new Thread() {
                @Override
                public void run() {
                    String token = getSharedPreferences("kiway", 0).getString("accessToken", "");
                    Log.d("test", "取出token=" + token);
                    File file = new File(finalFilepath);
                    final String ret = UploadUtil.uploadFile(file, WXApplication.url + "/common/file?access_token=" + token, file.getName());
                    Log.d("test", "upload ret = " + ret);
                    if (TextUtils.isEmpty(ret)) {
                        toast("上传图片失败，请稍后再试");
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject(ret);
                                String url = WXApplication.url + obj.getJSONObject("data").getString("url");
                                obj.getJSONObject("data").put("url", url);
                                Log.d("test", "obj = " + obj.toString());
                                wv.loadUrl("javascript:fileUploadCallback(" + obj.toString() + ")");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }.start();
        }


        @JavascriptInterface
        public String getVersionCode() {
            Log.d("test", "getVersionCode");
            return getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
        }

        @JavascriptInterface
        public void record() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startRecord();
                    final Dialog dialog = new Dialog(MainActivity.this, R.style.popupDialog);
                    dialog.setContentView(R.layout.dialog_voice);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    final Button voice = (Button) dialog.findViewById(R.id.voice);
                    final TextView time = (TextView) dialog.findViewById(R.id.time);
                    voice.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            stopRecord();
                            dialog.dismiss();
                        }
                    });
                    new Thread() {
                        @Override
                        public void run() {
                            int duration = 0;
                            while (dialog.isShowing()) {
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                duration++;
                                final int finalDuration = duration;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        time.setText(Utils.getTimeFormLong(finalDuration * 1000));
                                    }
                                });
                            }
                        }
                    }.start();
                }
            });
        }

        @JavascriptInterface
        public void selectPhoto() {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像
            Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, SELECT_PHOTO);
        }

        @JavascriptInterface
        public void snapshot() {
            //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //snapshotFile = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
            //Uri uri = Uri.fromFile(new File(snapshotFile));
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            //startActivityForResult(intent, SNAPSHOT);
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA);
            startActivityForResult(intent, SNAPSHOT);
        }

        @JavascriptInterface
        public void setRequestedOrientation(String screen) {
            Log.d("test", "setRequestedOrientation = " + screen);
            if (Integer.parseInt(screen) == 1) {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }

        @JavascriptInterface
        public void httpRequest(String url, String param, final String method, String time, String tagname, String related, String event) {
            if (!WXApplication.isTest) {
                url = url.replace(ceshiUrl, zhengshiUrl);
            }
            try {
                Integer.parseInt(time);
                param = param.replace("\\\"", "\"");
                if (param.startsWith("\"")) {
                    param = param.substring(1);
                }
                if (param.endsWith("\"")) {
                    param = param.substring(0, param.length() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("test", "参数错误");
                return;
            }
            Log.d("test", "httpRequest url = " + url + " , param = " + param + " , method = " + method + " , time = " + time + " , tagname = " + tagname + " , related = " + related + ", event = " + event);
            CountlyUtil.getInstance().addEvent(event);

            //0.检查网络
            if (!method.equalsIgnoreCase("GET") && !NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                toast("没有网络，请检查网络稍后再试");
                httpRequestCallback(tagname, "");
                return;
            }
            if (time.equals("0")) {
                //1.重新获取
                doHttpRequest(url, param, method, tagname, time, related);
            } else {
                //2.取缓存
                String request = url + param + method;
                HTTPCache cache1 = new MyDBHelper(MainActivity.this).getHttpCacheByRequest(request, Integer.parseInt(time));
                if (cache1 == null) {
                    Log.d("test", "没有缓存");
                    //3.如果是查询题目的话，还要再查一下资源包
                    HTTPCache cache2 = new ResourceUtil(MainActivity.this).searchResourceByUrl(url, tagname);
                    if (cache2 == null) {
                        doHttpRequest(url, param, method, tagname, time, related);
                    } else {
                        httpRequestCallback(cache2.tagname, cache2.response);
                    }
                } else {
                    Log.d("test", "有缓存");
                    httpRequestCallback(cache1.tagname, cache1.response);
                }
            }
        }

    }

    private void doHttpRequest(final String url, final String param, final String method, final String tagname, final String time, final String related) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    String token = getSharedPreferences("kiway", 0).getString("accessToken", "");
                    client.addHeader("X-Auth-Token", token);
                    if (method.equalsIgnoreCase("POST")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.post(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                Log.d("test", "post onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new MyDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
//                                Log.d("test", "post onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("PUT")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.put(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                Log.d("test", "put onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new MyDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                Log.d("test", "put onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("GET")) {
                        client.get(MainActivity.this, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                Log.d("test", "get onSuccess = " + ret);
                                saveDB(url, param, method, ret, tagname);
                                httpRequestCallback(tagname, ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                Log.d("test", "get onFailure = " + ret);
                                //如果是get，把缓存回它
                                String request = url + param + method;
                                HTTPCache cache = new MyDBHelper(getApplicationContext()).getHttpCacheByRequest(request, Integer.parseInt(time));
                                if (cache != null) {
                                    httpRequestCallback(cache.tagname, cache.response);
                                } else {
                                    httpRequestCallback(tagname, ret);
                                }
                            }
                        });
                    } else if (method.equalsIgnoreCase("DELETE")) {
                        client.delete(MainActivity.this, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                Log.d("test", "get onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new MyDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                Log.d("test", "get onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "exception = " + e.toString());
                }
            }
        });
    }

    public void clickSetToken(View view) {
        getSharedPreferences("kiway", 0).edit().putString("accessToken", "123456").commit();
    }


    private void saveDB(String url, String param, String method, String ret, String tagname) {
        if (kill.getVisibility() == View.VISIBLE) {
            return;
        }
        Log.d("test", "saveDB");
        String request = url + param + method;
        HTTPCache cache = new MyDBHelper(this).getHttpCacheByRequest(request);
        if (cache == null) {
            cache = new HTTPCache();
            cache.request = request;
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            cache.tagname = tagname;
            new MyDBHelper(this).addHTTPCache(cache);
        } else {
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            new MyDBHelper(this).updateHTTPCache(cache);
        }

    }

    private void httpRequestCallback(final String tagname, String result) {
        if (result == null) {
            result = "";
        }
        final String finalResult = result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String r = finalResult.replace("null", "\"\"").replace("\"\"\"\"", "\"\"");
                    Log.d("test", "httpRequestCallback , tagname = " + tagname + " , result = " + r);
                    wv.loadUrl("javascript:" + tagname + "(" + r + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //录音
    private void startRecord() {
        try {
            // 判断，若当前文件已存在，则删除
            String path = "/mnt/sdcard/voice/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            recordFile = new File(path + System.currentTimeMillis() + ".mp3");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setAudioChannels(2);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//输出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//编码格式
            mediaRecorder.setAudioEncodingBitRate(16);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            toast("录制声音失败，请检查SDcard");
        }
        start = System.currentTimeMillis();
    }

    private void stopRecord() {
        if (recordFile == null) {
            return;
        }
        if (mediaRecorder == null) {
            return;
        }

        mediaRecorder.stop();

        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("太短了");
            return;
        }

        wv.loadUrl("javascript:recordCallback('file://" + recordFile.getAbsolutePath() + "')");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SNAPSHOT && resultCode == RESULT_OK) {
            Log.d("test", "snapshotCallback");
//            Log.d("test", "压缩前大小" + new File(snapshotFile).length());
//            File newFile = CompressHelper.getDefault(this).compressToFile(new File(snapshotFile));
//            Log.d("test", "压缩后大小" + newFile.length());
//            wv.loadUrl("javascript:snapshotCallback('file://" + newFile.getAbsolutePath() + "')");
            String path = data.getExtras().getString(ScanConstants.SCANNED_RESULT);
            Log.d("test", "path = " + path);
            Log.d("test", "压缩前大小" + new File(path).length());
            File newFile = CompressHelper.getDefault(this).compressToFile(new File(path));
            Log.d("test", "压缩后大小" + newFile.length());
            wv.loadUrl("javascript:snapshotCallback('file://" + newFile.getAbsolutePath() + "')");
        } else if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;

            wv.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        } else if (requestCode == SAOMAWANG) {
            if (data == null) {
                return;
            }
            int responseCode = data.getIntExtra("RESULT_OK", -1);
            Log.d("test", "responseCode = " + responseCode);
        } else if (requestCode == REQUEST_SELECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = data.getStringExtra(BluetoothDevice.EXTRA_NAME);
                mService.connect(deviceAddress, deviceName);
            }
        } else if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
            } else {
                checkBLEEnable();
            }
        }

    }


    //下面是版本检测相关代码
    public void checkNewVersion() {
        checking = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    checkTimeout();
                    HttpGet httpRequest = new HttpGet(url + "/download/version/zip_xs.json");
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
    }

    public Handler mHandler = new Handler() {
        public void
        handleMessage(android.os.Message msg) {
            if (isJump) {
                return;
            }
            isSuccess = true;
            if (msg.what == 1) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                int arg1 = msg.arg1;
                int arg2 = msg.arg2;
                if (arg1 == 0) {
                    rl_nonet.setVisibility(View.VISIBLE);
                    //无网络
                    Log.d("test", "无网络");
                } else {
                    rl_nonet.setVisibility(View.GONE);
                    //有网络
                    Log.d("test", "有网络");
                    if (arg2 == 1) {
                        wv.loadUrl("javascript:reConnect()");
                    }
                }
            } else if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");

                    String zipCode = new JSONObject(ret).getString("zipCode");
                    String zipUrl = new JSONObject(ret).getString("zipUrl");

                    if (Utils.getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
//                        showUpdateConfirmDialog(apkUrl);
                        downloadSilently(apkUrl, apkVersion);
                        jump(false);
                    } else {
                        //如果APK没有最新版本，比较包的版本。如果内置包的版本号比较高，直接替换
                        boolean flag = false;
                        String currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        if (currentPackage.compareTo(currentPackageVersion) < 0) {
                            Log.d("test", "内置包比较大，拷贝内置包");
                            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
                            checkIsFirst();
                            flag = true;
                        }
                        //替换完内置包之后，比较内置包和外包，如果版本号还是小了，更新外包
                        currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        Log.d("test", "currentPackage = " + currentPackage);
                        String outer_package = zipCode;
                        if (currentPackage.compareTo(outer_package) < 0) {
                            //提示有新的包，下载新的包
                            Log.d("test", "下载新的H5包");
                            updatePackage(outer_package, zipUrl);
                        } else {
                            Log.d("test", "H5包是最新的");
                            jump(flag);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jump(false);
                }
            } else if (msg.what == 3) {
                jump(false);
            } else if (msg.what == 4) {
                pd.dismiss();
                // 下载完成后安装
                CountlyUtil.getInstance().addEvent("升级APP");
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            } else if (msg.what == 5) {
                pd.dismiss();
                toast(R.string.downloadfailure);
                jump(false);
            } else if (msg.what == 6) {
                pd.setProgress(msg.arg1);
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/xtzy_student_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage("发现新的版本，是否更新？本次更新不消耗流量。").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog_download.dismiss();
                Message msg = new Message();
                msg.what = 4;
                msg.obj = savedFilePath;
                mHandler.sendMessage(msg);
            }
        }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jump(false);
            }
        }).create();
        dialog_download.show();
    }

    private void updatePackage(final String outer_package, final String downloadUrl) {
        new Thread() {
            @Override
            public void run() {
                final int ret = new HttpDownload().downFile(downloadUrl, WXApplication.ROOT, WXApplication.ZIP);
                Log.d("test", "下载新包 ret = " + ret);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ret == -1) {
                            jump(false);
                            return;
                        }
                        Log.d("test", "删除旧包");
                        if (new File(WXApplication.ROOT + "xtzy_student").exists()) {
                            FileUtils.delFolder(WXApplication.ROOT + "xtzy_student");
                        }
                        try {
                            Log.d("test", "解压新包");
                            new ZipFile(WXApplication.ROOT + WXApplication.ZIP).extractAll(WXApplication.ROOT);
                        } catch (ZipException e) {
                            e.printStackTrace();
                        }
                        Log.d("test", "解压完毕");
                        //解压完毕，删掉zip文件
                        new File(WXApplication.ROOT + WXApplication.ZIP).delete();
                        getSharedPreferences("kiway", 0).edit().putString("version_package", outer_package).commit();
                        jump(true);
                    }
                });
            }
        }.start();
    }

    protected void showUpdateConfirmDialog(final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(R.string.getnewversion).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog_download.dismiss();
                downloadNewVersion(url);
            }
        }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jump(false);
            }
        }).create();
        dialog_download.setCancelable(false);
        dialog_download.show();
    }

    protected void downloadNewVersion(final String urlString) {
        pd.setMessage(getString(R.string.downloading));
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        pd.setCancelable(false);
        pd.setProgress(0);

        new Thread() {
            public void run() {
                try {
                    InputStream input = null;
                    HttpURLConnection urlConn = null;
                    URL url = new URL(urlString);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("Accept-Encoding", "identity");
                    urlConn.setReadTimeout(10000);
                    urlConn.setConnectTimeout(10000);
                    input = urlConn.getInputStream();
                    int total = urlConn.getContentLength();
                    File file = null;
                    OutputStream output = null;
                    if (!new File("/mnt/sdcard/cache/").exists()) {
                        new File("/mnt/sdcard/cache/").mkdirs();
                    }
                    String savedFilePath = "";
                    savedFilePath = "/mnt/sdcard/cache/xtzy_student.apk";
                    file = new File(savedFilePath);
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int temp = 0;
                    int read = 0;
                    while ((temp = input.read(buffer)) != -1) {
                        output.write(buffer, 0, temp);
                        read += temp;
                        float progress = ((float) read) / total;
                        int progress_int = (int) (progress * 100);
                        if (lastProgress != progress_int) {
                            lastProgress = progress_int;
                            Message msg = new Message();
                            msg.what = 6;// downloading
                            msg.arg1 = progress_int;
                            mHandler.sendMessage(msg);
                        } else {
                            // do not send msg
                        }
                    }
                    output.flush();
                    output.close();
                    input.close();
                    Message msg = new Message();
                    msg.what = 4;
                    msg.obj = savedFilePath;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(5);//APK失败
                }
            }
        }.start();
    }

    protected void checkTimeout() {
        new Thread() {
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess) {
                    return;
                }
                jump(false);
            }
        }.start();
    }

    protected void checkIsFirst() {
        if (!checkFileComplete()) {
            Log.d("test", "文件不完整");
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
        }
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            CountlyUtil.getInstance().addEvent("安装APP");
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.ROOT).exists()) {
                new File(WXApplication.ROOT).mkdirs();
            }
            //拷贝
            FileUtils.copyRawToSdcard(this, R.raw.xtzy_student, WXApplication.ZIP);
            //解压
            try {
                new ZipFile(WXApplication.ROOT + WXApplication.ZIP).extractAll(WXApplication.ROOT);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            //解压完毕，删掉zip文件
            new File(WXApplication.ROOT + WXApplication.ZIP).delete();
            //保存最新版本
            getSharedPreferences("kiway", 0).edit().putString("version_package", currentPackageVersion).commit();
        }
    }

    private boolean checkFileComplete() {
        if (!new File(WXApplication.ROOT + WXApplication.HTML).exists()) {
            return false;
        }
        return true;
    }

    public void jump(final boolean refresh) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout_welcome.setVisibility(View.GONE);
                if (refresh) {
                    load();
                }
                //更新完成完成
                checking = false;
            }
        });
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    private boolean checkBLEEnable() {
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            return true;
        }
        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService = null;
    }
}