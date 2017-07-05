package com.dispatch.sys.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class CacheDataDic implements BeanPostProcessor  {

	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
			if(obj instanceof GetDataDicCache){
				  ((GetDataDicCache) obj).getDataDic();
			}
		return obj;
	}

	@Override
	public Object postProcessBeforeInitialization(Object obj, String arg1)
			throws BeansException {
		return obj;
	}

}
