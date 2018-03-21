package cn.kiway.mdm.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.WindowManager;

import cn.kiway.mdm.camera.managers.CameraManager;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2018/3/19.
 */

public class LukeCameraActivity extends BaseActivity {
    private SurfaceView surfaceView;
    private CameraManager mCameraManager;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suf);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        if (surfaceView != null) {
            if (checkPermisson()) {
                mCameraManager = new CameraManager(this, surfaceView);
                mCameraManager.startPreview();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_CAMERA_CODE);
                return false;
            } else
                return true;
        } else {
            return true;
        }
    }

    @Override
    protected void onResume() {
        RecordService.isCamera = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecordService.isCamera = false;
        if (mCameraManager != null)
            mCameraManager.stop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraManager != null)
            mCameraManager.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            int grantResult = grantResults[0];
            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                mCameraManager = new CameraManager(this, surfaceView);
                mCameraManager.startPreview();
            }
        }
    }
}
