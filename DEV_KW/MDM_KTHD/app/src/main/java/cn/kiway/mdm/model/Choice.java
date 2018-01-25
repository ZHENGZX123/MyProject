package cn.kiway.mdm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/25.
 */

public class Choice implements Serializable {

    public String content;
    public boolean selected;


    public Choice(String content, boolean selected) {
        this.content = content;
        this.selected = selected;
    }
}
