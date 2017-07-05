/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}DaoImpl.java     <BR>
 * Author: ${author}  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: ${nowDate} <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.ecc.${packageName}.${objectNameLower}.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.ecc.${packageName}.${objectNameLower}.dao.${objectName}Dao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.frames.util.Page;
import com.frames.util.PageData;


/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: ${author}  <BR>
 * 时间：    ${nowDate}  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：${nowDate} <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@Repository
public class ${objectName}DaoImpl extends PageListJdbcTemplate implements ${objectName}Dao{
	
	

	
	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.impl.${objectName}Dao
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public List findList${objectName}(Page page) {
		// TODO Auto-generated method stub
		String sql="";
		return  queryGridist(page, sql);
	}

	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.impl.${objectName}Dao
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public List listAll${objectName}(PageData pd) {
		// TODO Auto-generated method stub
		String sql="select * from sys_right";
		
		return queryForList(sql);
	}
	
	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.impl.${objectName}Dao
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public boolean save${objectName}(PageData pd) {
		// TODO Auto-generated method stub
		 try {
			 
			 return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		 
		
	}

}
