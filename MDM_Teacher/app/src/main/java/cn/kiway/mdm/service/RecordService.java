package cn.kiway.mdm.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.Course0Activity;
import cn.kiway.mdm.activity.LukeCameraActivity;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.view.wm.FloatBallManager;
import cn.kiway.mdm.view.wm.floatball.FloatBallCfg;
import cn.kiway.mdm.view.wm.menu.FloatMenuCfg;
import cn.kiway.mdm.view.wm.menu.MenuItem;
import cn.kiway.mdm.view.wm.utils.DensityUtil;

import static cn.kiway.mdm.KWApplication.ROOT;
import static cn.kiway.mdm.teacher.R.drawable.rk1;
import static cn.kiway.mdm.view.wm.utils.BackGroudSeletor.getdrawble;


public class RecordService extends Service {
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;

    private boolean running;
    private int width = 720;
    private int height = 1080;
    private int dpi;
    public String output;
    //录课
    public static boolean recording = false;

    @Override
    public IBinder onBind(Intent intent) {
        return new RecordBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread serviceThread = new HandlerThread("service_thread",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        initWm();
        running = false;
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatballManager != null)
            mFloatballManager.hide();
    }

    public void setMediaProject(MediaProjection project) {
        mediaProjection = project;
    }

    public boolean isRunning() {
        return running;
    }

    public void setConfig(int width, int height, int dpi) {
        this.width = width;
        this.height = height;
        this.dpi = dpi;
    }

    public boolean startRecord() {
        if (mFloatballManager == null) {
            initWm();
        }
        mFloatballManager.show();
        if (mediaProjection == null || running) {
            return false;
        }

        initRecorder();
        createVirtualDisplay();
        mediaRecorder.start();
        running = true;
        return true;
    }

    public boolean stopRecord() {
        if (KWApplication.currentActivity != null && KWApplication.currentActivity instanceof Course0Activity) {
            ((Course0Activity) KWApplication.currentActivity).setLuke(rk1);
        }
        Toast.makeText(this, "结束录制本地视频", Toast.LENGTH_SHORT).show();
        if (mFloatballManager != null)
            mFloatballManager.hide();
        if (!running) {
            return false;
        }
        running = false;
        mediaRecorder.setOnErrorListener(null);
        mediaRecorder.setPreviewDisplay(null);
        mediaRecorder.stop();
        mediaRecorder.reset();
        virtualDisplay.release();
        mediaProjection.stop();
        return true;
    }

    private void createVirtualDisplay() {
        virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", width, height, dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    private void initRecorder() {

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//DEFAULT WEBM MPEG_4 THREE_GPP
        output = getSaveDirectory() + System.currentTimeMillis() + ".mp4";
        mediaRecorder.setOutputFile(output);
        mediaRecorder.setVideoSize(width, height);
        // mediaRecorder.setVideoFrameRate(12);//30  zzx add  视频的帧率和视频大小是需要硬件支持的，如果设置的帧率和视频大小,如果硬件不支持就会出现错误。
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//DEFAULT VP8 H264
        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//AMR_NB
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//AMR_NB
        mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        // mediaRecorder.setVideoFrameRate(12);//30
        mediaRecorder.setMaxDuration(45 * 60 * 1000);//最大录制45分钟
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSaveDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = ROOT + "ScreenRecord/";
            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }
            return rootDir;
        } else {
            return null;
        }
    }

    public class RecordBinder extends Binder {
        public RecordService getRecordService() {
            return RecordService.this;
        }
    }

    private FloatBallManager mFloatballManager;

    public void initWm() {
        //1 初始化悬浮球配置，定义好悬浮球大小和icon的drawable
        int ballSize = DensityUtil.dip2px(this, 40);
        Drawable ballIcon = getdrawble(R.drawable.logo, this);
        FloatBallCfg ballCfg = new FloatBallCfg(ballSize, ballIcon, FloatBallCfg.Gravity.RIGHT_CENTER);
        //2 需要显示悬浮菜单
        //2.1 初始化悬浮菜单配置，有菜单item的大小和菜单item的个数
        int menuSize = DensityUtil.dip2px(this, 180);
        int menuItemSize = DensityUtil.dip2px(this, 30);
        FloatMenuCfg menuCfg = new FloatMenuCfg(menuSize, menuItemSize);
        //3 生成floatballManager
        mFloatballManager = new FloatBallManager(this, ballCfg, menuCfg);
        addFloatMenuItem();
        // mFloatballManager.show();
    }

    public static boolean isCamera = false;
    MenuItem camera;

    private void addFloatMenuItem() {
        camera = new MenuItem(getdrawble(R.drawable.camera, this)) {
            @Override
            public void action() {
                if (isCamera) {
                    if (KWApplication.currentActivity instanceof LukeCameraActivity) {
                        ((LukeCameraActivity) KWApplication.currentActivity).finish();
                    }
                } else
                    startActivity(new Intent(RecordService.this, LukeCameraActivity.class).addFlags(Intent
                            .FLAG_ACTIVITY_NEW_TASK));
            }
        };
        MenuItem stopRecord = new MenuItem(getdrawble(R.drawable.stop, this)) {
            @Override
            public void action() {
                if (KWApplication.currentActivity instanceof LukeCameraActivity) {
                    ((LukeCameraActivity) KWApplication.currentActivity).finish();
                }
                stopRecord();
            }
        };
        mFloatballManager.addMenuItem(camera).addMenuItem(stopRecord)
                .buildMenu();
    }


}