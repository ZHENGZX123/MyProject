package com.zk.myweex.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qiniu.util.Auth;
import com.zk.myweex.activity.MainActivity2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Administrator on 2017/3/17.
 */

public class Utils {

    public static String getQiniuToken() {
        String accessKey = "SQWC77eBEVRT_5_3XntqEBfL1SDgwxluAaZxRnKZ";
        String secretKey = "HBfVSA1kPjvzD6m92imabqUo4w8Uqna7AJV9MMgl";
        String bucket = "kiway";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

    public static void checkNetWork(Context context) {
        //获取手机的连接服务管理器，这里是连接管理器类
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            Message msg = new Message();
            if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
//            String type = getCurrentNetworkType(context);
                msg.arg1 = 1;
                msg.obj = "GPRS网络连接！";
            } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                msg.arg1 = 2;
                msg.obj = "WIFI连接！";
            } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                msg.arg1 = 0;
                msg.obj = "手机没有任何网络...";
            }
            MainActivity2.main.mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap createQRImage(String url, final int width, final int height) {
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

    public static String saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File("/mnt/sdcard/" + bitName + ".jpg");
        if (f.exists()) {
            f.delete();
        }
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
        return filepath;
    }
}
