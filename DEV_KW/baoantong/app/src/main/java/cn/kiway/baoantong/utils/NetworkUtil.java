package cn.kiway.baoantong.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 
 * @version (版本标识) 1.0
 * 
 */
public class NetworkUtil {
	public static final int BUFFER_SIZE_1K_HARF = 512;
	public static final int BUFFER_SIZE_1K = 1024;
	public static final int BUFFER_SIZE_1M = 1024 * 1024;

	public static final String HTTP_REQUEST_GET = "get";
	public static final String HTTP_REQUEST_POST = "post";
	public static final int HTTP_RESPONSE_CODE_OK = 200;
	public static final int CHANNEL_IMAGE_ALPHA = 50;
	public static final int SCREEN_DEFAULT_PIXEL = 1280;
	public static final int YIBAI = 100;

	public static final int TIME_1K_MILLISECONDS = 1000;
	public static final int TIME_3K_MILLISECONDS = 3000;
	public static final int TIME_5K_MILLISECONDS = 5000;
	public static final int TIME_10K_MILLISECONDS = 10000;

	public static boolean isNetworkAvailable2(Context context) {
		final ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
		if (cm != null) {
			final NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null && info.isConnectedOrConnecting()) {
				return true;
			}
		}

		return false;
	}

	public static boolean isNetworkAvailable(Context context) {
		final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			final NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isNetworkConnectInternet(Context c) {
		final ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}

		return false;
	}

	public static boolean isNetworkAvailable3(Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null) {
			return network.isConnected();
		}

		return false;
	}

	public static boolean isGpsEnabled(Context context) {
		final LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		final List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	public static String getStringFromUrl(String urlPath, String encoding) throws Exception {
		final URL url = new URL(urlPath);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(HTTP_REQUEST_GET);
		conn.setConnectTimeout(6 * TIME_1K_MILLISECONDS);
		if (conn.getResponseCode() == HTTP_RESPONSE_CODE_OK) {
			final InputStream inputStream = conn.getInputStream();
			final byte[] data = readStream(inputStream);
			return new String(data, encoding);
		}
		return null;
	}

	public static boolean isWifiEnabled(Context context) {
		final ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	public static byte[] getImageFromUrl(String urlPath) throws Exception {
		final URL url = new URL(urlPath);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(HTTP_REQUEST_GET);
		conn.setConnectTimeout(6 * TIME_1K_MILLISECONDS);
		if (conn.getResponseCode() == HTTP_RESPONSE_CODE_OK) {
			final InputStream inputStream = conn.getInputStream();
			return readStream(inputStream);
		}
		return null;
	}

	public static boolean isWifi(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	public static boolean is3G(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		final ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		final byte[] buffer = new byte[BUFFER_SIZE_1K];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		inStream.close();

		return outstream.toByteArray();
	}

	public synchronized Bitmap getBitmapFromUrl(String urlPath) {
		URL myFileUrl = null;
		Bitmap bitmap = null;

		try {
			myFileUrl = new URL(urlPath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpURLConnection conn;

		try {
			conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			final InputStream is = conn.getInputStream();
			if (is.toString().length() == 0) {
				return null;
			}
			final int length = (int) conn.getContentLength();
			if (length > 0) {
				final byte[] imgData = new byte[length];
				final byte[] temp = new byte[BUFFER_SIZE_1K_HARF];
				int readLen = 0;
				int destPos = 0;

				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}

				bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public String getLocalWiFiMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();

		String ip = intToIp(info.getIpAddress());
		System.out.println("------------------>  ip = " + ip);

		return info.getMacAddress();

	}

	private String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
	}

	public String getUsingMacAddress() {
		String result = "";
		String Mac = "";
		result = callCmd("busybox ifconfig", "HWaddr");

		// 如果返回的result == null，则说明网络不可取
		if (result == null) {
			return "网络出错，请检查网络";
		}

		// 对该行数据进行解析
		// 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
		if (result.length() > 0 && result.contains("HWaddr") == true) {
			Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
			Log.i("test", "Mac:" + Mac + " Mac.length: " + Mac.length());

			result = Mac;

			/*
			 * if(Mac.length()>1){ Mac = Mac.replaceAll(" ", ""); result = "";
			 * String[] tmp = Mac.split(":"); for(int i = 0;i<tmp.length;++i){
			 * result +=tmp[i]; } }
			 * Log.i("test",result+" result.length: "+result.length());
			 */
		}
		return result;
	}

	public String callCmd(String cmd, String filter) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);

			// 执行命令cmd，只取结果中含有filter的这一行
			while ((line = br.readLine()) != null && line.contains(filter) == false) {
				// result += line;
				Log.i("test", "line: " + line);
			}

			result = line;
			Log.i("test", "result: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// read mac address from file: /sys/class/net/eth0/address
	public static String loadFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/*
	 * 获取以太网 MAC 地址
	 */
	public static String getEth0MacAddress() {
		try {
			return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 获取Wifi MAC 地址
	 */
	public static String getWifiMacAddress() {
		try {
			return loadFileAsString("/sys/class/net/wlan0/address").toUpperCase().substring(0, 17);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
