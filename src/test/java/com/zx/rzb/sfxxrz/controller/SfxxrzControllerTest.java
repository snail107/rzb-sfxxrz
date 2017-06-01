package com.zx.rzb.sfxxrz.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zx.rzb.sfxxrz.util.CipherUtil;
import com.zx.rzb.sfxxrz.util.SslUtils;
import com.zx.rzb.sfxxrz.util.ToolUtil;

public class SfxxrzControllerTest {
	
	/**
	 * local jboss:	https://localhost:8443
	 * local jetty:	http://localhost:8080
	 * server 		https://139.196.149.98:8443
	 * server 域名 	https://zhx.chanmaobank.com
	 */
//	private static String urlPrefix="https://zhx.chanmaobank.com";
	private static String urlPrefix="http://localhost:8080";
	
//	private static String idNumber = "430281198209257217";	//身份证号
//	private static String name = "杨羚均";					//姓名
	
//	private static String idNumber = "370704198602152428";	//身份证号
//	private static String name = "鲁婷婷";					//姓名
	
	/**
	 * 银行账号查询
	 */
	private static String idNumber = "410928199101093940";	//身份证号
	private static String name = "孙淑静";					//姓名
	private static String account = "6228486523000002";		//银行账号
	private static String mobile = "13915592414";			//手机号码
	
//	private static String merchantId="008310107420024";		//商户号
//	private static String key ="25E562C52DF0D352D920FA90482F3CB4F921668BE2B8D8AB8A0F44F123BABA77";//密钥
	
	private static String merchantId="001312107420025";		//商户号（豆子科技）
	private static String key ="FEFC7996FBA128076F8DCEEFE3CDDD4F11ADF7832F9FCA02A28C634B692E901A";//密钥

