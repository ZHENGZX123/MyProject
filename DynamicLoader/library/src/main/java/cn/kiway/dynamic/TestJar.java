package cn.kiway.dynamic;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/9/30.
 */

public class TestJar {

	// 测试函数，打包的时候注释掉
	public static void main(String[] args) throws Exception {
		Class<?> classToCall = Class.forName("cn.kiway.dynamic.TestJar");
		String[] argu = { "1", "2" };
		Method methodToExecute = classToCall.getDeclaredMethod("test2", new Class[] { String[].class });
		methodToExecute.invoke(classToCall.newInstance(), new Object[] { argu });
	}

	public static String test1() {
		return "无参数调用升级成功";
	}

	public static String test2(String params[]) {
		for (String p : params) {
			System.out.println("p = " + p);
		}
		return "有参数调用升级成功";
	}
}
