package cn.kiway.mdm.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import cn.kiway.mdm.App;

import static cn.kiway.mdm.activity.QuestionActivity.SELECT_PHOTO;

/**
 * Created by Administrator on 2018/1/25.
 */

public class JsAndroidInterface2 {
    private Activity activity;

    public JsAndroidInterface2(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String setBackgroundImage() {
        Log.d("test", "setBackgroundImage is called");
        return "";///mnt/sdcard/test.jpg
    }

    @JavascriptInterface
    public void saveBase64(String param) {
        Log.d("test", "saveBase64 is called , param = " + param);
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(param.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            String filename = System.currentTimeMillis() + ".png";
            String filepath = App.ROOT + "image/" + filename;
            Utils.saveBitmap(bitmap, filename, App.ROOT + "image/");
            if (this.listener != null) {
                this.listener.onImage(filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void selectPhoto() {
        Log.d("test", "selectPhoto is called");
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


    private OnListener listener;

    public void setListener(OnListener l) {
        this.listener = l;
    }

    public interface OnListener {
        void onImage(String filepath);
    }


}
