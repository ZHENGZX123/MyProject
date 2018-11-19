package com.zk.dynamicloader.utils;

import android.content.Context;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by Administrator on 2018/11/19.
 */

public class InvokeUtil {

    public Context c;
    public String jarFilePath;
    public String className;
    public String methodName;

    public InvokeUtil(Context c, String jarFilePath, String className, String methodName) {
        this.c = c;
        this.jarFilePath = jarFilePath;
        this.className = className;
        this.methodName = methodName;
    }

    private Class<?> classToCall;
    private Method method;

    public void getMethod(Class<?>... paramTypes) {
        try {
            String tmpPath = c.getApplicationContext().getDir("Jar", 0).getAbsolutePath();
            DexClassLoader cl = new DexClassLoader(jarFilePath, tmpPath
                    , null, c.getClass().getClassLoader());
            classToCall = cl.loadClass(className);
            method = classToCall.getMethod(methodName, paramTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String invokeMethod(Object... params) {
        String ret = null;
        try {
            ret = (String) method.invoke(classToCall.newInstance(), params);
        } catch (Exception e) {
            e.printStackTrace();
            return "jar异常" + e.toString();
        }
        return ret;
    }

}
