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
	 * 线程管理
	 */
	public ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
	/**
	 * 参数传递
	 */
	public String BUNDLE_PARAMS = "bundle_params";
	public String BUNDLE_PARAMS1 = "bundle_params1";
	public String BUNDLE_PARAMS2 = "bundle_params2";
	public String BUNDLE_PARAMS3 = "bundle_params3";

	/*---------------------------------------文件相关------------------------------------------------*/
	/**
	 * 缓存文件
	 */
	public String ZWHD_ROOT = "Yjpty";
	/**
	 * 录制的音频文件目录
	 */
	public final String RECORDER_AUDIO_FLODER = "recorder_audios";
	/**
	 * 下载
	 */
	public final String DOWNLOAD_FILES = "download_files";
	/**
	 * 拍摄的照片目录
	 */
	public final String CAMERA_PHOTO_FLODER = "photos";
	/**
	 * 下载的照片目录
	 */
	public final String DOWNLOAD_PHOTO_FLODER = "download_photos";
	/**
	 * 录制的视频目录
	 */
	public final String RECORDER_VIDEO_FLODER = "recorder_videos";
	/**
	 * 下载的视频目录
	 */
	public final String DOWNLOAD_VIDEO_FLODER = "download_videos";
	/*---------------------------------------文件相关------------------------------------------------*/

	/**
	 * 是否在wifi 环境下
	 * */
	public static final String WIFI = "wifi";
	/**
	 * 用户名
	 * */
	public static final String USER_NAME = "user_name";
	/**
	 * 验证码
	 * */
	public static final String PASSWORD = "password";
	/**
	 * 选择的班级
	 * */
	public static final String CLASS_NAME = "class_name";
	/**
	 * wifi名字
	 * */
	public static final String WIFI_NEME = "wifi_name";
	/**
	 * wifi密码
	 * */
	public static final String WIFI_PASSWORD = "wifi_password";
	/**
	 * 是否在上课
	 * */
	public static final String IS_ON_CLASS = "is_onclass";
	/**
	 * 缓存线程
	 * */
	public final ExecutorService executorService = Executors
			.newCachedThreadPool();
}
