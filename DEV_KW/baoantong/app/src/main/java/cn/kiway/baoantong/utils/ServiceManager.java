package cn.kiway.baoantong.utils;

import android.util.Log;

import cn.kiway.baoantong.WXApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Service;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ServiceManager {
    private static ServiceManager instance;

    private ServiceManager() {

    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public static ArrayList<Service> downloaded = new ArrayList<>();
    public static ArrayList<Service> notDownloaded = new ArrayList<>();

    public synchronized static void getService() {
        try {
            List<Service> services = new Service().find(new KWQuery().like("name", "function%"));
            Log.d("test", "所有的 function count = " + services.size());

            //已经下载的是：
            downloaded.clear();
            //没有下载的是：
            notDownloaded.clear();
            for (Service s : services) {
                if (new File(WXApplication.PATH + s.get("name").toString() + ".zip").exists()) {
                    downloaded.add(s);
                } else {
                    notDownloaded.add(s);
                }
            }
            Log.d("test", "download function count = " + downloaded.size());
            Log.d("test", "notdownload function count = " + notDownloaded.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
