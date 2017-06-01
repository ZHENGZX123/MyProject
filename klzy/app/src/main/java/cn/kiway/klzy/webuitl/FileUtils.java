package cn.kiway.klzy.webuitl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Android on 2016/4/20.
 */
public class FileUtils {


    //创建私有文件夹
    public static String createZipFloder() {
        File file = new File(Environment.getExternalStorageDirectory(),
                "klzy");
        if (!file.exists())
            file.mkdirs();
        File file1 = new File(Environment.getExternalStorageDirectory(), ".nomedia");
        if (!file1.exists())
            file1.mkdirs();
        return file.getAbsolutePath();
    }

    //创建私有文件夹
    public static String createDocFloder() {
        File file1 = new File(createZipFloder(), "doc");
        if (!file1.exists())
            file1.mkdirs();
        return file1.getAbsolutePath();
    }
    //创建私有文件夹
    public static String createImgFloder() {
        File file1 = new File(createZipFloder(), "img   ");
        if (!file1.exists())
            file1.mkdirs();
        return file1.getAbsolutePath();
    }
    public static void openFile(Context context, String filePath) {
        filePath = "file://" + filePath;
        String type = filePath.split("\\.")[1].toLowerCase();
        String typeOpenFile = "*";
        Log.e("*****************", filePath);
        Log.e("*****************", type);
        if (type.equals("pdf"))
            typeOpenFile = "application/pdf";
        else if (type.equals("ppt") || type.equals("pptx"))
            typeOpenFile = "application/vnd.ms-powerpoint";
        else if (type.equals("doc") || type.equals("docx") || type.equals("docm") || type.equals("dotx") || type
                .equals("dotm"))
            typeOpenFile = "application/msword";
        else if (type.equals("xlsx") || type.equals("xlsm") || type.equals("xltx"))
            typeOpenFile = "application/vnd.ms-excel";
        else if (type.equals("mp3") || type.equals("amr") || type.equals("ogg") || type.equals("wav")) {
            typeOpenFile = "audio/*";
        } else if (type.equals("mp4") || type.equals("3gp") || type.equals("avi") || type.equals("rmvb") || type
                .equals("mpg") | type.equals("rm") || type.equals("flv") | type.equals("swf")) {
            typeOpenFile = "video/*";
        }else if (type.equals("png") || type.equals("jpg") || type.equals("jpeg") || type.equals("dxf") || type
                .equals("gif") | type.equals("tiff") || type.equals("psd") | type.equals("raw")) {
            typeOpenFile = "video/*";
        }
        if (!typeOpenFile.equals("")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse(filePath), typeOpenFile);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                if (typeOpenFile.equals("video/*") || typeOpenFile.equals("audio/*"))
                    Toast.makeText(context, "手机没有安装相关的播放器，请下载安装", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "手机没有安装相关的办公软件，请下载安装", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "未知类型文件，无法打开", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 检查SD卡是否挂载
     *
     * @return
     */
    public static boolean checkSDcard(Context context){
        boolean flag = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!flag) {
            Toast.makeText(context, "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
}


