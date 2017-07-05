package com.frames.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	public final static String STRING_YMD = "yyyy-MM-dd";
	public final static String STRING_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
			"yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate1(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		return getAfterDayDate(days, "");
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days, String format) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		if (Tools.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdfd = new SimpleDateFormat(format);
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 得到n天之前的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getBeforeDayDate(String days, String format) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		if (Tools.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdfd = new SimpleDateFormat(format);
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 方法说明： 获取当前时间周几的日期. <BR>
	 * 
	 * @see com.frames.util.DateUtil <BR>
	 * @return
	 * @return: String
	 * @Author: wangjiaxin <BR>
	 * @Datetime：2016-1-2 上午11:14:27 <BR>
	 */
	public static String getWeekWithNum(int num) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, num+1); 
		
		if(num == 7){
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return df.format(cal.getTime());
	}
	
	
	/**
	 * 方法说明：获取当前时间的月的具体的那天的日期 . <BR>
	 * @see com.frames.util.DateUtil <BR>
	 * @param num
	 * @return
	 * @return: String
	 * @Author: guoyuejun <BR>
	 * @Datetime：2016-1-2 上午11:32:22 <BR>
	 */
	public static String getMonthWithNum(int num){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(num>=cal.getMaximum(Calendar.DAY_OF_MONTH)){
			cal.set(Calendar.DAY_OF_MONTH,cal.getMaximum(Calendar.DAY_OF_MONTH)); 
		}else{
			cal.set(Calendar.DAY_OF_MONTH,num);
		}
		return df.format(cal.getTime());
	}

	/**
	 * 方法说明：获取两个日期之间的间隔，返回列表  . <BR>
	 * @see com.frames.util.DateUtil <BR>
	 * @param dateS
	 * @param dateE
	 * @return
	 * @return: List<String>
	 * @Author: guoyuejun <BR>
	 * @Datetime：2016-1-4 下午12:16:24 <BR>
	 */
	public static List<String> getDateGapToList(String dateS, String dateE) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dates = format.parse(dateS);
			Date datee = format.parse(dateE);
			Calendar calS  =  Calendar.getInstance();
			calS.setTime(dates);
			//相差几天
			long day = (datee.getTime() - dates.getTime()) / (24 * 60 * 60 * 1000);
			for(int i=0;i<=day;i++){
				if(i!=0){
					calS.set(Calendar.DAY_OF_YEAR, calS.get(Calendar.DAY_OF_YEAR)+1);
				}
				list.add(i, format.format(calS.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) {
		System.out.println(getAfterDayDate("0", "yyyy-MM-dd"));
	}
	
}
