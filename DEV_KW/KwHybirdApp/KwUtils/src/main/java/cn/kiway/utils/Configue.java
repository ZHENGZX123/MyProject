package cn.kiway.utils;

/**
 * Created by Administrator on 2018/2/26.
 */
//配置文件，同时也是使用说明；当有新项目的时候，修改该文件即可
public class Configue {

    //1.修改项目包名：当前是cn.kiway.hybird.teacher，搜索一下全局替换

    //2.修改应用的名称和图标，分别在strings.xml和res目录ic_launcher.png

    //3.修改应用的APPID和Module名称
    public static final String KwAppId = "c77b6c47dbcee47d7ffbc9461da0c82a";
    public static final String KwModule = "teacher";

    //4.如果用到推送，配置appkey，目前包括小米、华为、极光：
    //4.1小米推送用APPID APPKEY
    public static final String APP_ID = "2882303761517595244";
    public static final String APP_KEY = "5101759558244";
    //4.2华为推送,修改manifest.xml即可
    //4.3极光推送,修改manifest.xml即可

    //5.配置正式版和测试版Host地址，配置该版本是正式还是测试
    public static final String zhengshiHost = "http://xtzy.xtclass.com";//正式地址
    public static final String ceshiHost = "http://cszy.xtclass.com:8389";//测试地址8390
    public static String host;
    public static boolean isTest = false;

    static {
        if (isTest) {
            host = Configue.ceshiHost;
        } else {
            host = Configue.zhengshiHost;
        }
    }

    //6.配置项目使用到的httpUrl路径，由于大部分业务在前端，所以平台仅有几个
    public static final String url_upgrade = "/download/version/zip_ls.json";
    public static final String url_install = "/push/installation";
    public static final String url_uninstall = "/push/uninstall";
    public static final String url_getbooks = "/teacher/book?access_token=%s";
    public static final String url_downloadBooks = "/resource/book/%s?access_token=%s";
    public static final String url_upload = "/common/file?access_token=%s";

    //7.1手动生成压缩包，放在raw文件夹下。压缩包的目录结构是xtzy_teacher\dist\...
    //7.2设置zip包相关参数。ZIP同zip文件名字
    public static String ROOT;
    public static String ZIP = "xtzy_teacher";
    public static String ZIP_Name = "xtzy_teacher.zip";
    public static String HTML = ZIP + "/dist/index.html";


    //8.配置countly
    public static final String url_countly = "/countly";
    public static final String countlyAPPKEY = "e3a6f65596ea867c2f739c12d5120d5d76353b5e";

    //9.APK升级和包升级配置：修改build.gradle和currentPackageVersion。重要！
    public static final String currentPackageVersion = "0.0.1";

    //10.配置其他参数
    public static final String BOOKS = "/mnt/sdcard/books/";

    //11.关闭MLog的DEBUG开关
    public static final boolean DEBUG = true;
    //12.配置build.gradle的dependencies-classpath是2.2.3（build）还是2.3.3（release）
    //13.配置APK使用的签名文件（固定不变，在build.gradle文件）和混淆规则（固定不变，在proguard-rules.pro文件），release生成apk文件
    //14.APK加壳，目前使用的是360加壳技术
}
