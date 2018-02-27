package cn.kiway.mdm.utils;

import static cn.kiway.mdm.utils.Constant.clientUrl;

/**
 * Created by Administrator on 2018/1/23.
 */

public class HttpUtil {
    public final static String getMyFile = clientUrl + "device/student/myFile?style=";//获取我的课堂资料
    public final static String uploadUserFile = clientUrl + "device/student/userFile";//学生上传文件记录
    public final static String uploadUserInfo = clientUrl + "device/avatarName";//更改用户头像

    public final static String getMyClassAnalysis = clientUrl + "device/student/classStatusAnalysis?style=";//获取课堂分析情况
    public final static String getAnalysisDetial = clientUrl + "device/student/classStatusAnalysis/detail/";
    public final static String getYiShangkeUrl = clientUrl + "device/student/attend?currentPage=";

    public final static String uploadFile = clientUrl + "common/file?x-auth-token=";
}
