package com.zx.rzb.sfxxrz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.zx.rzb.sfxxrz.mail.SimpleMailSender;
import com.zx.rzb.sfxxrz.mybatis.entity.MerchantInfo;
import com.zx.rzb.sfxxrz.mybatis.entity.MerchantQuery;
import com.zx.rzb.sfxxrz.mybatis.entity.Sfxxrz;
import com.zx.rzb.sfxxrz.mybatis.mapper.MerchantInfoMapper;
import com.zx.rzb.sfxxrz.mybatis.mapper.MerchantQueryMapper;
import com.zx.rzb.sfxxrz.mybatis.mapper.SfxxrzMapper;
import com.zx.rzb.sfxxrz.outpf.RzbpfCall;
import com.zx.rzb.sfxxrz.util.CipherUtil;
import com.zx.rzb.sfxxrz.util.ConfigInfo;
import com.zx.rzb.sfxxrz.util.Constants;
import com.zx.rzb.sfxxrz.util.TimeUtil;
import com.zx.rzb.sfxxrz.util.ToolUtil;

public class SfxxrzServiceImpl implements SfxxrzService {
	Logger log = Logger.getLogger(SfxxrzServiceImpl.class);

	private RzbpfCall rzbpfCall;

	public void setRzbpfCall(RzbpfCall rzbpfCall) {
		this.rzbpfCall = rzbpfCall;
	}

	private SimpleMailSender mailSend;

	public void setMailSend(SimpleMailSender mailSend) {
		this.mailSend = mailSend;
	}

	@Inject
	private MerchantQueryMapper mqm;

	@Inject
	private MerchantInfoMapper mim;

