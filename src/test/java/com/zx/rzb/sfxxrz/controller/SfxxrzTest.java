package com.zx.rzb.sfxxrz.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

import sun.misc.BASE64Encoder;

import com.zx.rzb.sfxxrz.util.CipherUtil;
import com.zx.rzb.sfxxrz.util.ToolUtil;


public class SfxxrzTest {
	
	private static String urlPrefix="https://zhx.chanmaobank.com";
	private static String idNumber = "370704198602152428";					//身份证号
	private static String name = "鲁婷婷";						//姓名
	private static String merchantId="001312107420025";					//商户号（豆子科技）
	private static String key ="FEFC7996FBA128076F8DCEEFE3CDDD4F11ADF7832F9FCA02A28C634B692E901A";							//密钥
	
	/**
	 * @Title: testSimpleCheck
	 * @Description: TODO(简项认证)
	 * @date 2016年4月27日 下午2:25:39 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testSimpleCheck() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=encodeHmac(signStr.getBytes("UTF-8"), hex2byte(key));
			String sign=bytes2hex(signByte);
			url = urlPrefix+"/rzb/simpleCheck.action?idNumber=" + idNumber
						+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&merchantId=" + merchantId + "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	
	/**
	 * @Title: testfaceCheck
	 * @Description: TODO(人像认证)
	 * @date 2016年4月27日 下午2:25:22 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testFaceCheck() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String photo="";
		String url="";
		String postData="";
		try {
			String imgFile="";//---------------img url---------------------
			FileInputStream fis=new FileInputStream(imgFile);
			byte[] data=new byte[fis.available()];
			fis.read(data);
			fis.close();
			BASE64Encoder encoder = new BASE64Encoder();  
			photo=encoder.encode(data);//返回Base64编码过的字节数组字符串  
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/faceCheck.action";
			postData= "idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") 
					+ "&photo="+ URLEncoder.encode(photo, "UTF-8")
					+ "&merchantId=" + merchantId + "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url,postData);
		System.out.println(json);
	}
	
	

	
	/**
	 * @Title: testQueryBalance
	 * @Description: TODO(查询账户余额)
	 * @date 2016年4月27日 下午2:24:31 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryBalance() throws UnsupportedEncodingException {
		String signStr=merchantId;
		String url;
		try {
			byte[] signByte=encodeHmac(signStr.getBytes("UTF-8"), hex2byte(key));
			String sign=bytes2hex(signByte);
			url = urlPrefix+"/rzb/queryBalance.action?merchantId=" + merchantId + "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
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
				ignoreSsl();
			}
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
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
	
	/**
	 * @Title: getHtml
	 * @Description: TODO(带post的请求参数)
	 * @date 2016年4月29日 下午2:35:07 
	 * @author yang-lj
	 * @param url
	 * @param postDate
	 * @return
	 */
	static String getHtml(String url,String postData) {
		try {
			URL obj = new URL(url);
			if("https".equalsIgnoreCase(obj.getProtocol())){
				ignoreSsl();
			}
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
            PrintWriter out = new PrintWriter(con.getOutputStream());
            out.print(postData);
            out.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
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
	
	public static byte[] encodeHmac(byte[] data,byte[] key)throws Exception{
		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA256");
		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	public static void ignoreSsl() throws Exception{
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
	private static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
	
	private static class miTM implements TrustManager,X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}
		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}
		public void checkServerTrusted(X509Certificate[] certs, String authType)
				throws CertificateException {
			return;
		}
		public void checkClientTrusted(X509Certificate[] certs, String authType)
				throws CertificateException {
			return;
		}
	}
	
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
	
	public static byte[] hex2byte(String s) {
		if (s.length() % 2 == 0) {
			return hex2byte(s.getBytes(), 0, s.length() >> 1);
		} else {
			throw new RuntimeException("Uneven number(" + s.length()
					+ ") of hex digits passed to hex2byte.");
		}
	}
	
	private static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}
	
	
}
