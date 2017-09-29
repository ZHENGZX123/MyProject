package com.sonix.oidbluetooth;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.sonix.oid.Dots;
import com.sonix.oid.DrawView;
import com.sonix.util.MyDBHelper;
import com.tqltech.tqlpencomm.Dot;
import com.tqltech.tqlpencomm.PenCommAgent;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class OidActivity extends Activity {

    private final String TAG = "OidActivity";
    private BluetoothLEService mService = null;
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private static final int A5_OUT_WIDTH = 158;
    private static final int A5_OUT_HEIGHT = 215;
    private static final float A5_WIDTH = 158;
    private static final float A5_HEIGHT = 214;
    private static final int BG_REAL_WIDTH = 840;
    private static final int BG_REAL_HEIGHT = 1147;

//    private static final int A5_OUT_WIDTH = 110;
//    private static final int A5_OUT_HEIGHT = 154;
//    private static final float A5_WIDTH = 110;
//    private static final float A5_HEIGHT = 153;
//    private static final int BG_REAL_WIDTH = 875;
//    private static final int BG_REAL_HEIGHT = 1241;

    private int BG_WIDTH;
    private int BG_HEIGHT;
    private int BG_WIDTH1;
    private int BG_HEIGHT1;
    private int A5_X_OFFSET;
    private int A5_Y_OFFSET;
    private int gcontentLeft, gcontentTop;
    private ImageView gImageView;
    private RelativeLayout gLayout;
    private float mov_x;//声明起点坐标
    private float mov_y;//声明起点坐标
    public static float mWidth;
    public static float mHeight;
    public static int gCurPageID = -1;
    public static float gScale = 1;
    private int gColor = 6;
    private int gWidth = 2;
    private int width1;
    private int gSpeed = 30;
    public static float gOffsetX = 0;
    public static float gOffsetY = 0;

    ArrayListMultimap<Integer, Dots> dot_number = ArrayListMultimap.create();
    private Intent serverIntent = null;
    private Intent LogIntent = null;

    public static float g_x0, g_x1, g_x2, g_x3;
    public static float g_y0, g_y1, g_y2, g_y3;
    public static float g_p0, g_p1, g_p2, g_p3;
    public static float g_vx01, g_vy01, g_n_x0, g_n_y0;
    public static float g_vx21, g_vy21;
    public static float g_norm;
    public static float g_n_x2, g_n_y2;

    private int gPIndex = -1;

    public static boolean gbEverZoom = false;
    private boolean gbSetNormal = false;
    private boolean gbReplay = false;
    private boolean gbCover = false;
    private ProgressDialog pd;

    private float pointX;
    private float pointY;
    private int pointZ;
    private float gpointX;
    private float gpointY;
    private DrawView bDrawl;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((BluetoothLEService.LocalBinder) rawBinder).getService();
            if (!mService.initialize()) {
                finish();
            }
            mService.setOnDataReceiveListener(new BluetoothLEService.OnDataReceiveListener() {
                @Override
                public void onDataReceive(final Dot dot) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Log.i(TAG, dot.toString());
                                // 回放模式，不接受点
                                if (bIsReply == true) {
                                    return;
                                }
                                ProcessEachDot(dot);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    });
                }

                @Override
                public void onStartOfflineDownload(final boolean isSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSuccess) {
                                Toast.makeText(OidActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                                pd = new ProgressDialog(OidActivity.this);
                                pd.setMessage("获取离线数据。。。");
                                pd.show();
                            } else {
                                Toast.makeText(OidActivity.this, "没有缓存", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onOfflineDataReceive(final Dot dot) {
                    Log.i(TAG, dot.toString());
                    ProcessEachDot2(dot);
                }

                @Override
                public void onFinishedOfflineDownload() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //1.删除所有缓存
                            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
                            bleManager.WritePenOffLineDelet();
                            pd.dismiss();
                        }
                    });
                }

                @Override
                public void onConnected(String address, String penName) {

                }

                @Override
                public void onPenNameSetupResponse(boolean isSuccess) {

                }

                @Override
                public void onReceivePenStatus(int battery) {

                }
            });
        }

        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        Intent gattServiceIntent = new Intent(this, BluetoothLEService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        bDrawl = new DrawView(this);
        bDrawl.setVcolor(Color.YELLOW);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;

        Log.i(TAG, "width:" + mWidth + "height:" + mHeight);

        gLayout = (RelativeLayout) super.findViewById(R.id.mylayout);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        param.width = (int) mWidth;
        param.height = (int) mHeight;
        param.rightMargin = 1;
        param.bottomMargin = 1;

        gLayout.addView(bDrawl, param);
        drawInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        gImageView = (ImageView) findViewById(R.id.imageView);//得到ImageView对象的引用
        gImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams para;
        para = gImageView.getLayoutParams();
        Log.i(TAG, "ImageView width:" + para.width + "height:" + para.height);

        float ratio;
        if (mWidth == 1536 && mHeight == 2048)  // 适应小米PAD,4:3
        {
            ratio = (mWidth * 63 / 72) / BG_REAL_WIDTH;
        } else {
            ratio = (mWidth * 70 / 72) / BG_REAL_WIDTH;
        }

        BG_WIDTH = (int) (BG_REAL_WIDTH * ratio);
        BG_HEIGHT = (int) (BG_REAL_HEIGHT * ratio);
        gcontentLeft = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getLeft();
        gcontentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        A5_X_OFFSET = (int) (mWidth - gcontentLeft - BG_WIDTH) / 2;
        A5_Y_OFFSET = (int) (mHeight - gcontentTop - BG_HEIGHT) / 2;
        para.width = BG_WIDTH / 2;
        para.height = BG_HEIGHT / 2;

        BG_WIDTH1 = para.width;
        BG_HEIGHT1 = para.height;
        Log.i(TAG, "ImageView width2:" + BG_WIDTH + "height:" + BG_HEIGHT);
        Log.i(TAG, "ImageView width2:" + para.width + "height:" + para.height);
        gImageView.setLayoutParams(para);
        gImageView.setImageResource(R.drawable.pcover);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.connect_scan) {
            serverIntent = new Intent(this, SelectDeviceActivity.class);
            startActivityForResult(serverIntent, REQUEST_SELECT_DEVICE);
            return true;
        } else if (i == R.id.offline) {
            PenCommAgent bleManager = PenCommAgent.GetInstance(getApplication());
            if (bleManager != null && bleManager.isConnect()) {
                bleManager.ReqOfflineDataTransfer(true);
            } else {
                Toast.makeText(this, "请先连接蓝牙", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.offlinelist) {
            final Dialog dialog = new Dialog(OidActivity.this, R.style.popupDialog);
            dialog.setContentView(R.layout.dialog_offline);
            dialog.show();

            ListView lv = (ListView) dialog.findViewById(R.id.lv);

            MyAdapter adapter = new MyAdapter();
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialog.dismiss();
                    //1.使用原生
                    final int ii = items.get(position);
//                        drawInit();
//                        SetBackgroundImage(ii);
//                        SetBackgroundImage(ii);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ReplayCurrentPage(ii, 0);
//                            }
//                        }).start();
                    //2.使用html
                    startActivity(new Intent(OidActivity.this, WebViewActivity.class).putExtra("pageid", ii));
                }
            });

            dot_number.clear();
            ArrayList<Dots> dots = new MyDBHelper(getApplicationContext()).getDotsByPageID(null);
            for (Dots d : dots) {
                dot_number.put(d.PageID, d);
            }
            items.clear();
            Set<Integer> keys = dot_number.keySet();
            for (int key : keys) {
                items.add(key);
            }
            adapter.notifyDataSetChanged();


        } else if (i == R.id.clearOffline) {
            PenCommAgent bleManager2 = PenCommAgent.GetInstance(getApplication());
            if (bleManager2 != null && bleManager2.isConnect()) {
                bleManager2.WritePenOffLineDelet();
            } else {
                Toast.makeText(this, "请先连接蓝牙", Toast.LENGTH_SHORT).show();
            }

            drawInit();
            bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            dot_number.clear();
            return true;
        } else if (i == R.id.clear) {
            drawInit();
            bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            dot_number.clear();
            return true;
        } else if (i == R.id.color_white) {
            gColor = 0;
            return true;
        } else if (i == R.id.color_red) {
            gColor = 1;
            return true;
        } else if (i == R.id.color_yellow) {
            gColor = 2;
            return true;
        } else if (i == R.id.color_green) {
            gColor = 3;
            return true;
        } else if (i == R.id.color_cyan) {
            gColor = 4;
            return true;
        } else if (i == R.id.color_blue) {
            gColor = 5;
            return true;
        } else if (i == R.id.color_black) {
            gColor = 6;
            return true;
        } else if (i == R.id.w1) {
            gWidth = 2;
            return true;
        } else if (i == R.id.w2) {
            gWidth = 4;
            return true;
        } else if (i == R.id.w3) {
            gWidth = 6;
            return true;
        } else if (i == R.id.w4) {
            gWidth = 8;
            return true;
        } else if (i == R.id.w5) {
            gWidth = 10;
            return true;
        } else if (i == R.id.w6) {
            gWidth = 12;
            return true;
        } else if (i == R.id.s1) {
            gbReplay = true;
            gSpeed = 10;
            RunReplay();
            return true;
        } else if (i == R.id.s2) {
            gbReplay = true;
            gSpeed = 20;
            RunReplay();
            return true;
        } else if (i == R.id.s3) {
            gbReplay = true;
            gSpeed = 30;
            RunReplay();
            return true;
        } else if (i == R.id.s4) {
            gbReplay = true;
            gSpeed = 40;
            RunReplay();
            return true;
        } else if (i == R.id.s5) {
            gbReplay = true;
            gSpeed = 50;
            RunReplay();
            return true;
        } else if (i == R.id.setting) {
            LogIntent = new Intent(this, FunActivity.class);
            startActivity(LogIntent);
            return true;
        }
        return false;
    }

    private boolean bIsReply = false;

    public void RunReplay() {
        if (gCurPageID < 0) {
            return;
        }
        drawInit();
        bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReplayCurrentPage(gCurPageID, gSpeed);
            }
        }).start();
    }

    public void ReplayCurrentPage(int PageID, int SpeedID) {
        Set<Integer> keys = dot_number.keySet();
        for (int key : keys) {
            bIsReply = true;
            if (key == PageID) {
                List<Dots> dots = dot_number.get(key);
                for (Dots dot : dots) {
                    drawSubFountainPen1(bDrawl, gScale, gOffsetX, gOffsetY, dot.penWidth,
                            dot.pointX, dot.pointY, dot.force, dot.ntype, dot.ncolor);
                    bDrawl.postInvalidate();
                    SystemClock.sleep(SpeedID);
                }
            }
        }

        bIsReply = false;
        gPIndex = -1;
    }

    public void SetPenColor(int ColorIndex) {
        switch (ColorIndex) {
            case 0:
                bDrawl.paint.setColor(Color.GRAY);
                return;
            case 1:
                bDrawl.paint.setColor(Color.RED);
                return;
            case 2:
                bDrawl.paint.setColor(Color.rgb(192, 192, 0));
                return;
            case 3:
                bDrawl.paint.setColor(Color.rgb(0, 128, 0));
                return;
            case 4:
                bDrawl.paint.setColor(Color.rgb(0, 0, 192));
                return;
            case 5:
                bDrawl.paint.setColor(Color.BLUE);
                return;
            case 6:
                bDrawl.paint.setColor(Color.BLACK);
                return;
        }
        return;
    }

    public void drawInit() {
        bDrawl.initDraw();
        bDrawl.setVcolor(Color.WHITE);
        bDrawl.setVwidth(1);
        SetPenColor(gColor);
        bDrawl.paint.setStrokeCap(Paint.Cap.ROUND);
        bDrawl.paint.setStyle(Paint.Style.FILL);
        bDrawl.paint.setAntiAlias(true);
        bDrawl.invalidate();
        if (gbSetNormal == true) {
            LayoutParams para;
            para = gImageView.getLayoutParams();
            para.width = (int) (BG_WIDTH * gScale);
            para.height = (int) (BG_HEIGHT * gScale);
            if (gbCover == false) {
                para.width = (int) (BG_WIDTH1 * gScale);
                para.height = (int) (BG_HEIGHT1 * gScale);
            }
            gImageView.setLayoutParams(para);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_DEVICE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = data.getStringExtra(BluetoothDevice.EXTRA_NAME);
                    mService.connect(deviceAddress , deviceName);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();
                } else {
                    checkBLEEnable();
                }
                break;
            default:
                break;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        bDrawl.DrawDestroy();
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService = null;
        dot_number.clear();
        dot_number = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        checkBLEEnable();
    }

    private void checkBLEEnable() {
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_SERVICES_DISCOVERED);
        return intentFilter;
    }

    private String gStrHH = "";
    private String gFileName = "";

    private void saveData(Integer pageID, float pointX, float pointY, int force, int ntype, int penWidth, int color, int counter, int angle) {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String str1 = formatter1.format(curDate);
        String hh = str.substring(0, 2);

        if (!gStrHH.equals(hh)) {
            gStrHH = hh;
        }
        //String txt = str + "  pointX: "+pointX+"  pointY: "+pointY+"  force: "+force+"  angle: "+angle;
        String txt = pointX + "," + pointY + "," + ntype;
        String path = "/sdcard/Test/";
        String fileName = str1 + gStrHH + "log";
        gFileName = fileName;
        writeTxtToFile(txt, path, fileName);
    }

    private void ProcessEachDot(Dot dot) {
        int counter = 0;
        pointZ = dot.force;
        if (pointZ < 0) {
            Log.i(TAG, "Counter=" + counter + ", Pressure=" + pointZ + "  Cut!!!!!");
            return;
        }

        int tmpx = dot.x;
        pointX = dot.fx;
        pointX /= 100.0;
        pointX += tmpx;

        int tmpy = dot.y;
        pointY = dot.fy;
        pointY /= 100.0;
        pointY += tmpy;

        //点本身的范围
        if (pointY > 1540 || pointX > 1100) {
            return;
        }

        gpointX = pointX;
        gpointY = pointY;

        pointX %= A5_OUT_WIDTH;
        pointY %= A5_OUT_HEIGHT;
        pointX *= (BG_WIDTH);
        pointX /= A5_WIDTH;
        pointY *= (BG_HEIGHT);
        pointY /= A5_HEIGHT;
        pointX += A5_X_OFFSET;
        pointY += A5_Y_OFFSET;

        if (pointZ > 0) {
            if (dot.type == Dot.DotType.PEN_DOWN) {

                gPIndex = 0;
                int PageID;
                if (gbReplay == true) {
                    SystemClock.sleep(50);
                    drawInit();
                    Log.i(TAG, "First DrawExistingStroke======" + gCurPageID);
                    DrawExistingStroke(gCurPageID);
                }
                PageID = CheckXYLocation(gpointX, gpointY);
                if (PageID != gCurPageID || gbEverZoom == true) {
                    SetBackgroundImage(PageID);
                    gCurPageID = PageID;
                    drawInit();
                    Log.i(TAG, "Second DrawExistingStroke======" + gCurPageID);
                    DrawExistingStroke(gCurPageID);
                    gbEverZoom = false;
                }

                SetPenColor(gColor);

                drawSubFountainPen(bDrawl, gScale, gOffsetX, gOffsetY, gWidth, pointX, pointY, pointZ); //gPindex=0
                Log.i(TAG, "Dot.DotType.PEN_DOWN======");

                Dots dots = new Dots(dot.SectionID, dot.OwnerID, dot.BookID, gCurPageID, pointX, pointY, pointZ, 0, width1, gColor, counter, dot.angle);
                dot_number.put(gCurPageID, dots);
                // 保存屏幕坐标，原始坐标会使比例缩小
                saveData(gCurPageID, pointX, pointY, pointZ, 0, width1, gColor, dot.Counter, dot.angle);

                mov_x = pointX;
                mov_y = pointY;
                return;
            }

            if (dot.type == Dot.DotType.PEN_MOVE) {
                gPIndex += 1;
                Log.i(TAG, "----------move---------");
                SetPenColor(gColor);
                drawSubFountainPen(bDrawl, gScale, gOffsetX, gOffsetY, gWidth, pointX, pointY, pointZ);
                bDrawl.invalidate((int) (Math.min(mov_x, pointX)) - 30, (int) (Math.min(mov_y, pointY)) - 30, (int) (Math.max(mov_x, pointX)) + 30, (int) (Math.max(mov_y, pointY)) + 30);
                mov_x = pointX;
                mov_y = pointY;

                Dots dots = new Dots(dot.SectionID, dot.OwnerID, dot.BookID, gCurPageID, pointX, pointY, pointZ, 1, width1, gColor, counter, dot.angle);
                dot_number.put(gCurPageID, dots);
                // 保存屏幕坐标，原始坐标会使比例缩小
                saveData(gCurPageID, pointX, pointY, pointZ, 1, width1, gColor, dot.Counter, dot.angle);
            }
        } else if (dot.type == Dot.DotType.PEN_UP) {
            if (dot.x == 0 || dot.y == 0) {
                pointX = mov_x;
                pointY = mov_y;
            }
            drawSubFountainPen(bDrawl, gScale, gOffsetX, gOffsetY, gWidth, pointX, pointY, pointZ);
            Dots dots = new Dots(dot.SectionID, dot.OwnerID, dot.BookID, gCurPageID, pointX, pointY, pointZ, 2, width1, gColor, counter, dot.angle);
            dot_number.put(gCurPageID, dots);
            // 保存屏幕坐标，原始坐标会使比例缩小
            saveData(gCurPageID, pointX, pointY, pointZ, 2, width1, gColor, dot.Counter, dot.angle);

            bDrawl.invalidate();
            pointX = 0;
            pointY = 0;
            gPIndex = -1;
        }
    }

    private synchronized void ProcessEachDot2(Dot dot) {
        int counter = 0;
        pointZ = dot.force;
        if (pointZ < 0) {
            Log.i(TAG, "Counter=" + counter + ", Pressure=" + pointZ + "  Cut!!!!!");
            return;
        }
        int tmpx = dot.x;
        pointX = dot.fx;
        pointX /= 100.0;
        pointX += tmpx;

        int tmpy = dot.y;
        pointY = dot.fy;
        pointY /= 100.0;
        pointY += tmpy;

/*        if (pointY > 1540 || pointX > 1100) {
            return;
        }
        gpointX = pointX;
        gpointY = pointY;

        pointX %= A5_OUT_WIDTH;
        pointY %= A5_OUT_HEIGHT;

        pointX *= (BG_WIDTH);
        pointX /= A5_WIDTH;
        pointY *= (BG_HEIGHT);
        pointY /= A5_HEIGHT;

        pointX += A5_X_OFFSET;
        pointY += A5_Y_OFFSET;

        int PageID = CheckXYLocation(gpointX, gpointY);*/

        int PageID = dot.PageID;

        int type = 0;
        if (dot.type == Dot.DotType.PEN_DOWN) {
            type = 0;
        } else if (dot.type == Dot.DotType.PEN_MOVE) {
            type = 1;
        } else if (dot.type == Dot.DotType.PEN_UP) {
            type = 2;
        }

        Dots dots = new Dots(dot.SectionID, dot.OwnerID, dot.BookID, PageID, pointX, pointY, pointZ, type, width1, gColor, dot.Counter, dot.angle);
        new MyDBHelper(this).addDots(dots);
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothLEService.ACTION_GATT_CONNECTED.equals(action)) {
                bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                invalidateOptionsMenu();
            }

            if (BluetoothLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
                gbCover = false;
                bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                dot_number.clear();
                invalidateOptionsMenu();
            }
        }
    };

    private int CheckXYLocation(float x, float y) {
        int w, h;
        w = (int) (x / A5_OUT_WIDTH);
        h = (int) (y / A5_OUT_HEIGHT);
        return (10 * h + w);
    }

    private void SetBackgroundImage(int PageID) {
        if (gbSetNormal == false) {
            LayoutParams para;
            para = gImageView.getLayoutParams();
            para.width = BG_WIDTH;
            para.height = BG_HEIGHT;
            gImageView.setLayoutParams(para);
            gbSetNormal = true;

        }
        gbCover = true;
        bDrawl.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        switch (PageID) {
            case 0:
                //gImageView.setImageResource(R.drawable.p01);
                gImageView.setImageResource(R.drawable.p8);
                return;
            case 1:
                gImageView.setImageResource(R.drawable.p02);
                return;
            case 2:
                gImageView.setImageResource(R.drawable.p03);
                return;
            case 3:
                gImageView.setImageResource(R.drawable.p04);
                return;
            case 4:
                gImageView.setImageResource(R.drawable.p05);
                return;
            case 5:
                gImageView.setImageResource(R.drawable.p06);
                return;
            case 6:
                gImageView.setImageResource(R.drawable.p07);
                return;
            case 7:
                gImageView.setImageResource(R.drawable.p08);
                return;
            case 8:
                gImageView.setImageResource(R.drawable.p09);
                return;
            case 9:
                gImageView.setImageResource(R.drawable.p10);
                return;
            case 10:
                gImageView.setImageResource(R.drawable.p11);
                return;
            case 11:
                gImageView.setImageResource(R.drawable.p12);
                return;
            case 12:
                gImageView.setImageResource(R.drawable.p13);
                return;
            case 13:
                gImageView.setImageResource(R.drawable.p14);
                return;
            case 14:
                gImageView.setImageResource(R.drawable.p15);
                return;
            case 15:
                gImageView.setImageResource(R.drawable.p16);
                return;
            case 16:
                gImageView.setImageResource(R.drawable.p17);
                return;
            case 17:
                gImageView.setImageResource(R.drawable.p18);
                return;
            case 18:
                gImageView.setImageResource(R.drawable.p19);
                return;
            case 19:
                gImageView.setImageResource(R.drawable.p20);
                return;
            case 20:
                gImageView.setImageResource(R.drawable.p21);
                return;
            case 21:
                gImageView.setImageResource(R.drawable.p22);
                return;
            case 22:
                gImageView.setImageResource(R.drawable.p23);
                return;
            case 23:
                gImageView.setImageResource(R.drawable.p24);
                return;
            case 24:
                gImageView.setImageResource(R.drawable.p25);
                return;
            case 25:
                gImageView.setImageResource(R.drawable.p26);
                return;
            case 26:
                gImageView.setImageResource(R.drawable.p27);
                return;
            case 27:
                gImageView.setImageResource(R.drawable.p28);
                return;
            case 28:
                gImageView.setImageResource(R.drawable.p29);
                return;
            case 29:
                gImageView.setImageResource(R.drawable.p30);
                return;
            case 30:
                gImageView.setImageResource(R.drawable.p31);
                return;
            case 31:
                gImageView.setImageResource(R.drawable.p32);
                return;
            case 32:
                gImageView.setImageResource(R.drawable.p33);
                return;
            case 33:
                gImageView.setImageResource(R.drawable.p34);
                return;
            case 34:
                gImageView.setImageResource(R.drawable.p35);
                return;
            case 35:
                gImageView.setImageResource(R.drawable.p36);
                return;
            case 36:
                gImageView.setImageResource(R.drawable.p37);
                return;
            case 37:
                gImageView.setImageResource(R.drawable.p38);
                return;
            case 38:
                gImageView.setImageResource(R.drawable.p39);
                return;
            case 39:
                gImageView.setImageResource(R.drawable.p40);
                return;
            case 40:
                gImageView.setImageResource(R.drawable.p41);
                return;
            case 41:
                gImageView.setImageResource(R.drawable.p42);
                return;
            case 42:
                gImageView.setImageResource(R.drawable.p43);
                return;
            case 43:
                gImageView.setImageResource(R.drawable.p44);
                return;
            case 44:
                gImageView.setImageResource(R.drawable.p45);
                return;
            case 45:
                gImageView.setImageResource(R.drawable.p46);
                return;
            case 46:
                gImageView.setImageResource(R.drawable.p47);
                return;
            case 47:
                gImageView.setImageResource(R.drawable.p48);
                return;
            case 48:
                gImageView.setImageResource(R.drawable.p49);
                return;
            case 49:
                gImageView.setImageResource(R.drawable.p50);
                return;
            case 50:
                gImageView.setImageResource(R.drawable.p51);
                return;
            case 51:
                gImageView.setImageResource(R.drawable.p52);
                return;
            case 52:
                gImageView.setImageResource(R.drawable.p53);
                return;
            case 53:
                gImageView.setImageResource(R.drawable.p54);
                return;
            case 54:
                gImageView.setImageResource(R.drawable.p55);
                return;
            case 55:
                gImageView.setImageResource(R.drawable.p56);
                return;
            case 56:
                gImageView.setImageResource(R.drawable.p57);
                return;
            case 57:
                gImageView.setImageResource(R.drawable.p58);
                return;
            case 58:
                gImageView.setImageResource(R.drawable.p59);
                return;
            case 59:
                gImageView.setImageResource(R.drawable.p60);
                return;
            case 60:
                gImageView.setImageResource(R.drawable.p61);
                return;
            case 61:
                gImageView.setImageResource(R.drawable.p62);
                return;
            case 62:
                gImageView.setImageResource(R.drawable.p63);
                return;
            case 63:
                gImageView.setImageResource(R.drawable.p64);
                return;
            case 64:
                gImageView.setImageResource(R.drawable.p65);
                return;
            case 65:
                gImageView.setImageResource(R.drawable.p66);
                return;
            case 66:
                gImageView.setImageResource(R.drawable.p67);
                return;
            case 67:
                gImageView.setImageResource(R.drawable.p68);
                return;
            case 68:
                gImageView.setImageResource(R.drawable.p69);
                return;
            case 69:
                gImageView.setImageResource(R.drawable.p70);
                return;
            case 70:
                gImageView.setImageResource(R.drawable.p71);
                return;
            case 71:
                gImageView.setImageResource(R.drawable.p72);
                return;
            case 72:
                gImageView.setImageResource(R.drawable.p73);
                return;
            case 73:
                gImageView.setImageResource(R.drawable.p74);
                return;
            case 74:
                gImageView.setImageResource(R.drawable.p75);
                return;
            case 75:
                gImageView.setImageResource(R.drawable.p76);
                return;
            case 76:
                gImageView.setImageResource(R.drawable.p77);
                return;
            case 77:
                gImageView.setImageResource(R.drawable.p78);
                return;
            case 78:
                gImageView.setImageResource(R.drawable.p79);
                return;
            case 79:
                gImageView.setImageResource(R.drawable.p80);
                return;
            case 80:
                gImageView.setImageResource(R.drawable.p81);
                return;
            case 81:
                gImageView.setImageResource(R.drawable.p82);
                return;
            case 82:
                gImageView.setImageResource(R.drawable.p83);
                return;
            case 83:
                gImageView.setImageResource(R.drawable.p84);
                return;
            case 84:
                gImageView.setImageResource(R.drawable.p85);
                return;
            case 85:
                gImageView.setImageResource(R.drawable.p86);
                return;
            case 86:
                gImageView.setImageResource(R.drawable.p87);
                return;
            case 87:
                gImageView.setImageResource(R.drawable.p88);
                return;
            case 88:
                gImageView.setImageResource(R.drawable.p89);
                return;
            case 89:
                gImageView.setImageResource(R.drawable.p90);
                return;
            case 90:
                gImageView.setImageResource(R.drawable.p91);
                return;
            case 91:
                gImageView.setImageResource(R.drawable.p92);
                return;
            case 92:
                gImageView.setImageResource(R.drawable.p93);
                return;
            case 93:
                gImageView.setImageResource(R.drawable.p94);
                return;
            case 94:
                gImageView.setImageResource(R.drawable.p95);
                return;
            case 95:
                gImageView.setImageResource(R.drawable.p96);
                return;
            case 96:
                gImageView.setImageResource(R.drawable.p97);
                return;
            case 97:
                gImageView.setImageResource(R.drawable.p98);
                return;
            case 98:
                gImageView.setImageResource(R.drawable.p99);
                return;
            case 99:
                gImageView.setImageResource(R.drawable.p100);
                return;
            default:
                Log.i(TAG, "setImageResource error index = " + PageID);
                break;
        }
        return;
    }

    protected Path mDrawPath = new Path();

    private void drawSubFountainPen1(DrawView DV, float scale, float offsetX, float offsetY, int penWidth, float x, float y, int force, int ntype, int color) {
        if (ntype == 0) {
            g_x0 = x;
            g_y0 = y;
            g_x1 = x;
            g_y1 = y;
        }

        g_x1 = x;
        g_y1 = y;
        if (ntype == 2) {
            return;
        }

        mDrawPath.rewind();

        DV.paint.setStrokeWidth(penWidth);
        SetPenColor(color);
        DV.canvas.drawLine(g_x0, g_y0, g_x1, g_y1, DV.paint);

        g_x0 = g_x1;
        g_y0 = g_y1;
        return;
    }

    private void drawSubFountainPen(DrawView DV, float scale, float offsetX, float offsetY, int penWidth, float x, float y, int force) {
        // the first actual point is treated as a midpoint
        if (gPIndex == 0) {
            g_x0 = x * scale + offsetX + 0.1f;
            g_y0 = y * scale + offsetY;
            g_p0 = Math.max(1, penWidth * scale * force / 1023);
            width1 = (int) g_p0;
            DV.canvas.drawCircle((float) (g_x0), (float) (g_y0), (float) 0.5, DV.paint);
            return;
        }

        if (gPIndex == 1) {
            g_x1 = x * scale + offsetX + 0.1f;
            g_y1 = y * scale + offsetY;
            g_p1 = Math.max(1, penWidth * scale * force / 1023);
            width1 = (int) g_p1;
            g_vx01 = g_x1 - g_x0;
            g_vy01 = g_y1 - g_y0;
            g_norm = (float) Math.sqrt(g_vx01 * g_vx01 + g_vy01 * g_vy01 + 0.0001f) * 2f;
            g_vx01 = g_vx01 / g_norm * g_p0;
            g_vy01 = g_vy01 / g_norm * g_p0;
            g_n_x0 = g_vy01;
            g_n_y0 = -g_vx01;
            return;
        }

        if (gPIndex > 1 && gPIndex < 10000) {
            g_x3 = x * scale + offsetX + 0.1f;
            g_y3 = y * scale + offsetY;
            g_p3 = Math.max(1, penWidth * scale * force / 1023);
            width1 = (int) g_p3;

            g_x2 = (g_x1 + g_x3) / 2f;
            g_y2 = (g_y1 + g_y3) / 2f;
            g_p2 = (g_p1 + g_p3) / 2f;
            g_vx21 = g_x1 - g_x2;
            g_vy21 = g_y1 - g_y2;
            g_norm = (float) Math.sqrt(g_vx21 * g_vx21 + g_vy21 * g_vy21 + 0.0001f) * 2f;
            g_vx21 = g_vx21 / g_norm * g_p2;
            g_vy21 = g_vy21 / g_norm * g_p2;
            g_n_x2 = -g_vy21;
            g_n_y2 = g_vx21;


            mDrawPath.rewind();
            mDrawPath.moveTo(g_x0 + g_n_x0, g_y0 + g_n_y0);
            // The + boundary of the stroke
            mDrawPath.cubicTo(g_x1 + g_n_x0, g_y1 + g_n_y0, g_x1 + g_n_x2, g_y1 + g_n_y2, g_x2 + g_n_x2, g_y2 + g_n_y2);
            // round out the cap
            mDrawPath.cubicTo(g_x2 + g_n_x2 - g_vx21, g_y2 + g_n_y2 - g_vy21, g_x2 - g_n_x2 - g_vx21, g_y2 - g_n_y2 - g_vy21, g_x2 - g_n_x2, g_y2 - g_n_y2);
            // THe - boundary of the stroke
            mDrawPath.cubicTo(g_x1 - g_n_x2, g_y1 - g_n_y2, g_x1 - g_n_x0, g_y1 - g_n_y0, g_x0 - g_n_x0, g_y0 - g_n_y0);
            // round out the other cap
            mDrawPath.cubicTo(g_x0 - g_n_x0 - g_vx01, g_y0 - g_n_y0 - g_vy01, g_x0 + g_n_x0 - g_vx01, g_y0 + g_n_y0 - g_vy01, g_x0 + g_n_x0, g_y0 + g_n_y0);
            DV.canvas.drawPath(mDrawPath, DV.paint);

            g_x0 = g_x2;
            g_y0 = g_y2;
            g_p0 = g_p2;
            g_x1 = g_x3;
            g_y1 = g_y3;
            g_p1 = g_p3;
            g_vx01 = -g_vx21;
            g_vy01 = -g_vy21;
            g_n_x0 = g_n_x2;
            g_n_y0 = g_n_y2;
            return;
        }
        if (gPIndex >= 10000) {//Last Point
            g_x2 = x * scale + offsetX + 0.1f;
            g_y2 = y * scale + offsetY;
            g_p2 = Math.max(1, penWidth * scale * force / 1023);
            width1 = (int) g_p2;

            g_vx21 = g_x1 - g_x2;
            g_vy21 = g_y1 - g_y2;
            g_norm = (float) Math.sqrt(g_vx21 * g_vx21 + g_vy21 * g_vy21 + 0.0001f) * 2f;
            g_vx21 = g_vx21 / g_norm * g_p2;
            g_vy21 = g_vy21 / g_norm * g_p2;
            g_n_x2 = -g_vy21;
            g_n_y2 = g_vx21;

            mDrawPath.rewind();
            mDrawPath.moveTo(g_x0 + g_n_x0, g_y0 + g_n_y0);
            mDrawPath.cubicTo(g_x1 + g_n_x0, g_y1 + g_n_y0, g_x1 + g_n_x2, g_y1 + g_n_y2, g_x2 + g_n_x2, g_y2 + g_n_y2);
            mDrawPath.cubicTo(g_x2 + g_n_x2 - g_vx21, g_y2 + g_n_y2 - g_vy21, g_x2 - g_n_x2 - g_vx21, g_y2 - g_n_y2 - g_vy21, g_x2 - g_n_x2, g_y2 - g_n_y2);
            mDrawPath.cubicTo(g_x1 - g_n_x2, g_y1 - g_n_y2, g_x1 - g_n_x0, g_y1 - g_n_y0, g_x0 - g_n_x0, g_y0 - g_n_y0);
            mDrawPath.cubicTo(g_x0 - g_n_x0 - g_vx01, g_y0 - g_n_y0 - g_vy01, g_x0 + g_n_x0 - g_vx01, g_y0 + g_n_y0 - g_vy01, g_x0 + g_n_x0, g_y0 + g_n_y0);
            DV.canvas.drawPath(mDrawPath, DV.paint);
            return;
        }
    }


    public void DrawExistingStroke(int PageID) {
        Set<Integer> keys = dot_number.keySet();
        for (int key : keys) {
            if (key == PageID) {
                List<Dots> dots = dot_number.get(key);
                for (Dots dot : dots) {
                    drawSubFountainPen1(bDrawl, gScale, gOffsetX, gOffsetY, dot.penWidth,
                            dot.pointX, dot.pointY, dot.force, dot.ntype, dot.ncolor);
                }
            }
        }
        gPIndex = -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(OidActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_offline, null);
                holder = new ViewHolder();

                holder.tv = (TextView) rowView.findViewById(R.id.tv);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            int i = items.get(position);
            List<Dots> dots = dot_number.get(i);
            holder.tv.setText("pageid = " + i + ",该页有多少个点:" + dots.size());
            return rowView;
        }

        public class ViewHolder {
            public TextView tv;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Integer getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    private ArrayList<Integer> items = new ArrayList<>();

}
