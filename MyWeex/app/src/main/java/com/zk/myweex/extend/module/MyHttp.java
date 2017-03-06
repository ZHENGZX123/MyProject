package com.zk.myweex.extend.module;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.entity.HttpCache;
import com.zk.myweex.utils.NetworkUtil;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import io.realm.Realm;


/**
 * Created by Administrator on 2017/3/2.
 */

public class MyHttp extends WXModule {

    @JSMethod(uiThread = true)
    public void doRequest(final String url, final String param, final JSCallback callback) {
        HttpCache temp = Realm.getDefaultInstance().where(HttpCache.class).equalTo("url", url).equalTo("param", param).findFirst();
        Log.d("test", "temp = " + temp);

        if (!NetworkUtil.isNetworkAvailable(mWXSDKInstance.getContext())) {
            //没有网络，返回上次缓存的数据
            returnCacheData(callback);
            return;
        }
        //访问网络，缓存返回值，返回字符串
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            JSONObject jsonObject = new JSONObject();
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            client.post(mWXSDKInstance.getContext(), url, stringEntity, "application/json", new TextHttpResponseHandler() {

                public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                    Log.d("test", "onFailure = " + responseString);
                    //失败的话，返回上次缓存的数据
                    returnCacheData(callback);
                }

                public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString) {
                    Log.d("test", "onSuccess = " + responseString);
                    //成功的话直接返回数据
                    callback.invoke("success");

                    HttpCache cache = new HttpCache();
                    cache.url = url;
                    cache.param = param;
                    cache.result = responseString;
                    Realm.getDefaultInstance().beginTransaction();
                    Realm.getDefaultInstance().insertOrUpdate(cache);
                    Realm.getDefaultInstance().commitTransaction();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            returnCacheData(callback);
        }
    }

    private void returnCacheData(JSCallback callback) {
        callback.invoke("failure");
    }


    private void toast(String txt) {
        Toast.makeText(mWXSDKInstance.getContext(), txt, Toast.LENGTH_SHORT).show();
    }

}
