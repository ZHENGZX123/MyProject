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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
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
        int cameraWidth = mCamera.getParameters().getPreviewSize().width;
        int cameraHeight = mCamera.getParameters().getPreviewSize().height;
        Log.d("test", "cameraWidth = " + cameraWidth);
        Log.d("test", "cameraHeight = " + cameraHeight);
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {


            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
//                long current = System.currentTimeMillis();
//                if (current - last > 5000) {
//                    last = current;
                if (!snapshot) {
                    return;
                }

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

                Log.d("test", "bitmap width = " + bitmap.getWidth());

                Log.d("test", "bitmap height = " + bitmap.getHeight());

                //getcolor
                //getColor(bitmap, bitmap.getWidth(), bitmap.getHeight());

                savetoSdcard();
                snapshot = false;
            }
//            }
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

//            setResult(999, i);
//            finish();

        } catch (Exception e) {
            e.printStackTrace();
            toast("保存失败");
        }
    }

    protected void getColor(Bitmap bitmap, int width, int height) {
        int x1 = (int) (width * 0.0625);
        int y1 = (int) (height * 0.03);

        int x2 = (int) (width * 0.9375);
        int y2 = (int) (height * 0.03);

        int x3 = (int) (width * 0.0625);
        int y3 = (int) (height * 0.97);

        int x4 = (int) (width * 0.9375);
        int y4 = (int) (height * 0.97);

        Log.d("test", "x1 =" + x1 + " ,  y1 = " + y1);
        Log.d("test", "x2 =" + x2 + " ,  y2 = " + y2);
        Log.d("test", "x3 =" + x3 + " ,  y3 = " + y3);
        Log.d("test", "x4 =" + x4 + " ,  y4 = " + y4);

        int color1 = bitmap.getPixel(x1, y1);
        int r1 = ((color1 >> 16) & 0xFF);
        int g1 = ((color1 >> 8) & 0xFF);
        int b1 = ((color1) & 0xFF);
        Log.d("test", "左上角: " + r1 + " , " + g1 + " , " + b1);

        int color2 = bitmap.getPixel(x2, y2);
        int r2 = ((color2 >> 16) & 0xFF);
        int g2 = ((color2 >> 8) & 0xFF);
        int b2 = ((color2) & 0xFF);
        Log.d("test", "右上角: " + r2 + " , " + g2 + " , " + b2);

        int color3 = bitmap.getPixel(x3, y3);
        int r3 = ((color3 >> 16) & 0xFF);
        int g3 = ((color3 >> 8) & 0xFF);
        int b3 = ((color3) & 0xFF);
        Log.d("test", "左下角: " + r3 + " , " + g3 + " , " + b3);

        int color4 = bitmap.getPixel(x4, y4);
        int r4 = ((color4 >> 16) & 0xFF);
        int g4 = ((color4 >> 8) & 0xFF);
        int b4 = ((color4) & 0xFF);
        Log.d("test", "右下角: " + r4 + " , " + g4 + " , " + b4);
    }

    public void clickSnapshot(View v) {
//        mCamera.autoFocus(null);
//        mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);

        snapshot = true;

        //snapshot
//        mCamera.autoFocus(new Camera.AutoFocusCallback() {
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                if (success) {
//                    toast("拍照成功");
//                    savetoSdcard();
//                    snapshot = false;
//                } else {
//                    toast("聚焦不成功，请重试");
//                }
//            }
//        });

    }

    // define shutterCallback
    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            //TODO Do something when the shutter closes.
        }
    };
    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Do something with the image RAW data.
        }
    };
    // stroe the picture in format jpeg
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            // Save the image JPEG data to the SD card
            FileOutputStream outStream = null;
            try {        //修改图片路径和名称
                String tempFilename = String.valueOf(System.currentTimeMillis()) + ".jpeg";
                File path = new File("/mnt/sdcard/mypicture");
                if (!path.exists()) {
                    path.mkdir();
                }
                String filepath = path.getAbsolutePath() + File.separator + tempFilename;
                outStream = new FileOutputStream(filepath);
                outStream.write(data);
                outStream.flush();
                outStream.close();
                toast("拍照成功");

                Intent i = new Intent(CameraActivity.this, SetPointActivity.class);
                i.putExtra("snapshot", filepath);
                startActivity(i);

//                setResult(999, i);
//                finish();
            } catch (FileNotFoundException e) {
                Log.e("TAG", "File Note Found", e);
            } catch (IOException e) {
                Log.e("TAG", "IO Exception", e);
            }
        }
    };

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
