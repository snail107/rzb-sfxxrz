package com.zx.rzb.sfxxrz.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zx.rzb.sfxxrz.mybatis.entity.MerchantInfo;

public interface MerchantInfoMapper extends RzbSQLMapper {
	
	/**
	 * @Title: queryAllMerchantInfo
	 * @Description: TODO(查询所有商户信息，用于定时检查商户余额，发送告警邮件)
	 * @date 2016年5月19日 上午10:48:55 
	 * @author yang-lj
	 * @return
	 */
	public List<MerchantInfo> queryAllMerchantInfo();

	/**
	 * @Title: queryMerchantInfo
	 * @Description: TODO(根据商户号取商户信息)
	 * @date 2016年5月19日 上午10:49:17 
	 * @author yang-lj
	 * @param merchId
	 * @return
	 */
	public List<MerchantInfo> queryMerchantInfo(String merchId);
	
	/**
	 * @Title: insertMerchantInfo
	 * @Description: TODO(新增商户)
	 * @date 2016年5月19日 上午10:51:05 
	 * @author yang-lj
	 * @param merchantInfo
	 */
	public void insertMerchantInfo(MerchantInfo merchantInfo);
	
	/**
	 * @Title: updateMerchantInfo
	 * @Description: TODO(更新商户余额)
	 * @date 2016年5月19日 上午10:50:39 
	 * @author yang-lj
	 * @param map
	 */
	public void updateMerchantInfo(Map<String,Object> map);
	
}
