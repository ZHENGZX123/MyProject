package cn.kwim.mqttcilent.common.utils;

import java.security.MessageDigest;

/**
 * @Title: Md5算法实现
 * @Description: 
 * @Copyright: Copyright (c) 2007
 * @Company: 
 * @author 
 * @version 1.0
 */

public class Md5 {
	/**
	 * @功能：计算输入的字符串的MD5
	 * @输入：s,需要加密的字符串;
	 * @输出：该字符串的MD5；
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(Md5.MD5("md5加密"));
	}
}
