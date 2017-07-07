package cn.kiway.homework.util;

import android.content.Context;

import cn.kiway.homework.entity.HTTPCache;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ResourceUtil {

    private Context c;

    public ResourceUtil(Context c) {
        this.c = c;
    }

    public HTTPCache searchResourceByRequest(String request) {
        HTTPCache cache = null;
        //0.url是不是查题目,如果不是，直接返回null
        if (!request.contains("searchxxx")) {
            return null;
        }
        //1.搜索题库，如果没有，返回null

        //2.返回cache，其中cache.response要和服务器返回的一致。json
        return cache;
    }

}
