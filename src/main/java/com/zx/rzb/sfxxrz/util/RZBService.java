package com.zx.rzb.sfxxrz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class RZBService {

	public static void main(String[] args) {	
		simpleCheck();
	}
	
	static void simpleCheck(){
		String account = "ziji_admin";
		String key = "3e22A75z";
		String idNumber = "110101198010103365";
		String name = "测试";
		String sign = md5(md5(idNumber + account) + key);
		String url;
		try {
			url = "https://service.sfxxrz.com/simpleCheck.ashx?idNumber=" + idNumber
						+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + account + "&pwd="
						+ key + "&sign=" + sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		String json = getHtml(url);
		System.out.println(json);
	}
	
	static void queryBalance(){
		String account = "xxx";
		String key = "xxx";
		String sign = md5(md5(account) + key);
		String url = "https://service.sfxxrz.com/queryBalance.ashx?account=" + account + "&pwd="
						+ key + "&sign=" + sign;
		String json = getHtml(url);
		System.out.println(json);
	}
	
	static void isSimpleCitizenExists(){
		String account = "xxx";
		String key = "xxx";
		String idNumber = "110101198010103365";
		String name = "测试";
		String sign = md5(md5(idNumber + account) + key);
		String url;
		try {
			url = "https://service.sfxxrz.com/isSimpleCitizenExists.ashx?idNumber=" + idNumber
						+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + account + "&pwd="
						+ key + "&sign=" + sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		String json = getHtml(url);
		System.out.println(json);
	}
	
	static void querySimpleCitizenData(){
		String account = "xxx";
		String key = "xxx";
		String idNumber = "110101198010103365";
		String name = "测试";
		String sign = md5(md5(idNumber + account) + key);
		String url;
		try {
			url = "https://service.sfxxrz.com/querySimpleCitizenData.ashx?idNumber=" + idNumber
						+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + account + "&pwd="
						+ key + "&sign=" + sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		String json = getHtml(url);
		System.out.println(json);
	}
	
	static String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	static String getHtml(String url) {
		try {
			URL obj = new URL(url);
			if("https".equalsIgnoreCase(obj.getProtocol())){
				SslUtils.ignoreSsl();
			}
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
