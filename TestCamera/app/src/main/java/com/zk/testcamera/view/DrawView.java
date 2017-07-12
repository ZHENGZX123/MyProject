package com.zk.testcamera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/12.
 */

public class DrawView extends View {

    private int currentPoint = 0;// 1 2 3 4

    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int x3;
    public int y3;
    public int x4;
    public int y4;
    public int width;
    public int height;
    public Bitmap bitmap;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(int width, int height, Bitmap bitmap) {
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;

        x1 = (int) (width * 0.1);
        y1 = (int) (height * 0.1);
        x2 = (int) (width * 0.9);
        y2 = (int) (height * 0.1);
        x3 = (int) (width * 0.9);
        y3 = (int) (height * 0.9);
        x4 = (int) (width * 0.1);
        y4 = (int) (height * 0.9);

        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setAntiAlias(true);

        //画图片，就是贴图
        canvas.drawBitmap(bitmap, 0, 0, p);

        //画圆
        canvas.drawCircle(x1, y1, 20, p);
        canvas.drawCircle(x2, y2, 20, p);
        canvas.drawCircle(x3, y3, 20, p);
        canvas.drawCircle(x4, y4, 20, p);


        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        Path path1 = new Path();
        path1.moveTo(x1, y1);
        path1.lineTo(x2, y2);
        path1.lineTo(x3, y3);
        path1.lineTo(x4, y4);
        path1.close();
        canvas.drawPath(path1, p);

    }

    public void doTouchDown(int x, int y) {
        checkInPoint(x, y);
    }

    public void doTouchUp(int x, int y) {
        currentPoint = 0;
    }

    public void doTouchMove(int x, int y) {
        if (x < 0 || y < 0 || x > width || y > height) {
            return;
        }
        if (currentPoint == 0) {
            return;
        }
        if (currentPoint == 1) {
            x1 = x;
            y1 = y;
        }
        if (currentPoint == 2) {
            x2 = x;
            y2 = y;
        }
        if (currentPoint == 3) {
            x3 = x;
            y3 = y;
        }
        if (currentPoint == 4) {
            x4 = x;
            y4 = y;
        }
        postInvalidate();
    }


    private void checkInPoint(int x, int y) {
        int d1 = (int) Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
        int d2 = (int) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        int d3 = (int) Math.sqrt((x - x3) * (x - x3) + (y - y3) * (y - y3));
        int d4 = (int) Math.sqrt((x - x4) * (x - x4) + (y - y4) * (y - y4));
        Log.d("test", d1 + " , " + d2 + " , " + d3 + " , " + d4);
        if (d1 < 40) {
            currentPoint = 1;
        }
        if (d2 < 40) {
            currentPoint = 2;
        }
        if (d3 < 40) {
            currentPoint = 3;
        }
        if (d4 < 40) {
            currentPoint = 4;
        }
    }
}