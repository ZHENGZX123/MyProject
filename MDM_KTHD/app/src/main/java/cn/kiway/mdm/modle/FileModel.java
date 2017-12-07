package cn.kiway.mdm.modle;

/**
 * Created by Administrator on 2017/11/15.
 */

public class FileModel {
    public String filename;
    public String filepath;
    public String time;

    @Override
    public String toString() {
        return "FileModel{" +
                "filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
