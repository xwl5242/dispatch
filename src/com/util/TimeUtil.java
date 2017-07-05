package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static String getFullQueryDate(){
		Date dt = new Date();
		dt.setMinutes(dt.getMinutes()-dt.getMinutes()%10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Calendar calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -20);
         String startDate5 = sdf.format(calendar.getTime());
         calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -10);
	     String curDate = sdf.format(calendar.getTime());
	     
	     calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -30);
	     String startDate10 = sdf.format(calendar.getTime());
	     
	     calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -40);
	     String startDate15 = sdf.format(calendar.getTime());
	     
	     calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -50);
	     String startDate20 = sdf.format(calendar.getTime());
	     
	     calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -60);
	     String startDate25 = sdf.format(calendar.getTime());
	     
	     calendar = Calendar.getInstance();      
	     calendar.setTime(dt);
	     calendar.add(Calendar.MINUTE, -70);
	     String startDate30 = sdf.format(calendar.getTime());
	     
	     StringBuffer strDate = new StringBuffer();
	     strDate.append(startDate30).append("--").append(startDate25).append(",")
	             .append(startDate25).append("--").append(startDate20).append(",")
	             .append(startDate20).append("--").append(startDate15).append(",")
	             .append(startDate15).append("--").append(startDate10).append(",")
	             .append(startDate10).append("--").append(startDate5).append(",")
	             .append(startDate5).append("--").append(curDate).append(",");
	    return strDate.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(TimeUtil.getFullQueryDate());
		
	}

}
