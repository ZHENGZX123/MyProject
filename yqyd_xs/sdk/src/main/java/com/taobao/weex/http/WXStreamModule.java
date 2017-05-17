/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.taobao.weex.http;

import android.net.Uri;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.cache.HTTPCache;
import com.taobao.weex.utils.cache.OfflineTask;
import com.taobao.weex.utils.cache.WXDBHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.taobao.weex.http.WXHttpUtil.KEY_USER_AGENT;

public class WXStreamModule extends WXModule {

    public static final String STATUS_TEXT = "statusText";
    public static final String STATUS = "status";
    final IWXHttpAdapter mAdapter;
    static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([a-z0-9-]+)");

    public WXStreamModule() {
        this(null);
    }

    public WXStreamModule(IWXHttpAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * send HTTP request
     *
     * @param params   {method:POST/GET/PUT/DELETE/HEAD/PATCH,url:http://xxx,header:{key:value},
     *                 body:{key:value}}
     * @param callback formate：handler(err, response)
     */
    @Deprecated
    @JSMethod(uiThread = false)
    public void sendHttp(String params, final String callback) {

        JSONObject paramsObj = JSON.parseObject(params);
        String method = paramsObj.getString("method");
        String url = paramsObj.getString("url");
        JSONObject headers = paramsObj.getJSONObject("header");
        String body = paramsObj.getString("body");
        int timeout = paramsObj.getIntValue("timeout");

        if (method != null) method = method.toUpperCase();
        Options.Builder builder = new Options.Builder()
                .setMethod(!"GET".equals(method)
                        && !"POST".equals(method)
                        && !"PUT".equals(method)
                        && !"DELETE".equals(method)
                        && !"HEAD".equals(method)
                        && !"PATCH".equals(method) ? "GET" : method)
                .setUrl(url)
                .setBody(body)
                .setTimeout(timeout);

        extractHeaders(headers, builder);
        sendRequest(builder.createOptions(), new ResponseCallback() {
            @Override
            public void onResponse(WXResponse response, Map<String, String> headers) {
                if (callback != null && mWXSDKInstance != null)
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callback,
                            (response == null || response.originalData == null) ? "{}" :
                                    readAsString(response.originalData,
                                            headers != null ? getHeader(headers, "Content-Type") : ""
                                    ));
            }
        }, null);
    }

    /**
     * @param optionsStr       request options include:
     *                         method: GET 、POST、PUT、DELETE、HEAD、PATCH
     *                         headers：object，请求header
     *                         url:
     *                         body: "Any body that you want to add to your request"
     *                         type: json、text、jsonp（native实现时等价与json）
     * @param callback         finished callback,response object:
     *                         status：status code
     *                         ok：boolean 是否成功，等价于status200～299
     *                         statusText：状态消息，用于定位具体错误原因
     *                         data: 响应数据，当请求option中type为json，时data为object，否则data为string类型
     *                         headers: object 响应头
     * @param progressCallback in progress callback,for download progress and request state,response object:
     *                         readyState: number 请求状态，1 OPENED，开始连接；2 HEADERS_RECEIVED；3 LOADING
     *                         status：status code
     *                         length：当前获取的字节数，总长度从headers里「Content-Length」获取
     *                         statusText：状态消息，用于定位具体错误原因
     *                         headers: object 响应头
     */
    @JSMethod(uiThread = false)
    public void fetch(final String optionsStr, final JSCallback callback, final JSCallback progressCallback) {
        Log.d("stream", "stream optionsStr = " + optionsStr);
        JSONObject optionsObj = null;
        try {
            optionsObj = JSON.parseObject(optionsStr);
        } catch (JSONException e) {
            WXLogUtils.e("", e);
        }
        boolean invaildOption = optionsObj == null || optionsObj.getString("url") == null;
        if (invaildOption) {
            if (callback != null) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("ok", false);
                resp.put(STATUS_TEXT, Status.ERR_INVALID_REQUEST);
                callback.invoke(resp);
            }
            return;
        }
        final String url = optionsObj.getString("url");
        final String method = optionsObj.getString("method").toUpperCase();
        Log.d("stream", "stream url = " + url);
        JSONObject headers = optionsObj.getJSONObject("headers");
        String body = optionsObj.getString("body");
        String type = optionsObj.getString("type");
        int timeout = optionsObj.getIntValue("timeout");

