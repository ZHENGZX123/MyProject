package com.zk.testcamera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends Activity implements Callback {

    private SurfaceView mSurfaceView1;
    private SurfaceHolder mSurfaceHolder1;
    private Camera mCamera;
    private boolean snapshot;
    private long last = 0;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();

    }

    private void initView() {

        mSurfaceView1 = (SurfaceView) this.findViewById(R.id.surface1);

        mSurfaceHolder1 = mSurfaceView1.getHolder();
        mSurfaceHolder1.addCallback(this);
        mSurfaceHolder1.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mSurfaceView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "autofocus");
                mCamera.autoFocus(null);
            }
        });

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        Log.d("test", "screenWidth = " + screenWidth);
        Log.d("test", "screenHeight = " + screenHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("test", "surfaceCreated");
        if (mCamera == null) {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        mCamera = Camera.open(camIdx);
                    } catch (RuntimeException e) {

                    }
                }
            }
        }
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private Bitmap bitmap;
    private Matrix matrix;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera == null) {
            toast("打开摄像头失败");
            return;
        }
        mCamera.setDisplayOrientation(90);
        Camera.Parameters mParameters = mCamera.getParameters();
//         List<Camera.Size> s = mParameters.getSupportedPreviewSizes();
//         for (int i = 0; i < s.size(); i++) {
//         Log.d("test", "width = " + s.get(i).width);
//         Log.d("test", "height = " + s.get(i).height);
//         }
//        mParameters.setPreviewSize(320, 320);
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(mParameters);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception ex) {
        }
        mCamera.startPreview();
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (!snapshot) {
                    return;
                }
                //拍出来是1080*1920，屏幕大小只有1080*1776
                int width = camera.getParameters().getPreviewSize().width;
                int height = camera.getParameters().getPreviewSize().height;
                YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,
                        width, height, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvimage.compressToJpeg(new Rect(0, 0, width, height), 100,
                        baos);
                data = baos.toByteArray();
                bitmap = BitmapFactory
                        .decodeByteArray(data, 0, data.length);
                //rotate90
                matrix = new Matrix();
                matrix.setRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, screenHeight, screenWidth,
                        matrix, true);
                savetoSdcard();
                snapshot = false;
            }
        });
    }

    protected void savetoSdcard() {
        try {
            String name = System.currentTimeMillis() + ".jpeg";
            File path = new File("/mnt/sdcard/mypicture");
            if (!path.exists()) {
                path.mkdir();
            }
            String filePath = "/mnt/sdcard/mypicture/" + name;
            File saveFile = new File(filePath);
            saveFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            if (fileOutputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            toast("保存成功");

            Intent i = new Intent(CameraActivity.this, SetPointActivity.class);
            i.putExtra("snapshot", filePath);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
            toast("保存失败");
        }
    }

    public void clickSnapshot(View v) {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    snapshot = true;
                } else {
                    toast("对焦失败，请重试");
                }
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
