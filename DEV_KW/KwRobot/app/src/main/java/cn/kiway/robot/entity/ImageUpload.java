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

    public int status; //0初始态   1补救中  2补救成功，准备上传  3上传中 4上传成功 5上报
    public int saveCount;//补救次数

    public String sender;
    public String imgPath;//本地路径
    public String clientGroupId;
    public String imgUrl;//上传后的路径


    //个人消息用
    public ImageUpload(String imgPath, String sender) {
        this.imgPath = imgPath;
        this.sender = sender;
    }

    //群消息用
    public ImageUpload(String imgPath, String sender, String clientGroupId) {
        this.imgPath = imgPath;
        this.sender = sender;
        this.clientGroupId = clientGroupId;
    }

    @Override
    public String toString() {
        return "ImageUpload{" +
                "imgPath='" + imgPath + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", status=" + status +
                /*", action=" + action +*/
                '}';
    }
}
