package com.android.kiway.entity;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Network {

    public String id;
    public String url;
    public int type;
    public int enable;
    public String operation;

    @Override
    public String toString() {
        return "Network{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", enable=" + enable +
                ", operation='" + operation + '\'' +
                '}';
    }
}
