package com.alibaba.weex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;

public class MyImagePicker extends WXModule {

    private JSCallback callback;

    @JSMethod(uiThread = true)
    public void showPicker(JSCallback callback) {
        this.callback = callback;

        //imagepicker
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
        imagePicker.setSelectLimit(9);// 设置可以选择几张
        imagePicker.setMultiMode(true);// 是否为多选
        imagePicker.setCrop(true);// 是否剪裁
        imagePicker.setFocusWidth(1000);// 需要剪裁的宽
        imagePicker.setFocusHeight(1000);// 需要剪裁的高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
        imagePicker.setShowCamera(true);// 是否显示摄像

        Intent intent = new Intent(mWXSDKInstance.getContext(), ImageGridActivity.class);
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, 888);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 888) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.d("test", "images count = " + images.size());

                callback.invoke(images.get(0).path);
            } else {
                Toast.makeText(mWXSDKInstance.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
