package cn.kiway.mdm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/22. */

public class Fenxi implements Serializable{

    public String content;//内容，可能是图片。
    public int type; //知识点1 测评题2 抽答题3
    public String time;
    public int status;
    public String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
