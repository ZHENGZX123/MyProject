package com.zk.myweex.extend.adapter;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/15.
 */
public class SaveCookiesInterceptor implements Interceptor {

    private Context c;

    public SaveCookiesInterceptor(Context c) {
        this.c = c;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            c.getSharedPreferences("cookies", 0).edit().putStringSet("cookies", cookies).apply();
        }

        return originalResponse;
    }
}