package yjpty.teaching.http;

import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import yjpty.teaching.util.Logger;

/**
 * 网络请求
 *
 * @author Zao
 */
public class BaseHttpRequest extends Thread {
    /**
     * HTTP请求地址
     */
    String requestUrl;
    /**
     * HTTP请求客户端
     */
    HttpClient httpClient = new DefaultHttpClient();
    /**
     * HTTP POST请求
     */
    HttpPost httpPost;
    /**
     * Http Get请求
     */
    HttpGet httpGet;
    /**
     * Http Delete 请求
     */
    HttpDelete httpDelete;
    /**
     * Http put 请求
     */
    HttpPut httpPut;
    /**
     * HTTP 请求回调
     */
    BaseHttpHandler handler;
    /**
     * 是否继续执行回调操作
     */
    boolean requesting;
    /**
     * HTTP 请求参数
     */
    Object params;
    /**
     * 同时请求同一个地址的第几次请求
     */
    int which;
    /**
     * HTTP请求的附带参数
     */
    Map<String, Object> map;
    /**
     * 是否展现等待进度
     */
    boolean isShowLoad;
    /**
     * HTTP 请求标记
     */
    String requestTag;
    /**
     * 应用标题
     */
    private String app = "yjpty";
    /**
     * 获得的cookie
     **/
    CookieStore cookieStore;
    /**
     * 请求的类型 ost 2 get 3delete 4put
     */

    public static String JSESSIONID = "";

    private int type;

    public boolean isRequesting() {
        return requesting;
    }

    public void setRequesting(boolean requesting) {
        this.requesting = requesting;
    }

    public boolean isShowLoad() {
        return isShowLoad;
    }

    @SuppressWarnings("unused")
    private BaseHttpRequest() {
    }

    /**
     * @param requestUrl
     * 请求地址
     * @param params
     * 请求参数 支持(protobuf,Map<String,String>,JSONObject)
     * @param handler
     * 请求回调函数
     * @param which
     * 第几次请求
     * @param map
     * 请求的附带参数
     * @param isShowLoad
     * 是否展现等待进度
     */
    public final static int HTTP_TIMEOUT = 7 * 1000;
    public final static int HTTP_SOCKET_TIMEOUT = 30 * 1000;

