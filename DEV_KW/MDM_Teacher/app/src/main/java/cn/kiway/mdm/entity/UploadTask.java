package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2018/3/12.
 */

public class UploadTask {

    public static final int STATUS_START = 0;
    public static final int STATUS_UPLOADING = 1;
    public static final int STATUS_FINISH = 2;

    public String id;
    public String filepath;
    public String courseID;
    public int status;//0未上传 1上传中 2上传完成
    public int progress;
    public String url;

    public UploadTask() {
    }

    public UploadTask(String filepath, String courseID, int status, int progress, String url) {
        this.filepath = filepath;
        this.courseID = courseID;
        this.status = status;
        this.progress = progress;
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadTask{" +
                "id='" + id + '\'' +
                ", filepath='" + filepath + '\'' +
                ", courseID='" + courseID + '\'' +
                ", status=" + status +
                ", progress=" + progress +
                ", url='" + url + '\'' +
                '}';
    }
}
