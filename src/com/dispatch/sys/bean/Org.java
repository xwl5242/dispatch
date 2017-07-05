package com.dispatch.sys.bean;

import java.io.Serializable;

import com.frames.base.BaseData;

/**
 * 功能描述： 组织机构. <BR>
 */
public class Org extends BaseData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// id
	private String parentId;// 父组织id
	private String orgCode;// 组织编码
	private String orgName;// 组织名称
	private String sortName;// 简称
	private String jianPin;// 简拼
	private String orgType;// 组织类型
	private String managerType;// 管理类型
	private String seq;// 排序
	private String isDelete;// 是否删除
	private String createTime;// 创建人
	private String createMan;// 创建时间
	private String companyType;
	private String groupType;// 集团性质

	/**
	 * 如果组织类型等于用能单位，那么unitParentId=id;否则unitParentId 等于该组织机构上一级的用能单位的id,
	 * 该字段属于冗余字段，不做数据库保存
	 */
	private String unitParentId;

	public String getUnitParentId() {
		return unitParentId;
	}

	public void setUnitParentId(String unitParentId) {
		this.unitParentId = unitParentId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	private String unitId;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getManagerType() {
		return managerType;
	}

	public String getJianPin() {
		return jianPin;
	}

	public void setJianPin(String jianPin) {
		this.jianPin = jianPin;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

}
