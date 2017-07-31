package cn.kiway.homework.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.huawei.android.pushagent.api.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lingala.zip4j.core.ZipFile;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

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

import cn.kiway.homework.WXApplication;
import cn.kiway.homework.entity.KV;
import cn.kiway.homework.util.FileUtils;
import cn.kiway.homework.util.HttpDownload;
import cn.kiway.homework.util.MyDBHelper;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {
    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());
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
            Bitmap b = ImageLoader.getInstance().loadImageSync(url, WXApplication.getLoaderOptions());
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
        long start = System.currentTimeMillis();
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
            Log.d("test", "解密文件" + url + "，耗时:" + (System.currentTimeMillis() - start));
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

    public void getBooks() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        String token = getSharedPreferences("homework", 0).getString("token", "");
        client.addHeader("X-Auth-Token", token);
        client.get(this, "http://202.104.136.9:8389/teacher/book", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                Log.d("test", "get onSuccess = " + ret);
                downloadZip(ret);
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.d("test", "get onFailure = " + s);
            }
        });
    }

    private void downloadZip(final String ret) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONArray array = new JSONObject(ret).getJSONArray("data");
                    int count = array.length();
                    for (int i = 0; i < count; i++) {
                        JSONObject o = array.getJSONObject(i);
                        String id = o.getString("id");
                        Log.d("test", "id = " + id);
                        //0.检查本地是否存在
                        if (new File(WXApplication.BOOKS + id + ".zip").exists()) {
                            continue;
                        }
                        //1.下载
                        int ret = new HttpDownload().downFile("http://202.104.136.9:8389/resource/book/" + id, "/mnt/sdcard/books/", id + ".zip");
                        Log.d("test", "下载结果 ret = " + ret);
                        if (ret == 0) {
                            //2.解压
                            new ZipFile(WXApplication.BOOKS + id + ".zip").extractAll(WXApplication.BOOKS + id);
                            //3.读取data.json文件
                            String filepath = WXApplication.BOOKS + id + "/data.json";
                            String content = FileUtils.readSDCardFile(filepath, getApplicationContext());
                            Log.d("test", "content = " + content);
                            JSONObject all = new JSONObject(content);
                            Iterator<String> keys = all.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = all.getString(key);
                                Log.d("test", "key = " + key);
                                Log.d("test", "value = " + value);
                                //4.插入数据库
                                KV a = new KV();
                                a.k = key;
                                a.v = value;
                                new MyDBHelper(getApplicationContext()).addKV(a);
                            }
                            Log.d("test", "book" + id + "插入sql完毕");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