        readCookie(url, headers);

        Options.Builder builder = new Options.Builder()
                .setMethod(!"GET".equals(method)
                        && !"POST".equals(method)
                        && !"PUT".equals(method)
                        && !"DELETE".equals(method)
                        && !"HEAD".equals(method)
                        && !"PATCH".equals(method) ? "GET" : method)
                .setUrl(url)
                .setBody(body)
                .setType(type)
                .setTimeout(timeout);

        extractHeaders(headers, builder);

        final Options options = builder.createOptions();

        final String finalMethod = method;
        sendRequest(options, new ResponseCallback() {
            @Override
            public void onResponse(WXResponse response, Map<String, String> headers) {

                int code = 0;
                String respData = "";
                Map<String, Object> resp = new HashMap<>();
                if (response == null || "-1".equals(response.statusCode)) {
                    resp.put(STATUS, -1);
                    resp.put(STATUS_TEXT, Status.ERR_CONNECT_FAILED);
                } else {
                    code = Integer.parseInt(response.statusCode);
                    Log.d("stream", "http code = " + code);
                    resp.put(STATUS, code);
                    resp.put("ok", (code >= 200 && code <= 299));
                    if (response.originalData == null) {
                        resp.put("data", null);
                    } else {
                        respData = readAsString(response.originalData, headers != null ? getHeader(headers, "Content-Type") : "");
                        Log.d("stream", "headers = " + headers);
                        Log.d("stream", "http data = " + respData);

                        //save cookie
                        saveCookie(headers);

                        try {
                            resp.put("data", parseData(respData, options.getType()));
                        } catch (JSONException exception) {
                            WXLogUtils.e("", exception);
                            resp.put("ok", false);
                            resp.put("data", "{'err':'Data parse failed!'}");
                        }
                    }
                    resp.put(STATUS_TEXT, Status.getStatusText(response.statusCode));
                }
                resp.put("headers", headers);
                if (response == null || "-1".equals(response.statusCode)) {
                    if (checkOfflineTaskConf(url)) {
                        //1.invoke fake
                        Log.d("stream", "praise invoke fake , add task");
                        resp.put("ok", true);
                        resp.put(STATUS, 200);
                        resp.put(STATUS_TEXT, "OK");
                        //FIXME
                        resp.put("data", "{\"status\":\"200\"}");
                        invoke(callback, resp);
                        //2.add offline task
                        OfflineTask task = new WXDBHelper(mWXSDKInstance.getContext()).getOfflineTaskByRequest(optionsStr);
                        if (task == null) {
                            task = new OfflineTask();
                            task.request = optionsStr;
                            task.requesttime = "" + System.currentTimeMillis();
                            new WXDBHelper(mWXSDKInstance.getContext()).addOfflineTask(task);
                        } else {
                            task.requesttime = "" + System.currentTimeMillis();
                            new WXDBHelper(mWXSDKInstance.getContext()).updateOfflineTask(task);
                        }
                        return;
                    }

                    //其他的只能取缓存
                    String cache = getCacheFromDB(optionsStr);
                    Log.d("stream", "use cache = " + cache);
                    if (cache == null) {
                        invoke(callback, resp);
                        return;
                    }
                    resp.put("ok", true);
                    resp.put(STATUS, 200);
                    resp.put(STATUS_TEXT, "OK");
                    resp.put("data", cache);//JSONObject.parse(cache)
                    invoke(callback, resp);
                } else {
                    Log.d("stream", "use http");
                    invoke(callback, resp);
                    if (checkOfflineTaskConf(url)) {
                        //check offline task db , if exsit , delete it
                        new WXDBHelper(mWXSDKInstance.getContext()).deleteOfflineTask(optionsStr);
                        return;
                    }
                    if (checkHttpcacheConf(url)) {
                        return;
                    }
                    if (code != 200) {
                        return;
                    }
                    saveCacheToDB(optionsStr, respData);
                }
            }
        }, progressCallback);
    }

    private boolean checkOfflineTaskConf(String url) {
        int count = WXSDKEngine.offlinetasks.size();
        for (int i = 0; i < count; i++) {
            if (url.contains(WXSDKEngine.offlinetasks.get(i).toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHttpcacheConf(String url) {
        int count = WXSDKEngine.httpcaches.size();
        for (int i = 0; i < count; i++) {
            if (url.contains(WXSDKEngine.httpcaches.get(i).toString())) {
                return false;
            }
        }
        return true;
    }

    private void invoke(JSCallback callback, Map<String, Object> resp) {
        if (callback == null) {
            return;
        }
        callback.invoke(resp);
    }


    private void saveCookie(Map<String, String> headers) {
        //JSESSIONID=2b1ccd02-4eb4-49d8-8760-f4e67c1e5bc7; Path=/yjpt; HttpOnly
        try {
            if (headers != null && headers.containsKey("Set-Cookie")) {
                String value = headers.get("Set-Cookie");
                String[] splits = value.split(";");
                String jsessionid = splits[0].trim();
                String path = splits[1].trim().replace("Path=", "");
                if (value.contains("JSESSIONID")) {
                    mWXSDKInstance.getContext().getSharedPreferences("kiway_cookie", 0).edit().putString(path, jsessionid).commit();
                }
            }
        } catch (Exception e) {
            Log.d("stream", "save cookie exception  e = " + e.toString());
        }
    }

    private void readCookie(String url, JSONObject headers) {
        Map<String, String> all = (Map<String, String>) mWXSDKInstance.getContext().getSharedPreferences("kiway_cookie", 0).getAll();
        for (String key : all.keySet()) {
            //Login donot need set cookie
            if (url.contains(key) && !url.contains("login")) {
                String value = all.get(key);
                headers.put("Cookie", value);
            }
        }
    }

    private void saveCacheToDB(String optionsStr, String respData) {
        try {
            Log.d("stream", "saveCacheToDB");
            HTTPCache a = new WXDBHelper(mWXSDKInstance.getContext()).getHttpCacheByRequest(optionsStr);
            // if existed , update
            if (a == null) {
                a = new HTTPCache();
                a.request = optionsStr;
                a.response = respData;
                a.requesttime = "" + System.currentTimeMillis();
                new WXDBHelper(mWXSDKInstance.getContext()).addHTTPCache(a);
            } else {
                a.response = respData;
                a.requesttime = "" + System.currentTimeMillis();
                new WXDBHelper(mWXSDKInstance.getContext()).updateHTTPCache(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCacheFromDB(String optionsStr) {
        try {
            HTTPCache cache = new WXDBHelper(mWXSDKInstance.getContext()).getHttpCacheByRequest(optionsStr);
            if (cache == null) {
                return null;
            }
            long current = System.currentTimeMillis();
            long requesttime = Long.parseLong(cache.requesttime);
            long between = current - requesttime;
            if (between > 4 * 60 * 60 * 1000) {
                return null;
            }
            return cache.response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Object parseData(String data, Options.Type type) throws JSONException {
        if (type == Options.Type.json) {
            return JSONObject.parse(data);
        } else if (type == Options.Type.jsonp) {
            if (data == null || data.isEmpty()) {
                return new JSONObject();
            }
            int b = data.indexOf("(") + 1;
            int e = data.lastIndexOf(")");
            if (b == 0 || b >= e || e <= 0) {
                return new JSONObject();
            }

            data = data.substring(b, e);
            return JSONObject.parse(data);
        } else {
            return data;
        }
    }

    static String getHeader(Map<String, String> headers, String key) {
        if (headers == null || key == null) {
            return null;
        }
        if (headers.containsKey(key)) {
            return headers.get(key);
        } else {
            return headers.get(key.toLowerCase());
        }
    }

    static String readAsString(byte[] data, String cType) {
        String charset = "utf-8";
        if (cType != null) {
            Matcher matcher = CHARSET_PATTERN.matcher(cType.toLowerCase());
            if (matcher.find()) {
                charset = matcher.group(1);
            }
        }
        try {
            return new String(data, charset);
        } catch (UnsupportedEncodingException e) {
            WXLogUtils.e("", e);
            return new String(data);
        }
    }


    private void extractHeaders(JSONObject headers, Options.Builder builder) {
        //set user-agent
        String UA = WXHttpUtil.assembleUserAgent(WXEnvironment.getApplication(), WXEnvironment.getConfig());
        if (headers != null) {
            for (String key : headers.keySet()) {
                if (key.equals(KEY_USER_AGENT)) {
                    UA = headers.getString(key);
                    continue;
                }
                builder.putHeader(key, headers.getString(key));
            }
        }
        builder.putHeader(KEY_USER_AGENT, UA);
    }


    private void sendRequest(Options options, ResponseCallback callback, JSCallback progressCallback) {
        WXRequest wxRequest = new WXRequest();
        wxRequest.method = options.getMethod();
        wxRequest.url = mWXSDKInstance.rewriteUri(Uri.parse(options.getUrl()), URIAdapter.REQUEST).toString();
        wxRequest.body = options.getBody();
        wxRequest.timeoutMs = options.getTimeout();

        if (options.getHeaders() != null)
            if (wxRequest.paramMap == null) {
                wxRequest.paramMap = options.getHeaders();
            } else {
                wxRequest.paramMap.putAll(options.getHeaders());
            }


        IWXHttpAdapter adapter = (mAdapter == null && mWXSDKInstance != null) ? mWXSDKInstance.getWXHttpAdapter() : mAdapter;
        if (adapter != null) {
            adapter.sendRequest(wxRequest, new StreamHttpListener(callback, progressCallback));
        } else {
            WXLogUtils.e("stream", "No HttpAdapter found,request failed.");
        }
    }

    private interface ResponseCallback {
        void onResponse(WXResponse response, Map<String, String> headers);
    }

    private static class StreamHttpListener implements IWXHttpAdapter.OnHttpListener {
        private ResponseCallback mCallback;
        private JSCallback mProgressCallback;
        private Map<String, Object> mResponse = new HashMap<>();
        private Map<String, String> mRespHeaders;

        private StreamHttpListener(ResponseCallback callback, JSCallback progressCallback) {
            mCallback = callback;
            mProgressCallback = progressCallback;
        }


        @Override
        public void onHttpStart() {
            if (mProgressCallback != null) {
                mResponse.put("readyState", 1);//readyState: number 请求状态，1 OPENED，开始连接；2 HEADERS_RECEIVED；3 LOADING
                mResponse.put("length", 0);
                mProgressCallback.invokeAndKeepAlive(mResponse);
            }
        }

        @Override
        public void onHttpUploadProgress(int uploadProgress) {

        }

        @Override
        public void onHeadersReceived(int statusCode, Map<String, List<String>> headers) {
            mResponse.put("readyState", 2);
            mResponse.put("status", statusCode);

            Map<String, String> simpleHeaders = new HashMap<>();
            if (headers != null) {
                Iterator<Map.Entry<String, List<String>>> it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, List<String>> entry = it.next();
                    if (entry.getValue().size() > 0) {
                        simpleHeaders.put(entry.getKey() == null ? "_" : entry.getKey(), entry.getValue().get(0));
                    }
                }
            }

            mResponse.put("headers", simpleHeaders);
            mRespHeaders = simpleHeaders;
            if (mProgressCallback != null) {
                mProgressCallback.invokeAndKeepAlive(mResponse);
            }
        }

        @Override
        public void onHttpResponseProgress(int loadedLength) {
            mResponse.put("length", loadedLength);
            if (mProgressCallback != null) {
                mProgressCallback.invokeAndKeepAlive(mResponse);
            }
        }

        @Override
        public void onHttpFinish(final WXResponse response) {
            //compatible with old sendhttp
            if (mCallback != null) {
                mCallback.onResponse(response, mRespHeaders);
            }

            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("WXStreamModule", response != null && response.originalData != null ? new String(response.originalData) : "response data is NUll!");
            }
        }
    }

}
