package cn.kiway.mdm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
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

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.Course0Activity;
import cn.kiway.mdm.activity.CourseListActivity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
import cn.kiway.mdm.teacher.R;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_DIANMING;

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

    public static void checkNetWork(Context context, boolean reConnect) {
        //获取手机的连接服务管理器，这里是连接管理器类
        try {
            boolean available = NetworkUtil.isNetworkAvailable(context);
            Message msg = new Message();
            if (available) {
                msg.what = 1;
                msg.arg1 = 1;
                msg.arg2 = reConnect ? 1 : 0;
            } else {
                msg.what = 1;
                msg.arg1 = 0;
                msg.arg2 = reConnect ? 1 : 0;
            }
            MainActivity.instance.mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void openFile(Context context, String filePath) {
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
                        Toast.makeText(context, context.getString(R.string.mobile_no_office), Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    /**
     * 获取和保存当前屏幕的截图
     */
    public static void GetandSaveCurrentImage(Activity c) {
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
        String SavePath = "/mnt/sdcard/";
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
                Toast.makeText(c, "截屏文件已保存至SD卡根目录", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        File f = new File("/mnt/sdcard/", picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
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

    private static String wifiIp;

    public static void shangke(Activity c, String wifiIp) {
        Utils.wifiIp = wifiIp;
        try {
            //1.发“上课”推送命令
            ((BaseActivity) c).showPD();
            String url = KWApplication.clientUrl + "/device/push/teacher/attendClass?flag=1&ip=" + wifiIp + "&platform=Android";
            Log.d("test", "shangke url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.post(c, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "shangke onSuccess = " + ret);
                    try {
                        ((BaseActivity) c).dismissPD();
                        int statusCode = new JSONObject(ret).optInt("statusCode");
                        if (statusCode == 200) {
                            c.startActivity(new Intent(c, StudentGridActivity.class).putExtra("type", TYPE_DIANMING));
                        } else {
                            String errorMsg = new JSONObject(ret).optString("errorMsg");
                            toast(c, errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "shangke onFailure = " + s);
                    if (!check301(c, s, "shangke")) {
                        ((BaseActivity) c).dismissPD();
                        toast(c, "请求失败，请稍后再试");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast(c, "请求失败，请稍后再试");
            ((BaseActivity) c).dismissPD();
        }
    }

    public static void xiake(Activity c) {
        try {
            //1.发“下课”推送命令
            String url = KWApplication.clientUrl + "/device/push/teacher/attendClass?flag=2&ip=0.0.0.0&platform=Android";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.post(c, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    if (!check301(c, s, "xiake")) {
                        toast(c, "请求失败，请稍后再试");
                        ((BaseActivity) c).dismissPD();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast(c, "请求失败，请稍后再试");
        }
    }

    private static String courseID;

    public static void endClass(Activity c, String courseID) {
        Utils.courseID = courseID;
        try {
            ((BaseActivity) c).showPD();
            //1.发“下课”推送命令
            String url = KWApplication.clientUrl + "/device/teacher/course/" + courseID + "/attend";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.post(c, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "endClass onSuccess = " + ret);
                    ((BaseActivity) c).dismissPD();
                    ((BaseActivity) c).finish();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "endClass onFailure = " + s);
                    if (!check301(c, s, "endclass")) {
                        toast(c, "请求失败，请稍后再试");
                        ((BaseActivity) c).dismissPD();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast(c, "请求失败，请稍后再试");
            ((BaseActivity) c).dismissPD();
        }
    }

    public static boolean check301(final Activity c, String result, String type) {
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
            String url = KWApplication.clientUrl + "/device/teacher/login";
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
                        c.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
                        if (type.equals("shangke")) {
                            shangke(c, wifiIp);
                        } else if (type.equals("xiake")) {
                            xiake(c);
                        } else if (type.equals("endclass")) {
                            endClass(c, courseID);
                        } else if (type.equals("students")) {
                            ((StudentGridActivity) KWApplication.currentActivity).initData();
                        } else if (type.equals("dianming")) {
                            ((StudentGridActivity) KWApplication.currentActivity).doStartSign();
                        } else if (type.equals("courselist")) {
                            ((CourseListActivity) KWApplication.currentActivity).initData();
                        } else if (type.equals("coursedetail")) {
                            ((Course0Activity) KWApplication.currentActivity).initData();
                        } else if (type.equals("knowledgeCount")) {
                            ((StudentGridActivity) KWApplication.currentActivity).doEndTongji();
                        }
                    } catch (Exception e) {
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
}
