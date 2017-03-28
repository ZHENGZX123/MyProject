package cn.kiway.entity;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/6.
 */


public class HttpCache extends RealmObject {

    public String url;
    public String param;
    public String result;
    public long requestTime;


    @Override
    public String toString() {
        return url + " " + param + " " + requestTime;
    }
}
