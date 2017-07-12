package com.zk.testcamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
//没用了
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        File f = new File("/mnt/sdcard/mypicture/1499161198523.jpeg");

        Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());

        Log.d("test", "width = " + b.getWidth());
        Log.d("test", "height = " + b.getHeight());

        //左上角区域
        int x = b.getWidth() / 5;
        int y = b.getHeight() / 5;

        //逐行扫描，找出连续的黑色。。。
        int count = 0;
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                int color1 = b.getPixel(i, j);
                int r1 = ((color1 >> 16) & 0xFF);
                int g1 = ((color1 >> 8) & 0xFF);
                int b1 = ((color1) & 0xFF);
                Log.d("test", "[" + i + " , " + j + "]" + "的色值: " + r1 + " , " + g1 + " , " + b1);
                if (r1 + g1 + b1 < 60) {

                }
            }
        }


    }
}
