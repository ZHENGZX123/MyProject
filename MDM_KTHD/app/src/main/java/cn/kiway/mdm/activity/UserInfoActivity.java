package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.App;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.UploadUtil;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.activity.QuestionActivity.SELECT_PHOTO;
import static cn.kiway.mdm.utils.HttpUtil.uploadFile;
import static studentsession.kiway.cn.mdm_studentsession.R.id.userPic;

/**
 * Created by Administrator on 2017/12/15.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    TextView account, userName, versionCode;
    ImageView pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView1();
    }

    void initView1() {
        account = (TextView) findViewById(R.id.account);
        userName = (TextView) findViewById(R.id.userName);
        versionCode = (TextView) findViewById(R.id.versionCode);
        pic = (ImageView) findViewById(R.id.pic);
        pic.setOnClickListener(this);
        if (!getSharedPreferences("kiway", 0).getString("userUrl", "").equals(""))
            ImageLoader.getInstance().displayImage(getSharedPreferences("kiway", 0).getString("userUrl", ""),
                    pic, App.getLoaderOptions());
        account.setText("学号：" + getSharedPreferences("kiwaykthd", 0).getString("studentNumber", ""));
        userName.setText("姓名:" + getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
        versionCode.setText(Utils.getCurrentVersion(this));
    }

    public void Before(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic:
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
                imagePicker.setSelectLimit(1);// 设置可以选择几张
                imagePicker.setMultiMode(false);// 是否为多选
                imagePicker.setCrop(true);// 是否剪裁
                imagePicker.setFocusWidth(1000);// 需要剪裁的宽
                imagePicker.setFocusHeight(1000);// 需要剪裁的高
                imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
                imagePicker.setShowCamera(true);// 是否显示摄像
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, SELECT_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null)
                return;
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                    .EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;
            ImageLoader.getInstance().displayImage("file://" + path, pic, App.getLoaderOptions());
            uploadPic(path, new File(path).getName());
        }
    }

    private void uploadPic(final String filepath, final String fileName) {
        new Thread() {
            @Override
            public void run() {
                final String ret = UploadUtil.uploadFile(filepath, uploadFile, fileName);
                if (TextUtils.isEmpty(ret)) {
                    toast("上传失败");
                    return;
                }
                try {
                    JSONObject obj = new JSONObject(ret);
                    if (obj.optInt("statusCode") != 200) {
                        toast("上传失败");
                        return;
                    }
                    Logger.log("::::::::" + obj);
                    final String url = obj.optJSONObject("data").optString("url");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            App.instance.uploadUserInfo(url,null);
                        }
                    });

                    ImageLoader.getInstance().displayImage(url, pic, App.getLoaderOptions());
                    getSharedPreferences("kiway", 0).edit().putString("userUrl",url).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
