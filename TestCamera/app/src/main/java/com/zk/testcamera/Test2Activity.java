package com.zk.testcamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import static com.zk.testcamera.R.id.imageView1;

//没写好，不用了
public class Test2Activity extends AppCompatActivity {
    private ImageView iv1;
    private ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        iv1 = (ImageView) findViewById(imageView1);
        iv2 = (ImageView) findViewById(R.id.imageView2);


        Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/DCIM/Camera/IMG_20170710_162214.jpg");
        iv1.setImageBitmap(bitmap);

        int width = DensityUtil.dip2px(this, 140);
        int height = DensityUtil.dip2px(this, 180);
        Log.d("test", "width = " + width);
        Log.d("test", "height = " + height);

        int bw = width;
        int bh = height;
        float[] src = {bw * 129 / 583, bh * 53 / 760, bw * 500 / 583, bh * 73 / 760, bw, bh * 682 / 760, 0, 650 / 760 * bh};

        float[] dst = {0, 0, bw, 0, bw, bh, 0, bh};
        Matrix mMatrix = new Matrix();
        mMatrix.setPolyToPoly(src, 0, dst, 0, 4);


        Bitmap whiteBgBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(whiteBgBitmap);
        canvas.drawBitmap(bitmap, mMatrix, null);
        iv2.setImageBitmap(whiteBgBitmap);
    }
}
