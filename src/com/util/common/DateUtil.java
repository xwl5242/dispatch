package com.util.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getMonthStartOrEnd(String time){
		
		String endDate = "";
		if(!time.equals("")&&time!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar cal = Calendar.getInstance();
			String[] times = time.split("-");
			String year = times[0];
			String month = times[1];
			Date date = new Date();
			String str = sdf.format(date); 
			String[] nowDate = str.split("-");
			String nowYear = nowDate[0];
			String nowMonth = nowDate[1];
			String nowDay = nowDate[2];
			if(year.equals(nowYear)){
				if(month.equals(nowMonth)){
					endDate = year +"-"+ month +"-"+ nowDay;
				}else{
					cal.set(Calendar.YEAR, Integer.parseInt(year));
					cal.set(Calendar.MONTH, Integer.parseInt(month)); //我的版本是jdk1.6月不用-1
					cal.set(Calendar.DATE, 0);
					Date newEndDate = cal.getTime();
					endDate = sdf.format(newEndDate);
				}
			}else{
				cal.set(Calendar.YEAR, Integer.parseInt(year));
				cal.set(Calendar.MONTH, Integer.parseInt(month)); //我的版本是jdk1.6月不用-1
				cal.set(Calendar.DATE, 0);
				Date newEndDate = cal.getTime();
				endDate = sdf.format(newEndDate);
			}
		}
		return endDate;
	}

}
