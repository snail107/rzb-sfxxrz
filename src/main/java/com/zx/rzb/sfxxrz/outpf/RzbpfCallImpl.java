package com.zx.rzb.sfxxrz.outpf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.zx.rzb.sfxxrz.util.CipherUtil;
import com.zx.rzb.sfxxrz.util.Constants;
import com.zx.rzb.sfxxrz.util.SslUtils;

public class RzbpfCallImpl implements RzbpfCall {
	Logger log = Logger.getLogger(RzbpfCallImpl.class);
	
	
	
	/**
	 * @Title: call
	 * @Description: TODO(合一道，调用-个人身份核查接口)
	 * @date 2016年4月21日 上午11:14:50 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	public String idCardCheck(String idNumber,String name){
		String result="";
		try {
			String prarm=Constants.RCOMP_ID
					+"|"+Constants.RAPI_ID_CHECK
					+"|"+name
					+"|"+idNumber
					+"|"+Constants.RCOMP_ORDERID
					+"|"+Constants.RCOMP_KEY;
//			log.info("prarm:"+prarm);
			String sign=CipherUtil.md5(prarm).toLowerCase();//合一道接口MD5结果要求 小写
			String url = "https://"+Constants.HYD_URI+"/api/personal/idCardCheck?pdocument_no=" + idNumber
					+ "&pname=" + URLEncoder.encode(name, "UTF-8")
					+ "&rcomp_orderid=" + Constants.RCOMP_ORDERID
					+ "&rcomp_id="+ Constants.RCOMP_ID
					+ "&rapi_id="+ Constants.RAPI_ID_CHECK
					+ "&sign=" + sign;
			log.info("----idCardCheck--hyd--req:"+url);
			result= call(url);
			log.info("----idCardCheck--hyd--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("个人身份核查接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: call
	 * @Description: TODO(合一道，个人身份证照片信息查询接口)
	 * @date 2016年4月21日 上午11:14:50 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	public String idCardPhotoQuery(String idNumber,String name){
		String result="";
		try {
			String prarm=Constants.RCOMP_ID+"|"+Constants.RAPI_ID_PHOTO_QUERY+"|"+name+"|"+idNumber+"|"+ Constants.RCOMP_ORDERID+"|"+ Constants.RCOMP_KEY;
			log.info("prarm:"+prarm);
			String sign=CipherUtil.md5(prarm).toLowerCase();//合一道接口MD5结果要求 小写
			String url = "https://"+Constants.HYD_URI+"/api/personal/idCardPhotoInfoQuery?pname=" 
					+ URLEncoder.encode(name, "UTF-8")
					+ "&pdocument_no=" + idNumber
					+ "&rcomp_orderid=" + Constants.RCOMP_ORDERID
					+ "&rcomp_id="+ Constants.RCOMP_ID
					+ "&rapi_id="+ Constants.RAPI_ID_PHOTO_QUERY
					+ "&sign=" + sign;
			log.info("----idCardPhotoQuery--hyd--req:"+url);
			result= call(url);
			log.info("----idCardPhotoQuery--hyd--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("个人身份证照片信息查询接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份证核查及证照比对)
	 * @date 2016年5月24日 下午4:28:39 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @return
	 */
	public String idCardPhotoCompare(String idNumber,String name,String photo){
		String result="";
		try {
			String photoDel=photo;
//			String photoDel=photo.replaceAll ("\\r\\n", "");
			String prarm=Constants.RCOMP_ID+"|"+Constants.RAPI_ID_PHOTO_COMPARE+"|"+name+"|"+idNumber+"|"+photoDel+"|"+ Constants.RCOMP_ORDERID+"|"+ Constants.RCOMP_KEY;
//			log.info("---------------prarm:"+prarm);
			String sign=CipherUtil.md5(prarm).toLowerCase();//合一道接口MD5结果要求 小写
			String url = "https://"+Constants.HYD_URI+"/api/personal/photoCompare";
			String post_data = null;
			post_data = "rcomp_id="+ Constants.RCOMP_ID
					+ "&rapi_id="+ Constants.RAPI_ID_PHOTO_COMPARE
					+ "&pname=" + URLEncoder.encode(name, "UTF-8")
					+ "&pdocno=" + idNumber
					+ "&pphoto=" + URLEncoder.encode(photoDel, "UTF-8")
					+ "&rcomp_orderid=" + Constants.RCOMP_ORDERID
					+ "&sign=" + sign;
			log.info("----idCardPhotoCompare--hyd--req:"+url);
//			log.info("----idCardPhotoCompare--hyd--post_data:"+post_data);
			result= call(url,post_data);
			log.info("----idCardPhotoCompare--hyd--res:"+result);
		} catch (UnsupportedEncodingException e) {
			log.error("个人身份证照片信息查询接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	
	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(3项)接口)
	 * @date 2016年8月17日 下午4:21:42 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @return
	 */
	public String bankCardCheckThree(String idNumber,String name,String account){
		String result="";
		try {
			String prarm=Constants.RCOMP_ID+"|"+Constants.RAPI_ID_BANKCARD_CHECK3+"|"+name
					+"|"+idNumber+"|"+account+"|"+ Constants.RCOMP_ORDERID+"|"+ Constants.RCOMP_KEY;
			log.info("---------------prarm:"+prarm);
			String sign=CipherUtil.md5(prarm).toLowerCase();//合一道接口MD5结果要求 小写
			String url = "https://"+Constants.HYD_URI+"/api/personal/bankCardCheckThree?pname="+ URLEncoder.encode(name, "UTF-8")
					+ "&paccount_no=" + account
					+ "&pdocument_no=" + idNumber
					+ "&rcomp_orderid=" + Constants.RCOMP_ORDERID
					+ "&rcomp_id="+ Constants.RCOMP_ID
					+ "&rapi_id="+ Constants.RAPI_ID_BANKCARD_CHECK3
					+ "&sign=" + sign;
			log.info("----bankCardCheckThree--hyd--req:"+url);
			result= call(url);
			log.info("----bankCardCheckThree--hyd--res:"+result);
		} catch (UnsupportedEncodingException e) {
			log.error("个人银行账户核查(3项)接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(4项)接口)
	 * @date 2016年8月17日 下午4:21:42 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @return
	 */
	public String bankCardCheckFourParts(String idNumber,String name,String account,String mobile){
		String result="";
		try {
			String prarm=Constants.RCOMP_ID+"|"+Constants.RAPI_ID_BANKCARD_CHECK4+"|" +name
					+"|"+idNumber+"|"+account+"|"+mobile+"|"+ Constants.RCOMP_ORDERID+"|"+ Constants.RCOMP_KEY;
			log.info("---------------prarm:"+prarm);
			String sign=CipherUtil.md5(prarm).toLowerCase();//合一道接口MD5结果要求 小写
			String url = "https://"+Constants.HYD_URI+"/api/personal/bankCardCheckFourParts?pname="+ URLEncoder.encode(name, "UTF-8")
					+ "&pdocument_no=" + idNumber
					+ "&paccount_no=" + account
					+ "&pmobile=" + mobile
					+ "&rcomp_orderid=" + Constants.RCOMP_ORDERID
					+ "&rcomp_id="+ Constants.RCOMP_ID
					+ "&rapi_id="+ Constants.RAPI_ID_BANKCARD_CHECK4
					+ "&sign=" + sign;
			log.info("----bankCardCheckFourParts--hyd--req:"+url);
			result= call(url);
			log.info("----bankCardCheckFourParts--hyd--res:"+result);
		} catch (UnsupportedEncodingException e) {
			log.error("个人银行账户核查(4项)接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	
	/**
	 * @Title: call
	 * @Description: TODO(认证宝接口调用-简项认证)
	 * @date 2016年4月21日 上午11:14:50 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	public String simpleCheck(String idNumber,String name){
		String result="";
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.SIMPLE_ACCOUNT) + Constants.SIMPLE_KEY);//签名
			String url = "https://"+Constants.RZB_URI+"/simpleCheck.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----simpleCheck--rzb--req:"+url);
			result= call(url);
			log.info("----simpleCheck--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("简项认证接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: exactCheck
	 * @Description: TODO(认证宝接口调用-多项认证)
	 * @date 2016年4月27日 上午10:43:09 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String exactCheck(String idNumber,String name){
		String result="";
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.EXACT_ACCOUNT) + Constants.EXACT_KEY);//签名
			String url = "https://"+Constants.RZB_URI+"/exactCheck.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----exactCheck--rzb--req:"+url);
			result= call(url);
			log.info("----exactCheck--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("多项认证接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	
	/**
	 * @Title: faceCheck
	 * @Description: TODO(认证宝接口调用-人像认证)
	 * @date 2016年4月27日 上午10:43:09 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String faceCheck(String idNumber,String name,String photo){
		String result="";
		try {
			String param=idNumber + Constants.FACE_ACCOUNT;
			String sign = CipherUtil.md5(param + CipherUtil.md5(Constants.FACE_KEY));//签名
			String url = "http://"+Constants.FACE_URI+"/api/faceCheck";
			String post_data = null;
			try {
				post_data = "idNumber=" + idNumber
						+ "&name=" + URLEncoder.encode(name, "UTF-8") 
						+ "&photo="+ URLEncoder.encode(photo, "UTF-8") 
						+ "&account=" + Constants.FACE_ACCOUNT 
						+ "&sign=" + sign;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			log.info("----faceCheck--rzb--req:"+url);
			result= call(url,post_data);
			log.info("----faceCheck--rzb--res:"+result);
		}catch(Exception e){
			log.error("人像认证接口调用出错err:"+e.getMessage(),e);
		}
		return result;
	}
	
	
	/**
	 * @Title: isSimpleCitizenExists
	 * @Description: TODO(是否存在简项认证历史记录)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String isSimpleCitizenExists(String idNumber,String name,String sign){
		String result="";
		try {
			String url = "https://"+Constants.RZB_URI+"/isSimpleCitizenExists.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----isSimpleCitizenExists--rzb--req:"+url);
			result= call(url);
			log.info("----isSimpleCitizenExists--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("是否存在简项认证历史记录err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: isExactCitizenExists
	 * @Description: TODO(是否存在多项认证历史记录)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String isExactCitizenExists(String idNumber,String name,String sign){
		String result="";
		try {
			String url = "https://"+Constants.RZB_URI+"/isExactCitizenExists.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----isExactCitizenExists--rzb--req:"+url);
			result= call(url);
			log.info("----isExactCitizenExists--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("是否存在多项认证历史记录err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: isSimpleCitizenExists
	 * @Description: TODO(提取简项认证历史记录)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String querySimpleCitizenData(String idNumber,String name,String sign){
		String result="";
		try {
			String url = "https://"+Constants.RZB_URI+"/querySimpleCitizenData.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----querySimpleCitizenData--rzb--req:"+url);
			result= call(url);
			log.info("----querySimpleCitizenData--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("提取简项认证历史记录err:"+e.getMessage(),e);
		}
		return result;
	}
	
	
	/**
	 * @Title: queryExactCitizenData
	 * @Description: TODO(提取多项认证历史记录)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param sign
	 * @return
	 */
	public String queryExactCitizenData(String idNumber,String name,String sign){
		String result="";
		try {
			String url = "https://"+Constants.RZB_URI+"/queryExactCitizenData.ashx?idNumber=" + idNumber
					+ "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----queryExactCitizenData--rzb--req:"+url);
			result= call(url);
			log.info("----queryExactCitizenData--rzb--res:"+result);
		}catch(UnsupportedEncodingException e){
			log.error("提取多项认证历史记录err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询账户余额)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param sign
	 * @return
	 */
	public String queryBalance(){
		String result="";
		String sign = CipherUtil.md5(CipherUtil.md5(Constants.SIMPLE_ACCOUNT) + Constants.SIMPLE_KEY);// 签名
		try {
			String url = "https://"+Constants.RZB_URI+"/queryBalance.ashx?account=" + Constants.SIMPLE_ACCOUNT + "&pwd="
					+ Constants.SIMPLE_KEY + "&sign=" + sign;
			log.info("----queryBalance--rzb--req:"+url);
			result= call(url);
			log.info("----queryBalance--rzb--res:"+result);
		}catch(Exception e){
			log.error("查询账户余额err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询账户余额)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param sign
	 * @return
	 */
	public String queryFaceBalance(){
		String result="";
		String sign = CipherUtil.md5(CipherUtil.md5(Constants.FACE_ACCOUNT) + Constants.FACE_KEY);// 签名
		try {
			String url = "https://"+Constants.RZB_URI+"/queryBalance.ashx?account=" + Constants.FACE_ACCOUNT + "&pwd="
					+ Constants.FACE_KEY + "&sign=" + sign;
			log.info("----queryFaceBalance--rzb--req:"+url);
			result= call(url);
			log.info("----queryFaceBalance--rzb--res:"+result);
		}catch(Exception e){
			log.error("查询账户余额err:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * @Title: call
	 * @Description: TODO(统一接口调用)
	 * @date 2016年4月21日 上午11:34:55 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	private String call(String url){
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
			log.error("认证宝接口调用出错err:"+e.getMessage(),e);
			return "";
		}
	}
	
	
	/**
	 * @Title: call
	 * @Description: TODO(统一接口调用)
	 * @date 2016年4月21日 上午11:34:55 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	private String call(String url,String postData){
		try {
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(postData);
            out.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
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
