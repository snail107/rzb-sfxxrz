package com.zx.rzb.sfxxrz.mybatis.entity;

/**
 * @Package com.zx.rzb.sfxxrz.mybatis.entity
 * @Title: MerchantQuery.java
 * @ClassName: MerchantQuery
 * @Description: TODO(商户查询记录)
 * @author yang-lj
 * @date 2016年4月25日 下午3:01:36
 * @version V1.0
 */
public class MerchantInfo {
	public String merchId="";		//商户ID
	public String merchName="";		//商户名称
	public String adress="";		//地址
	public float balance;			//账户余额
	public String updateTime="";	//余额更新时间
	public float warning;			//告警余额
	public String warningTime="";	//告警时间
	public String email="";			//商户邮箱
	
	public String getWarningTime() {
		return warningTime;
	}
	public void setWarningTime(String warningTime) {
		this.warningTime = warningTime;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getWarning() {
		return warning;
	}
	public void setWarning(float warning) {
		this.warning = warning;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
