package cn.kiway.hybird.util;

/**
 * Created by Administrator on 2018/2/26.
 */
//配置文件，同时也是使用说明；当有新项目的时候，修改该文件即可

public class Configue {

    //1.修改build.gradle的包名，即applicationId
    //2.修改应用的名称和图标，分别在strings.xml和res目录ic_launcher.png
    //3.如果用到推送，配置appkey，目前包括小米、华为、极光

    //4.手动生成压缩包，放在raw文件夹下。压缩包的目录结构是xtzy_teacher\dist\...
    //5.配置正式版和测试版Host地址，配置该版本是正式还是测试
    //6.设置ROOT地址，即zip包解压地址和项目其他资源缓存地址。该步骤已不需要，ROOT固定是内置存储目录
    //7.


    //15.关闭MLog的DEBUG开关
    //16.配置APK使用的签名文件（固定不变，在build.gradle文件）和混淆规则（固定不变，在proguard-rules.pro文件），release项目
    //17.APK加壳，目前使用的是360加壳技术
}
