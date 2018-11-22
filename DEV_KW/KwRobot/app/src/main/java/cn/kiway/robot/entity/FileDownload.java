package cn.kiway.robot.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/13.
 */

public class FileDownload {

    public static final int DOWNLOAD_STATUS_0 = 0;
    public static final int DOWNLOAD_STATUS_1 = 1;
    public static final int DOWNLOAD_STATUS_2 = 2;
    public static final int DOWNLOAD_STATUS_3 = 3;
    public static final int DOWNLOAD_STATUS_4 = 4;

    public ArrayList<String> urls = new ArrayList<>();
    public ArrayList<String> filepaths = new ArrayList<>();
    public int status; //0未开始下载 1下载中 2下载成功 3.下载失败 4.已发给机器人
    public String original;
    public int successUrl = 0;

    public FileDownload() {

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
