package yjpty.teaching.http;

import android.os.Handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 网络请求链接池
 *
 * @author Zao
 */
public class BaseHttpConnectPool  {


    /**
     * 获取本对象
     */
    public static BaseHttpConnectPool getInstance() {
        return Hcp.httpConnectionPool;
    }

    private BaseHttpConnectPool() {
    }

    static class Hcp {
        static BaseHttpConnectPool httpConnectionPool = new BaseHttpConnectPool();
    }

    /**
     * 开启进度显示
     */
    static final int OPEN_SUG = 0;
    /**
     * 关闭进度显示
     */
    static final int CLOSE_SUG = 1;
    /**
     * 直接开启进度显示
     */
    public static final int OPEN = 2;
    /**
     * 直接关闭进度显示
     */
    public static final int CLOSE = 3;
    /**
     * 网络提示
     */
    public static final int NETWORK = 4;
    /**
     * 当前的请求池
     */
    static Map<String, BaseHttpRequest> httpRequests = new ConcurrentHashMap<String, BaseHttpRequest>();
    /**
     * 请求锁
     */
    static Object object = new Object();
    /**
     * 对等待进度的显示处理
     */
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg == null )
                return;
            removeInaliveRequest();
            int count = countInaliveRequest();
            switch (msg.what) {
                case OPEN_SUG:// 检测打开等待
                    if (count > 0) {

                    }
                    break;
                case CLOSE_SUG:// 检测关闭等待
                    removeInaliveRequest();
                    if (count <= 0) {
                    }
                    break;
                case OPEN:

                    break;
                case CLOSE:

                    break;
                case NETWORK:
                    // ViewUtil.showMessage(BaseActivity.baseActivityInsantnce,
                    // "当前网络不能连接互联网,请连接互联网网络");
                    break;
            }
        }
    };

    /**
     * 移除失效的请求
     */
    static void removeInaliveRequest() {
        Set<Entry<String, BaseHttpRequest>> set = httpRequests.entrySet();
        for (Entry<String, BaseHttpRequest> entry : set) {
            if (!entry.getValue().isAlive())
                httpRequests.remove(entry.getKey());
        }
    }

    /**
     * 统计处于请求状态的显示等待进度的请求
     */
    static int countInaliveRequest() {
        int count = 0;
        Set<Entry<String, BaseHttpRequest>> set = httpRequests.entrySet();
        for (Entry<String, BaseHttpRequest> entry : set) {
            BaseHttpRequest request = entry.getValue();
            if (request.isAlive() && request.isShowLoad) {
                count++;
            }
        }
        return count;
    }

    /**
     * 网络请求添加
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param which       同一个地址的第几次请求
     * @param map         请求附带参数
     * @param isShowLoad  是否显示等待进度
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */

    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, int which, Map<String, Object> map,
                                        boolean isShowLoad, int requestMode) {
        synchronized (object) {
            // 移除已经失效的请求
            try {
                removeInaliveRequest();
                String requestTag = requestUrl;
                if (which != -1)
                    requestUrl += which;
                if (httpRequests.containsKey(requestTag))
                    return;// 当前的请求还处于活动状态
                BaseHttpRequest baseHttpRequest = new BaseHttpRequest(
                        requestUrl, params, handler, which, map, requestTag,
                        isShowLoad, requestMode);
                httpRequests.put(requestTag, baseHttpRequest);
                baseHttpRequest.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 网络请求添加,不显示等待进度
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param which       同一个地址的第几次请求
     * @param map         请求附带参数
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, int which, Map<String, Object> map, int requestMode) {
        addRequest(requestUrl, params, handler, which, map, false, requestMode);
    }

    /**
     * 网络请求添加,不显示等待进度
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param which       同一个地址的第几次请求
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, int which, int requestMode) {
        addRequest(requestUrl, params, handler, which, null, false, requestMode);
    }

    /**
     * 网络请求添加
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param which       同一个地址的第几次请求
     * @param isShowLoad  是否显示等待进度
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, int which, boolean isShowLoad, int requestMode) {
        addRequest(requestUrl, params, handler, which, null, isShowLoad, requestMode);
    }

    /**
     * 网络请求添加,不显示等待进度
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param map         请求附带参数
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, Map<String, Object> map, int requestMode) {
        addRequest(requestUrl, params, handler, -1, map, false, requestMode);
    }

    /**
     * 网络请求添加
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param map         请求附带参数
     * @param isShowLoad  是否显示等待进度
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, Map<String, Object> map, boolean isShowLoad, int
                                                requestMode) {
        addRequest(requestUrl, params, handler, -1, map, isShowLoad, requestMode);
    }

    /**
     * 网络请求添加,不显示等待进度
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, int requestMode) {
        addRequest(requestUrl, params, handler, -1, null, false, requestMode);
    }

    /**
     * 网络请求添加
     *
     * @param requestUrl  请求地址
     * @param params      请求参数
     * @param handler     请求回调
     * @param isShowLoad  是否显示等待进度
     * @param requestMode 1 post 请求 2 get请求 3 delete 请求 4 put请求
     */
    public synchronized void addRequest(String requestUrl, Object params,
                                        BaseHttpHandler handler, boolean isShowLoad, int requestMode) {
        addRequest(requestUrl, params, handler, -1, null, isShowLoad, requestMode);
    }

}
