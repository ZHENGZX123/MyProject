package cn.kiway.hybird.util;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.smtt.sdk.WebView;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;

import cn.kiway.countly.CountlyUtil;
import cn.kiway.database.entity.HTTPCache;
import cn.kiway.database.util.KwDBHelper;
import cn.kiway.database.util.ResourceUtil;
import cn.kiway.http.UploadUtil;
import cn.kiway.hybird.activity.MainActivity;
import cn.kiway.hybird.teacher.R;
import cn.kiway.log.MLog;
import cn.kiway.record.RecordAudioUtil;
import cn.kiway.sharedpref.SPUtil;
import cn.kiway.utils.Configue;
import uk.co.senab.photoview.sample.ViewPagerActivity;

/**
 * Created by Administrator on 2018/3/1.
 */

public class KwJsInterface {
    private MainActivity act;
    private WebView wv;

    public KwJsInterface(MainActivity act, WebView wv) {
        this.act = act;
        this.wv = wv;
    }

    @JavascriptInterface
    public void setKeyAndValue(String key, String value) {
        MLog.d("test", "setKeyAndValue is called , key = " + key + " , value = " + value);
        if (TextUtils.isEmpty(key)) {
            return;
        }
        //TODO 加密
        SPUtil.instance().setValue(key, value);
    }

    @JavascriptInterface
    public String getValueByKey(String key) {
        MLog.d("test", "getValueByKey is called , key = " + key);
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        //TODO 解密
        String ret = SPUtil.instance().getValue(key, "");
        MLog.d("test", "ret = " + ret);
        return ret;
    }

    @JavascriptInterface
    public String getOS() {
        MLog.d("test", "getOS is called");
        return "Android";
    }

    @JavascriptInterface
    public void scanQR() {
        act.startActivityForResult(new Intent(act, CaptureActivity.class), QRSCAN);
    }


