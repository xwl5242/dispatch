package com.dispatch.warn.bean;

public class WarnTotal {

	private String unitName;//系统名称
	private String pointName;//点名称
	private String pointDesc;//点描述
	private String warnTimes;//报警次数
	private String warnTotalDuration;//报警累计时长
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointDesc() {
		return pointDesc;
	}
	public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	}
	public String getWarnTimes() {
		return warnTimes;
	}
	public void setWarnTimes(String warnTimes) {
		this.warnTimes = warnTimes;
	}
	public String getWarnTotalDuration() {
		return warnTotalDuration;
	}
	public void setWarnTotalDuration(String warnTotalDuration) {
		this.warnTotalDuration = warnTotalDuration;
	}
	@Override
	public String toString() {
		return "WarnTotal [unitName=" + unitName + ", pointName=" + pointName
				+ ", pointDesc=" + pointDesc + ", warnTimes=" + warnTimes
				+ ", warnTotalDuration=" + warnTotalDuration + "]";
	}
	
}
