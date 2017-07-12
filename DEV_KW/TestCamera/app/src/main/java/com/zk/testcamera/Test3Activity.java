package com.zk.testcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

public class Test3Activity extends AppCompatActivity {
    private int screenwidth;
    private int screenheight;
    private MyView v;
    private Bitmap bitmap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenwidth = wm.getDefaultDisplay().getWidth();
        screenheight = wm.getDefaultDisplay().getHeight();
        bitmap = BitmapFactory.decodeFile("/mnt/sdcard/DCIM/Camera/IMG_20170710_162214.jpg");

        //initview
        v = new MyView(this);
        setContentView(v);
    }

    class MyView extends View {


        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Log.d("test", "width = " + width);
            Log.d("test", "height = " + height);


            float[] src = new float[]{
                    width * 129 / 583, height * 53 / 760,//左上角顶点。。。
                    width * 508 / 583, height * 77 / 760,//右上
                    width, height * 682 / 760,//右下
                    0, height * 650 / 760
            };

            float[] dst = new float[]{0, 0,         //左上
                    screenwidth, 0,//右上
                    screenwidth, screenheight,//右下
                    0, screenheight};//左下

            Matrix mMatrix = new Matrix();
            mMatrix.setPolyToPoly(src, 0, dst, 0, 4);
            canvas.drawBitmap(bitmap, mMatrix, null);


//            if (first) {
//                first = false;
//                screenshot();
//            }
        }

        private boolean first = true;

        private void screenshot() {
            // 获取屏幕
            View dView = v; //getRootView();//getWindow().getDecorView();
            dView.setDrawingCacheEnabled(true);
            dView.buildDrawingCache();
            Bitmap bmp = dView.getDrawingCache();
            String name = "xxx.png";
            String filePath = "/mnt/sdcard/" + name;
            if (bmp != null) {
                try {
                    // 图片文件路径
                    File file = new File(filePath);
                    FileOutputStream os = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //            Path path = new Path();
//            path.moveTo(0, 0);
//            path.lineTo(screenwidth, 0);
//            path.lineTo(screenwidth, screenheight);
//            path.lineTo(0, screenheight);
//            path.lineTo(0, 0);
//            canvas.clipPath(path);
//            canvas.drawPath(path, new Paint());
//            canvas.save(Canvas.ALL_SAVE_FLAG);
//            canvas.restore();
//            try {
//                saveBitmapToJPG(bitmap, new File("/mnt/sdcard/aaa2.jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            try {
//                Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                        bitmap.getHeight(), mMatrix, true);
//                saveBitmapToJPG(dstbmp, new File("/mnt/sdcard/aaa.jpg"));
//
//                Bitmap b = Bitmap.createBitmap(dstbmp, width * 129 / 583, height * 53 / 760, screenwidth, screenheight);
//                saveBitmapToJPG(b, new File("/mnt/sdcard/aaa2.jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
//            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(newBitmap);
//            canvas.drawColor(Color.WHITE);
//            canvas.drawBitmap(bitmap, 0, 0, null);
//            OutputStream stream = new FileOutputStream(photo);
//            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
//            stream.close();
//        }
    }
}
