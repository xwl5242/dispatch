package com.dispatch.unit.bean;

import com.frames.base.BaseData;

/**
 * 用能单位 功能描述： . <BR>
 */
public class UnitInfo extends BaseData{

	private String id; // 主键
	private String unitCode; // 编号 ECC_简拼_3位流水
	private String unitName; // 名称
	private String shortName; // 简称
	private String jianPin; // 简拼
	private String orgId; // 所属组织id
	private String orgName; // 所属组织名称
	private String onwerType; // 所属类型 1、用能单位  3、热源  9、中心
	private String areaId;//地区
	private String areaname;//地区名称
	private String address;//地址
	private String unitId; // 父级主键 顶级为0，其余为父级主键id
	private String villageId;//所属小区
	private String villageName;//所属小区
	private String companyType;//公司类型
	private String unitPName; // 父级主键 顶级为0，其余为父级主键id
	private String unitType; // 性质 1、用能单位  3、热源 4、热力站 5、一级管网 6、二级管网 7：楼座  9、中心
	private String heatingType; // 供热类型 1、区域供热 2、集中供热
	private String manageType; // 管理类型 热力站 1、统管 2、自管 3、代管
	private String gwLength;//管网长度
	private String seq; // 排序
	private String remarks; // 备注
	private String status; // 状态 1、可用 2、不可用
	
	//民建供热面积、公建供热面积、用户数量、用热建筑数量、产能、排序、备注
	//隐藏信息：性质、状态、创建人编号、创建人部门编号、创建时间、修改人编号、修改人部门编号、修改时间
	/*
	 * <!-- 除了用能单位、编号、名称、简称、简拼、热力站数量、总供热面积，其余都可修改。
总供热面积、民建供热面积、公建供热面积、用户数量、用热建筑数量，这几个数据都是显示最新日期的数据。
用能单位、编号、名称、简称、简拼、供热类型、热力站总数量、一次管网总数量、一次管网总长度、总供热面积、
民建供热面积、公建供热面积、用户数量、用热建筑数量、排序、状态
	 */
	
	//热源信息details
	private String detailsId;//热源主键
	private String validStartDate;//有效期开始
	private String validEndDate;//有效期结束
	private String mjHeatarea;//民建供热面积
	private String gjheatarea;//公建供热面积
	private String mjHeatareaH;//高区民建供热面积
	private String gjheatareaH;//高区公建供热面积
	private String mjgjsum;//公建供热面积
	private String heatnumH;//用户数量
	private String heatnum;//用热建筑数量
	private String gjHeatNumH;
	private String gjHeatNum;
	private String longitude;
	private String latitude;
	
	private String heatCount;//热力站总数量
	private String gwCount;//一次管网总数量
	private String gwLengths;//一次管网总长度
	private String mjgjSum;//总供热面积MJGJSUM,民建加公建
	private String unitOutId;//区域外热源ID
	private String unitOutName;//区域外热源名称
	private String groupType;//集团性质
	
	//2015.10.29新增字段
	private String cnmj;//采暖面积
	
	
	private String energyType;
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 获得描述：String  energyType. <BR>
	 */
	public String getEnergyType() {
		return energyType;
	}

	/**
	 * 获得描述：String  energyType. <BR>
	 * @param energyType the energyType to set
	 */
	public void setEnergyType(String energyType) {
		this.energyType = energyType;
	}

	/**
	 * 获得描述：String  cnmj. <BR>
	 */
	public String getCnmj() {
		return cnmj;
	}

	/**
	 * 获得描述：String  cnmj. <BR>
	 * @param cnmj the cnmj to set
	 */
	public void setCnmj(String cnmj) {
		this.cnmj = cnmj;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getUnitOutId() {
		return unitOutId;
	}

	public void setUnitOutId(String unitOutId) {
		this.unitOutId = unitOutId;
	}

	public String getUnitOutName() {
		return unitOutName;
	}

	public void setUnitOutName(String unitOutName) {
		this.unitOutName = unitOutName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getAreaname() {
		return areaname; 
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVillageId() {
		return villageId;
	}

	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getGwLength() {
		return gwLength;
	}

	public void setGwLength(String gwLength) {
		this.gwLength = gwLength;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	private String yongneng;//用能单位名称（父级节点uname）
	
	
	public String getHeatnumH() {
		return heatnumH;
	}

	public void setHeatnumH(String heatnumH) {
		this.heatnumH = heatnumH;
	}

	public String getGjHeatNumH() {
		return gjHeatNumH;
	}

	public void setGjHeatNumH(String gjHeatNumH) {
		this.gjHeatNumH = gjHeatNumH;
	}

	public String getGjHeatNum() {
		return gjHeatNum;
	}

	public void setGjHeatNum(String gjHeatNum) {
		this.gjHeatNum = gjHeatNum;
	}

	
	
	public String getMjHeatareaH() {
		return mjHeatareaH;
	}

	public void setMjHeatareaH(String mjHeatareaH) {
		this.mjHeatareaH = mjHeatareaH;
	}

	public String getGjheatareaH() {
		return gjheatareaH;
	}

	public void setGjheatareaH(String gjheatareaH) {
		this.gjheatareaH = gjheatareaH;
	}

	
	
	
	public String getYongneng() {
		return yongneng;
	}

	public void setYongneng(String yongneng) {
		this.yongneng = yongneng;
	}

	public String getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(String detailsId) {
		this.detailsId = detailsId;
	}

	public String getHeatCount() {
		return heatCount;
	}

	public void setHeatCount(String heatCount) {
		this.heatCount = heatCount;
	}

	public String getGwCount() {
		return gwCount;
	}

	public void setGwCount(String gwCount) {
		this.gwCount = gwCount;
	}

	public String getGwLengths() {
		return gwLengths;
	}

	public void setGwLengths(String gwLengths) {
		this.gwLengths = gwLengths;
	}

	public String getMjgjSum() {
		return mjgjSum;
	}

	public void setMjgjSum(String mjgjSum) {
		this.mjgjSum = mjgjSum;
	}

	public String getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(String validStartDate) {
		this.validStartDate = validStartDate;
	}

	public String getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getMjHeatarea() {
		return mjHeatarea;
	}

	public void setMjHeatarea(String mjHeatarea) {
		this.mjHeatarea = mjHeatarea;
	}

	public String getGjheatarea() {
		return gjheatarea;
	}

	public void setGjheatarea(String gjheatarea) {
		this.gjheatarea = gjheatarea;
	}

	public String getHeatnum() {
		return heatnum;
	}

	public void setHeatnum(String heatnum) {
		this.heatnum = heatnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getJianPin() {
		return jianPin;
	}

	public void setJianPin(String jianPin) {
		this.jianPin = jianPin;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOnwerType() {
		return onwerType;
	}

	public void setOnwerType(String onwerType) {
		this.onwerType = onwerType;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getHeatingType() {
		return heatingType;
	}

	public void setHeatingType(String heatingType) {
		this.heatingType = heatingType;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getUnitPName() {
		return unitPName;
	}

	public void setUnitPName(String unitPName) {
		this.unitPName = unitPName;
	}

	public String getMjgjsum() {
		return mjgjsum;
	}

	public void setMjgjsum(String mjgjsum) {
		this.mjgjsum = mjgjsum;
	}

}
