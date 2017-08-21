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

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cn.kiway.homework.WXApplication;
import cn.kiway.homework.util.CountlyUtil;
import cn.kiway.homework.util.Utils;
import ly.count.android.api.Countly;

import static cn.kiway.homework.util.Utils.SYS_EMUI;
import static cn.kiway.homework.util.Utils.SYS_MIUI;

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
            String accessToken = getSharedPreferences("kiway", 0).getString("accessToken", "");
            String userId = getSharedPreferences("kiway", 0).getString("userId", "");
            String xiaomitoken = getSharedPreferences("kiway", 0).getString("xiaomitoken", "");
            String huaweitoken = getSharedPreferences("kiway", 0).getString("huaweitoken", "");
            String othertoken = getSharedPreferences("kiway", 0).getString("othertoken", "");
            client.addHeader("X-Auth-Token", accessToken);
            Log.d("test", "xiaomitoken = " + xiaomitoken);
            Log.d("test", "huaweitoken = " + huaweitoken);
            Log.d("test", "othertoken = " + othertoken);
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
            param.put("appId", "c77b6c47dbcee47d7ffbc9461da0c82a");
            param.put("deviceId", deviceId);
            param.put("type", type);
            param.put("module", "teacher");
            param.put("userId", userId);
            Log.d("push", "param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            client.post(this, WXApplication.url + "/push/installation", stringEntity, "application/json", new TextHttpResponseHandler() {
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

    @Override
    protected void onStart() {
        super.onStart();
        Countly.sharedInstance().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CountlyUtil.getInstance().sendAll();
        Countly.sharedInstance().onStop();
    }
}
