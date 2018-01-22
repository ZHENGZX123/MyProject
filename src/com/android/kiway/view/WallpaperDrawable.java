package com.android.kiway.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/1/8.
 */

public class WallpaperDrawable extends Drawable {


    Bitmap mBitmap;
    int mIntrinsicWidth;
    int mIntrinsicHeight;

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        if (mBitmap == null)
            return;
        mIntrinsicWidth = mBitmap.getWidth();
        mIntrinsicHeight = mBitmap.getHeight();
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override

    public void draw(Canvas canvas) {

        if (mBitmap == null) return;

        int width = canvas.getWidth();

        int height = canvas.getHeight();
        int x = (width - mIntrinsicWidth) / 2;
        int y = (height - mIntrinsicHeight) / 2;
        canvas.drawBitmap(mBitmap, x, y, null);
    }

    @Override

    public int getOpacity() {

        return android.graphics.PixelFormat.OPAQUE;

    }

    @Override
    public void setAlpha(int alpha) {
        // Ignore
    }


    @Override
    public void setColorFilter(ColorFilter cf) {
        // Ignore
    }
}
