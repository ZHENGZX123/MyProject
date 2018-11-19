package cn.kiway.dynamic;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/9/30.
 */

public class MDMUtil {

    // 测试函数，打包的时候注释掉
    public static void main(String[] args) throws Exception {
        Class<?> classToCall = Class.forName("cn.kiway.dynamic.MDMUtil");
        Method methodToExecute = classToCall.getMethod("setStatusBarExpandPanelDisabled", boolean.class);
        methodToExecute.invoke(classToCall.newInstance(), true);
    }

    public static String isStatusBarExpandPanelDisabled() {
        return "true";
    }

    public static String setStatusBarExpandPanelDisabled(boolean p1) {
        System.out.println("p1 = " + p1);
        return "";
    }

}
