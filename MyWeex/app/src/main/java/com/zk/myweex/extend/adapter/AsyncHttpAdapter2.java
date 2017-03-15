package com.zk.myweex.extend.adapter;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;

import org.apache.http.Header;

import java.util.Set;

/**
 * Created by Administrator on 2017/3/15.
 */

public class AsyncHttpAdapter2 implements IWXHttpAdapter {

    private Context c;

    public AsyncHttpAdapter2(Context c) {
        this.c = c;
    }

    @Override
    public void sendRequest(final WXRequest request, final OnHttpListener listener) {
        Log.d("test", "AsyncHttpAdapter url = " + request.url);
        final AsyncHttpClient client = new FinalAsyncHttpClient().getAsyncHttpClient();
        CookieUtils.saveCookie(client, c);

        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        if (request.paramMap != null) {
            Set<String> keySets = request.paramMap.keySet();
            for (String key : keySets) {
                params.put(key, request.paramMap.get(key)); // 设置请求的参数名和参数值
            }
        }
        //偷懒的写法
        String url = request.url + "?" + request.body;

        try {
            client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "failure = " + s);
//                    listener.onHttpFinish();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    Log.d("test", "success = " + s);
                    WXResponse response = new WXResponse();
//                    listener.onHeadersReceived("" + i, headers);//不会写这里，就不返回了，哈哈哈
                    try {
                        response.statusCode = "" + i;
                        response.originalData = s.getBytes();
                        listener.onHttpFinish(response);
                        CookieUtils.setCookies(CookieUtils.getCookie(c));
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.statusCode = "-1";
                        response.errorCode = "-1";
                        response.errorMsg = e.getMessage();
                        listener.onHttpFinish(response);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
