package com.zk.myweex.entity;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TabEntity extends RealmObject {


    //TODO 新增id属性,name用来做显示用。  zip包名和id一致
    public int sort;
    public String name;
    public String image_default;
    public String image_selected;
}
