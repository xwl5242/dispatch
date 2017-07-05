package com.dispatch.warn.bean;

public class WarnDetail {

	private String unitName;//系统名称
	private String pointName;//点名（力控）
	private String pointDesc;//点描述（力控）
	private String startTime;//报警开始时间
	private String endTime;//报警结束时间
	private String warnDuration;//报警时长
	private String warnSetValue;//报警设置值
	private String collValue;//采集值
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWarnDuration() {
		return warnDuration;
	}
	public void setWarnDuration(String warnDuration) {
		this.warnDuration = warnDuration;
	}
	public String getWarnSetValue() {
		return warnSetValue;
	}
	public void setWarnSetValue(String warnSetValue) {
		this.warnSetValue = warnSetValue;
	}
	public String getCollValue() {
		return collValue;
	}
	public void setCollValue(String collValue) {
		this.collValue = collValue;
	}
	@Override
	public String toString() {
		return "WarnDetail [unitName=" + unitName + ", pointName=" + pointName
				+ ", pointDesc=" + pointDesc + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", warnDuration=" + warnDuration
				+ ", warnSetValue=" + warnSetValue + ", collValue=" + collValue
				+ "]";
	}
	
}
