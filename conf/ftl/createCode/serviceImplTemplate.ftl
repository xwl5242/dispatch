
/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}ServiceImpl.java     <BR>
 * Author: ${author}  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: ${nowDate} <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.ecc.${packageName}.${objectNameLower}.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecc.${packageName}.${objectNameLower}.dao.${objectName}Dao;
import com.ecc.${packageName}.${objectNameLower}.service.${objectName}Service;
import com.frames.util.Page;
import com.frames.util.PageData;

/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者:  ${author}  <BR>
 * 时间：${nowDate}  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：${nowDate} <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */

@Service
public class ${objectName}ServiceImpl implements ${objectName}Service{
	
	@Resource
	private ${objectName}Dao dao;

	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.service.impl.${objectName}Service
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public List findList${objectName}(Page page) {
		// TODO Auto-generated method stub
		
		return dao.findList${objectName}(page);
		
	}

	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.service.impl.${objectName}Service
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public List listAll${objectName}(PageData pd) {
		// TODO Auto-generated method stub
		return dao.listAll${objectName}(pd);
	}
	
	/**
	 * 实现说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.service.impl.${objectName}Service
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@Override
	public boolean save${objectName}(PageData pd) {
		// TODO Auto-generated method stub
		return dao.save${objectName}(pd);
	}

}
