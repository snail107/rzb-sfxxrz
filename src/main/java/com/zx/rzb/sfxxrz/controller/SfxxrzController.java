package com.zx.rzb.sfxxrz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zx.rzb.sfxxrz.service.SfxxrzService;
import com.zx.rzb.sfxxrz.util.CipherUtil;
import com.zx.rzb.sfxxrz.util.ConfigInfo;
import com.zx.rzb.sfxxrz.util.Constants;
import com.zx.rzb.sfxxrz.util.ToolUtil;


/**
 * @Package com.zx.rzb.sfxxrz.controller
 * @Title: SfxxrzController.java
 * @ClassName: SfxxrzController
 * @Description: TODO(认证宝征信-身份认证入口)
 * @author yang-lj
 * @date 2016年4月19日 下午2:59:18
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SfxxrzController {
	Logger log = Logger.getLogger(SfxxrzController.class);
	HashMap<String,String> tmids=new HashMap<String,String>();
 									
	@Autowired
	private SfxxrzService sfxxrzService;

	
	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份信息核查)
	 * @date 2016年5月26日 上午9:14:10 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param sign
	 */
	@RequestMapping("/idCardCheck")
	public void idCardCheck(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------idCardCheck--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.idCardCheck(idNumber, name, merchantId);
			log.info("--------idCardCheck--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}

	/**
	 * @Title: idCardPhotoInfoQuery
	 * @Description: TODO(合一道，个人身份照片信息查询)
	 * @date 2016年5月26日 上午9:20:06 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param sign
	 */
	@RequestMapping("/idCardPhotoInfoQuery")
	public void idCardPhotoInfoQuery(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------idCardPhotoInfoQuery--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.idCardPhotoInfoQuery(idNumber, name, merchantId);
			log.info("--------idCardPhotoInfoQuery--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: photoCompare
	 * @Description: TODO(合一道，人个身份证核查及证照比对)
	 * @date 2016年5月26日 上午9:22:09 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param sign
	 */
	@RequestMapping("/photoCompare")
	public void photoCompare(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String photo,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------photoCompare--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.photoCompare(idNumber, name,photo, merchantId);
			log.info("--------photoCompare--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: bankCardCheckThree
	 * @Description: TODO(合一道，个人银行账户核查(3项)接口)
	 * @date 2016年8月17日 下午3:15:08 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param sign
	 */
	@RequestMapping("/bankCardCheckThree")
	public void bankCardCheckThree(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String account,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------bankCardCheckThree--------req:--params--idNumber:"+idNumber+" name:"+name
					+" account:"+account+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(merchantId, sign,idNumber,name,account)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.bankCardCheckThree(idNumber, name,account,merchantId);
			log.info("--------bankCardCheckThree--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	
	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(4项)接口)
	 * @date 2016年8月17日 下午3:14:03 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber 身份证号码
	 * @param name	姓名
	 * @param account	银行账号
	 * @param merchantId 商户ID
	 * @param sign	签名
	 */
	@RequestMapping("/bankCardCheckFourParts")
	public void bankCardCheckFourParts(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String account,String mobile,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------bankCardCheckFourParts--------req:--params--idNumber:"+idNumber+" name:"+name
					+" account:"+account+" mobile:"+mobile+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(merchantId, sign,idNumber,name,account,mobile)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.bankCardCheckFourParts(idNumber, name,account,mobile, merchantId);
			log.info("--------bankCardCheckFourParts--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	
	/**
	 * @Title: simpleCheck
	 * @Description: TODO(简项认证)
	 * @date 2016年4月20日 下午5:28:08 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param idNumber 身份证号
	 * @param Name	姓名
	 * @param merchantId 商户ID
	 * @param Sign 签名
	 */
	@RequestMapping("/simpleCheck")
	public void simpleCheck(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------simpleCheck--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.simpleCheck(idNumber, name, merchantId);
			log.info("--------simpleCheck--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: exactCheck
	 * @Description: TODO(多项认证)
	 * @date 2016年4月19日 下午3:16:39 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/exactCheck")
	public void exactCheck(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------exactCheck--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.exactCheck(idNumber, name, merchantId);
			log.info("--------exactCheck--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: exactCheck
	 * @Description: TODO(人像对比-高清照片)
	 * @date 2016年4月19日 下午3:16:39 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/faceCheck")
	public void faceCheck(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String photo,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------faceCheck--------req:--params--idNumber:"+idNumber+" name:"+name+" photo:"+photo+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.faceCheck(idNumber, name,photo, merchantId);
			log.info("--------faceCheck--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}

	
	/**
	 * @Title: isSimpleCitizenExists
	 * @Description: TODO(是否存在简项认证历史记录)
	 * @date 2016年4月19日 下午3:17:28 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/isSimpleCitizenExists")
	public void isSimpleCitizenExists(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------isSimpleCitizenExists--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.isSimpleCitizenExists(idNumber, name, merchantId);
			log.info("--------isSimpleCitizenExists--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: isExactCitizenExists
	 * @Description: TODO(是否存在多项认证历史记录)
	 * @date 2016年4月19日 下午3:19:07 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/isExactCitizenExists")
	public void isExactCitizenExists(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------isExactCitizenExists--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.isExactCitizenExists(idNumber, name, merchantId);
			log.info("--------isExactCitizenExists--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: querySimpleCitizenData
	 * @Description: TODO(提取简项认证历史记录)
	 * @date 2016年4月19日 下午3:19:33 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/querySimpleCitizenData")
	public void querySimpleCitizenData(HttpServletRequest request,HttpServletResponse response,
			String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------querySimpleCitizenData--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.querySimpleCitizenData(idNumber, name, merchantId);
			log.info("--------querySimpleCitizenData--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: queryExactCitizenData
	 * @Description: TODO(提取多项认证历史记录)
	 * @date 2016年4月19日 下午3:20:14 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/queryExactCitizenData")
	public void queryExactCitizenData(HttpServletRequest request,HttpServletResponse response,
		String idNumber,String name,String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------queryExactCitizenData--------req:--params--idNumber:"+idNumber+" name:"+name+" merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign(idNumber, name, merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.queryExactCitizenData(idNumber, name, merchantId);
			log.info("--------queryExactCitizenData--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询账户余额)
	 * @date 2016年4月19日 下午3:21:47 
	 * @author yang-lj
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/queryBalance")
	public void queryBalance(HttpServletRequest request,HttpServletResponse response,
			String merchantId,String sign){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------queryBalance--------req:--params-- merchantId:"+merchantId+" sign:"+sign);
			if(!verifySign("","", merchantId, sign)){
				resJson.put("responseCode", Constants.RES_ERROR_SIGN);
				resJson.put("responseText","签名校验失败");
			}
			resJson=sfxxrzService.queryBalance(merchantId);
			log.info("--------queryBalance--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	
	/**
	 * @Title: queryRZBBalance
	 * @Description: TODO(查询挚尖在爰金的余额)
	 * @date 2016年7月7日 上午9:58:20 
	 * @author yang-lj
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryRZBBalance")
	public void queryRZBBalance(HttpServletRequest request,HttpServletResponse response){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------queryRZBBalance--------req");
			resJson=sfxxrzService.queryRZBBalance();
			log.info("--------queryRZBBalance--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: queryRZBBalance
	 * @Description: TODO(查询挚尖在爰金的余额)
	 * @date 2016年7月7日 上午9:58:20 
	 * @author yang-lj
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryRZBFaceBalance")
	public void queryRZBFaceBalance(HttpServletRequest request,HttpServletResponse response){
		JSONObject resJson=new JSONObject();
		try{
			request.setCharacterEncoding("utf-8");
			log.info("--------queryRZBFaceBalance--------req");
			resJson=sfxxrzService.queryRZBFaceBalance();
			log.info("--------queryRZBFaceBalance--------res:"+resJson);
		}catch(Exception e){
			if (!resJson.containsKey("responseCode")) {
				resJson.put("responseCode", Constants.RES_ERROR_UNKNOWN);
				resJson.put("responseText","未知异常");
			}
		}finally{
			responseProc(response, resJson);
		}
	}
	
	/**
	 * @Title: responseProc
	 * @Description: TODO(结果响应)
	 * @date 2016年4月27日 上午10:02:13 
	 * @author yang-lj
	 * @param response
	 * @param resJson
	 */
	private void responseProc(HttpServletResponse response, JSONObject resJson){
		try {
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(resJson.toString());
		response.getWriter().flush();
		response.getWriter().close();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * @Title: verifySign
	 * @Description: TODO(验签名)
	 * @date 2016年4月21日 上午8:59:22 
	 * @author yang-lj
	 * @param idNumber 身份证号
	 * @param Name	姓名
	 * @param merchantId 商户ID
	 * @param sign 签名
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private boolean verifySign(String idNumber, String name, String merchantId, String sign) throws Exception, UnsupportedEncodingException {
		String key=ConfigInfo.merchantKeyMap.get(merchantId);
		String signStr=idNumber+name+merchantId;
		byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
		return Arrays.areEqual(ToolUtil.hex2byte(sign), signByte);
	}
	
	/**
	 * @Title: verifySign
	 * @Description: TODO(验签名)
	 * @date 2016年8月17日 下午3:50:43 
	 * @author yang-lj
	 * @param merchantId
	 * @param sign
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private boolean verifySign(String merchantId, String sign,String... params) throws Exception, UnsupportedEncodingException {
		String key=ConfigInfo.merchantKeyMap.get(merchantId);
		String signStr="";
		for (String param : params)  
		signStr+=param;
		signStr+=merchantId;
		log.info("----verifySign------signStr:"+signStr);
		byte[] signByte=CipherUtil.encodeHmac(signStr.getBytes("UTF-8"), ToolUtil.hex2byte(key));
		return Arrays.areEqual(ToolUtil.hex2byte(sign), signByte);
	}
}
