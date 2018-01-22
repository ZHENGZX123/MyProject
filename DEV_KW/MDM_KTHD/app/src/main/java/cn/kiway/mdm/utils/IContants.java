package cn.kiway.mdm.utils;

/**
 * Created by Administrator on 2017/12/13.
 */

public interface IContants {

    //public static String BASE_URL = "http://202.104.136.9:8080/mdms";//测试地址8390
    public static String BASE_URL = "http://202.104.136.9:8084/";//测试地址8390
    //public static String BASE_URL = "http://192.168.8.161:8084/";//测试地址8390
    //public static String BASE_URL = "http://yuertong.com/mdms";//测试地址8390

    public String CHECK_VERSION_URL = BASE_URL + "static/download/version/zip_kthd.json";//检查更新地址

    public String SING_URL = BASE_URL + "device/student/sign/response/server";//签到

    public String REPONSE_URL= BASE_URL + "device/student/response/server";//响应器
}
