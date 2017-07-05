
/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}Dao.java     <BR>
 * Author: ${author}  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: ${nowDate} <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.ecc.${packageName}.${objectNameLower}.dao;


import java.util.List;
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
public interface ${objectName}Dao {
	
	/**
	 * 
	 * 方法说明：分页查询 . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.${objectName}Dao <BR>
	 * @param page
	 * @return
	 * @return: List<PageData>
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate}  <BR>
	 */
	public List findList${objectName}(Page page);
	
	
	/**
	 * 
	 * 方法说明：导出查询 . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.${objectName}Dao <BR>
	 * @param pd
	 * @return
	 * @return: List<PageData>
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate}  <BR>
	 */
	public List listAll${objectName}(PageData pd);
	
	
	/**
	 * 
	 * 方法说明：插入 . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.dao.${objectName}Dao <BR>
	 * @param pd
	 * @return
	 * @return: boolean
	 * @Author: ${author}  <BR>
	 * @Datetime：${nowDate}  <BR>
	 */
	public boolean    save${objectName}(PageData pd);

}
