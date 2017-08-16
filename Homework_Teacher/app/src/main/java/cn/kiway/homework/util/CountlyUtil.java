package cn.kiway.homework.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ly.count.android.api.Countly;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CountlyUtil {

    private static CountlyUtil instance;
    private static Map<String, Integer> events;

    private CountlyUtil() {
    }

    public static CountlyUtil getInstance() {
        if (instance == null) {
            instance = new CountlyUtil();
            events = new HashMap<>();
        }
        return instance;
    }

    public static void addEvent(String event) {
        //1.加1条记录
        if (events.containsKey(event)) {
            events.put(event, events.get(event).intValue() + 1);
        } else {
            events.put(event, 1);
        }
        //2.打印日志
        int totalcount = 0;
        Iterator iterator = events.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            int count = (Integer) entry.getValue();
            Log.d("countly", key + " , " + count);
            totalcount += count;
        }
        //3.攒够50条，发出去
        if (totalcount >= 50) {
            sendAll();
        }
    }

    public static synchronized void sendAll() {
        Iterator iterator = events.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            int count = (Integer) entry.getValue();
            Countly.sharedInstance().recordEvent(key, count);
        }
        events.clear();
    }

}
