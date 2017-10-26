package cn.kiway.mdm.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class FileACache {

    /**
     * 定义一些你项目里面的 缓存文件名字 ，自定义，不要也没关系，调用函数再传入也行
     */

    public static String QzInfo = "Qz_List_Info";
    public static String CyInfo = "Cy_List_Info";
    private static String DataCache = "kiwaylauncher";

    /**
     * 保存 一组 数据
     *
     * @param ctx       上下文
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveListCache(Context ctx, List<T> data, String cacheName) {
        new DataCache<T>().saveGlobal(ctx, data, cacheName);
    }

    /**
     * 保存 一组 数据
     *
     * @param ctx       上下文
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveListCache(Context ctx, ArrayList<T> data, String cacheName) {
        new DataCache<T>().saveGlobal(ctx, data, cacheName);
    }

    /**
     * 直接根据 缓存文件名获取
     */
    public static <T> List<T> loadListCache(Context ctx, String cacheName) {
        return new DataCache<T>().loadGlobal(ctx, cacheName);
    }

    /**
     * 获取 一组 数据
     *
     * @param <T> 数据缓存 save or load
     */
    static class DataCache<T> {
        public void save(Context ctx, ArrayList<T> data, String name) {
            save(ctx, data, name, "");
        }

        public void saveGlobal(Context ctx, List<T> data, String name) {
            save(ctx, data, name, DataCache);
        }

        private void save(Context ctx, List<T> data, String name, String folder) {
            if (ctx == null) {
                return;
            }
            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(Environment.getExternalStorageDirectory(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(Environment.getExternalStorageDirectory(), name);
            }
            if (file.exists()) {
                file.delete();
            }
            Log.d("zzzzz", file.getAbsolutePath());
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(data);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public List<T> load(Context ctx, String name) {
            return load(ctx, name, "");
        }

        public List<T> loadGlobal(Context ctx, String name) {
            return load(ctx, name, DataCache);
        }

        private List<T> load(Context ctx, String name, String folder) {
            List<T> data = null;

            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(Environment.getExternalStorageDirectory(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(Environment.getExternalStorageDirectory(), name);
            }
            Log.e("zzzzz", "file " + file.getAbsolutePath());
            if (file.exists()) {
                try {
                    Log.e("zzzzz", "write object");
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (List<T>) ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    Log.e("zzzzz", e.toString());
                }
            }
            if (data == null) {     /** 如果没有 */
                Log.e("zzzzz", "data == null");
                data = new ArrayList<T>();
            }
            return data;
        }
    }

}
