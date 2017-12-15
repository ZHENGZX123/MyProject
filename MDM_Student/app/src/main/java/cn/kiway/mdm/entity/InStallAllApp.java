package cn.kiway.mdm.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15.
 */

public class InStallAllApp implements Serializable {
    public String appName;
    public String versionNum;
    public String icon;
    public String ids;
    public String packages;
    public String versionName;
    public String category;
    public String createDate;
    public String record;
    public String flag;

    public JSONObject getRecord() {
        if (record == null || record.equals(""))
            return new JSONObject();
        try {
            return new JSONObject(record);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public String toString() {
        return "InStallAllApp{" +
                "appName='" + appName + '\'' +
                ", versionNum='" + versionNum + '\'' +
                ", icon='" + icon + '\'' +
                ", ids='" + ids + '\'' +
                ", packages='" + packages + '\'' +
                ", versionName='" + versionName + '\'' +
                ", category='" + category + '\'' +
                ", createDate='" + createDate + '\'' +
                ", record=" + record +
                ", flag='" + flag + '\'' +
                ", imei='" + imei + '\'' +
                ", selected=" + selected +
                '}';
    }

    public String imei;
    public boolean selected;

}
