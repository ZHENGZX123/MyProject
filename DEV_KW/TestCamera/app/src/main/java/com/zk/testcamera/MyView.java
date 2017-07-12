package com.zk.testcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyView extends View {
    private int screenwidth;
    private int screenheight;
    private Bitmap bitmap;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private float x4;
    private float y4;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("test", "width = " + width);
        Log.d("test", "height = " + height);

        float[] src = new float[]{
                width * x1, height * y1,//左上角顶点。。。
                width * x2, height * y2,//右上
                width * x3, height * y3,//右下
                width * x4, height * y4
        };

        float[] dst = new float[]{0, 0,         //左上
                screenwidth, 0,//右上
                screenwidth, screenheight,//右下
                0, screenheight};//左下

        Matrix mMatrix = new Matrix();
        mMatrix.setPolyToPoly(src, 0, dst, 0, 4);
        canvas.drawBitmap(bitmap, mMatrix, null);
    }

    public void setParam(int screenwidth, int screenheight, Bitmap bitmap, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        this.screenwidth = screenwidth;
        this.screenheight = screenheight;
        this.bitmap = bitmap;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        postInvalidate();
    }
}
