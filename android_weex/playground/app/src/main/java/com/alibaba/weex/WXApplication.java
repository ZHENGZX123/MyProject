package com.alibaba.weex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.alibaba.weex.commons.adapter.DefaultWebSocketAdapterFactory;
import com.alibaba.weex.commons.adapter.JSExceptionAdapter;
import com.alibaba.weex.commons.adapter.UniversalImageAdapter;
import com.alibaba.weex.extend.adapter.PlayDebugAdapter;
import com.alibaba.weex.extend.component.MyAlwaysMarqueeTextView;
import com.alibaba.weex.extend.component.MyAutofitTextView;
import com.alibaba.weex.extend.component.MyCalendarView;
import com.alibaba.weex.extend.component.MyChartView;
import com.alibaba.weex.extend.component.MyCheckBox;
import com.alibaba.weex.extend.component.MyDatePicker;
import com.alibaba.weex.extend.component.MyDragSortListView;
import com.alibaba.weex.extend.component.MyGridView;
import com.alibaba.weex.extend.component.MyListView;
import com.alibaba.weex.extend.component.MyMapView;
import com.alibaba.weex.extend.component.MyMoneyEditText;
import com.alibaba.weex.extend.component.MyNumberPicker;
import com.alibaba.weex.extend.component.MyPhotoView;
import com.alibaba.weex.extend.component.MyProgressBar;
import com.alibaba.weex.extend.component.MyProgressBar2;
import com.alibaba.weex.extend.component.MyProgressBar3;
import com.alibaba.weex.extend.component.MyRadioButton;
import com.alibaba.weex.extend.component.MyRichText;
import com.alibaba.weex.extend.component.MyRoundedImageView;
import com.alibaba.weex.extend.component.MySeekbar;
import com.alibaba.weex.extend.component.MySpinner;
import com.alibaba.weex.extend.component.MySquareProgressBar;
import com.alibaba.weex.extend.component.MyStaggeredGridView;
import com.alibaba.weex.extend.component.MyTimePicker;
import com.alibaba.weex.extend.component.MyVideoPlayer;
import com.alibaba.weex.extend.component.MyVideoPlayer2;
import com.alibaba.weex.extend.component.MyVideoView;
import com.alibaba.weex.extend.component.MyVitamioVideoView;
import com.alibaba.weex.extend.component.MyWebView;
import com.alibaba.weex.extend.component.MyWheelView;
import com.alibaba.weex.extend.module.MyAliPay;
import com.alibaba.weex.extend.module.MyAudioRecorderPlayer;
import com.alibaba.weex.extend.module.MyCamera;
import com.alibaba.weex.extend.module.MyDropDown;
import com.alibaba.weex.extend.module.MyImagePicker;
import com.alibaba.weex.extend.module.MyLocation;
import com.alibaba.weex.extend.module.MyLog;
import com.alibaba.weex.extend.module.MyNotification;
import com.alibaba.weex.extend.module.MyOpenURL;
import com.alibaba.weex.extend.module.MyRegion;
import com.alibaba.weex.extend.module.MyShake;
import com.alibaba.weex.extend.module.MyShare;
import com.alibaba.weex.extend.module.MyThirdLogin;
import com.alibaba.weex.extend.module.MyToast;
import com.alibaba.weex.extend.module.MyVibrator;
import com.alibaba.weex.extend.module.MyWechatPay;
import com.alibaba.weex.extend.module.MyWifiManager;
import com.alibaba.weex.extend.module.WXEventModule;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.common.WXException;

import java.io.File;

import io.vov.vitamio.Vitamio;

public class WXApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        //百度地图。。
        SDKInitializer.initialize(getApplicationContext());

        //vitamio
        Vitamio.initialize(getApplicationContext());

        /**
         * Set up for fresco usage.
         * Set<RequestListener> requestListeners = new HashSet<>();
         * requestListeners.add(new RequestLoggingListener());
         * ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
         *     .setRequestListeners(requestListeners)
         *     .build();
         * Fresco.initialize(this,config);
         **/
//    initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");

        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
