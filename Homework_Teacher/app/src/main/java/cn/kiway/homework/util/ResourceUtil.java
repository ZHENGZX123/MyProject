package cn.kiway.homework.util;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (!checkUrl(url)) {
            return null;
        }
        //1.搜索题库，如果没有，返回null
        //TODO url==>key
        String key = url.replace("http://202.104.136.9:8389", "");
        KV a = new MyDBHelper(c).getKVByK(key);
        if (a == null) {
            return null;
        }
        HTTPCache cache = new HTTPCache();
        cache.tagname = tagname;
        cache.response = a.v;
        return cache;
    }

    private boolean checkUrl(String url) {
        String expression1 = "/book/.*/chapter/.*/question";
        String expression2 = "/book/.*/chapter";
        String[] expressions = new String[]{expression1, expression2};
        for (String s : expressions) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}
