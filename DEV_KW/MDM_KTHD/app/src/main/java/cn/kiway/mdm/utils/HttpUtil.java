package cn.kiway.mdm.utils;

import static cn.kiway.mdm.App.clientUrl;

/**
 * Created by Administrator on 2018/1/23.
 */

public class HttpUtil {
    public final static String getMyClassroomFile = clientUrl + "device/student/myClassroomFile";//获取我的课堂资料
    public final static String getMyScreenshotFile = clientUrl + "device/student/myScreenshotFile";//获取我的截图资料
    public final static String uploadUserFile =  clientUrl + "device/student/userFile";//学生上传文件记录


    public final static String uploadFile=clientUrl+"common/file?x-auth-token=";
}
