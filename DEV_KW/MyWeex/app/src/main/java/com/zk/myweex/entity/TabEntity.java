package com.zk.myweex.entity;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TabEntity extends RealmObject {
    public int sort;
    public String name;
    public String image_default;
    public String image_selected;
}
