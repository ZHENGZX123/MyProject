package com.zk.dynamicloader.utils;

import android.content.Context;

import java.lang.reflect.Constructor;
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


    private Object constructor;
    private Class<?> aClass;
    private Method method;

    public void getMethod(Class<?>... paramTypes) {
        try {
            String tmpPath = c.getApplicationContext().getDir("Jar", 0).getAbsolutePath();
            DexClassLoader cl = new DexClassLoader(jarFilePath, tmpPath
                    , null, c.getClass().getClassLoader());
            aClass = cl.loadClass(className);
            method = aClass.getMethod(methodName, paramTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //构造函数无参
    public String invokeMethod(Object... params) {
        String ret = null;
        try {
            ret = (String) method.invoke(aClass.newInstance(), params);
        } catch (Exception e) {
            e.printStackTrace();
            return "jar异常" + e.toString();
        }
        return ret;
    }

    //构造函数有一个参数context
    public String invokeMethod(Context c, Object... params) {
        String ret = null;
        try {
            Constructor constructor = aClass.getDeclaredConstructor(Context.class);
            Object obj = constructor.newInstance(c);
            ret = (String) method.invoke(obj, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "jar异常" + e.toString();
        }
        return ret;
    }


}
