package cn.kiway.mdm.model;

/**
 * Created by Administrator on 2017/11/15.
 */

public class FileModel {

    public String size;
    public String name;
    public String id;
    public String userId;
    public String url;//snapshot就是截图资料
    public String type;
    public String createDate;


    public FileModel(String size,String name, String id, String userId, String url,String type,String createDate) {
        this.size=size;
        this.name = name;
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.type = type;
        this.createDate = createDate;
    }

    public FileModel() {
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
