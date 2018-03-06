package cn.kiway.hybird.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.huawei.android.pushagent.api.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lingala.zip4j.core.ZipFile;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cn.kiway.countly.CountlyUtil;
import cn.kiway.database.entity.KV;
import cn.kiway.database.util.KwDBHelper;
import cn.kiway.http.FileUtils;
import cn.kiway.http.HttpDownload;
import cn.kiway.http.NetworkUtil;
import cn.kiway.hybird.KwAPP;
import cn.kiway.hybird.util.Utils;
import cn.kiway.log.MLog;
import cn.kiway.sharedpref.SPUtil;
import cn.kiway.utils.Configue;
import ly.count.android.api.Countly;

import static cn.kiway.hybird.util.Utils.SYS_EMUI;
import static cn.kiway.hybird.util.Utils.SYS_MIUI;
import static cn.kiway.utils.Configue.KwAppId;
import static cn.kiway.utils.Configue.KwModule;
import static cn.kiway.utils.Configue.ZIP;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {

    public void huaweiPush() {
        if (Utils.getSystem().equals(SYS_EMUI)) {
            PushManager.requestToken(this);
            Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());

        }
    }

    public void toast(final String txt) {
        MLog.d("test", "toast is called");
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getMimeType(String url) {
        String type = null;
        if (url.toLowerCase(Locale.CHINA).endsWith(".w")) {
            type = "text/html";
        } else {
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                type = mime.getMimeTypeFromExtension(extension);
            }
        }
        return type;
    }

    public InputStream getStreamByUrl(String url) {
        try {
            InputStream is = null;
            Bitmap b = ImageLoader.getInstance().loadImageSync(url, KwAPP.getLoaderOptions());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, baos);
            is = new ByteArrayInputStream(baos.toByteArray());
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getStreamByUrl2(String url) {
        try {
            File file = new File(url);
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int r;
            byte[] max = new byte[1 * 1024 * 1024];
            int pos = 0;
            while ((r = is.read(buffer)) > 0) {
                System.arraycopy(buffer, 0, max, pos, r);
                pos += r;
            }
            is.close();
            byte[] read = new byte[pos];
            System.arraycopy(max, 0, read, 0, pos);
            System.out.println(read.length);
            byte[] result = decrypt(read, "kiwaykiway123456abcdefghijklmnop");
            System.out.println("解密后：" + new String(result));
            InputStream is2 = new ByteArrayInputStream(result);
            return is2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    public void installationPush() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String accessToken = SPUtil.instance().getValue("accessToken", "");
            String userId = SPUtil.instance().getValue("userId", "");
            String xiaomitoken = SPUtil.instance().getValue("xiaomitoken", "");
            String huaweitoken = SPUtil.instance().getValue("huaweitoken", "");
            String othertoken = SPUtil.instance().getValue("othertoken", "");
            client.addHeader("X-Auth-Token", accessToken);
            MLog.d("test", "xiaomitoken = " + xiaomitoken);
            MLog.d("test", "huaweitoken = " + huaweitoken);
            MLog.d("test", "othertoken = " + othertoken);
            String type = Utils.getSystem();
            String deviceId = "";
            switch (type) {
                case SYS_MIUI:
                    deviceId = xiaomitoken;
                    break;
                case SYS_EMUI:
                    deviceId = huaweitoken;
                    break;
                case Utils.SYS_OTHER:
                    deviceId = othertoken;
                    break;
            }
            JSONObject param = new JSONObject();
            param.put("appId", KwAppId);
            param.put("deviceId", deviceId);
            param.put("type", type);
            param.put("module", KwModule);
            param.put("userId", userId);
            Log.d("push", "param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            client.post(this, Configue.host + Configue.url_install, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("push", "installationPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("push", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("push", "e = " + e.toString());
        }
    }

    public void uninstallationPush() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String accessToken = SPUtil.instance().getValue("accessToken", "");
            String userId = SPUtil.instance().getValue("userId", "");
            String xiaomitoken = SPUtil.instance().getValue("xiaomitoken", "");
            String huaweitoken = SPUtil.instance().getValue("huaweitoken", "");
            String othertoken = SPUtil.instance().getValue("othertoken", "");
            client.addHeader("X-Auth-Token", accessToken);
            MLog.d("test", "xiaomitoken = " + xiaomitoken);
            MLog.d("test", "huaweitoken = " + huaweitoken);
            MLog.d("test", "othertoken = " + othertoken);
            String type = Utils.getSystem();
            String deviceId = "";
            switch (type) {
                case SYS_MIUI:
                    deviceId = xiaomitoken;
                    break;
                case SYS_EMUI:
                    deviceId = huaweitoken;
                    break;
                case Utils.SYS_OTHER:
                    deviceId = othertoken;
                    break;
            }
            JSONObject param = new JSONObject();
            param.put("appId", KwAppId);
            param.put("deviceId", deviceId);
            param.put("type", type);
            param.put("module", KwModule);
            param.put("userId", userId);
            Log.d("push", "uninstall param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            client.post(this, Configue.host + Configue.url_uninstall, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("push", "uninstall onSuccess = " + ret);
                    SPUtil.instance().setValue("accessToken", "");
                    SPUtil.instance().setValue("userId", "");
                    new KwDBHelper(getApplicationContext()).deleteAllHttpCache();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("push", "uninstall onFailure = " + s);
                    SPUtil.instance().setValue("accessToken", "");
                    SPUtil.instance().setValue("userId", "");
                    new KwDBHelper(getApplicationContext()).deleteAllHttpCache();
                }
            });
        } catch (Exception e) {
            Log.d("push", "e = " + e.toString());
            SPUtil.instance().setValue("accessToken", "");
            SPUtil.instance().setValue("userId", "");
            new KwDBHelper(getApplicationContext()).deleteAllHttpCache();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Countly.sharedInstance().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Countly.sharedInstance().onStop();
    }

    public void getBooks() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        String token = SPUtil.instance().getValue("accessToken", "");
        client.addHeader("X-Auth-Token", token);
        String url = Configue.host + String.format(Configue.url_getbooks, token);
        client.get(this, url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                MLog.d("test", "getBooks onSuccess = " + ret);
                downloadBooks(ret);
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                MLog.d("test", "getBooks onFailure = " + s);
            }
        });
    }

    private void downloadBooks(final String ret) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONArray array = new JSONObject(ret).getJSONArray("data");
                    int count = array.length();
                    for (int i = 0; i < count; i++) {
                        JSONObject o = array.getJSONObject(i);
                        String id = o.getString("id");
                        MLog.d("test", "id = " + id);
                        //0.检查本地是否存在
                        if (new File(Configue.BOOKS + id + ".zip").exists()) {
                            continue;
                        }
                        //1.下载
                        String token = SPUtil.instance().getValue("accessToken", "");
                        String downloadurl = Configue.host + String.format(Configue.url_downloadBooks, id, token);
                        MLog.d("test", "downloadurl = " + downloadurl);
                        int ret = new HttpDownload().downFile(downloadurl, "/mnt/sdcard/books/", id + ".zip");
                        MLog.d("test", "下载结果 ret = " + ret);
                        if (ret == 0) {
                            //2.解压
                            new ZipFile(Configue.BOOKS + id + ".zip").extractAll(Configue.BOOKS + id);
                            //3.读取data.json文件
                            String filepath = Configue.BOOKS + id + "/data.json";
                            String content = FileUtils.readSDCardFile(filepath, getApplicationContext());
                            MLog.d("test", "content = " + content);
                            JSONObject all = new JSONObject(content);
                            Iterator<String> keys = all.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = all.getString(key);
                                MLog.d("test", "key = " + key);
                                MLog.d("test", "value = " + value);
                                //4.插入数据库
                                KV a = new KV();
                                a.k = key;
                                a.v = value;
                                new KwDBHelper(getApplicationContext()).addKV(a);
                            }
                            MLog.d("test", "book" + id + "插入sql完毕");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Dialog dialog_download;

    public void checkNewAPK() {
        //apkUrl , apkVersion
        String apkUrl = getIntent().getStringExtra("apkUrl");
        String apkVersion = getIntent().getStringExtra("apkVersion");
        if (TextUtils.isEmpty(apkUrl)) {
            return;
        }
        if (TextUtils.isEmpty(apkVersion)) {
            return;
        }
        if (Utils.getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
            downloadSilently(apkUrl, apkVersion);
        }
    }

    private void downloadSilently(final String apkUrl, String version) {
        final String savedFilePath = "/mnt/sdcard/cache/" + ZIP + "_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            MLog.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        int isWifi = NetworkUtil.isWifi(this);
        if (isWifi == 1) {
            startDownloadAPK(apkUrl, savedFilePath);
        } else if (isWifi == 0) {
            MLog.d("test", "不是wifi...");
            //提示4G
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            dialog_download = builder.setMessage("有新的版本需要更新，您当前的网络是4G，确定使用流量下载新的APK吗？").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    toast("后台下载APK文件");
                    dialog_download.dismiss();
                    startDownloadAPK(apkUrl, savedFilePath);
                }
            }).setPositiveButton(android.R.string.cancel, null).create();
            dialog_download.show();
        }
    }

    private void startDownloadAPK(String apkUrl, final String savedFilePath) {
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage("发现新的版本，是否更新？本次更新不消耗流量。").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog_download.dismiss();
                CountlyUtil.getInstance().addEvent("升级APP");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            }
        }).setPositiveButton(android.R.string.cancel, null).create();
        dialog_download.show();
    }


}
