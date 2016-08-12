package com.zx.rzb.sfxxrz.outpf;


public interface RzbpfCall {

	/**
	 * @Title: call
	 * @Description: TODO(认证宝接口调用-简项认证)
	 * @date 2016年4月21日 上午11:14:50 
	 * @author yang-lj
	 * @param url
	 * @return
	 */
	public String simpleCheck(String idNumber,String name);
	
	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份信息查核)
	 * @date 2016年5月24日 下午4:28:39 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @return
	 */
	public String idCardCheck(String idNumber,String name);

	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份证照片信息查询)
	 * @date 2016年5月24日 下午4:28:39 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @return
	 */
	public String idCardPhotoQuery(String idNumber,String name);
	
	/**
	 * @Title: idCardCheck
	 * @Description: TODO(合一道，个人身份证核查及证照比对)
	 * @date 2016年5月24日 下午4:28:39 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @return
	 */
	public String idCardPhotoCompare(String idNumber,String name,String photo);
	
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
	public String exactCheck(String idNumber,String name );
	
	
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
	public String faceCheck(String idNumber,String name,String photo);
	
	
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
	public String isSimpleCitizenExists(String idNumber,String name,String sign);
	
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
	public String isExactCitizenExists(String idNumber,String name,String sign);
	
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
	public String querySimpleCitizenData(String idNumber,String name,String sign);
	
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
	public String queryExactCitizenData(String idNumber,String name,String sign);
	
	
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询账户余额)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param sign
	 * @return
	 */
	public String queryBalance();
	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询账户余额-人像)
	 * @date 2016年4月27日 上午11:39:03 
	 * @author yang-lj
	 * @param sign
	 * @return
	 */
	public String queryFaceBalance();
	
}
