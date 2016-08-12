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
public class MerchantQuery {
	public int id;					//ID
	public String merchId="";		//商户ID
	public String idNumber="";		//身份证号
	public String personName="";	//姓名
	public String rzType="";		//认证类型
	public String rzResult="";		//认证结果
	public float  rzFee;			//认证扣费
	public String rzTime="";		//认证时间
	
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public float getRzFee() {
		return rzFee;
	}
	public void setRzFee(float rzFee) {
		this.rzFee = rzFee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getRzType() {
		return rzType;
	}
	public void setRzType(String rzType) {
		this.rzType = rzType;
	}
	public String getRzResult() {
		return rzResult;
	}
	public void setRzResult(String rzResult) {
		this.rzResult = rzResult;
	}
	public String getRzTime() {
		return rzTime;
	}
	public void setRzTime(String rzTime) {
		this.rzTime = rzTime;
	}
	
}
