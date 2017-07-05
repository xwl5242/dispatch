package com.util.common;

public class SessionFilterMode {
	/** 变量描述：异常. */
	private String filter_exception;
	/** 变量描述：内容. */
	private String filter_content;
	/** 变量描述：地址. */
	private String redirect_url;
	/** 变量描述：是否开启. */
	private String iffilter;
	/** 变量描述：参数. */
	private String session_parameter;
	
	
	public String getFilter_exception() {
		return filter_exception;
	}
	public void setFilter_exception(String filterException) {
		filter_exception = filterException;
	}
	public String getFilter_content() {
		return filter_content;
	}
	public void setFilter_content(String filterContent) {
		filter_content = filterContent;
	}
	public String getRedirect_url() {
		return redirect_url;
	}
	public void setRedirect_url(String redirectUrl) {
		redirect_url = redirectUrl;
	}
	public String getIffilter() {
		return iffilter;
	}
	public void setIffilter(String iffilter) {
		this.iffilter = iffilter;
	}
	public String getSession_parameter() {
		return session_parameter;
	}
	public void setSession_parameter(String sessionParameter) {
		session_parameter = sessionParameter;
	}
	  
}
