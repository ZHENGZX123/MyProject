package cn.kiway.utils;

/**
 * Created by Administrator on 2018/2/26.
 */
//配置文件，同时也是使用说明；当有新项目的时候，修改该文件即可

public class Configue {

    //1.修改build.gradle的包名，即applicationId

    //2.修改应用的名称和图标，分别在strings.xml和res目录ic_launcher.png

    //3.如果用到推送，配置appkey，目前包括小米、华为、极光：
    //3.1小米推送用APPID APPKEY
    public static final String APP_ID = "2882303761517595244";
    public static final String APP_KEY = "5101759558244";
    //3.2华为推送，配置哪里忘记了。。。
    //3.3极光推送，配置哪里忘记了。。。

    //4.手动生成压缩包，放在raw文件夹下。压缩包的目录结构是xtzy_teacher\dist\...

    //5.配置正式版和测试版Host地址，配置该版本是正式还是测试

    public static final String zhengshiUrl = "http://xtzy.xtclass.com";//正式地址
    public static final String ceshiUrl = "http://cszy.xtclass.com:8389";//测试地址8390
    public static String url;
    public static boolean isTest = false;

    static {
        if (isTest) {
            url = Configue.ceshiUrl;
        } else {
            url = Configue.zhengshiUrl;
        }
    }

    //6.设置ROOT地址，即zip包解压地址和项目其他资源缓存地址。该步骤已不需要，ROOT固定是内置存储目录。以前是外部路径"/mnt/sdcard/kiway_teacher/"
    public static String ROOT;
    //7.配置countly
    public static final String countlyUrl = "/countly";
    public static final String countlyAPPKEY = "e3a6f65596ea867c2f739c12d5120d5d76353b5e";

    //14.配置其他参数
    public static final String BOOKS = "/mnt/sdcard/books/";

    //15.关闭MLog的DEBUG开关
    public static final boolean DEBUG = true;
    //16.配置build.gradle的dependencies-classpath是2.2.3还是2.3.3
    //17.配置APK使用的签名文件（固定不变，在build.gradle文件）和混淆规则（固定不变，在proguard-rules.pro文件），release生成apk文件
    //18.APK加壳，目前使用的是360加壳技术
}
