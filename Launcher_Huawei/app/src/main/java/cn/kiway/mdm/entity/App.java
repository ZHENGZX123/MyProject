package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class App implements Serializable {

    public String name;
    public String packageName;
    //public Drawable icon;
    public boolean selected;

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", selected=" + selected +
                '}';
    }
}
