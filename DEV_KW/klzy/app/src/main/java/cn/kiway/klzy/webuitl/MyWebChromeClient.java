package cn.kiway.klzy.webuitl;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.kiway.klzy.R;
import cn.kiway.klzy.WebViewActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MyWebChromeClient extends WebChromeClient implements EasyPermissions.PermissionCallbacks {

    private Uri cameraUri;
    WebViewActivity activity;
    boolean isAnimStart = false;
    int currentProgress;

    public MyWebChromeClient(WebViewActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        currentProgress = activity.mProgressBar.getProgress();
        if (newProgress >= 100 && !isAnimStart) {
            // 防止调用多次动画
            isAnimStart = true;
            activity.mProgressBar.setProgress(newProgress);
            // 开启属性动画让进度条平滑消失
            startDismissAnimation(activity.mProgressBar.getProgress());
        } else {
            // 开启属性动画让进度条平滑递增
            startProgressAnimation(newProgress);
        }
    }

    // For Android 3.0+ 选择本地文件 各个版本的系统支持
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (activity.mUploadMessage != null) return;
        activity.mUploadMessage = uploadMsg;
        selectImage();
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams
            fileChooserParams) {
        if (activity.mUploadMessagesAboveL != null) {
            activity.mUploadMessagesAboveL.onReceiveValue(null);
        } else {
            activity.mUploadMessagesAboveL = filePathCallback;
            selectImage();
        }
        return true;
    }

    private void selectImage() {
        if (!FileUtils.checkSDcard(activity)) {
            return;
        }
        String[] selectPicTypeStr = {"拍照", "图库"};
        ReOnCancelListener reOnCancelListener = new ReOnCancelListener();
        new AlertDialog.Builder(activity)
                .setOnCancelListener(reOnCancelListener)
                .setOnDismissListener(reOnCancelListener)
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // 相机拍摄
                                    case 0:
                                        openCarcme();
                                        break;
                                    // 手机相册
                                    case 1:
                                        chosePicture();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();
    }


    /**
     * 本地相册选择图片
     */
    private void chosePicture() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            choosePic();
        } else {
            EasyPermissions.requestPermissions(activity, "需要摄像头和读取sd卡权限",
                    HanderMessageWhat.ResultMessage999, perms);
        }
    }

    /**
     * 打开照相机
     */
    private void openCarcme() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            openCramera();
        } else {
            EasyPermissions.requestPermissions(activity, "拍照需要摄像头和读取sd卡权限",
                    HanderMessageWhat.ResultMessage888, perms);
        }
    }

    private void choosePic() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        activity.startActivityForResult(wrapperIntent, HanderMessageWhat.ResultMessage999);
    }

    private void openCramera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(FileUtils.createImgFloder(), System.currentTimeMillis() + ".png");
        if (vFile.exists()) {
            vFile.delete();
        }
        cameraUri = Uri.fromFile(vFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        activity.startActivityForResult(intent, HanderMessageWhat.ResultMessage888);
    }

    /**
     * dialog监听类
     */
    private class ReOnCancelListener implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (activity.mUploadMessage != null) {
                activity.mUploadMessage.onReceiveValue(null);
                activity.mUploadMessage = null;
            }

            if (activity.mUploadMessagesAboveL != null) {
                activity.mUploadMessagesAboveL.onReceiveValue(null);
                activity.mUploadMessagesAboveL = null;
            }
        }

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (activity.mUploadMessage != null) {
                activity.mUploadMessage.onReceiveValue(null);
                activity.mUploadMessage = null;
            }

            if (activity.mUploadMessagesAboveL != null) {
                activity.mUploadMessagesAboveL.onReceiveValue(null);
                activity.mUploadMessagesAboveL = null;
            }
        }
    }

    /**
     * 选择照片后结束
     *
     * @param data
     */
    public static Uri afterChosePic(WebViewActivity activity, Intent data) {
        if (data != null) {
            final String path = data.getData().getPath();
            if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") || path
                    .endsWith(".JPG"))) {
                return data.getData();
            } else {
                Toast.makeText(activity, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    /**
     * 5.0以后机型 返回文件选择
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResultAboveL(WebViewActivity activity, int requestCode, int resultCode, Intent data) {
        Uri[] results = null;
        if (requestCode == HanderMessageWhat.ResultMessage888 && resultCode == RESULT_OK) {
            results = new Uri[]{Uri.parse("")};
        }
        if (requestCode == HanderMessageWhat.ResultMessage999 && resultCode == RESULT_OK) {
            if (data != null) {
                String dataString = data.getDataString();
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        activity.mUploadMessagesAboveL.onReceiveValue(results);
        activity.mUploadMessagesAboveL = null;
        return;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("-----------", perms.toString());
        switch (requestCode) {
            case HanderMessageWhat.ResultMessage888:
                openCramera();
                break;
            case HanderMessageWhat.ResultMessage999:
                choosePic();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(activity,
                "为了您的正常使用，快乐作业需要获得相机权限，请您进去设置->应用->权限进行设置",
                R.string.setting, R.string.cancle, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(activity.mProgressBar, "progress", currentProgress,
                newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(activity.mProgressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速
        // 关键, 添加动画进度监听器
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();      // 0.0f ~ 1.0f
                int offset = 100 - progress;
                activity.mProgressBar.setProgress((int) (progress + offset * fraction));
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                activity.mProgressBar.setProgress(0);
                activity.mProgressBar.setVisibility(View.GONE);
                isAnimStart = false;
            }
        });
        anim.start();
    }
}