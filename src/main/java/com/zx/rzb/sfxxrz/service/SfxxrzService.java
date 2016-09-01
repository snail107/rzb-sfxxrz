package com.zx.rzb.sfxxrz.service;

import net.sf.json.JSONObject;

public interface SfxxrzService {
	
	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份信息核查)
	 * @date 2016年5月26日 上午9:24:37 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject idCardCheck(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: idCardPhotoInfoQuery
	 * @Description: TODO(合一道，个人身份照片信息查询)
	 * @date 2016年5月26日 上午9:24:45 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject idCardPhotoInfoQuery(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: photoCompare
	 * @Description: TODO(合一道，人个身份证核查及证照比对)
	 * @date 2016年5月26日 上午9:24:55 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject photoCompare(String idNumber,String name,String photo,String merchantId)throws Exception;

	
	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(3项)接口)
	 * @date 2016年8月17日 下午4:10:48 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	public JSONObject bankCardCheckThree(String idNumber,String name,String account,String merchantId)throws Exception;

	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(4项)接口)
	 * @date 2016年8月17日 下午4:10:48 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	public JSONObject bankCardCheckFourParts(String idNumber,String name,String account,String mobile,String merchantId)throws Exception;
	
	/**
	 * @Title: simpleCheck
	 * @Description: TODO(简项认证)
	 * @date 2016年4月21日 上午10:32:29 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject simpleCheck(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: exactCheck
	 * @Description: TODO(多项认证)
	 * @date 2016年4月27日 上午10:04:04 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject exactCheck(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: exactCheck
	 * @Description: TODO(人像认证)
	 * @date 2016年4月27日 上午10:04:04 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject faceCheck(String idNumber,String name,String phone,String merchantId)throws Exception;
	
	/**
	 * @Title: isSimpleCitizenExists
	 * @Description: TODO(是否存在简项认证历史记录)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject isSimpleCitizenExists(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: isExactCitizenExists
	 * @Description: TODO(是否存多项认证历史记录)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject isExactCitizenExists(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: querySimpleCitizenData
	 * @Description: TODO(提取简项认证历史记录)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject querySimpleCitizenData(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: queryExactCitizenData
	 * @Description: TODO(提取多项认证历史记录)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryExactCitizenData(String idNumber,String name,String merchantId)throws Exception;
	
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询商户的账户余额)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryBalance(String merchantId)throws Exception;
	
	/**
	 * @Title: queryRZBBalance
	 * @Description: TODO(查询挚尖在爰金的账户余额)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryRZBBalance()throws Exception;
	
	/**
	 * @Title: queryRZBFaceBalance
	 * @Description: TODO(查询挚尖在爰金的账户余额-人像)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryRZBFaceBalance()throws Exception;
}
