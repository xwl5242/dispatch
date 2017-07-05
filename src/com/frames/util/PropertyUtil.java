/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  PropertyUtil.java     <BR>
 * Author: guoyuejun  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016年7月11日 下午3:09:52 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.frames.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyUtil {
	
	private static Resource application_resource = null;
	private static Properties application_pros = null;
	private static Resource jdbc_resource = null;
	private static Properties jdbc_pros = null;
	static{
		try {
			//加载class目录下的application.properties文件
			application_resource = new ClassPathResource("application.properties");
			application_pros = new Properties();
			application_pros.load(application_resource.getInputStream());
			//加载class目录下的application.properties文件
			jdbc_resource = new ClassPathResource("jdbc.properties");
			jdbc_pros = new Properties();
			jdbc_pros.load(jdbc_resource.getInputStream());
		} catch (IOException e) {
		}
	}

	public static String getApplicationValue(String key){
		if(null==application_resource){
			application_resource = new ClassPathResource("application.properties");
			application_pros = new Properties();
			try {
				application_pros.load(application_resource.getInputStream());
			} catch (IOException e) {
			}
		}
		
		return application_pros.getProperty(key);
	}
	
	public static String getJdbcValue(String key){
		if(null==jdbc_resource){
			jdbc_resource = new ClassPathResource("jdbc.properties");
			jdbc_pros = new Properties();
			try {
				jdbc_pros.load(jdbc_resource.getInputStream());
			} catch (IOException e) {
			}
		}
		
		return jdbc_pros.getProperty(key);
	}
	
}
