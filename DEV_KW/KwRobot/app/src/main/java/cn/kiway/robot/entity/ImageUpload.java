package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/11/21.
 */

public class ImageUpload {

    public static final int UPLOAD_STATUS_0 = 0;
    public static final int UPLOAD_STATUS_1 = 1;
    public static final int UPLOAD_STATUS_2 = 2;
    public static final int UPLOAD_STATUS_3 = 3;
    public static final int UPLOAD_STATUS_4 = 4;
    public static final int UPLOAD_STATUS_5 = 5;

    public int status; //0初始态   1补救中   2上传中 3上传成功 4上报  5上传失败
    public int saveCount;//补救次数
    public int uploadCount;//上传次数

    public String sender;
    public String imgPath;//本地路径
    public String clientGroupId;
    public String imgUrl;//上传后的路径
    public String saved;//原content，saved用

    //个人消息用
    public ImageUpload(String imgPath, String sender, String saved) {
        this.imgPath = imgPath;
        this.sender = sender;
        this.saved = saved;
    }

    //群消息用
    public ImageUpload(String imgPath, String sender, String clientGroupId, String saved) {
        this.imgPath = imgPath;
        this.sender = sender;
        this.clientGroupId = clientGroupId;
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "ImageUpload{" +
                "imgPath='" + imgPath + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
