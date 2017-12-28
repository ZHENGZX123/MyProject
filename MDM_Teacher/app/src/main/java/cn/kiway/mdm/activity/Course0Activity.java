package cn.kiway.mdm.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.utils.Constant;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.List;

import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;
import io.agora.rtc.ss.app.HelloAgoraScreenSharingActivity;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.kiway.mdm.util.ResultMessage.RECORD_REQUEST_CODE;
import static cn.kiway.mdm.web.JsAndroidInterface.picPath;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile;

/**
 * Created by Administrator on 2017/12/28.
 */

//未上课
public class Course0Activity extends HelloAgoraScreenSharingActivity {

    private FrameLayout x5FileLayout;
    private TbsReaderView readerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course0);

        x5FileLayout = (FrameLayout) findViewById(R.id.x5FileLayout);
        initRecord();
    }

    private void initRecord() {
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
        }
    }

    public void testX5(View view) {
        openFileByX5("");
    }

    public void openFileByX5(String path) {
        path = "/mnt/sdcard/test.docx";

        String finalPath = path;

        x5FileLayout.setVisibility(View.VISIBLE);

        readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", finalPath);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(Utils.getFileType(finalPath), false);
        if (b) {
            readerView.openFile(bundle);
        } else {
            toast("打开文件失败");
        }
        x5FileLayout.addView(readerView);
    }

    @Override
    public void onBackPressed() {
        if (x5FileLayout.isShown()) {
            readerView.onStop();
            readerView = null;
            x5FileLayout.removeAllViews();
            x5FileLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    //-------------------------tools1----------------------

    public void jieping(View view) {
        Utils.GetandSaveCurrentImage(this);
    }

    public void paizhao(View view) {
        //众人通是拍照后，放在画布上。
        String picPath = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivity(intent);
    }

    public void huabi(View view) {
        //等彭毅
    }

    private boolean tuiping = false;

    public void tuiping(View view) {
        //先接入声网
        if (tuiping) {
            toast("结束推屏");
            mRtcEngine.leaveChannel();
            stopCapture();
        } else {
            toast("开始推屏");
            initModules();
            startCapture();
            mRtcEngine.joinChannel(null, "kiway", "", 0);
        }
        tuiping = !tuiping;
    }

    public void chaping(View view) {
        //查看学生屏幕，需要获取学生列表。
    }

    public void suoping(View view) {
        //锁定学生屏幕，需要获取学生列表。
    }

    public void wenjian(View view) {
        //给学生发文件，需要获取学生列表。
    }

    public void shezhi(View view) {
        //设置？？？不知道是什么。测试用
    }

    //-------------------------tools2----------------------
    public void tongji(View view) {
        //知识点统计，给全班发送统计命令。
    }

    public void dianmingda(View view) {
        //点名答，需要获取学生列表。
    }

    public void qiangda(View view) {
        //抢答，给全班发送抢答命令。
    }

    public void suijichouda(View view) {
        //随机抽答，随机找几个发命令。
    }

    public void ceping(View view) {
        //测评，给全班发测评命令
    }

    //-------------------------------录屏相关-----------------------------
    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService;


    public void startRecord() {
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void stopRecord() {
        if (recordService.isRunning()) {
            recordService.stopRecord();
        }
        //上传视频文件。。。
        //上传课程记录。。。
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };
}
