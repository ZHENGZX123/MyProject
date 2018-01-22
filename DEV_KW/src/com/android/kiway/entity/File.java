package com.android.kiway.entity;

/**
 * Created by Administrator on 2017/11/15.
 */

public class File {
    public String filename;
    public String filepath;
    public String time;

    @Override
    public String toString() {
        return "File{" +
                "filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
