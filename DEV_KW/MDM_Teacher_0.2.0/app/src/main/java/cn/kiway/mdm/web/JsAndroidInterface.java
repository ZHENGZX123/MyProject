package cn.kiway.mdm.web;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.webkit.JavascriptInterface;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.view.X5WebView;

import static cn.kiway.mdm.WXApplication.url;
import static cn.kiway.mdm.activity.MainActivity.SELECT_PHOTO;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {
    MainActivity activity;

    public JsAndroidInterface(MainActivity activity, X5WebView webView) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void setScreenOrientation(String orientation) {//设置横竖
        if (orientation.equals("0"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation.equals("1"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @JavascriptInterface
    public String getPlatform() {
        return "Android";
    }

    @JavascriptInterface
    public String getHost() {
        return url;
    }

    @JavascriptInterface
    public void selectPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
        imagePicker.setSelectLimit(1);// 设置可以选择几张
        imagePicker.setMultiMode(false);// 是否为多选
        imagePicker.setCrop(true);// 是否剪裁
        imagePicker.setFocusWidth(1000);// 需要剪裁的宽
        imagePicker.setFocusHeight(1000);// 需要剪裁的高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
        imagePicker.setShowCamera(true);// 是否显示摄像

        Intent intent = new Intent(this.activity, ImageGridActivity.class);
        this.activity.startActivityForResult(intent, SELECT_PHOTO);
    }




}
