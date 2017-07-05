package com.dispatch.sys.log.dao;

import com.dispatch.sys.bean.Operatelog;

public interface OperatelogDao {

	/**
	 * 方法说明： 保存操作日志. <BR>
	 */
	public void saveOperateLog(Operatelog opt);
}
