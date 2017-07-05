/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  IDetails.java     <BR>
 * Author: fupenglin  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2014-12-16 下午07:23:43 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.dispatch.unit.unitInfo.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.unit.bean.UnitInfo;

/**
 * 
 * 功能描述：热源数据信息  .  <BR>
 * 历史版本: <Br>
 * 开发者: fupenglin  <BR>
 * 时间：2014-12-16 下午07:23:51  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2014-12-16 下午07:23:51 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
public interface IDetailsDao {
	
	/**
	 * 
	 * 方法说明：保存热源信息 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param ui
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-16 下午08:07:41 <BR>
	 */
	public int saveDetails(UnitInfo ui);
	
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param ui
	 * @return
	 * @return: String
	 * @Author: xupei <BR>
	 * @Datetime：2015-4-17 下午4:08:31 <BR>
	 */
	public UnitInfo getParentId(UnitInfo ui);
	/**
	 * 
	 * 方法说明：删除热源信息 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param u
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-16 下午08:08:21 <BR>
	 */
	public int deleteDetails(UnitInfo u);
	/**
	 * 
	 * 方法说明：通过id查询热源 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param id
	 * @return
	 * @return: UnitInfo
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-16 下午08:08:38 <BR>
	 */
	public UnitInfo findDetailsById(String id);
	
	/**
	 * 
	 * 方法说明：通过单位id查询热源信息 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param id
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 * @return: List
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:17:26 <BR>
	 */
	public Map findDetailsByUnitId(String id, int currentPage, int pageSize);
	/**
	 * 
	 * 方法说明：批量保存热源信息 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param list
	 * @param unitId
	 * @param user
	 * @param org
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:18:00 <BR>
	 */
	public int saveDetailsBatch(List<JSONObject> list, String unitId, User user, Org org);
	/**
	 * 
	 * 方法说明： 删除热源子信息. <BR>
	 * @see com.ecc.ieu.unitInfo.dao.IDetailsDao <BR>
	 * @param list
	 * @return
	 * @return: int
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:19:33 <BR>
	 */
	public int deleteChildInfo(List<JSONObject> list);
	public int checkBydate(String changeDate,String unitid,String id);
	
	
	public int checkHeatStation(UnitInfo u);
	
	
	
}
