package cn.kiway.robot.util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/13.
 */

public class FileDownload {

    public ArrayList<String> urls = new ArrayList<>();
    public ArrayList<String> filepaths = new ArrayList<>();
    public int status; //0未开始下载 1下载中 2下载成功 3.下载失败 4.已发给机器人
    public String original;
    public int successUrl = 0;

    public FileDownload(){

    }

    public FileDownload(ArrayList<String> urls, ArrayList<String> filepaths, int status, String original) {
        this.urls = urls;
        this.filepaths = filepaths;
        this.status = status;
        this.original = original;
    }

    @Override
    public String toString() {
        return "FileDownload{" +
                "urls=" + urls +
                ", filepaths=" + filepaths +
                ", status=" + status +
                ", original='" + original + '\'' +
                '}';
    }
}
