package com.zx.rzb.sfxxrz.util;

public class Constants {

	/**
	 * 认证平台URI
	 */
	public static final String RZB_URI="service.sfxxrz.com";			//身份信息认证
	public static final String FACE_URI="facecheck.sfxxrz.com:4399";	//人像认证
	public static final String HYD_URI="api.hydsk.com";					//合一道个人身份查核
	
	/**
	 * 	合一道
	 */
	public static final String RCOMP_ORDERID="";						//商户自定义KEY
	public static final String RCOMP_ID="40664";						//商户id
	public static final String RCOMP_KEY="CYQgEhbUKloEIarJDW4o";		//商户密钥
	/**
	 * 合一道API
	 */
	public static final String RAPI_ID_CHECK="50201";					//身份信息查核合一道Api id
	public static final String RAPI_ID_PHOTO_QUERY="50202";				//个人身份证照片信息查询合一道Api id
	public static final String RAPI_ID_PHOTO_COMPARE="50206";			//个人身份证核查及证照比对合一道Api id
	public static final String RAPI_ID_BANKCARD_CHECK4="50307";			//个人银行账户核查(4项)接口合一道Api id
	public static final String RAPI_ID_BANKCARD_CHECK3="50311";			//个人银行账户核查(3项)接口合一道Api id
	
	
	/**
	 * 爰金,认证账号、密码
	 */
	public static String SIMPLE_ACCOUNT="ziji_admin";	//简项认证用户账号
	public static String SIMPLE_KEY="3e22A75z";			//简项认证用户密码
	
	public static String EXACT_ACCOUNT="";				//多项认证用户账号
	public static String EXACT_KEY="";					//多项认证用户密码
	
	public static String FACE_ACCOUNT="zhijian0506";					//人像认证用户账号
	public static String FACE_KEY="84526166cd54d62dc0b408f96ceeb85d";	//人像认证用户密码
//	public static String FACE_ACCOUNT="MTIyODQ4NDk=";					//人像认证用户账号
//	public static String FACE_KEY="b9eb5377df93455e8faa8136227136f0";	//人像认证用户密码
	
	

	/**
	 * 签名算法
	 */
//	public static String MAC_ALGORITHM="HmacMD5";	//摘要128位
//	public static String MAC_ALGORITHM="HmacSHA1";	//摘要160位
	public static String MAC_ALGORITHM="HmacSHA256";//摘要256位
	
	/**
	 * 接口调用返回码，认证宝
	 */
	public static final int RES_SUCCESS=100;			//调用成功
	public static final int RES_ERROR_UNKNOWN=-1;		//未知异常
	public static final int RES_ERROR_MERCHANT_UNKNOWN=-101;		//未知商户
	public static final int RES_ERROR_INSUFFICIENT_BALANCE=-31;		//余额不足
	public static final int RES_ERROR_PARAM=-60;		//缺少参数
	public static final int RES_ERROR_SIGN=-66;			//签名校验失败

	/**
	 * 接口调用返回码，合一道
	 */
	public static final String RES_HYD_SUCCESS="0000";			//调用成功
	public static final int RES_HYD_STATE_SUCCESS=1;			//本次查询结果(查得)
	
	
	/**
	 * 认证类型-认证宝
	 */
	public static final String RZ_TYPE_SIMPLE="simpleCheck";	//认证类型-简项认证
	public static final String RZ_TYPE_EXACT="exactCheck";		//认证类型-多项认证
	public static final String RZ_TYPE_FACE="faceCheck";		//认证类型-人像认证
	
	/**
	 * 认证类型-合一道
	 */
	public static final String RZ_TYPE_IDCARD="idCardCheck";	//认证类型-个人身份核查
	public static final String RZ_TYPE_PHOTOINFOQUERY="idCardPhotoInfoQuery";	//认证类型-个人身份证照片信息查询
	public static final String RZ_TYPE_PHOTOCOMPARE="photoCompare";		//认证类型-个人身份证核查及证照比对
	public static final String RZ_TYPE_BANKCARDCHECKTHREE="bankCardCheckThree";		//认证类型-个人银行账户核查(3项)接口
	public static final String RZ_TYPE_BANKCARDCHECKFOURPARTS="bankCardCheckFourParts";		//认证类型-个人银行账户核查(4项)接口

	/**
	 * 认证扣费单笔金额
	 */
//	public static float RZ_FEE_SIMPLE=0.80f;	//认证费用-简项认证
//	public static float RZ_FEE_EXACT=1.20f;	//认证费用-多项认证
//	public static float RZ_FEE_FACE=2.00f;	//认证费用-人像认证
	
	/**
	 * 余额告警相关
	 */
	public static String BALANCE_WARNING_EMAIL=""; 

}
