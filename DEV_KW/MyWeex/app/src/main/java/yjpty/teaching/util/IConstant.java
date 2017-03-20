package yjpty.teaching.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yjpty.teaching.http.BaseHttpConnectPool;


/**
 * 常用数据定义
 */
public interface IConstant {
	/**
	 * 请求连接池
	 */
	public BaseHttpConnectPool HTTP_CONNECT_POOL = BaseHttpConnectPool
			.getInstance();
	/**
	 * 参数传递
	 */
	public String BUNDLE_PARAMS = "bundle_params";
	public String BUNDLE_PARAMS1 = "bundle_params1";
	public String BUNDLE_PARAMS2 = "bundle_params2";

	/**
	 * 下载的照片目录
	 */
	public final String DOWNLOAD_PHOTO_FLODER = "download_photos";

	/**
	 * wifi名字
	 * */
	public static final String WIFI_NEME = "wifi_name";
	/**
	 * 是否在上课
	 * */
	public static final String IS_ON_CLASS = "is_onclass";
	/**
	 * 缓存线程
	 * */
	public final ExecutorService executorService = Executors
			.newCachedThreadPool();
	/**
	 * 调用相册返回码
	 */
	public int FOR_PHOTO = 1002;
}
