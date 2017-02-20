package com.zk.weexdemo.util;

import android.content.Context;
import android.os.Environment;

import com.taobao.weex.utils.WXLogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Android on 2016/4/20.
 */
public class FileUtils {
    private String SDPATH;

    public String getSDPATH() {
        return SDPATH;
    }

    //构造方法
    public FileUtils() {
        //得到当前外部存储设备的目录      /SDCARD/...
        SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    public File writeToSDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        FileOutputStream output = null;
        try {
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            String savedFilePath = path + fileName;
            createSDDir(path);
            file = new File(savedFilePath);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int length;
            while ((length = (input.read(buffer))) != -1) {
                output.write(buffer, 0, length);
            }
            //清缓存，将流中的数据保存到文件中
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String loadFile(String path, Context context) {
        if (path == null || context == null) {
            return null;
        }
        StringBuilder builder;
        try {
            File f = new File(path);

            InputStream in = new FileInputStream(f);

            builder = new StringBuilder(in.available() + 10);

            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(in));
            char[] data = new char[2048];
            int len = -1;
            while ((len = localBufferedReader.read(data)) > 0) {
                builder.append(data, 0, len);
            }
            localBufferedReader.close();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    WXLogUtils.e("WXFileUtils loadAsset: ", e);
                }
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            WXLogUtils.e("", e);
        }

        return "";
    }
}
