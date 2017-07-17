package cn.kiway.homework.util;

import android.content.Context;

import cn.kiway.homework.entity.HTTPCache;
import cn.kiway.homework.entity.KV;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ResourceUtil {

    private Context c;

    public ResourceUtil(Context c) {
        this.c = c;
    }

    public HTTPCache searchResourceByUrl(String url, String tagname) {

        //0.url是不是查题目,如果不是，直接返回null
//        if (!request.contains("book")) {
//            return null;
//        }
        //1.搜索题库，如果没有，返回null
        //url==>key
        String key = url.replace("", "");
        KV a = new MyDBHelper(c).getKVByK(key);
        if (a == null) {
            return null;
        }
        HTTPCache cache = new HTTPCache();
        cache.tagname = tagname;
        cache.response = a.v;
        //2.返回cache，其中cache.response要和服务器返回的一致。json
        return cache;
    }

}