    @JavascriptInterface
    public void login(String param) {
        MLog.d("test", "login param = " + param);
        try {
            String accessToken = new JSONObject(param).optString("accessToken");
            String userId = new JSONObject(param).optString("userId");
            String schoolName = new JSONObject(param).optString("schoolName");
            SPUtil.instance().setValue("accessToken", accessToken);
            SPUtil.instance().setValue("userId", userId);
            SPUtil.instance().setValue("schoolName", schoolName);
            act.installationPush();
            //getBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void logout() {
        MLog.d("test", "logout");
        act.uninstallationPush();
    }

    @JavascriptInterface
    public String getCacheSize() {
        return "1.5M";
    }

    @JavascriptInterface
    public void clearCache() {
        act.toast("清理完毕");
    }

    @JavascriptInterface
    public void showPhoto(String param1, String param2) {
        try {
            try {
                MLog.d("test", "showPhoto param1 = " + param1);
                MLog.d("test", "showPhoto param2 = " + param2);
                ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
                Intent intent = new Intent(act, ViewPagerActivity.class);
                intent.putExtra("position", Integer.parseInt(param2));
                act.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void fileUpload(String filepath) {
        filepath = filepath.replace("file://", "");
        MLog.d("test", "fileUpload , filepath = " + filepath);
        //选择图片
        final String finalFilepath = filepath;
        new Thread() {
            @Override
            public void run() {
                String token = SPUtil.instance().getValue("accessToken", "");
                MLog.d("test", "取出token=" + token);
                File file = new File(finalFilepath);
                final String ret = UploadUtil.uploadFile(file, Configue.host + String.format(Configue.url_upload, token), file.getName());
                MLog.d("test", "upload ret = " + ret);
                if (TextUtils.isEmpty(ret)) {
                    act.toast("上传图片失败，请稍后再试");
                    return;
                }
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(ret);
                            String url = Configue.host + obj.getJSONObject("data").getString("url");
                            obj.getJSONObject("data").put("url", url);
                            MLog.d("test", "obj = " + obj.toString());
                            wv.loadUrl("javascript:fileUploadCallback(" + obj.toString() + ")");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

    @JavascriptInterface
    public String getVersionCode() {
        MLog.d("test", "getVersionCode");
        return SPUtil.instance().getValue("version_package", "0.0.1");
    }

    @JavascriptInterface
    public void record() {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final RecordAudioUtil rau = new RecordAudioUtil();
                rau.init(act);
                rau.stopRecord();

                final Dialog dialog = new Dialog(act, R.style.popupDialog);
                dialog.setContentView(R.layout.dialog_voice);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final Button voice = (Button) dialog.findViewById(R.id.voice);
                final TextView time = (TextView) dialog.findViewById(R.id.time);
                voice.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        String ret = rau.stopRecord();
                        if (!TextUtils.isEmpty(ret)) {
                            wv.loadUrl("javascript:recordCallback('file://" + ret + "')");
                        }
                        dialog.dismiss();
                    }
                });
                new Thread() {
                    @Override
                    public void run() {
                        int duration = 0;
                        while (dialog.isShowing()) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            duration++;
                            final int finalDuration = duration;
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    time.setText(Utils.getTimeFormLong(finalDuration * 1000));
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }

    @JavascriptInterface
    public void selectPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
        imagePicker.setSelectLimit(1);// 设置可以选择几张
        imagePicker.setMultiMode(false);// 是否为多选
        imagePicker.setCrop(true);// 是否剪裁
        imagePicker.setFocusWidth(1000);// 需要剪裁的宽
        imagePicker.setFocusHeight(1000);// 需要剪裁的高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
        imagePicker.setShowCamera(true);// 是否显示摄像

        Intent intent = new Intent(act, ImageGridActivity.class);
        act.startActivityForResult(intent, SELECT_PHOTO);
    }

    @JavascriptInterface
    public void snapshot() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        snapshotFile = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Uri uri = Uri.fromFile(new File(snapshotFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        act.startActivityForResult(intent, SNAPSHOT);
    }

    @JavascriptInterface
    public void setRequestedOrientation(String screen) {
        MLog.d("test", "setRequestedOrientation = " + screen);
        if (Integer.parseInt(screen) == 1) {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @JavascriptInterface
    public String isTest() {
        MLog.d("test", "isTest is called");
        return Configue.isTest ? "1" : "0";
    }

    @JavascriptInterface
    public void httpRequest(String url, String param, final String method, String time, String tagname, String related, String event) {
        if (!Configue.isTest) {
            url = url.replace(Configue.ceshiHost, Configue.zhengshiHost);
        }
        try {
            Integer.parseInt(time);
            param = param.replace("\\\"", "\"");
            if (param.startsWith("\"")) {
                param = param.substring(1);
            }
            if (param.endsWith("\"")) {
                param = param.substring(0, param.length() - 1);
            }
        } catch (Exception e) {
            MLog.d("test", "参数错误");
            return;
        }
        MLog.d("test", "httpRequest url = " + url + " , param = " + param + " , method = " + method + " , time = " + time + " , tagname = " + tagname + " , related = " + related + ", event = " + event);
        CountlyUtil.getInstance().addEvent(event);

        if (time.equals("0")) {
            //1.重新获取
            doHttpRequest(url, param, method, tagname, time, related);
        } else {
            //2.取缓存
            String request = url + param + method;
            HTTPCache cache1 = new KwDBHelper(act).getHttpCacheByRequest(request, Integer.parseInt(time));
            if (cache1 == null) {
                MLog.d("test", "没有缓存");
                //3.如果是查询题目的话，还要再查一下资源包
                HTTPCache cache2 = new ResourceUtil(act).searchResourceByUrl(url, tagname);
                if (cache2 == null) {
                    doHttpRequest(url, param, method, tagname, time, related);
                } else {
                    httpRequestCallback(cache2.tagname, cache2.response);
                }
            } else {
                MLog.d("test", "有缓存");
                httpRequestCallback(cache1.tagname, cache1.response);
            }
        }
    }

    public static final int SNAPSHOT = 9999;
    public static final int SELECT_PHOTO = 8888;
    public static final int QRSCAN = 6666;


    public static String snapshotFile;

    private void doHttpRequest(final String url, final String param, final String method, final String tagname, final String time, final String related) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    String token = SPUtil.instance().getValue("accessToken", "");
                    client.addHeader("X-Auth-Token", token);
                    if (method.equalsIgnoreCase("POST")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.post(act, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                MLog.d("test", "post onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(act).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                MLog.d("test", "post onFailure = " + ret);
                                MLog.d("test", "post onFailure = arg0" + arg0);
                                MLog.d("test", "post onFailure = arg3" + arg3.toString());
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("PUT")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.put(act, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                MLog.d("test", "put onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(act).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                MLog.d("test", "put onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("GET")) {
                        String checkUrl = doCheckUrl(url);
                        MLog.d("test", "checkUrl = " + checkUrl);
                        client.get(act, checkUrl, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                MLog.d("test", "get onSuccess = " + ret);
                                saveDB(url, param, method, ret, tagname);
                                httpRequestCallback(tagname, ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                MLog.d("test", "get onFailure = " + ret);
                                //如果是get，把缓存回它
                                String request = url + param + method;
                                HTTPCache cache = new KwDBHelper(act).getHttpCacheByRequest(request, Integer.parseInt(time));
                                if (cache != null) {
                                    httpRequestCallback(cache.tagname, cache.response);
                                } else {
                                    httpRequestCallback(tagname, ret);
                                }
                            }
                        });
                    } else if (method.equalsIgnoreCase("DELETE")) {
                        client.delete(act, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                MLog.d("test", "get onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(act).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                MLog.d("test", "get onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MLog.d("test", "exception = " + e.toString());
                }
            }
        });
    }

    private String doCheckUrl(String url) {
        //1.使用正则
        String split[] = url.split("\\?");
        if (split.length > 1) {
            String queryString = split[1];
            StringBuilder sb = new StringBuilder();
            for (String param : queryString.split("&")) {
                String[] paramSplit = param.split("=");
                String key = paramSplit[0];
                String value = "";
                if (paramSplit.length > 1) {
                    value = paramSplit[1];
                }
                sb.append(key).append("=");
                if (paramSplit.length > 1) {
                    sb.append(URLEncoder.encode(value));
                }
                sb.append("&");
            }
            //sb.deleteCharAt(sb.length() -1);
            queryString = sb.toString();
            url = split[0] + "?" + queryString;
        }
        return url;
    }

    private void saveDB(String url, String param, String method, String ret, String tagname) {
        if (false) {
            return;
        }
        if (TextUtils.isEmpty(ret)) {
            return;
        }
        if (ret.startsWith("<html>")) {
            return;
        }
        MLog.d("test", "saveDB");
        String request = url + param + method;
        HTTPCache cache = new KwDBHelper(act).getHttpCacheByRequest(request);
        if (cache == null) {
            cache = new HTTPCache();
            cache.request = request;
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            cache.tagname = tagname;
            new KwDBHelper(act).addHTTPCache(cache);
        } else {
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            new KwDBHelper(act).updateHTTPCache(cache);
        }
    }

    private void httpRequestCallback(final String tagname, final String result) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String finalResult = "";
                    if (TextUtils.isEmpty(result)) {
                        finalResult = get503String();
                    } else if (result.startsWith("<html>")) {
                        finalResult = get503String();
                    } else {
                        finalResult = result;
                    }
                    String r = finalResult.replace("null", "\"\"").replace("\"\"\"\"", "\"\"");
                    MLog.d("test", "httpRequestCallback , tagname = " + tagname + " , result = " + r);
                    wv.loadUrl("javascript:" + tagname + "(" + r + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String get503String() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("data", "");
        o.put("errorMsg", "请求失败，请检查网络稍后再试");
        o.put("statusCode", 503);
        return o.toString();
    }


}
