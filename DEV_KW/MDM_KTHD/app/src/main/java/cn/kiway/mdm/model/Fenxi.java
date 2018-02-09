package cn.kiway.mdm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/22.
 */

public class Fenxi implements Serializable {

    public String content;//内容，可能是图片。
    public int type; //知识点1 测评题2 抽答题3
    public int status;
    public String id;
    public String createDate;

    public int questionType;
    public String questionContent;
    public String questionImg;
    public String questionOptions;
    public String answerContent;
    public String answerImg;

}
