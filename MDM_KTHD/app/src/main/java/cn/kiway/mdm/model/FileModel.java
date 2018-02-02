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
    public String userName;


    public FileModel(String size,String name, String id, String userId, String url,String type,String createDate,String userName) {
        this.size=size;
        this.name = name;
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.type = type;
        this.createDate = createDate;
        this.userName=userName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
