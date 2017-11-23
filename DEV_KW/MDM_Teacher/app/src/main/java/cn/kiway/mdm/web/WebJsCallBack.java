package cn.kiway.mdm.web;

/**
 * Created by Administrator on 2017/11/9.
 */

public class WebJsCallBack {
    public final static String scoketStaute = "javascript:scoketStaute(msg)";//服务器状态 1 开启，2关闭，3异常
    public final static String scoketClientDis = "javascript:scoketClientDis(msg)";//某个学生掉线
    public final static String accpterMessage = "javascript:accpterMessage(msg)";//接收学生端消息
    public final static String accpterFilePath = "javascript:accpterFilePath('fileName','filePath')";//发送文件给web的回调
}
