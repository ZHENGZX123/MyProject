package cn.kiway.homework.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

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

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {
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
}
