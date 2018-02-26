package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2018/2/26.
 */

public class FileModel {
    public String name;
    public String size;
    public long time;
    public int staute;
    public String filePath;

    @Override
    public String toString() {
        return "FileModel{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", time=" + time +
                ", staute=" + staute +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
