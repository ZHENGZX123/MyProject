package cn.kiway.robot.util;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/2/23.
 */

public class Constant {

    public static final String APPID = "930a4b41b8c92d30f790a6bf01bfe78a";//  //f2ec1fb69b27c7ab5260d2eb7cd95dea
    public static final String APPKEY = "c83f643092046c0328624fe59aeec6548dac256c";//9a9b01f8ab910e12422bcc0e88d95dff2f95f582

    public static String zbusHost = "robot.kiway.cn";//192.168.8.161  rbtest.kiway.cn
    public static String zbusPost = "15555";

    public static String clientUrl = "http://robot.kiway.cn"; //"http://192.168.8.161:8081";


    public static HashMap<String, String> qas = new HashMap<>();

    static {
        qas.put("你好", "家长您好。");
        qas.put("您好", "家长您好。");
        qas.put("客服你好", "家长您好。");
        qas.put("客服您好", "家长您好。");
        qas.put("客服在吗", "家长您好，请问您要咨询什么问题？");
        qas.put("谢谢", "不用谢。欢迎下次再咨询。");
    }

    public static final String DEFAULT_WELCOME = "感谢您添加招生客服机器人，您可以按以下关键字发送咨询招生相关问题，谢谢！\n" +
            "1、计生证明或者计划生育证明\n" +
            "2、租房或者住房\n" +
            "3、台湾或者香港\n" +
            "4、户籍\n" +
            "5、网上报名\n" +
            "6、验核材料\n" +
            "7、录取\n";

}
