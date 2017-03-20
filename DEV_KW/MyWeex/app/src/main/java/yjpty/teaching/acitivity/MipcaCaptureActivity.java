package yjpty.teaching.acitivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;
import com.zbar.lib.decode.InactivityTimer;
import com.zbar.lib.decode.RGBLuminanceSource;
import com.zk.myweex.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import yjpty.teaching.dialog.ClearDataDialog;
import yjpty.teaching.dialog.LoginDialog;
import yjpty.teaching.dialog.NewVersionDialog;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.CRequest;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.SharedPreferencesUtil;
import yjpty.teaching.util.StringUtil;
import yjpty.teaching.wifi.WifiAdmin;

/**
 * 描述: 扫描界面
 */
public class MipcaCaptureActivity extends BaseActivity implements
        Callback, NewVersionDialog.NewVersionCallBack, ClearDataDialog.ClearDataCallBack {

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private boolean vibrate;
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;
    private int cropHeight = 0;
    private RelativeLayout mContainer = null;
    private RelativeLayout mCropLayout = null;
    private boolean isNeedCapture = false;
    NewVersionDialog dialog;

    private ProgressDialog mProgress;// 解析过程的dialgo
    private String photo_path;// 图像地址
    private Bitmap scanBitmap;
    private static final int PARSE_BARCODE_SUC = 300;// 解析成功
    private static final int PARSE_BARCODE_FAIL = 303;// 解析失败
    ClearDataDialog cleanDialog;
    LoginDialog loginDialog;

    public boolean isNeedCapture() {
        return isNeedCapture;
    }

    public void setNeedCapture(boolean isNeedCapture) {
        this.isNeedCapture = isNeedCapture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_qr_scan);
        // 初始化 CameraManager
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
        mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
        ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
        TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
                0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.85f);
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mQrLineView.setAnimation(mAnimation);
        findViewById(R.id.for_photos).setOnClickListener(this);
        dialog = new NewVersionDialog(this, this);
        if (bundle.getInt(IConstant.BUNDLE_PARAMS) == 1) // 1为上课动作
            findViewById(R.id.for_photos).setVisibility(View.GONE);
    }

    boolean flag = true;

    protected void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            flag = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.for_photos:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,
                        IConstant.FOR_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IConstant.FOR_PHOTO:
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(),
                            null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();
                    mProgress = new ProgressDialog(MipcaCaptureActivity.this);
                    mProgress.setMessage("扫描中...");
                    mProgress.setCancelable(false);
                    mProgress.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Result result = scanningImage(photo_path);
                            if (result != null) {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_SUC;
                                m.obj = result.getText();
                                mHandler.sendMessage(m);
                            } else {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_FAIL;
                                m.obj ="二维码错误";
                                mHandler.sendMessage(m);
                            }
                        }
                    }).start();
                    break;
            }
        }
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path))
            return null;
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {// 图片扫描处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgress.dismiss();
            switch (msg.what) {
                case PARSE_BARCODE_SUC:
                    try {
                        analyzeQR((String) msg.obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PARSE_BARCODE_FAIL:
                    dialog.setTitle((String) msg.obj);
                    if (dialog != null)
                        dialog.show();
                    break;
            }
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (loginDialog != null && loginDialog.isShowing())
            loginDialog.close();
        CameraManager.get().closeDriver();
        //finish();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    public void handleDecode(String result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        try {
            analyzeQR(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            int width = point.y;
            int height = point.x;

            int x = mCropLayout.getLeft() * width / mContainer.getWidth();
            int y = mCropLayout.getTop() * height / mContainer.getHeight();

            int cropWidth = mCropLayout.getWidth() * width
                    / mContainer.getWidth();
            int cropHeight = mCropLayout.getHeight() * height
                    / mContainer.getHeight();

            setX(x);
            setY(y);
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
            // 设置是否需要截图
            setNeedCapture(true);

        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(MipcaCaptureActivity.this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public Handler getHandler() {
        return handler;
    }

    private void playBeepSoundAndVibrate() {
        if (vibrate) {
            AppUtil.Vibrate(this, 100);
        }
    }

    String classId, schoolId, className, hCode, isHot, ip;
    boolean isChangHeziCode = false;

    // 解析二维码
    void analyzeQR(String result) throws Exception {
        if (result.length() > 10)
            ip = result.split("\\?")[0].substring(7,
                    result.split("\\?")[0].length() - 3);
        Map<String, String> map = new HashMap<>();
        Map<String, String> mapRequest = CRequest.URLRequest(result);
        String str = mapRequest.get("ref");
        if (str == null) {
            dialog.setTitle(result);
            if (dialog != null)
                dialog.show();
            return;
        }
        if (str.equals("box")) {// 上课
            hCode = mapRequest.get("cid");
            isHot = mapRequest.get("ishot");
            SharedPreferencesUtil.save(this, IConstant.WIFI_NEME
                    + app.classModels.get(app.getClassPosition()).getId(), mapRequest.get("ssid"));
            if (mapRequest.get("resoures").indexOf(StringUtil.getResourse(this, app.classModels.get(app
                    .getClassPosition()).getYear())) < 0) {
                dialog.setTitle("盒子资源不一致");
                if (dialog != null)
                    dialog.show();
                return;
            }
            if (app.classModels.get(app.getClassPosition()).getHezi_code() != null
                    && !"".equals(app.classModels.get(app.getClassPosition()).getHezi_code())
                    && !hCode.equals(app.classModels.get(app.getClassPosition()).getHezi_code())) {
                dialog.setTitle("是否更换盒子");
                if (dialog != null)
                    dialog.show();
                isChangHeziCode = true;
                return;
            }
            if ("null".equals(app.classModels.get(app.getClassPosition()).getHezi_code())
                    || !hCode.equals(app.classModels.get(app.getClassPosition()).getHezi_code())) {
                map.put("mac", hCode);
                IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.BANG_DING_HE_ZI_URL.replace("{classId}", "" + app
                        .classModels.get(app.getClassPosition()).getId()), map, activityHandler, true, 1);
            } else {
                scan();
            }
        }
    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        super.httpSuccess(message);
        if (message.getUrl().equals(IUrContant.BANG_DING_HE_ZI_URL.replace("{classId}", "" + app.classModels
                .get(app.getClassPosition()).getId()))) {
            JSONObject data = new JSONObject(new String(message.getResponse()));
            if (data.optInt("StatusCode") == 200) {
                if (isChangHeziCode) {
                    isChangHeziCode = false;
                    Map<String, String> map = new HashMap<>();
                    map.put("mac", hCode);
                    IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.BANG_DING_HE_ZI_URL.replace("{classId}", "" + app
                            .classModels.get(app.getClassPosition()).getId()), map, activityHandler, true, 1);
                } else {
                    app.classModels.get(app.getClassPosition()).setHezi_code(hCode);
                    scan();
                }
            }
        }
    }

    @Override
    public void HttpError(HttpResponseModel message) throws Exception {
        super.HttpError(message);
        if (message.getUrl().equals(IUrContant.BANG_DING_HE_ZI_URL.replace("{classId}", "" + app.classModels
                .get(app.getClassPosition()).getId())))
            dialog.setTitle("绑定失败");
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void newVersionCallBack() throws Exception {
        if (handler != null) // 实现连续扫描
            handler.sendEmptyMessage(R.id.restart_preview);
        if (isChangHeziCode) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mac", "null");
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.BANG_DING_HE_ZI_URL.replace("{classId}", "" + app
                    .classModels.get(app.getClassPosition()).getId()), map, activityHandler, true, 1);
        }
    }

    @Override
    public void newVersionOkCallBack() throws Exception {

    }

    // 扫码后的处理
    void scan() {
        SharedPreferencesUtil.save(this, SendWifiNameActivity.IS_NOTIFY
                + app.classModels.get(app.getClassPosition()).getHezi_code(), false);
        if (Boolean.parseBoolean(isHot)) {
            startActivity(SendWifiNameActivity.class);
            finish();
        } else {
            app.client = new HandlerClient();
            if (SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                    + app.classModels.get(app.getClassPosition()).getId()).equals(AppUtil.getConnectWifiSsid(this))) {
                app.client.connectTCP(ip);
                Bundle bundle = new Bundle();
                bundle.putBoolean(IConstant.BUNDLE_PARAMS, true);// 1上课 2 看备课
                startActivity(TeachingPlansActivity.class, bundle);
                finish();
            } else {
                app.setSessionIp(ip);
                cleanDialog = new ClearDataDialog(this, this,
                        StringUtil.format("请连接到：%s", SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                                + app.classModels.get(app.getClassPosition()).getId())),
                        null);
                cleanDialog.show();
            }
        }
    }


    @Override
    public void clearDataCallBack(View vx) throws Exception {
        WifiAdmin admin = new WifiAdmin(this);
        if (admin.getWifiConfigurated(SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                + app.classModels.get(app.getClassPosition()).getId())) == -1) {
            Bundle bundle = new Bundle();
            bundle.putString(IConstant.BUNDLE_PARAMS, SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                    + app.classModels.get(app.getClassPosition()).getId()));
            startActivity(SendWifiNameActivity.class, bundle);
            finish();
        } else {
            loginDialog = new LoginDialog(this);
            loginDialog.setTitle(resources.getString(R.string.conntecting));
            loginDialog.show();
            app.setChangeWifi(true);
            admin.connectConfiguratedWifi(SharedPreferencesUtil.getString(this, IConstant.WIFI_NEME
                    + app.classModels.get(app.getClassPosition()).getId()));
        }
    }
}