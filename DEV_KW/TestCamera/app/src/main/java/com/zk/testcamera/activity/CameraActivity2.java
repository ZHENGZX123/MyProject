package com.zk.testcamera.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zk.testcamera.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity2 extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {
    private SurfaceView camera_sf;
    private Button camera_btn;
    //安卓硬件相机
    private Camera mCamera;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mCamera) {
            mCamera = getCustomCamera();
            if (mHolder != null) {
                //开户相机预览
                previceCamera(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    private void initViews() {
        this.camera_sf = (SurfaceView) findViewById(R.id.camera_sf);
        this.camera_btn = (Button) findViewById(R.id.camera_btn);
        this.camera_btn.setOnClickListener(this);
        camera_sf.setOnClickListener(this);
        //获取SurfaceView的SurfaceHolder对象
        mHolder = camera_sf.getHolder();
        //实现SurfaceHolder.Callback接口
        mHolder.addCallback(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_sf://点击可以对焦
                if (null != mCamera)
                    mCamera.autoFocus(null);
                break;
            case R.id.camera_btn://点击进行拍照
                startTakephoto();
                break;
        }
    }

    private void startTakephoto() {
        //获取到相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        //设置图片保存格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //设置图片大小
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        parameters.setPreviewSize(screenwidth, screenheight);//480, 720
        mCamera.setParameters(parameters);
        //设置自动对焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            dealWithCameraData(data);
                        }
                    });
                }
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {
        try {
            //1.保存图片
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            //2.通过Matrix把图片旋转90度
            matrix.setRotate(90);

            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int screenwidth = wm.getDefaultDisplay().getWidth();
            int screenheight = wm.getDefaultDisplay().getHeight();
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            //3.保存
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

            Intent intent = new Intent(CameraActivity2.this, SetPointActivity.class);
            intent.putExtra("snapshot", filePath);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        previceCamera(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        previceCamera(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera getCustomCamera() {
        if (null == mCamera) {
            //使用Camera的Open函数开机摄像头硬件
            mCamera = Camera.open();
            //Camera.open()方法说明：2.3以后支持多摄像头，所以开启前可以通过getNumberOfCameras先获取摄像头数目，
            // 再通过 getCameraInfo得到需要开启的摄像头id，然后传入Open函数开启摄像头，
            // 假如摄像头开启成功则返回一个Camera对象
        }
        return mCamera;
    }

    private void previceCamera(Camera camera, SurfaceHolder holder) {
        try {
            //摄像头设置SurfaceHolder对象，把摄像头与SurfaceHolder进行绑定
            camera.setPreviewDisplay(holder);
            //调整系统相机拍照角度
            camera.setDisplayOrientation(90);
            //调用相机预览功能
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}