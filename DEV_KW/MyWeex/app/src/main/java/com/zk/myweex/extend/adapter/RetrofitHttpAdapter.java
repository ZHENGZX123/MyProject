package com.zk.myweex.extend.adapter;

import android.content.Context;
import android.util.Log;

import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.zk.myweex.WXApplication;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zk.myweex.WXApplication.repo;

/**
 * Created by Administrator on 2017/3/15.
 */

public class RetrofitHttpAdapter implements IWXHttpAdapter {

    private Context c;

    public RetrofitHttpAdapter(Context c) {
        this.c = c;
    }

    //如果用retrofit，来怎么实现呢。

    @Override
    public void sendRequest(WXRequest request, final OnHttpListener listener) {

        String url = request.url.replace(WXApplication.BASE_URL, "");//+ "?" + request.body;
        Log.d("test", "url = " + url);

        String body = request.body;
        String bodys[] = body.split("&");

        HashMap<String, String> map = new HashMap();
        for (int i = 0; i < bodys.length; i++) {
            String temp = bodys[i];
            String temps[] = temp.split("=");
            map.put(temps[0], temps[1]);
        }

        Call<String> call = repo.doGet(url, map);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                Log.d("test", "response = " + r.toString());
                Log.d("test", "response = " + r.body().toString());

                WXResponse response = new WXResponse();
//                    listener.onHeadersReceived("" + i, headers);//不会写这里，就不返回了，哈哈哈
                try {
                    response.statusCode = "" + r.code();
                    response.originalData = r.body().getBytes();
                    listener.onHttpFinish(response);

                } catch (Exception e) {
                    e.printStackTrace();
                    response.statusCode = "-1";
                    response.errorCode = "-1";
                    response.errorMsg = e.getMessage();
                    listener.onHttpFinish(response);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("test", "onFailure t=" + t.toString());
            }
        });


    }
}
