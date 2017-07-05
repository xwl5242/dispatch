package com.dispatch.sys.log.service;

import com.dispatch.sys.bean.Operatelog;

public interface OperatelogService {
	/**
	 * 方法说明： 保存操作日志. <BR>
	 */
	public void saveOperateLog(Operatelog opt);
}
