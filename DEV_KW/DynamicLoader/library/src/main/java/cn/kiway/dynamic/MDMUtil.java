package cn.kiway.dynamic;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/9/30.
 */

public class MDMUtil {

    // 测试函数，打包的时候注释掉
    public static void main(String[] args) throws Exception {
        Class<?> classToCall = Class.forName("cn.kiway.dynamic.TestJar");
        String[] argu = {"true"};
        Method methodToExecute = classToCall.getDeclaredMethod("setStatusBarExpandPanelDisabled", new Class[]{String[].class});
        methodToExecute.invoke(classToCall.newInstance(), new Object[]{argu});
    }

    public static String isStatusBarExpandPanelDisabled() {
        return "true";
    }

    public static String setStatusBarExpandPanelDisabled(String params[]) {
        for (String p : params) {
            System.out.println("p = " + p);
        }
        return "";
    }

}