    public BaseHttpRequest(String requestUrl, Object params,
                           BaseHttpHandler handler, int which, Map<String, Object> map,
                           String requestTag, boolean isShowLoad, int type) throws Exception {
        this.requestUrl = requestUrl;
        this.params = params;
        this.handler = handler;
        this.which = which;
        this.map = map;
        this.requestTag = requestTag;
        this.isShowLoad = isShowLoad;
        this.type = type;
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, HTTP_TIMEOUT);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                HTTP_SOCKET_TIMEOUT);
        httpClient.getConnectionManager().closeExpiredConnections();
        if (type == 1) {
            httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");
//            if (requestUrl.equals(IUrContant.LOGIN_URL)) {
//
//                map.put("token", token);
//                map.put("id", userId + "");
//            }

            if (params instanceof Map) {// Map<String,String>
                System.out.print("初始化请求Map数据请求::::");
                List<BasicNameValuePair> list = new ArrayList<>();
                @SuppressWarnings({"unchecked", "rawtypes"})
                Set<Map.Entry<String, String>> set = ((Map) params).entrySet();
                for (Map.Entry<String, String> entry : set) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            } else if (params instanceof JSONObject) {// JSONObject
                httpPost.setEntity(new StringEntity(((JSONObject) params)
                        .toString()));
            }
        } else if (type == 2) {
            System.out.print("初始化请求Get::::" + requestUrl);
            httpGet = new HttpGet(requestUrl);
        } else if (type == 3) {
            System.out.print("初始化请求HttpDelete::::");
            httpDelete = new HttpDelete(requestUrl);
            httpDelete.setHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");
        } else if (type == 4) {
            System.out.print("初始化请求Put::::");
            httpPut = new HttpPut(requestUrl);
            httpPut.setHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");
            if (params instanceof Map) {// Map<String,String>
                System.out.print("初始化请求Map数据请求::::");
                List<BasicNameValuePair> list = new ArrayList<>();
                @SuppressWarnings({"unchecked", "rawtypes"})
                Set<Map.Entry<String, String>> set = ((Map) params).entrySet();
                for (Map.Entry<String, String> entry : set) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
                httpPut.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            }
        }
        addCookies();// 添加头部
        setRequesting(true);
    }

    private void excute() throws Exception {
        HttpResponse httpResponse = null;
        try {
            if (this.type == 1) {
                httpResponse = httpClient.execute(httpPost);
                System.out.print("开始请求Post::::");
            } else if (this.type == 2) {
                System.out.print("开始请求Get::::");
                httpResponse = httpClient.execute(httpGet);
            } else if (this.type == 3) {
                httpResponse = httpClient.execute(httpDelete);
                System.out.print("开始请求Detet::::");
            } else if (this.type == 4) {
                httpResponse = httpClient.execute(httpPut);
                System.out.print("开始请求Put::::");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (handler != null && isRequesting())
                handler.sendEmptyMessage(HttpResponseMsgType.RESPONSE_ERR);
        }
        Logger.log("request url :" + requestUrl);
        Logger.log("request params :" + params);
        if (httpResponse != null && httpResponse.getStatusLine() != null)
            System.out.print("response code :"
                    + httpResponse.getStatusLine().getStatusCode());
        if (httpResponse != null
                && httpResponse.getStatusLine() != null
                && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity = httpResponse.getEntity();
            saveCookies();// 保存获取的cookies
            if (httpEntity != null) {
                byte[] b = EntityUtils.toByteArray(httpEntity);
                Logger.log(new String(b));
                Log.e("dfasdfsdf", new String(b));
                if (handler != null && isRequesting()) {
                    try {
                        Message message = new Message();
                        message.what = HttpResponseMsgType.RESPONSE_SUCCESS;
                        HttpResponseModel model = new HttpResponseModel(
                                requestUrl, b, which, map);
                        message.obj = model;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message message = new Message();
                        message.what = HttpResponseMsgType.RESPONSE_ERROR;
                        HttpResponseModel model = new HttpResponseModel(
                                requestUrl, b, which, map);
                        message.obj = model;
                        handler.sendMessage(message);
                    }
                }
            } else {
                if (handler != null && isRequesting())
                    try {
                        Message message = new Message();
                        message.what = HttpResponseMsgType.RESPONSE_ERROR;
                        HttpResponseModel model = new HttpResponseModel(
                                requestUrl, null, which, map);
                        message.obj = model;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                    }
            }
        } else {
            if (handler != null && isRequesting())
                try {
                    Message message = new Message();
                    message.what = HttpResponseMsgType.RESPONSE_ERROR;
                    HttpResponseModel model = new HttpResponseModel(requestUrl,
                            null, which, map);
                    message.obj = model;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
        }
        if (httpPost != null)
            httpPost.abort();
        if (httpGet != null)
            httpGet.abort();
        if (httpDelete != null)
            httpDelete.abort();
        if (httpPut != null)
            httpPut.abort();
    }

    @Override
    public void run() {
        super.run();
        BaseHttpConnectPool.handler
                .sendEmptyMessage(BaseHttpConnectPool.OPEN_SUG);
        try {
            excute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseHttpConnectPool.httpRequests.remove(requestTag);
        BaseHttpConnectPool.handler
                .sendEmptyMessage(BaseHttpConnectPool.CLOSE_SUG);

    }

    /**
     * 将返回的cookie保存起来
     *
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public void saveCookies() throws JSONException {
//        cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
//        List<Cookie> cookies = cookieStore.getCookies();
//        try {
//            @SuppressWarnings("rawtypes")
//            Map appinfo = Global.apps.get(app);
//            List<Map<String, String>> jsonArray = null;
//            /*
//             * if (appinfo.containsKey("cookieStr")) { jsonArray =
//			 * (List<Map<String, String>>) appinfo .get("cookieStr"); } else {
//			 */
//            jsonArray = new ArrayList<Map<String, String>>();
//            for (Cookie cookie : cookies) {
//                Map<String, String> obj = new HashMap<String, String>();
//                obj.put("name", cookie.getName());
//                obj.put("value", cookie.getValue());
//                obj.put("path", cookie.getPath());
//                obj.put("domain", cookie.getDomain());
//                obj.put("expiryDate", cookie.getExpiryDate() == null ? "" : ""
//                        + cookie.getExpiryDate());
//                Log.d(cookie.getName(),
//                        cookie.getDomain() + "====" + cookie.getValue()
//                                + "====" + cookie.getPath() + "====="
//                                + cookie.getExpiryDate());
//                jsonArray.add(obj);
//                //App.getInstance().setCookie(cookie);// 将session保存到app
//                // }
//            }
//            // 持久化cookie
//            appinfo.put("cookieStr", jsonArray);
//            Global.apps.put(app, appinfo);
//            Logger.log("获取到cokie" + jsonArray);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 给http头部增加Cookie
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addCookies() {
        try {
            Log.e("test", "teaching url = " + JSESSIONID);
            cookieStore = ((AbstractHttpClient) this.httpClient)
                    .getCookieStore();
            String name = "JSESSIONID";
            String value =JSESSIONID;
            String path = "/yjpts/";
            String domain ="www.yuertong.com";
            String expiryDate = "";
            BasicClientCookie cookie = new BasicClientCookie(name, value);
            cookie.setDomain(domain);
            cookie.setPath(path);
            if (expiryDate != null && !expiryDate.equals("")) {
                SimpleDateFormat format = new SimpleDateFormat(
                        "EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
                Date date = format.parse(expiryDate);
                cookie.setExpiryDate(date);
            }
            cookieStore.addCookie(cookie);
            cookieStore.clearExpired(new Date());
        } catch (Exception e) {
        }
    }
}
