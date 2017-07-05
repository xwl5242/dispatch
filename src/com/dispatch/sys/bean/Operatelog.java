package com.dispatch.sys.bean;

/**
 * 功能描述：  操作日志表.  <BR>
 */
public class Operatelog {

	private String id;
	private String userLoginName;
	private String ortTime;
	private String className;
	private String methodName;
	private String optRemark;
	private String optUrl;
	private String optUri;
	private String optIp;
	private String optHost;
	private String optPort;
	private String opttype;
	private String optkey;
	
	public String getOpttype() {
		return opttype;
	}
	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}
	public String getOptkey() {
		return optkey;
	}
	public void setOptkey(String optkey) {
		this.optkey = optkey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getOrtTime() {
		return ortTime;
	}
	public void setOrtTime(String ortTime) {
		this.ortTime = ortTime;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getOptRemark() {
		return optRemark;
	}
	public void setOptRemark(String optRemark) {
		this.optRemark = optRemark;
	}
	public String getOptUrl() {
		return optUrl;
	}
	public void setOptUrl(String optUrl) {
		this.optUrl = optUrl;
	}
	public String getOptUri() {
		return optUri;
	}
	public void setOptUri(String optUri) {
		this.optUri = optUri;
	}
	public String getOptIp() {
		return optIp;
	}
	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}
	public String getOptHost() {
		return optHost;
	}
	public void setOptHost(String optHost) {
		this.optHost = optHost;
	}
	public String getOptPort() {
		return optPort;
	}
	public void setOptPort(String optPort) {
		this.optPort = optPort;
	}
	
}
