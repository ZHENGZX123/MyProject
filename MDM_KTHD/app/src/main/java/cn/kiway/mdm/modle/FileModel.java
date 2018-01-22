package cn.kiway.mdm.modle;

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
    public String sender;//空就是截图资料

}
