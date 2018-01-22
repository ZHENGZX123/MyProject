package cn.kiway.mdm.model;

/**
 * Created by Administrator on 2017/11/15.
 */

public class FileModel {

    @Override
    public String toString() {
        return "FileModel{" +
                "filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", time='" + time + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }

    public String filename;
    public String filepath;
    public String time;
    public String sender;//snapshot就是截图资料


    public FileModel(String filename, String filepath, String time, String sender) {
        this.filename = filename;
        this.filepath = filepath;
        this.time = time;
        this.sender = sender;
    }

    public FileModel() {
    }
}
