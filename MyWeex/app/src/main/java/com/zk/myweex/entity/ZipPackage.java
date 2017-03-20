package com.zk.myweex.entity;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/2.
 */


public class ZipPackage extends RealmObject {

    public String name;//名字
    public String indexPath;//index的路径
    public String version;//当前版本号

    //包大小
    //更新日期

    //增量更新
    public String patchs = "";

}
