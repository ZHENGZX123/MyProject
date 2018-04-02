package cn.kiway.mdm.view.popup;

import android.support.annotation.DrawableRes;

import java.io.Serializable;

/**
 * Created by zhanglifeng on 2017/3/28.
 * Pop Data Model
 */

public class PopModel implements Serializable {

    private int drawableId;
    private String itemDesc;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

}
