package com.dispatch.sys.bean;

import java.io.Serializable;


/**
 * 功能描述：用户  .  <BR>
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String Id;
	private String loginName; //登陆账号
	private String passWord;  //密码
	private String userStat; //状态
	private String userName; 
	private String mobile;
	private String mail;
	private String loginTime;
	private String lastLoginTime;
	private Integer LoginCount;
	private String createTime;
	private String createMan;
	private String address;
	private String sysEmpId;
	
	public Integer getLoginCount() {
		return LoginCount;
	}
	public void setLoginCount(Integer loginCount) {
		LoginCount = loginCount;
	}
	public String getSysEmpId() {
		return sysEmpId;
	}
	public void setSysEmpId(String sysEmpId) {
		this.sysEmpId = sysEmpId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	} 
	public String getUserStat() {
		return userStat;
	}
	public void setUserStat(String userStat) {
		this.userStat = userStat;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
