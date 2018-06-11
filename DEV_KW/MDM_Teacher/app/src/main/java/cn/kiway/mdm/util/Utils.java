package cn.kiway.mdm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.Course0Activity;
import cn.kiway.mdm.activity.Course1Activity;
import cn.kiway.mdm.activity.CourseListActivity;
import cn.kiway.mdm.activity.ResultActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
import cn.kiway.mdm.entity.UploadTask;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.view.popup.PopModel;
import uk.co.senab.photoview.sample.ViewPagerActivity;

/**
 * Created by Administrator on 2017/7/5.
 */

public class Utils {

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getCurrentVersion(Context c) {
        String versionName = "1.0.0";
        try {
            PackageInfo pkg = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            versionName = pkg.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    public static void openFile(final Context context, String filePath) {
        Log.d("test", "openFile filepath = " + filePath);
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(context, "文件路径不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        filePath = "file://" + filePath;
        String[] splits = filePath.split("\\.");
        if (splits.length <= 1) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getString(R.string.no_support), Toast.LENGTH_SHORT).show();
                }
            });

            return;
        }
        String filetype = splits[1].toLowerCase();
        Log.d("test", "filetype = " + filetype);
        String typeOpenFile = "*";
        if (filetype.equals("pdf"))
            typeOpenFile = "application/pdf";
        else if (filetype.equals("ppt") || filetype.equals("pptx"))
            typeOpenFile = "application/vnd.ms-powerpoint";
        else if (filetype.equals("doc") || filetype.equals("docx") || filetype.equals("docm") || filetype.equals
                ("dotx") || filetype
                .equals("dotm"))
            typeOpenFile = "application/msword";
        else if (filetype.equals("xlsx") || filetype.equals("xlsm") || filetype.equals("xltx"))
            typeOpenFile = "application/vnd.ms-excel";
        else if (filetype.equals("mp3") || filetype.equals("amr") || filetype.equals("ogg") || filetype.equals("wav")) {
            typeOpenFile = "audio/*";
        } else if (filetype.equals("mp4") || filetype.equals("3gp") || filetype.equals("avi") || filetype.equals
                ("rmvb") || filetype
                .equals("mpg") | filetype.equals("rm") || filetype.equals("flv")) {
            typeOpenFile = "video/*";
        } else if (filetype.equals("swf")) {
            typeOpenFile = "application/x-shockwave-flash";
        } else if (filetype.equals("jpg") || filetype.equals("jpeg") || filetype.equals("png")) {
            typeOpenFile = "image/*";
        } else if (filetype.equals("apk")) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
            return;
        }
        Log.d("test", "typeOpenFile = " + typeOpenFile);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse(filePath), typeOpenFile);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (typeOpenFile.equals("video/*") || typeOpenFile.equals("audio/*"))
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.mobile_no_play), Toast.LENGTH_SHORT).show();
                    }
                });
            else
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.mobile_no_office), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        }
    }

    /**
     * 获取和保存当前屏幕的截图
     */
    public static String GetandSaveCurrentImage(Activity c) {
        //1.构建Bitmap
        WindowManager windowManager = c.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //2.获取屏幕
        View decorview = c.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
        String SavePath = "/mnt/sdcard/kiway_mdm_teacher/snapshot/";
        //3.保存Bitmap
        try {
            File path = new File(SavePath);
            //文件
            String filepath = SavePath + System.currentTimeMillis() + ".png";
            File file = new File(filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(c, "截屏文件已保存至kiway_mdm_teacher/snapshot/", Toast.LENGTH_LONG).show();
                return filepath;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }

    public static boolean saveBitmap(Bitmap bm, String picName) {
        String folder = KWApplication.ROOT + "snapshot/";
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        File f = new File(folder, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //保存课程操作行为
    public static void courseOperation(Activity c, String courseID, int type, String someID) {
        try {
            String url = Constant.clientUrl +
                    "/device/teacher/course/" + courseID + "/courseOperation";
            Log.d("test", "courseOperation url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("courseId", courseID);
            if (type == 2) {
                param.put("knowledgeId", someID);
            } else if (type > 2 && type < 7) {
                param.put("examinationId", someID);
            }
            param.put("type", type);
            Log.d("test", "courseOperation param array = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "courseOperation onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    //这个接口可以不检测301
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String courseID;
    public static int questionType;

    public static boolean check301(final Activity c, String result, final String type) {
        if (c == null) {
            return false;
        }
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        try {
            int statusCode = new JSONObject(result).optInt("statusCode");
            if (statusCode != 301) {
                return false;
            }
            Log.d("test", "301 happen");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = Constant.clientUrl + "/device/teacher/login";
            Log.d("test", "relogin url = " + url);
            RequestParams param = new RequestParams();
            param.put("userName", c.getSharedPreferences("kiway", 0).getString("username", ""));
            param.put("password", c.getSharedPreferences("kiway", 0).getString("password", ""));
            Log.d("test", "relogin param = " + param.toString());

            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "relogin  onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        //relogin 其他参数存不存都可以
                        String token = o.getJSONObject("data").getString("token");
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                        if (type.equals("endclass")) {
                            ((Course0Activity) c).endClass(courseID);
                        } else if (type.equals("students")) {
                            ((StudentGridActivity) c).initData();
                        } else if (type.equals("courselist")) {
                            ((CourseListActivity) c).initData();
                        } else if (type.equals("coursedetail0")) {
                            ((Course0Activity) c).initData();
                        } else if (type.equals("coursedetail1")) {
                            ((Course1Activity) c).initData();
                        } else if (type.equals("knowledgeCountResult")) {
                            ((StudentGridActivity) c).uploadResult();
                        } else if (type.equals("questionResult")) {
                            ((ResultActivity) c).uploadResult();
                        } else if (type.equals("filepush")) {
                            ((StudentGridActivity) c).uploadUserfile();
                        } else if (type.equals("knowledges")) {
                            ((Course0Activity) c).tongji(null);
                        } else if (type.equals("questions")) {
                            ((Course0Activity) c).question(questionType);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "relogin  failure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "relogin exception = " + e.toString());
        }
        return true;
    }

    private static void toast(Activity c, String txt) {
        ((BaseActivity) c).toast(txt);
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static void showBigImage(Context c, String[] images, int position) {
        ViewPagerActivity.sDrawables = images;
        Intent intent = new Intent(c, ViewPagerActivity.class);
        intent.putExtra("position", position);
        c.startActivity(intent);
    }

    public static String getIMEI(Context c) {
        String imei = FileUtils.readSDCardFile(KWApplication.ROOT + "/imei.txt", c);
        if (TextUtils.isEmpty(imei)) {
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                Log.d("test", "这个IMEI是生成的");
                imei = genIMEI();
            }
            FileUtils.saveFile(imei);
        }
        Log.d("test", "IMEI = " + imei);
        return imei;
    }

    public static String genIMEI() {
        int r1 = 1000000 + new java.util.Random().nextInt(9000000);
        int r2 = 1000000 + new java.util.Random().nextInt(9000000);
        String input = r1 + "" + r2;
        char[] ch = input.toCharArray();
        int a = 0, b = 0;
        for (int i = 0; i < ch.length; i++) {
            int tt = Integer.parseInt(ch[i] + "");
            if (i % 2 == 0) {
                a = a + tt;
            } else {
                int temp = tt * 2;
                b = b + temp / 10 + temp % 10;
            }
        }
        int last = (a + b) % 10;
        if (last == 0) {
            last = 0;
        } else {
            last = 10 - last;
        }
        return input + last;
    }

    public static String longToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(Long.parseLong(time));
        String str = sdf.format(date);
        return str;
    }

    public static String longToDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(time);
        String str = sdf.format(date);
        return str;
    }

    public static String longToDate2(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        java.util.Date date = new Date(Long.parseLong(time));
        String str = sdf.format(date);
        return str;
    }

    public static String longToDate3(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        java.util.Date date = new Date(Long.parseLong(time));
        String str = sdf.format(date);
        return str;
    }

    public static boolean isImage(String filePath) {
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png") || filePath
                .endsWith(".JPG") || filePath.endsWith(".JPEG") || filePath.endsWith(".PNG"))
            return true;
        return false;
    }

    public static void addVideoRecord(final Context c, UploadTask ut, final String courseID, final String fileUrl,
                                      final String fileSuffix) {
        try {
            String url = Constant.clientUrl + "/device/teacher/course/" + courseID + "/video";
            Log.d("test", "addVideoRecord url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("url", fileUrl);
            param.put("suffix", fileSuffix);
            Log.d("test", "addVideoRecord param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_FINISH);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getRemainingSDSize(Context c) {
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        Log.d("test", "usableSpace = " + usableSpace);
        return usableSpace;
    }

    public static List<PopModel> getPopList() {
        /** 初始化数据源 **/
        final List<PopModel> list = new ArrayList<>();

        PopModel feedPopModel = new PopModel();
        feedPopModel.setDrawableId(R.drawable.p_u1742);
        feedPopModel.setItemDesc("截屏");
        feedPopModel.setId(0);
        list.add(feedPopModel);

        PopModel messagePopMode = new PopModel();
        messagePopMode.setDrawableId(R.drawable.p_u1744);
        messagePopMode.setItemDesc("拍照");
        messagePopMode.setId(1);
        list.add(messagePopMode);

//        PopModel luke = new PopModel();
//        if (RecordService.recording) {
//            luke.setDrawableId(R.drawable.rk2);
//            luke.setItemDesc("结束录课");
//        } else {
//            luke.setDrawableId(R.drawable.p_rk1);
//            luke.setItemDesc("录课");
//        }
//         luke.setId(2);
//        list.add(luke);

        PopModel tuiping = new PopModel();
        if (Constant.tuiping) {
            tuiping.setDrawableId(R.drawable.screen_control2);
            tuiping.setItemDesc("结束推屏");
        } else {
            tuiping.setDrawableId(R.drawable.p_screen_control1);
            tuiping.setItemDesc("推屏");
        }
        tuiping.setId(3);
        list.add(tuiping);

        PopModel huibi = new PopModel();
        huibi.setDrawableId(R.drawable.p_u1750);
        huibi.setItemDesc("画笔");
        huibi.setId(4);
        list.add(huibi);

        PopModel chaping = new PopModel();
        chaping.setDrawableId(R.drawable.p_linkage);
        chaping.setItemDesc("查屏");
        chaping.setId(5);
        list.add(chaping);

        PopModel suoping = new PopModel();
        suoping.setDrawableId(R.drawable.p_lock);
        suoping.setItemDesc("锁屏");
        suoping.setId(6);
        list.add(suoping);

        PopModel jingying = new PopModel();
        jingying.setDrawableId(R.drawable.p_mute1);
        jingying.setItemDesc("静音");
        jingying.setId(7);
        list.add(jingying);

        PopModel wenjian = new PopModel();
        wenjian.setDrawableId(R.drawable.p_send_msg);
        wenjian.setItemDesc("文件");
        wenjian.setId(8);
        list.add(wenjian);

//        PopModel shezhi = new PopModel();
//        shezhi.setDrawableId(R.drawable.p_setting);
//        shezhi.setItemDesc("设置");
//        shezhi.setId(9);
//        list.add(shezhi);

        return list;
    }

    public static int dp2px(Context context, float value) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5);
    }
}
