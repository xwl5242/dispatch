package com.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static Map<String,String> paramMap=new HashMap<String,String>(){{
		put("累积热量", "SUMHEATQUANTITY");
		put("瞬时热量", "HEATQUANTITY");
		put("瞬时流量", "FLOW");
		put("累积流量", "SUMFLOW");
		
		put("一次回水压力", "RETURNPRESS");
		put("一次供水温度", "SUPPLYTEMP");
		put("一次回水温度", "RETURNTEMP");
		put("一次供水压力", "SUPPLYPRESS");
		put("二次回水压力", "SECRETURNPRESS");
		put("二次供水温度", "SECSUPPLYTEMP");
		put("二次回水温度", "SECRETURNTEMP");
		put("二次供水压力", "SECSUPPLYPRESS");
		
	}};
 
}
