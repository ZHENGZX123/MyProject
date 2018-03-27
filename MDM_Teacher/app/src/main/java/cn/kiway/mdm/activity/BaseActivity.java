package cn.kiway.mdm.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;
import com.xyzlf.share.library.interfaces.ShareConstant;

import java.io.File;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.ShareCallBack;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.RoundedImageView;
import cn.kiway.mdm.view.popup.PopCommon;
import cn.kiway.mdm.view.popup.PopModel;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.rtc.Constants;
import ly.count.android.api.Countly;

import static cn.kiway.mdm.KWApplication.recordService;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_CHAPING;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_JINGYIN;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_SUOPING;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_WENJIAN;
import static cn.kiway.mdm.util.Constant.tuiping;
import static cn.kiway.mdm.util.ResultMessage.RECORD_REQUEST_CODE;
import static cn.kiway.mdm.web.JsAndroidInterface.REQUEST_ORIGINAL;
import static cn.kiway.mdm.web.JsAndroidInterface.picPath;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends ScreenSharingActivity implements View.OnClickListener {

    public TextView teacherName;
    public RoundedImageView teacherIcon;
    public TextView titleName;
    public ImageButton videoBtn;

    public RelativeLayout toolsRL;
    public ProgressDialog pd;
    private RelativeLayout retryRL;
    public DisplayMetrics displaysMetrics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        displaysMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
    }

    @Override
    protected void onStart() {
        super.onStart();
        KWApplication.currentActivity = this;
        Countly.sharedInstance().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Countly.sharedInstance().onStop();
    }

    public void initView() {
        titleName = (TextView) findViewById(R.id.titleName);
        teacherName = (TextView) findViewById(R.id.teacherName);
        teacherIcon = (RoundedImageView) findViewById(R.id.teacherIcon);

        String url = getSharedPreferences("kiway", 0).getString("teacherAvatar", "");
        if (teacherIcon != null && !TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, teacherIcon, KWApplication.getLoaderOptions());
        }

        videoBtn = (ImageButton) findViewById(R.id.video);
        toolsRL = (RelativeLayout) findViewById(R.id.toolsRL);

        retryRL = (RelativeLayout) findViewById(R.id.retryRL);
    }

    public void showPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.show();
            }
        });
    }

    public void hidePD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    public void clickBack(View view) {
        finish();
    }

    public void clickRetry(View view) {

    }

    public void showRetry() {
        retryRL.setVisibility(View.VISIBLE);
    }

    public void hideRetry() {
        retryRL.setVisibility(View.GONE);
    }

    public void startPlayer(String roomName) {
        //1.开始播放学生推屏
        Intent i = new Intent(BaseActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, roomName);
        startActivity(i);
    }

    public void stopPlayer() {
        if (LiveRoomActivity.instance == null) {
            return;
        }
        LiveRoomActivity.instance.finish();
    }

    public void showMenuPop(View menuView) {
        final List<PopModel> list = Utils.getPopList();
        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0://截屏
                        Countly.sharedInstance().recordEvent("截屏");
                        String filePath = Utils.GetandSaveCurrentImage(BaseActivity.this);
                        if (!filePath.equals("")) {
                            if (Utils.isImage(filePath)) {
                                cropImage(filePath);
                            }
                        }
                        break;
                    case 1://拍照
                        Countly.sharedInstance().recordEvent("拍照");
                        picPath = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri = Uri.fromFile(new File(picPath));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_ORIGINAL);
                        break;
                    case 2://录课
                        if (RecordService.recording)
                            b_stopRecord();
                        else
                            b_startRecord();
                        break;
                    case 3://推屏
                        b_tuiping();
                        break;
                    case 4://画笔
                        Countly.sharedInstance().recordEvent("画笔");
                        startActivity(new Intent(BaseActivity.this, WhiteBoardActivity.class));
                        break;
                    case 5://查屏
                        b_chaping();
                        break;
                    case 6://锁屏
                        //锁定学生屏幕，需要获取学生列表。
                        startActivity(new Intent(BaseActivity.this, StudentGridActivity.class).putExtra("type",
                                TYPE_SUOPING));
                        break;
                    case 7://静音
                        //锁定学生屏幕，需要获取学生列表。
                        startActivity(new Intent(BaseActivity.this, StudentGridActivity.class).putExtra("type",
                                TYPE_JINGYIN));
                        break;
                    case 8://文件
                        //1.先选择一个文件
                        startActivity(new Intent(BaseActivity.this, StudentGridActivity.class).putExtra("type",
                                TYPE_WENJIAN));
                        break;
                    case 9://设置
                        break;
                }
            }


            @Override
            public void onDismiss() {

            }
        });
        popCommon.showPop(menuView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 分享回调处理
         */
        if (requestCode == ShareConstant.REQUEST_CODE) {
            if (data != null) {
                int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                new ShareCallBack().onShareCallback(channel, status);
            }
        } if (requestCode == Crop.REQUEST_CROP) {
            final Uri resultUri = Crop.getOutput(data);
            if (resultUri != null)
                sendFile(resultUri.getPath());
        } else if (requestCode == RECORD_REQUEST_CODE) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            boolean start = KWApplication.recordService.startRecord();
            if (!start) {
                toast("该手机不支持录屏");
                return;
            }
            toast("开始录制本地视频");
            RecordService.recording = true;
            String temp = KWApplication.recordService.output;
            if (BaseActivity.this instanceof Course0Activity) {
                ((Course0Activity) BaseActivity.this).recordScreenCall(temp);
            }
        } else if (requestCode == REQUEST_ORIGINAL) {
            cropImage(picPath);
        }
    }
    public void sendFile(String filePath) {
        toast(R.string.chooseStudent);
        //2.再选择学生
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_WENJIAN).putExtra("filePath",
                filePath));
    }
    public void cropImage(String filePath) {
        Log.d("test", "cropImage filePath = " + filePath);
        //需要裁剪的图片路径
        Uri sourceUri = Uri.fromFile(new File(filePath));
        //裁剪完毕的图片存放路径
        Uri destinationUri = Uri.fromFile(new File(filePath.split("\\.")[0] + "1." + filePath.split("\\.")[1]));
        Crop.of(sourceUri, destinationUri) //定义路径
                .start(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    //-------------------------------录屏相关-----------------------------
    public MediaProjectionManager projectionManager;
    public MediaProjection mediaProjection;

    public void b_startRecord() {
        //1.判断SD卡空间
        if (Utils.getRemainingSDSize(this) < 1024 * 1024 * 500) {
            toast("SD卡剩余空间不足500M，无法录制本地视频");
            return;
        }
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        //2.申请录制权限
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void b_stopRecord() {
        if (recordService.isRunning()) {
            recordService.stopRecord();
        }
    }


    //-------------------------------推屏相关-----------------------------
    public void b_chaping() {
        if (tuiping) {
            toast("请先停止推屏");
            return;
        }
        //查看学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_CHAPING));
    }

    public void b_tuiping() {    //1.发送推屏命令
        if (tuiping) {
            sendTuipingcommand(0);
        } else {
            sendTuipingcommand(1);
        }
    }

    public void startTuiping() {
        toast("开始推屏");
        initModules();
        startCapture();
        String userId = Utils.getIMEI(this);
        mRtcEngine.joinChannel(null, userId, "", 0);
    }

    public void endTuiping() {
        if (mRtcEngine == null) {
            return;
        }
        toast("结束推屏");
        mRtcEngine.leaveChannel();
        stopCapture();
        deInitModules();
    }

    public void sendTuipingcommand(final int status) {
        showPD();
        ZbusHost.tuiping(this, status, new OnListener() {

            @Override
            public void onSuccess() {
                hidePD();
                if (status == 1) {
                    tuiping = true;
                    if (BaseActivity.this instanceof Course0Activity) {
                        ((Course0Activity) BaseActivity.this).setTuipingIV(R.drawable.screen_control2);
                    }
                    startTuiping();
                } else {
                    tuiping = false;
                    if (BaseActivity.this instanceof Course0Activity) {
                        ((Course0Activity) BaseActivity.this).setTuipingIV(R.drawable.screen_control1);
                    }
                    endTuiping();
                }
            }

            @Override
            public void onFailure() {
                hidePD();
                toast("发送推屏命令失败");
            }
        });
    }

}
