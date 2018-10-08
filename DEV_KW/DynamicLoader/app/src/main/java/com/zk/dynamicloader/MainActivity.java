package com.zk.dynamicloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void loadJar1(View view) {
        loadJar("test1.jar", "cn.kiway.dynamic.TestJar", "hello");
    }

    public void loadJar2(View view) {
        loadJar("test2.jar", "cn.kiway.dynamic.TestJar", "hello");
    }

    private void loadJar(String jar, String className, String functionName) {
        try {
            String jarPath = "/mnt/sdcard/" + jar;
            String tmpPath = getApplicationContext().getDir("Jar", 0).getAbsolutePath();
            DexClassLoader cl = new DexClassLoader(jarPath, tmpPath
                    , null, this.getClass().getClassLoader());
            Class<?> libProviderCls = cl.loadClass(className);
            Constructor<?> localConstructor = libProviderCls.getConstructor(new Class[]{});
            Object obj = localConstructor.newInstance(new Object[]{});
            Method mMethodWrite = libProviderCls.getDeclaredMethod(functionName);
            mMethodWrite.setAccessible(true);
            String str = (String) mMethodWrite.invoke(obj);
            toast(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void toast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

}
