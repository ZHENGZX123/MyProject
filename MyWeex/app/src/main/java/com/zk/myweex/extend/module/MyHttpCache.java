package com.zk.myweex.extend.module;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.utils.NetworkUtil;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import cn.kiway.entity.HttpCache;
import io.realm.Realm;


/**
 * Created by Administrator on 2017/3/2.
 */

public class MyHttpCache extends WXModule {

    @JSMethod(uiThread = true)
    public void doRequest(final String url, final String param, final JSCallback callback) {
        //如果请求间隔的时间小于10秒，直接拿缓存
        long current = System.currentTimeMillis();
        long current_before_10s = current - 60 * 1000;
        HttpCache temp = Realm.getDefaultInstance().where(HttpCache.class).equalTo("url", url).equalTo("param", param).greaterThanOrEqualTo("requestTime", current_before_10s).findFirst();
        if (temp != null) {
            Log.d("test", "60秒内，该请求还可以用");
            callback.invoke(temp.result);
            return;
        }
        Log.d("test", "已经过60秒");
        if (!NetworkUtil.isNetworkAvailable(mWXSDKInstance.getContext())) {
            //没有网络，返回上次缓存的数据
            returnCacheData(url, param, callback);
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
                    returnCacheData(url, param, callback);
                }

                public void onSuccess(int statusCode, org.apache.http.Header[] headers, final String responseString) {
                    Log.d("test", "onSuccess = " + responseString);
                    //成功的话缓存，并直接返回数据
                    callback.invoke(responseString);

                    //用url+param作为key，查询是否有记录。如果没有就新增，如果有就插入。
                    //或者直接删除了，再次插入
                    final HttpCache temp = Realm.getDefaultInstance().where(HttpCache.class).equalTo("url", url).equalTo("param", param).findFirst();
                    if (temp == null) {
                        Realm.getDefaultInstance().beginTransaction();
                        HttpCache cache = Realm.getDefaultInstance().createObject(HttpCache.class);
                        cache.url = url;
                        cache.param = param;
                        cache.result = responseString;
                        cache.requestTime = System.currentTimeMillis();
                        Realm.getDefaultInstance().commitTransaction();
                    } else {
                        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                temp.url = url;
                                temp.param = param;
                                temp.result = responseString;
                                temp.requestTime = System.currentTimeMillis();
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            Log.d("test", "exception = " + e.toString());
            e.printStackTrace();
            returnCacheData(url, param, callback);
        }
    }

    private void returnCacheData(String url, String param, JSCallback callback) {
        HttpCache temp = Realm.getDefaultInstance().where(HttpCache.class).equalTo("url", url).equalTo("param", param).findFirst();
        Log.d("test", "temp = " + temp);
        if (temp == null) {
            callback.invoke("error");
        } else {
            callback.invoke(temp.result);
        }
    }


}
