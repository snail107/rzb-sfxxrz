package com.zx.rzb.sfxxrz.util;

import net.sf.json.JSONObject;

public class ToolUtil {
	
	
	/**
	 * @Title: byteTohexStr
	 * @Description: TODO(字节转十六进制)
	 * @date 2012-12-20 下午06:19:41
	 * @author yang-lj
	 * @param @param bytes
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String bytes2hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * @Title: hex2byte
	 * @Description: TODO(十六进制转字节)
	 * @date 2013-1-16 下午04:23:16
	 * @author yang-lj
	 * @param @param b
	 * @param @param offset
	 * @param @param len
	 * @param @return 设定文件
	 * @return byte[] 返回类型
	 * @throws
	 */
	private static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}

	/**
	 * @Title: hex2byte
	 * @Description: TODO( 十六进制转字节)
	 * @date 2013-1-16 下午04:24:21
	 * @author yang-lj
	 * @param @param s
	 * @param @return 设定文件
	 * @return byte[] 返回类型
	 * @throws
	 */
	public static byte[] hex2byte(String s) {
		if (s.length() % 2 == 0) {
			return hex2byte(s.getBytes(), 0, s.length() >> 1);
		} else {
			throw new RuntimeException("Uneven number(" + s.length()
					+ ") of hex digits passed to hex2byte.");
		}
	}
	
	/**
	 * 从json中取得对应的值--string
	 * @date 2012-11-14
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getJsonStr(JSONObject jsonObject, String key) {
		if (jsonObject != null && key != null && !key.isEmpty()
				&& jsonObject.containsKey(key)) {
			return jsonObject.getString(key);
		} else {
			return "";
		}
	}
	
	/**
	 * @Title: getJsonInt
	 * @Description: TODO(从json中取得对应的值--int，未取到值则返回0)
	 * @date 2016年5月27日 下午2:56:19 
	 * @author yang-lj
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static int getJsonInt(JSONObject jsonObject, String key) {
		if (jsonObject != null && key != null && !key.isEmpty()
				&& jsonObject.containsKey(key)) {
			return jsonObject.getInt(key);
		} else {
			return 0;
		}
	}
	
}