	/**
	 * @Title: testidCardCheck
	 * @Description: TODO(简项认证-合一道)
	 * @date 2016年4月27日 下午2:25:39 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testidCardCheck() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/idCardCheck.action?idNumber=" + idNumber
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
	 * @Title: testidCardCheck
	 * @Description: TODO(个人身份证照片信息查询接口-合一道)
	 * @date 2016年7月29日 下午3:02:50 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testidCardPhotoInfoQuery() throws UnsupportedEncodingException {
		idNumber="120107196301029013";
		name="王国征";
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/idCardPhotoInfoQuery.action?idNumber=" + idNumber
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
	 * @Title: testphotoCompare
	 * @Description: TODO(个人身份证照片核查及证照比对接口-合一道)
	 * @date 2016年7月29日 下午3:02:50 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testphotoCompare() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String photo="";
		String url="";
		String postData="";
		try {
			
			String imgFile="D://app//uploadfiles//plugin//sign//20160304//008310107420024//201507031000904//20160302151701966//201603041444450000_144526_id_f.jpg";
			FileInputStream fis=new FileInputStream(imgFile);
			byte[] data=new byte[fis.available()];
			fis.read(data);
			fis.close();
			BASE64Encoder encoder = new BASE64Encoder();
			photo=encoder.encode(data);//返回Base64编码过的字节数组字符串 
//			System.out.println("photo:"+photo);
			
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/photoCompare.action";
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
	 * @Title: testbankCardCheckThree
	 * @Description: TODO(银行账号核查(3项))
	 * @date 2016年8月18日 上午9:27:00 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testbankCardCheckThree() throws UnsupportedEncodingException {
		String signStr=idNumber+name+account+merchantId;
		String url="";
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/bankCardCheckThree.action?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") 
					+ "&account="+ URLEncoder.encode(account, "UTF-8")
					+ "&merchantId=" + merchantId 
					+ "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	/**
	 * @Title: testbankCardCheckFourParts
	 * @Description: TODO(个人银行账户核查(4项)接口)
	 * @date 2016年8月18日 上午10:11:10 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testbankCardCheckFourParts() throws UnsupportedEncodingException {
		String signStr=idNumber+name+account+mobile+merchantId;
		String url="";
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/bankCardCheckFourParts.action?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") 
					+ "&account="+ account
					+ "&mobile=" + mobile 
					+ "&merchantId=" + merchantId 
					+ "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	
	/**
	 * @Title: testSimpleCheck
	 * @Description: TODO(简项认证-爰金)
	 * @date 2016年4月27日 下午2:25:39 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testSimpleCheck() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
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
	 * @Title: testExactCheck
	 * @Description: TODO(多项认证-爰金)
	 * @date 2016年4月27日 下午2:25:22 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testExactCheck() throws UnsupportedEncodingException {
//		idNumber="120107196301029013";
//		name="王国征";
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/exactCheck.action?idNumber=" + idNumber
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
	 * @Description: TODO(人像认证-爰金)
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
			String imgFile="D://app//uploadfiles//plugin//sign//20160304//008310107420024//201507031000904//20160302151701966//201603041444450000_144526_id_f.jpg";
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
	 * @Title: testWritePhoto
	 * @Description: TODO(个人身份证照片查询返回的photo,转换成照片存放)
	 * @date 2016年7月29日 下午3:58:20 
	 * @author yang-lj
	 */
	@Test
	public void testWritePhoto(){
		try {
//			String base64Str="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";
//			String base64Str="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";			
			String base64Str="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";
			BASE64Decoder decoder= new BASE64Decoder();
			byte[] data=decoder.decodeBuffer(base64Str);
			FileOutputStream fis=new FileOutputStream(new File("c:\\temp\\photo2.jpg"));
			fis.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @Title: testIsSimpleCitizenExists
	 * @Description: TODO(是否存在简项认证历史记录)
	 * @date 2016年4月27日 下午2:25:02 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testIsSimpleCitizenExists() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/isSimpleCitizenExists.action?idNumber=" + idNumber
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
	 * @Title: testIsExactCitizenExists
	 * @Description: TODO(是否存在多项认证历史记录)
	 * @date 2016年4月27日 下午2:24:41 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testIsExactCitizenExists() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/isExactCitizenExists.action?idNumber=" + idNumber
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
	 * @Title: testQuerySimpleCitizenData
	 * @Description: TODO(提取简项认证历史记录)
	 * @date 2016年4月27日 下午2:24:31 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQuerySimpleCitizenData() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/querySimpleCitizenData.action?idNumber=" + idNumber
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
	 * @Title: testQueryExactCitizenData
	 * @Description: TODO(提取多项认证历史记录)
	 * @date 2016年4月27日 下午2:24:31 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryExactCitizenData() throws UnsupportedEncodingException {
		String signStr=idNumber+name+merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/queryExactCitizenData.action?idNumber=" + idNumber
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
	 * @Title: testQueryBalance
	 * @Description: TODO(查询挚尖在爰金的账户余额-简项、多项的认证次数)
	 * @date 2016年4月27日 下午2:24:31 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryRZBBalance() throws UnsupportedEncodingException {
		String url;
		try {
			url = urlPrefix+"/rzb/queryRZBBalance.action";
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	/**
	 * @Title: queryRZBFaceBalance
	 * @Description: TODO(查询挚尖在爰金的账户余额-人像-----------------爰金未提供人像的余额查询接口)
	 * @date 2016年4月27日 下午2:24:31 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void queryRZBFaceBalance() throws UnsupportedEncodingException {
		String url;
		try {
			url = urlPrefix+"/rzb/queryRZBFaceBalance.action";
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	
	/**
	 * @Title: testQueryBalance
	 * @Description: TODO(查询商户在挚尖的余额)
	 * @date 2016年7月20日 上午9:34:59 
	 * @author yang-lj
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryBalance() throws UnsupportedEncodingException {
		String signStr=merchantId;
		String url;
		try {
			byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
			String sign=ToolUtil.bytes2hex(signByte);
			url = urlPrefix+"/rzb/queryBalance.action?merchantId=" + merchantId + "&sign=" + sign;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(url);
		String json = getHtml(url);
		System.out.println(json);
	}
	
	/**
	 * @Title: md5
	 * @Description: TODO(计算摘要)
	 * @date 2016年7月29日 下午2:26:11 
	 * @author yang-lj
	 * @param text
	 * @return
	 */
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
	
	/**
	 * @Title: getHtml
	 * @Description: TODO(get请求)
	 * @date 2016年7月29日 下午2:27:04 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	static String getHtml(String url) {
		try {
			URL obj = new URL(url);
			if("https".equalsIgnoreCase(obj.getProtocol())){
				SslUtils.ignoreSsl();
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
				SslUtils.ignoreSsl();
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
	
}
