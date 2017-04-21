package com.alibaba.weex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.wjc.R;

import java.io.File;

public class VideoRecordActivity extends Activity {
    private Button start, stop;
    private MediaRecorder mRecorder;
    private SurfaceView sView;
    private boolean isRecording = false;
    private Camera mCamera;

    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takevideo);
        start = (Button) findViewById(R.id.takestart);
        stop = (Button) findViewById(R.id.takestop);
        sView = (SurfaceView) findViewById(R.id.video);
        // sView.getHolder().addCallback(this);
        // 设置surface不需要自己的缓冲区
        sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置分辨率
        // sView.getHolder().setFixedSize(320, 280);
        // 设置该组件让屏幕不会自动关闭
        sView.getHolder().setKeepScreenOn(true);

        start.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            public void onClick(View v) {
                try {
                    mRecorder = new MediaRecorder();
                    mRecorder.reset();
                    // 设置摄像头以及摄像头的方向
                    int CammeraIndex = FindBackCamera();
                    mCamera = Camera.open(CammeraIndex);
                    mCamera.setDisplayOrientation(0);//90 横屏。。。
                    mCamera.unlock();
                    mRecorder.setCamera(mCamera);

                    // 设置从麦克风采集声音
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    // 设置从摄像头采集图像
                    mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    // 设置视频文件的输出格式
                    // 必须在设置声音编码格式、图像编码格式之前设置
                    mRecorder
                            .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    // 设置声音编码格式
                    mRecorder
                            .setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    // 设置图像编码格式
                    mRecorder
                            .setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                    // mRecorder.setVideoSize(320, 280);
                    // 每秒4帧
                    // mRecorder.setVideoFrameRate(4);

                    String path = "/mnt/sdcard/";
                    if (!new File(path).exists()) {
                        new File(path).mkdirs();
                    }
                    mRecorder.setOutputFile(path + "record.mp4");

                    // 设置录制视频的方向
//					mRecorder.setOrientationHint(90); 横屏先不设置这个了。。。无语
                    // 指定使用SurfaceView来预览视频
                    mRecorder.setPreviewDisplay(sView.getHolder().getSurface());
                    mRecorder.prepare();
                    // 开始录制
                    mRecorder.start();
                    System.out.println("---开始录制---");
                    Toast.makeText(getApplicationContext(), "开始",
                            Toast.LENGTH_SHORT).show();
                    // 让start按钮不可用
                    start.setEnabled(false);
                    // 让stop按钮可用
                    stop.setEnabled(true);
                    isRecording = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stop.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (isRecording) {
                    // 停止录制
                    mRecorder.stop();
                    // 释放资源
                    mRecorder.release();
                    mRecorder = null;
                    // 让start按钮可用
                    start.setEnabled(true);
                    // 让stop按钮不可用
                    stop.setEnabled(false);
                    isRecording = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

    }

    // @SuppressLint("NewApi")
    // public void surfaceCreated(SurfaceHolder holder) {
    // 
    // // 开启相机
    // if (mCamera == null)
    // {
    // int CammeraIndex=FindFrontCamera();
    // if(CammeraIndex==-1)
    // {
    // //ToastUtil.TextToast(getApplicationContext(), "您的手机不支持前置摄像头",
    // ToastUtil.LENGTH_SHORT);
    // CammeraIndex=FindBackCamera();
    // }
    // mCamera = Camera.open(CammeraIndex);
    // try
    // {
    // mCamera.setPreviewDisplay(sView.getHolder());
    // mCamera.setDisplayOrientation(90);
    // }
    // catch (IOException e)
    // {
    // e.printStackTrace();
    // mCamera.release();
    // }
    // }
    // }
    //
    // public void surfaceChanged(SurfaceHolder holder, int format, int width,
    // int height) {
    // 
    // // 开始预览
    // mCamera.startPreview();
    // }
    //
    // public void surfaceDestroyed(SurfaceHolder holder) {
    // 
    // // 关闭预览并释放资源
    // if (mCamera != null) {
    // mCamera.stopPreview();
    // mCamera.release();
    // mCamera = null;
    // }
    // }
    @SuppressLint("NewApi")
    private int FindFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    // 判断后置摄像头是否存在
    @SuppressLint("NewApi")
    private int FindBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }
}
