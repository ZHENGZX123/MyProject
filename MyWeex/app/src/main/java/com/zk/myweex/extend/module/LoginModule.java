package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.activity.MainActivity2;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Administrator on 2017/3/10.
 */

//SJevent
public class LoginModule extends WXModule {

    @JSMethod(uiThread = true)
    public void loginSuccess(String url) {
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity2.class));
        ((Activity) mWXSDKInstance.getContext()).finish();
    }

    @JSMethod(uiThread = true)
    public void loginFailure() {
        Toast.makeText(mWXSDKInstance.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
    }

    @JSMethod(uiThread = true)
    public void makeQRCode(String dic, JSCallback callback) {
        Log.d("test", "makeQRCode = " + dic);
        //生成一个二维码，保存在本地，把路径callback回去。
        try {
            JSONObject obj = new JSONObject(dic);
            String classId = obj.getString("classId");
            String className = obj.getString("className");
            String schoolId = obj.getString("schoolId");


            Bitmap b = createQRImage("hello", 400, 400);
            saveMyBitmap(b, "myweex", callback);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void PostSigalImg(String url, JSCallback callback) {
        Log.d("test", "PostSigalImg url = " + url);
        //上传图片，怎么调用起来。。。

    }

    @JSMethod(uiThread = true)
    public void goChatView() {

    }


    public Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveMyBitmap(Bitmap mBitmap, String bitName, JSCallback callback) {
        File f = new File(Environment.getExternalStorageDirectory() + "/"
                + bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filepath = f.getAbsolutePath();

        Log.d("test", "filepath = " + filepath);
        callback.invoke(filepath);
    }


}
