package cn.kiway.mdm.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;


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
            //下载失败把文件删掉
            file.delete();
        } finally {
            //下载成功再改名
            file.renameTo(new File(path + fileName));
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
                    Log.e("WXFileUtils loadAsset: ", e.toString());
                }
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param ROOT 文件夹完整绝对路径
    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static String readSDCardFile(String path, Context context) {
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
                    Log.e("WXFileUtils loadAsset: ", e.toString());
                }
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", e.toString());
        }

        return "";
    }

    public static void saveFile(String str) {
        String filePath = "/mnt/sdcard/kiway_mdm/imei.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(Context context, String filePath) {
        filePath = "file://" + filePath;
        if (filePath.split("\\.").length < 2) {
            Toast.makeText(context, "未知名文件，无法打开", Toast.LENGTH_SHORT).show();
            return;
        }
        String type = filePath.split("\\.")[1].toLowerCase();
        String typeOpenFile = "*";
        Logger.log("*****************" + filePath);
        Logger.log("*****************" + type);
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
                .equals("mpg") | type.equals("rm") || type.equals("flv")) {
            typeOpenFile = "video/*";
        } else if (type.equals("jpeg") || type.equals("jpg") || type.equals("png") || type.equals("gif")) {
            typeOpenFile = "image/*";
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
        }
    }

    public static String GetFileSize(File file) {
        String size = "";
        if (file.exists() && file.isFile()) {
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) + "GB";
            }
        } else if (file.exists() && file.isDirectory()) {
            size = "";
        } else {
            size = "0BT";
        }
        return size;
    }
}

