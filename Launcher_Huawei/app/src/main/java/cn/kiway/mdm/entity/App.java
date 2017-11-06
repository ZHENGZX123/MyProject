package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class App implements Serializable {

    public String name;
    public String packageName;
    public String versionName;
    public int versionCode;
    public boolean selected;
    public int category;

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", selected=" + selected +
                ", category=" + category +
                '}';
    }
}
