package cn.kiway.mdm.utils;

/**
 * Created by Administrator on 2017/12/13.
 */

public interface IContants {

    //public static String url = "http://202.104.136.9:8080/mdms";//测试地址8390
    public static String BASE_URL = "http://202.104.136.9:8084";//测试地址8390
    //public static String url = "http://yuertong.com/mdms";//测试地址8390

    public String CHECK_VERSION_URL = BASE_URL+"/static/download/version/zip_kthd.json";//检查更新地址
}
