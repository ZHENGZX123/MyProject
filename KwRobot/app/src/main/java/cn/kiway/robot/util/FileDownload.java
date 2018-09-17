package cn.kiway.robot.util;

/**
 * Created by Administrator on 2018/9/13.
 */

public class FileDownload {

    public String url;
    public String filepath;
    public int status; //0未开始下载 1下载中 2下载成功 3.下载失败 4.已发给机器人
    public String original;

    public FileDownload(String url, String filepath, int status, String original) {
        this.url = url;
        this.filepath = filepath;
        this.status = status;
        this.original = original;
    }

    @Override
    public String toString() {
        return "FileDownload{" +
                "url='" + url + '\'' +
                ", filepath='" + filepath + '\'' +
                ", status=" + status +
                ", original='" + original + '\'' +
                '}';
    }
}
