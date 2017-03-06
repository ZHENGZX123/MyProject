package com.zk.myweex.entity;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/6.
 */

public class HttpCache extends RealmObject {

    public String url;
    public String param;
    public String result;


    @Override
    public String toString() {
        return url + " " + param + " " + result;
    }
}