	@Inject
	private SfxxrzMapper sfrzm;

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
	@Transactional
	public JSONObject idCardCheck(String idNumber, String name, String merchantId) throws Exception {
		String rzType = "idCardCheck";
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			/**
			 * 简项认证先查本地、本地没有则查外部平台
			 */
			Map<String, String> qMap = new HashMap<String, String>();
			qMap.put("idNumber", idNumber);
			qMap.put("personName", name);
			List<Sfxxrz> list = sfrzm.querySfxx(qMap);
			if (null != list && !list.isEmpty()) {
				Sfxxrz sfxx = list.get(0);
				resJson = simpleResultProc(idNumber, name, merchantId, rzType, flag, sfxx);
			} else {
				String json = rzbpfCall.idCardCheck(idNumber, name); // 调用合一道接口
				resJson = JSONObject.fromObject(json); // 认证宝接口返回值
				hydResultProc(idNumber, name, merchantId, rzType, flag, resJson);
			}
		} catch (Exception e) {
			log.error("个人身份信息核查出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

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
	@Transactional
	public JSONObject idCardPhotoInfoQuery(String idNumber, String name, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "idCardPhotoInfoQuery";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.idCardPhotoQuery(idNumber, name);// 调用合一道接口
			log.info("-----------------res:"+json);
			resJson = JSONObject.fromObject(json);
			/**
			 * 查询成功后的数据处理----------------待定
			 * 
			 * 
			 * 
			 * 
			 */
			hydResultProc(idNumber, name, merchantId, rzType, flag, resJson);
		} catch (Exception e) {
			log.error("个人身份照片信息查询出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

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
	@Transactional
	public JSONObject photoCompare(String idNumber, String name,String photo, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "photoCompare";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.idCardPhotoCompare(idNumber, name,photo);
			resJson = JSONObject.fromObject(json);
			hydResultProc(idNumber, name, merchantId, rzType, flag, resJson);//结果处理
			
		} catch (Exception e) {
			log.error("人个身份证核查及证照比对出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}
	
	
	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(3项)接口)
	 * @date 2016年8月17日 下午4:09:01 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public JSONObject bankCardCheckThree(String idNumber, String name,String account, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "bankCardCheckThree";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.bankCardCheckThree(idNumber, name,account);
			resJson = JSONObject.fromObject(json);
			hydResultProc(idNumber, name, merchantId, rzType, flag, resJson);//结果处理
		} catch (Exception e) {
			log.error("个人银行账户核查3项出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

	/**
	 * @Title: bankCardCheckFourParts
	 * @Description: TODO(合一道，个人银行账户核查(4项)接口)
	 * @date 2016年8月17日 下午4:09:01 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param account
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public JSONObject bankCardCheckFourParts(String idNumber, String name,String account,String mobile, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "bankCardCheckFourParts";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.bankCardCheckFourParts(idNumber, name,account,mobile);
			resJson = JSONObject.fromObject(json);
			hydResultProc(idNumber, name, merchantId, rzType, flag, resJson);//结果处理
			
		} catch (Exception e) {
			log.error("个人银行账户核查4项出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}
	

	/**
	 * @Title: simpleCheck
	 * @Description: TODO(简项认证-爰金)
	 * @date 2016年4月21日 上午10:32:29
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	@Transactional
	public JSONObject simpleCheck(String idNumber, String name, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "simpleCheck";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			/**
			 * 简项认证先查本地、本地没有则查外部平台
			 */
			Map<String, String> qMap = new HashMap<String, String>();
			qMap.put("idNumber", idNumber);
			qMap.put("personName", name);
			List<Sfxxrz> list = sfrzm.querySfxx(qMap);
			if (null != list && !list.isEmpty()) {
				Sfxxrz sfxx = list.get(0);
				resJson = simpleResultProc(idNumber, name, merchantId, rzType, flag, sfxx);
			} else {
				String json = rzbpfCall.simpleCheck(idNumber, name);// 调用认证宝接口
				resJson = JSONObject.fromObject(json); // 认证宝接口返回值
				rzResultProc(idNumber, name, merchantId, rzType, flag, resJson);
			}
		} catch (Exception e) {
			log.error("简项认证出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

	/**
	 * @Title: exactCheck
	 * @Description: TODO(多项认证-爰金)
	 * @date 2016年4月27日 上午10:04:04
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject exactCheck(String idNumber, String name, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;// 是否触发了余额告警
		String rzType = "exactCheck";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.exactCheck(idNumber, name);// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
			rzResultProc(idNumber, name, merchantId, rzType, flag, resJson);
		} catch (Exception e) {
			log.error("多项认证出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

	/**
	 * @Title: exactCheck
	 * @Description: TODO(人像认证-爰金)
	 * @date 2016年4月27日 上午10:04:04
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject faceCheck(String idNumber, String name, String photo, String merchantId) throws Exception {
		JSONObject resJson = new JSONObject();
		Boolean flag = false;
		String rzType = "faceCheck";
		try {
			verification(resJson, merchantId, flag,rzType);// 业务验证
			String json = rzbpfCall.faceCheck(idNumber, name, photo); // 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
			resJson=rzResultProcFace(idNumber, name, merchantId, rzType, flag, resJson);

		} catch (Exception e) {
			log.error("人像认证出错 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

	/**
	 * @Title: queryBalance
	 * @Description: TODO(查询商户在挚尖的账户余额)
	 * @date 2016年4月27日 上午11:36:11
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryBalance(String merchId) throws Exception {
		JSONObject resJson = new JSONObject();
		try {
			List<MerchantInfo> list = mim.queryMerchantInfo(merchId);
			if (null != list && !list.isEmpty()) {
				MerchantInfo mi = list.get(0);
				resJson.put("ResponseCode", Constants.RES_SUCCESS);
				resJson.put("ResponseText", "成功");
				resJson.put("balance", mi.getBalance());
			} else {
				resJson.put("ResponseCode", Constants.RES_ERROR_MERCHANT_UNKNOWN);
				resJson.put("ResponseText", "未知商户");
			}
		} catch (Exception e) {
			log.error("查询商户账户余额err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}

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
	public JSONObject queryRZBBalance()throws Exception{
		JSONObject resJson = new JSONObject();
		try {
			String json = rzbpfCall.queryBalance();// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("查询挚尖在爰金的账户余额 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}
	
	/**
	 * @Title: queryRZBBalance
	 * @Description: TODO(查询挚尖在爰金的账户余额-人像)
	 * @date 2016年4月27日 上午11:36:11 
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryRZBFaceBalance()throws Exception{
		JSONObject resJson = new JSONObject();
		try {
			String json = rzbpfCall.queryFaceBalance();// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("查询挚尖在爰金的账户余额 err:" + e.getMessage(), e);
			throw e;
		}
		return resJson;
	}
	
	/**
	 * @Title: isSimpleCitizenExists
	 * @Description: TODO(是否存在简项认证历史记录)------不用
	 * @date 2016年4月27日 上午11:36:11
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject isSimpleCitizenExists(String idNumber, String name, String merchantId) {
		JSONObject resJson = new JSONObject();
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.SIMPLE_ACCOUNT) + Constants.SIMPLE_KEY);// 签名
			String json = rzbpfCall.isSimpleCitizenExists(idNumber, name, sign);// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("是否存在简项认证历史记录 err:" + e.getMessage(), e);
		}
		return resJson;
	}

	/**
	 * @Title: isExactCitizenExists
	 * @Description: TODO(是否存多项认证历史记录)------不用
	 * @date 2016年4月27日 上午11:36:11
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject isExactCitizenExists(String idNumber, String name, String merchantId) {
		JSONObject resJson = new JSONObject();
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.EXACT_ACCOUNT) + Constants.EXACT_KEY);// 签名
			String json = rzbpfCall.isExactCitizenExists(idNumber, name, sign);// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("是否存在简项认证历史记录 err:" + e.getMessage(), e);
		}
		return resJson;
	}

	/**
	 * @Title: querySimpleCitizenData
	 * @Description: TODO(提取简项认证历史记录)------不用
	 * @date 2016年4月27日 上午11:36:11
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject querySimpleCitizenData(String idNumber, String name, String merchantId) {
		JSONObject resJson = new JSONObject();
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.SIMPLE_ACCOUNT) + Constants.SIMPLE_KEY);// 签名
			String json = rzbpfCall.querySimpleCitizenData(idNumber, name, sign);// 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("提取简项认证历史记录 err:" + e.getMessage(), e);
		}
		return resJson;
	}

	/**
	 * @Title: queryExactCitizenData
	 * @Description: TODO(提取多项认证历史记录)------不用
	 * @date 2016年4月27日 上午11:36:11
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @return
	 */
	public JSONObject queryExactCitizenData(String idNumber, String name, String merchantId) {
		JSONObject resJson = new JSONObject();
		try {
			String sign = CipherUtil.md5(CipherUtil.md5(idNumber + Constants.EXACT_ACCOUNT) + Constants.EXACT_KEY);// 签名
			String json = rzbpfCall.queryExactCitizenData(idNumber, name, sign); // 调用认证宝接口
			resJson = JSONObject.fromObject(json); // 认证宝接口返回值
		} catch (Exception e) {
			log.error("提取多项认证历史记录 err:" + e.getMessage(), e);
		}
		return resJson;
	}


	/**
	 * @Title: simpleResultProc
	 * @Description: TODO(简项认证结果处理，本地存在该认证的身份信息，保存商户查询记录，更新商户余额)
	 * @date 2016年5月5日 下午2:08:32
	 * @author yang-lj
	 * @param rzType
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @throws Exception
	 */
	private JSONObject simpleResultProc(String idNumber, String name, String merchantId, String rzType, boolean flag, Sfxxrz sfxx) throws Exception {
		JSONObject resJson = new JSONObject();
		float rzFee = getFee(rzType);
		String rzTime = TimeUtil.getCurrentTime();// 认证时间
		/**
		 * 写商户查询记录表
		 */
		MerchantQuery mq = new MerchantQuery();
		mq.setMerchId(merchantId);
		mq.setIdNumber(idNumber);
		mq.setPersonName(name);
		mq.setRzType(rzType);
		mq.setRzResult(sfxx.getRzResult());
		mq.setRzFee(rzFee);
		mq.setRzTime(rzTime);
		mqm.insertMerchantQuery(mq);
		log.info("----simpleResultProc----认证宝结果处理--新增商户查询记录");

		/**
		 * 更新商户信息表的余额、更新时间、告警邮件时间
		 */
		String warningTime = "";
		if (flag) {
			warningTime = TimeUtil.getCurrentDate();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchId", merchantId);
		map.put("rzFee", rzFee);
		map.put("updateTime", rzTime);
		map.put("warningTime", warningTime);
		mim.updateMerchantInfo(map);
		log.info("----simpleResultProc----认证宝结果处理--更新商户信息表的余额、更新时间、告警邮件时间");
		/**
		 * 写resJson
		 */
		resJson.put("ResponseCode", Constants.RES_SUCCESS);
		resJson.put("ResponseText", "成功");
		JSONObject identifier = new JSONObject();
		identifier.put("Birthday", sfxx.getBirthday());
		identifier.put("IDNumber", sfxx.getIdNumber());
		identifier.put("Name", sfxx.getPersonName());
		identifier.put("Result", sfxx.getRzResult());
		identifier.put("Sex", sfxx.getSex());
		resJson.put("Identifier", identifier);
		return resJson;
	}

	/**
	 * @Title: rzResultProc
	 * @Description: TODO(认证结果处理-认证宝)
	 * @date 2016年4月27日 上午10:47:26
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param resJson
	 * @throws Exception
	 */
	private void rzResultProc(String idNumber, String name, String merchantId, String rzType, boolean flag, JSONObject resJson) throws Exception {
		int resCode = resJson.getInt("ResponseCode");
		if (resCode == Constants.RES_SUCCESS) {
			JSONObject Identifier = resJson.getJSONObject("Identifier");
			String formerName = ToolUtil.getJsonStr(Identifier, "FormerName");
			String sex = ToolUtil.getJsonStr(Identifier, "Sex");
			String nation = ToolUtil.getJsonStr(Identifier, "Nation");
			String birthday = ToolUtil.getJsonStr(Identifier, "Birthday");
			String company = ToolUtil.getJsonStr(Identifier, "Company");
			String education = ToolUtil.getJsonStr(Identifier, "Education");
			String maritalstatus = ToolUtil.getJsonStr(Identifier, "MaritalStatus");
			String nativeplace = ToolUtil.getJsonStr(Identifier, "NativePlace");
			String birthplace = ToolUtil.getJsonStr(Identifier, "BirthPlace");
			String address = ToolUtil.getJsonStr(Identifier, "Address");
			String result = ToolUtil.getJsonStr(Identifier, "Result");
			float rzFee = getFee(rzType);
			String rzTime = TimeUtil.getCurrentTime();// 认证时间
			/**
			 * 写商户查询记录表
			 */
			MerchantQuery mq = new MerchantQuery();
			mq.setMerchId(merchantId);
			mq.setIdNumber(idNumber);
			mq.setPersonName(name);
			mq.setRzType(rzType);
			mq.setRzResult(result);
			mq.setRzFee(rzFee);
			mq.setRzTime(rzTime);
			mqm.insertMerchantQuery(mq);
			log.info("----rzResultProc----认证宝结果处理--新增商户查询记录");

			/**
			 * 更新商户信息表的余额、更新时间、告警邮件时间
			 */
			String warningTime = "";
			if (flag) {
				warningTime = TimeUtil.getCurrentDate();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchId", merchantId);
			map.put("rzFee", rzFee);
			map.put("updateTime", rzTime);
			map.put("warningTime", warningTime);
			mim.updateMerchantInfo(map);
			log.info("----rzResultProc----认证宝结果处理--更新商户信息表的余额、更新时间、告警邮件时间");
			/**
			 * 接口返回成功,且结果一致，写sfxxrz表。
			 */
			if (result.equals("一致")) {
				Sfxxrz sfxxrz = new Sfxxrz();
				sfxxrz.setIdNumber(idNumber);
				sfxxrz.setPersonName(name);
				sfxxrz.setFormerName(formerName);
				sfxxrz.setSex(sex);
				sfxxrz.setNation(nation);
				sfxxrz.setBirthday(birthday);
				sfxxrz.setCompany(company);
				sfxxrz.setEducation(education);
				sfxxrz.setMaritalStatus(maritalstatus);
				sfxxrz.setNativePlace(nativeplace);
				sfxxrz.setBirthPlace(birthplace);
				sfxxrz.setAddress(address);
				sfxxrz.setRzResult(result);
				sfxxrz.setRzTime(rzTime);
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("idNumber", idNumber);
				hm.put("personName", name);
				List<Sfxxrz> list = sfrzm.querySfxx(hm);
				if (null == list || list.isEmpty()) {
					log.info("----rzResultProc----认证宝结果处理--身份信息新增");
					sfrzm.insertSfxxrz(sfxxrz);
				} else {
					Sfxxrz oldXx = list.get(0);
					if (!sfxxrz.toString().equals(oldXx.toString())) {
						log.info("----rzResultProc----认证宝结果处理--身份信息更新");
						sfrzm.updateSfxx(sfxxrz);
					}
				}
			}
		}
	}

	/**
	 * @Title: hydResultProc
	 * @Description: TODO(认证结果处理-合一道)
	 * @date 2016年5月26日 下午2:33:35
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param rzType
	 * @param flag
	 * @param resJson
	 * @throws Exception 
	 */
	private void hydResultProc(String idNumber, String name, String merchantId, String rzType, boolean flag, JSONObject resJson) throws Exception {
		String status = resJson.getString("status");// 本次请求处理成功
		int ResponseCode=0;	//响应code
		String ResponseText="";//响应描述
		JSONObject Identifier=null;
		if (status.equals(Constants.RES_HYD_SUCCESS)) {//调用成功
			int state = ToolUtil.getJsonInt(resJson,"state");// 本次查询结果(1.查得,2.未查得,3.其他原因未查得)
			String msg = ToolUtil.getJsonStr(resJson, "msg"); 					// 本次请求处理消息
			String hyd_reserved = ToolUtil.getJsonStr(resJson, "hyd_reserved"); 		// 合一道预留字段
			String risk_total_score = ToolUtil.getJsonStr(resJson, "risk_total_score"); // 本次风险评估分数：0分
			String rcomp_orderid = ToolUtil.getJsonStr(resJson, "rcomp_orderid"); // 商户传入的KEY
			String errorMessage = ToolUtil.getJsonStr(resJson, "errorMessage"); // 本次查询结果的错误信息
			String query_name = ToolUtil.getJsonStr(resJson, "query_name"); // 输入姓名
			String query_document_no = ToolUtil.getJsonStr(resJson, "query_document_no"); // 输入的身份证号码
			int recode = ToolUtil.getJsonInt(resJson,"result"); // 认证结果 一致1.一致，2.不一致，3.库中无此号
			
			String query_photo=ToolUtil.getJsonStr(resJson,"query_photo"); //输入的个人照片(base64编码,不携带返回)
			String photo=ToolUtil.getJsonStr(resJson,"photo"); //公安部返回带网格照片
			String similarity=ToolUtil.getJsonStr(resJson,"similarity"); //个人照片与公安部返回带网格照片相似度(0-100数值)
			String result = "";
			float rzFee = getFee(rzType);// 认证扣费
			String rzTime = TimeUtil.getCurrentTime();// 认证时间
			switch (recode) {
			case 1:
				result = "一致";
				break;
			case 2:
				result = "不一致";
				break;
			case 3:
				result = "库中无此号";
				break;
			default:
				result = "库中无此号";
				break;
			}

			/**
			 * 写商户查询记录表
			 */
			MerchantQuery mq = new MerchantQuery();
			mq.setMerchId(merchantId);
			mq.setIdNumber(idNumber);
			mq.setPersonName(name);
			mq.setRzType(rzType);
			mq.setRzResult(result);
			mq.setRzFee(rzFee);
			mq.setRzTime(rzTime);
			mqm.insertMerchantQuery(mq);
			log.info("----hydResultProc----合一道结果处理--新增商户查询记录");

			/**
			 * 更新商户信息表的余额、更新时间、告警邮件时间
			 */
			String warningTime = "";
			if (flag) {
				warningTime = TimeUtil.getCurrentDate();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchId", merchantId);
			map.put("rzFee", rzFee);
			map.put("updateTime", rzTime);
			map.put("warningTime", warningTime);
			mim.updateMerchantInfo(map);
			log.info("----hydResultProc----合一道结果处理--更新商户信息表的余额、更新时间、告警邮件时间");

			/**
			 * 接口返回成功,且结果一致，写sfxxrz表。
			 */
			if (result.equals("一致")) {
				Sfxxrz sfxxrz = new Sfxxrz();
				sfxxrz.setIdNumber(idNumber);
				sfxxrz.setPersonName(name);
//				sfxxrz.setFormerName(formerName);
//				sfxxrz.setSex(sex);
//				sfxxrz.setNation(nation);
//				sfxxrz.setBirthday(birthday);
//				sfxxrz.setCompany(company);
//				sfxxrz.setEducation(education);
//				sfxxrz.setMaritalStatus(maritalstatus);
//				sfxxrz.setNativePlace(nativeplace);
//				sfxxrz.setBirthPlace(birthplace);
//				sfxxrz.setAddress(address);
				sfxxrz.setRzResult(result);
				sfxxrz.setRzTime(rzTime);
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("idNumber", idNumber);
				hm.put("personName", name);
				List<Sfxxrz> list = sfrzm.querySfxx(hm);
				if (null == list || list.isEmpty()) {
					log.info("----hydResultProc----合一道结果处理--身份信息新增");
					sfrzm.insertSfxxrz(sfxxrz);
				} else {
					Sfxxrz oldXx = list.get(0);
					if (!sfxxrz.toString().equals(oldXx.toString())) {
						log.info("----hydResultProc----合一道结果处理--身份信息更新");
						sfrzm.updateSfxx(sfxxrz);
					}
				}
			}
			
			/**
			 * 处理报文差异
			 */
			ResponseCode=Constants.RES_SUCCESS;
			ResponseText="成功";
			Identifier=new JSONObject();
			Identifier.put("Name", name);
			Identifier.put("IDNumber", idNumber);
			Identifier.put("Result", result);
			Identifier.put("Birthday", "");
			Identifier.put("Sex", "");
			
			/**
			 * ，统一响应报文
			 */
			resJson.clear();//清空原响应json
			resJson.put("ResponseCode", ResponseCode);
			resJson.put("ResponseText", ResponseText);
			resJson.put("Identifier", Identifier);
		}else{
			resJson.put("ResponseCode", resJson.getString("status"));
			resJson.put("ResponseText", resJson.getString("msg"));
		}
	}

	/**
	 * @Title: rzResultProcFace
	 * @Description: TODO(认证结果处理-人像认证)
	 * @date 2016年4月29日 下午1:56:14
	 * @author yang-lj
	 * @param idNumber
	 * @param name
	 * @param merchantId
	 * @param rzType
	 * @param resJson
	 * @throws Exception 
	 */
	private JSONObject rzResultProcFace(String idNumber, String name, String merchantId, String rzType, boolean flag, JSONObject resJson) throws Exception {
		int resCode = resJson.getInt("Code");
		String msg = resJson.getString("Msg"); // 接口调用返回代码的文本描述
		resJson.remove("Code");
		resJson.remove("Msg");
		String CitizenResult = resJson.getString("CitizenResult");// 身份证和姓名的认证结果
		if (resCode == Constants.RES_SUCCESS) {
			int Score = resJson.getInt("Score"); // 照片比对的分数
			String FaceResult = resJson.getString("FaceResult"); // 照片比对的分析结果
			String rzTime = TimeUtil.getCurrentTime();// 认证时间
			float rzFee = getFee(rzType);// 认证扣费
			/**
			 * 写商户查询记录表
			 */
			MerchantQuery mq = new MerchantQuery();
			mq.setMerchId(merchantId);
			mq.setIdNumber(idNumber);
			mq.setPersonName(name);
			mq.setRzType(rzType);
			mq.setRzResult(CitizenResult);
			mq.setRzFee(rzFee);
			mq.setRzTime(rzTime);
			mqm.insertMerchantQuery(mq);

			/**
			 * 更新商户信息表的余额、更新时间、告警邮件时间
			 */
			String warningTime = "";
			if (flag) {
				warningTime = TimeUtil.getCurrentDate();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchId", merchantId);
			map.put("rzFee", rzFee);
			map.put("updateTime", rzTime);
			map.put("warningTime", warningTime);
			mim.updateMerchantInfo(map);
		}
		/**
		 * json key名称转换，统一接口key名称
		 */
		resJson.put("ResponseCode", resCode);
		resJson.put("ResponseText", msg);
		return resJson;
	}

	
	/**
	 * @Title: getFee
	 * @Description: TODO(取配置的费用)
	 * @date 2016年5月26日 下午4:37:33
	 * @author yang-lj
	 * @param rzType
	 * @param rzFee
	 * @return
	 */
	private float getFee(String rzType) throws Exception {
		if (rzType.equals(Constants.RZ_TYPE_SIMPLE)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("simpleCheck"));
		} else if (rzType.equals(Constants.RZ_TYPE_EXACT)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("exactCheck"));
		} else if (rzType.equals(Constants.RZ_TYPE_FACE)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("faceCheck"));
		} else if (rzType.equals(Constants.RZ_TYPE_IDCARD)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("idCardCheck"));
		} else if (rzType.equals(Constants.RZ_TYPE_PHOTOINFOQUERY)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("idCardPhotoInfoQuery"));
		} else if (rzType.equals(Constants.RZ_TYPE_PHOTOCOMPARE)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("photoCompare"));
		} else if (rzType.equals(Constants.RZ_TYPE_BANKCARDCHECKTHREE)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("bankCardCheckThree"));
		} else if (rzType.equals(Constants.RZ_TYPE_BANKCARDCHECKFOURPARTS)) {
			return Float.parseFloat(ConfigInfo.channelFeeMap.get("bankCardCheckFourParts"));
		} else {
			throw new Exception("没有该认证类型的费用配置");
		}
	}

	/**
	 * @Title: getMerchantInfo
	 * @Description: TODO(商户信息查询)
	 * @date 2016年5月3日 下午2:10:35
	 * @author yang-lj
	 * @param merchantId
	 * @return
	 */
	private MerchantInfo getMerchantInfo(String merchantId) {
		List<MerchantInfo> listMi = mim.queryMerchantInfo(merchantId);
		if (listMi != null && !listMi.isEmpty()) {
			return listMi.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * @Title: verification
	 * @Description: TODO(业务验证)
	 * @date 2016年5月26日 上午9:51:20
	 * @author yang-lj
	 * @param resJson
	 * @param merchantId
	 * @param flag
	 * @throws Exception
	 */
	private void verification(JSONObject resJson, String merchantId, Boolean flag,String rzType) throws Exception {
		float fee=getFee(rzType);
		MerchantInfo mi = getMerchantInfo(merchantId);
		if (null == mi) {
			resJson.put("ResponseCode", Constants.RES_ERROR_MERCHANT_UNKNOWN);
			resJson.put("ResponseText", "未知商户");
			throw new Exception("未知商户");
		} else if (mi.getBalance() < fee) {
			resJson.put("ResponseCode", Constants.RES_ERROR_INSUFFICIENT_BALANCE);
			resJson.put("ResponseText", "余额不足");
			throw new Exception("余额不足");
		} else if (mi.getBalance() < mi.getWarning()) {
			flag = warningMail(mi);// 告警邮件处理
		}
	}

	/**
	 * @Title: warningMail
	 * @Description: TODO(告警邮件触发)
	 * @date 2016年5月19日 上午11:17:20
	 * @author yang-lj
	 * @param mi
	 * @return 返回是否触发了邮件
	 */
	private boolean warningMail(MerchantInfo mi) {
		boolean flag = false;
		String warnTime = mi.getWarningTime();
		if (null != warnTime && !warnTime.equals("")) {
			if (!TimeUtil.comparSysDate(warnTime)) {
				flag = true;
			}
		} else {
			flag = true;
		}
		if (flag) {
			mailSend.simpleSend(mi.getMerchName(), mi.getBalance() + "", mi.email);// 邮件通知商户
		}
		return flag;
	}

}
