package com.zk.testcamera.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.zk.testcamera.R;
import com.zk.testcamera.view.MatrixView;

import java.io.File;
import java.io.FileOutputStream;

public class Test4Activity extends AppCompatActivity {

    private MatrixView v;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);
        v = (MatrixView) findViewById(R.id.v);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int screenwidth = wm.getDefaultDisplay().getWidth();
        int screenheight = wm.getDefaultDisplay().getHeight();
        String file = getIntent().getStringExtra("snapshot");
        Bitmap bitmap = BitmapFactory.decodeFile(file);

        //initview
        float x1 = getIntent().getFloatExtra("x1", 0.1f);
        float y1 = getIntent().getFloatExtra("y1", 0.1f);
        float x2 = getIntent().getFloatExtra("x2", 0.9f);
        float y2 = getIntent().getFloatExtra("y2", 0.1f);
        float x3 = getIntent().getFloatExtra("x3", 0.9f);
        float y3 = getIntent().getFloatExtra("y3", 0.9f);
        float x4 = getIntent().getFloatExtra("x4", 0.1f);
        float y4 = getIntent().getFloatExtra("y4", 0.9f);

        v.setParam(screenwidth, screenheight, bitmap, x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public void clickOK(View view) {
        // 获取屏幕
        View dView = v; //getRootView();//getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        String name = "xxx.jpg";
        String filePath = "/mnt/sdcard/" + name;
        if (bmp != null) {
            try {
                // 图片文件路径
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
