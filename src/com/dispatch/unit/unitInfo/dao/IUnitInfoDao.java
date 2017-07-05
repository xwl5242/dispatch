/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  IUnitInfoDao.java     <BR>
 * Author: fupenglin  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2014-12-12 上午11:12:11 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */

package com.dispatch.unit.unitInfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dispatch.sys.bean.Org;
import com.dispatch.unit.bean.UnitInfo;

/**
 * 用能单位管理数据信息 功能描述： . <BR>
 * 历史版本: <Br>
 * 开发者: fupenglin <BR>
 * 时间：2014-12-12 上午11:22:58 <BR>
 * 变更原因： <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2014-12-12 上午11:22:58 <BR>
 * 描述： <BR>
 * 版本：V1.0
 */
public interface IUnitInfoDao {

	/**
	 * 查询所有用能单位信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param ui
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 * @return: List<?>
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:23:56 <BR>
	 */
	public List<Map<String, Object>> getUnitInfoList(UnitInfo ui, int pageIndex, int pagesize);

	/**
	 * 保存用能单位信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param ui
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:24:31 <BR>
	 */
	public int saveUnitInfo(UnitInfo ui);

	/**
	 * 修改用能单位信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param ui
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:24:50 <BR>
	 */
	public int updateUnitInfo(UnitInfo ui);

	/**
	 * 删除用能单位信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param u
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:25:10 <BR>
	 */
	public int deleteUnitInfo(UnitInfo u);

	/**
	 * 通过id查询用能单位信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param id
	 * @return
	 * @return: UnitInfo
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:25:45 <BR>
	 */
	public UnitInfo findUnitInfoById(String id);

	/**
	 * 查询用能数据条数 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:26:57 <BR>
	 */
	public int getUnitInfoCount(UnitInfo ui);
	
	/**
	 * 
	 * 方法说明：状态"1"可用 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param u
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 下午02:46:31 <BR>
	 */
	int enableUnitInfo(UnitInfo u);
	/**
	 * 
	 * 方法说明：状态"2"不可用 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param u
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 下午02:47:09 <BR>
	 */
	int disableUnitInfo(UnitInfo u);
	/**
	 * 
	 * 方法说明： 查询停用数据的下级ID. <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param u
	 * @return
	 * @return: List
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-30 下午05:21:00 <BR>
	 */
	public List infoList(UnitInfo u);
	/**
	 * 查询所有组织机构信息 方法说明： . <BR>
	 * 
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param org
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 * @return: List<?>
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:23:56 <BR>
	 */
	public List getOrgInfoList(Org org, int pageIndex, int pagesize,HttpServletRequest request);

	/**
	 * 查询所有组织机构信息 方法说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param ids
	 * @return
	 * @return: List<?>
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:23:56 <BR>
	 */
	public List<Org> getOrgInfoList(String ids);
	/**
	 * 
	 * 方法说明： 查询机构总数. <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param org
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:22:46 <BR>
	 */
	public int getOrgInfoCount(Org org);
	/**
	 * 
	 * 方法说明：更新用能unitid . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @return: void
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:23:02 <BR>
	 */
	public void updateAllUnitid();

	public boolean hasChild(String ids);

	public boolean disableHasChild(String ids);
	
	public Map getSyshomeCountInfo(Map queryMap);
	public List getSumheatquantityChart(Map queryMap);
	public List getWaterChart(Map queryMap);
	public List getPowerChart(Map queryMap);
	public List getCoalChart(Map queryMap);
	public List getGasChart(Map queryMap);
	public List getCarbonChart(Map queryMap);
	
	
	public List getOrgHeatPieData(UnitInfo unit,Org org);
	/**
	 * 
	 * 方法说明：查询首页柱状图详细 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IUnitInfoDao <BR>
	 * @param unit
	 * @param org
	 * @return
	 * @return: List
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年11月24日 下午4:51:43 <BR>
	 */
	public List getOrgHeatPieDetailData(UnitInfo unit,Org org);
	
	public List getHeatDataList(UnitInfo unit,Org org);
	
	public HashMap findEnergyByType(String type,UnitInfo unit,Org org);
	
	public HashMap findEnergyNodeByType(String type,UnitInfo unit,Org org);
	public HashMap findTotalEnergyNodeByType(String type,UnitInfo unit,Org org);
	
	public HashMap findHeatInfoByYear(Org org,UnitInfo unit);
	public HashMap findPowerInfoByYear(Org org,UnitInfo unit);
	public Map getFeedCount(String oid);
}
