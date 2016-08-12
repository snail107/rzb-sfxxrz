package com.zx.rzb.sfxxrz.mybatis.entity;

public class Sfxxrz {
	public String idNumber="";		//身份证号
	public String personName="";	//姓名
	public String formerName="";	//曾用名
	public String sex="";			//性别
	public String nation="";		//民族
	public String birthday="";		//生日
	public String company="";		//工作单位
	public String education="";		//文化程度
	public String maritalStatus="";	//婚姻状况
	public String nativePlace="";	//籍贯
	public String birthPlace="";	//出生地
	public String address="";		//住址
	public String rzResult="";		//认证结果
	public String rzTime="";		//认证时间
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append(idNumber);
		sb.append(personName);
		sb.append(formerName);
		sb.append(sex);
		sb.append(nation);
		sb.append(birthday);
		sb.append(company);
		sb.append(education);
		sb.append(maritalStatus);
		sb.append(nativePlace);
		sb.append(birthPlace);
		sb.append(address);
		return sb.toString();
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
	public String getFormerName() {
		return formerName;
	}
	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
