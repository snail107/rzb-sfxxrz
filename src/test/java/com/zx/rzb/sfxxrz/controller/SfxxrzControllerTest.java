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
			String base64Str="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";
//			String base64Str="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";
			BASE64Decoder decoder= new BASE64Decoder();
			byte[] data=decoder.decodeBuffer(base64Str);
			FileOutputStream fis=new FileOutputStream(new File("d:\\app\\photo1.jpg"));
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