//                        .setImgAdapter(new FrescoImageAdapter())
//                        .setImgAdapter(new PicassoImageAdapter())
                        .setImgAdapter(new UniversalImageAdapter())
                        .setDebugAdapter(new PlayDebugAdapter())
                        .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setHttpAdapter(new DefaultWXHttpAdapter())
                        .build()
        );
        try {
            initImageCache();
//            Fresco.initialize(this);

            WXSDKEngine.registerComponent("my_richtext", MyRichText.class);
            WXSDKEngine.registerComponent("my_checkbox", MyCheckBox.class);
            WXSDKEngine.registerComponent("my_radiobutton", MyRadioButton.class);
            WXSDKEngine.registerComponent("my_seekbar", MySeekbar.class);
            WXSDKEngine.registerComponent("my_webview", MyWebView.class);
            WXSDKEngine.registerComponent("my_mapview", MyMapView.class);
            WXSDKEngine.registerComponent("my_alwaysmarqueetextview", MyAlwaysMarqueeTextView.class);
            WXSDKEngine.registerComponent("my_moneyedittext", MyMoneyEditText.class);
            WXSDKEngine.registerComponent("my_autofittextview", MyAutofitTextView.class);
            WXSDKEngine.registerComponent("my_roundedimageview", MyRoundedImageView.class);
            WXSDKEngine.registerComponent("my_photoview", MyPhotoView.class);
            WXSDKEngine.registerComponent("my_chartview", MyChartView.class);
            WXSDKEngine.registerComponent("my_videoview", MyVideoView.class);
            WXSDKEngine.registerComponent("my_videoplayer", MyVideoPlayer.class);
            WXSDKEngine.registerComponent("my_videoplayer2", MyVideoPlayer2.class);
            WXSDKEngine.registerComponent("my_vitamiovideoview", MyVitamioVideoView.class);
            WXSDKEngine.registerComponent("my_gridview", MyGridView.class);
            WXSDKEngine.registerComponent("my_staggeredgridview", MyStaggeredGridView.class);
            WXSDKEngine.registerComponent("my_listview", MyListView.class);
            WXSDKEngine.registerComponent("my_dragsortlistview", MyDragSortListView.class);
            WXSDKEngine.registerComponent("my_progressbar", MyProgressBar.class);
            WXSDKEngine.registerComponent("my_progressbar2", MyProgressBar2.class);
            WXSDKEngine.registerComponent("my_progressbar3", MyProgressBar3.class);
            WXSDKEngine.registerComponent("my_squareprogressbar", MySquareProgressBar.class);
            WXSDKEngine.registerComponent("my_datepicker", MyDatePicker.class);
            WXSDKEngine.registerComponent("my_timepicker", MyTimePicker.class);
            WXSDKEngine.registerComponent("my_numberpicker", MyNumberPicker.class);
            WXSDKEngine.registerComponent("my_calendarview", MyCalendarView.class);
            WXSDKEngine.registerComponent("my_wheelview", MyWheelView.class);
            WXSDKEngine.registerComponent("my_spinner", MySpinner.class);


            WXSDKEngine.registerModule("my_toast", MyToast.class);
            WXSDKEngine.registerModule("my_log", MyLog.class);
            WXSDKEngine.registerModule("my_notification", MyNotification.class);
            WXSDKEngine.registerModule("my_openurl", MyOpenURL.class);
            WXSDKEngine.registerModule("my_location", MyLocation.class);
            WXSDKEngine.registerModule("my_camera", MyCamera.class);
            WXSDKEngine.registerModule("my_wifimanager", MyWifiManager.class);
            WXSDKEngine.registerModule("my_imagepicker", MyImagePicker.class);
            WXSDKEngine.registerModule("my_share", MyShare.class);
            WXSDKEngine.registerModule("my_thirdlogin", MyThirdLogin.class);
            WXSDKEngine.registerModule("my_wechatpay", MyWechatPay.class);
            WXSDKEngine.registerModule("my_alipay", MyAliPay.class);
            WXSDKEngine.registerModule("my_region", MyRegion.class);
            WXSDKEngine.registerModule("my_audiorecorderplayer", MyAudioRecorderPlayer.class);
            WXSDKEngine.registerModule("my_vibrator", MyVibrator.class);
            WXSDKEngine.registerModule("my_shake", MyShake.class);
            WXSDKEngine.registerModule("my_dropdown", MyDropDown.class);


            WXSDKEngine.registerModule("event", WXEventModule.class);
            /**
             * override default image tag
             * WXSDKEngine.registerComponent("image", FrescoImageComponent.class);
             */
        } catch (WXException e) {
            e.printStackTrace();
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                                               @Override
                                               public void onActivityCreated(Activity activity, Bundle bundle) {

                                               }

                                               @Override
                                               public void onActivityStarted(Activity activity) {

                                               }

                                               @Override
                                               public void onActivityResumed(Activity activity) {

                                               }

                                               @Override
                                               public void onActivityPaused(Activity activity) {

                                               }

                                               @Override
                                               public void onActivityStopped(Activity activity) {

                                               }

                                               @Override
                                               public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                                               }

                                               @Override
                                               public void onActivityDestroyed(Activity activity) {
                                                   // The demo code of calling 'notifyTrimMemory()'
                                                   if (false) {
                                                       // We assume that the application is on an idle time.
                                                       WXSDKManager.getInstance().notifyTrimMemory();
                                                   }
                                               }
                                           }

        );

    }


    /**
     * @param connectable debug server is connectable or not.
     *                    if true, sdk will try to connect remote debug server when init WXBridge.
     * @param debuggable  enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
     *                    true, you can launch a remote debugger and inspector both.
     *                    false, you can  just launch a inspector.
     * @param host        the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
     *                    for example "127.0.0.1".
     */
    private void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = debuggable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        // // 设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2);

        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                "imageloader/wjc");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(displayMetrics.widthPixels,
                        displayMetrics.heightPixels)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(5)
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
//                .discCache(new UnlimitedDiscCache(cacheDir))
                // 自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .defaultDisplayImageOptions(displayImageOptionsBuilder.build())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                /* .writeDebugLogs() */.build(); // Remove for release app
        ImageLoader.getInstance().init(config);
    }


}
