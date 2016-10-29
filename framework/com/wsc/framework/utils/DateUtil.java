package com.wsc.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间日期工具类
 */
public class DateUtil {
	
	static Map<String, Long> timerpool = null;
	static{
		timerpool = new HashMap<String,Long>();
	}

	public DateUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 返回指定格式的日期
	 * @param pattern 日期格式，如"yyyy-MM-dd HH:mm:ss"
	 * @param date Date对象
	 * @return 指定格式的日期
	 */
	public static String formatDate(String pattern,Date date){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 返回当前时间的时间戳字符串,格式为：2015-12-31 14:14:32
	 * @return 当前时间的时间戳字符串
	 */
	public static String getTimeStamp(){
		return formatDate("yyyy-MM-dd HH:mm:ss",new Date());
	}
	
	/**
	 * 返回包含当前系统时间信息的数组，依次为：年、月、日、时、分、秒
	 * @param d Date对象
	 * @return 包含当前系统时间信息的数组，依次为：年、月、日、时、分、秒
	 */
	public static String[] getArrayOfTimeStamp(Date d){
		Calendar src = Calendar.getInstance();
		src.setTime(d);
		String[] temp = new String[6];
		temp[0] = String.valueOf(src.get(Calendar.YEAR));
		temp[1] = formatNumber(src.get(Calendar.MONTH)+1);
		temp[2] = formatNumber(src.get(Calendar.DAY_OF_MONTH));
		temp[3] = formatNumber(src.get(Calendar.HOUR_OF_DAY));
		temp[4] = formatNumber(src.get(Calendar.MINUTE));
		temp[5] = formatNumber(src.get(Calendar.SECOND));
		return temp;
	}
	
	/**
	 * 格式化数字，如输入数字4(11)，返回字符串04(11)
	 * @param n
	 * @return
	 */
	private static String formatNumber(int n){
		if(n < 10 && n >= 0){
			return "0" + String.valueOf(n);
		}else if(n >= 10 && n < 100){
			return String.valueOf(n);
		}else{
			return "输入数字不符合要求";
		}
	}
	
	/**
	 * 计时器：开启计时
	 * @param name 计时名称，用以区分不同的计时器
	 */
	public static void start(String name){
		timerpool.put(name, System.currentTimeMillis());
	}
	
	/**
	 * 计时器：结束计时
	 * @param name 计时名称，用以区分不同的计时器
	 * @return 耗时时间，单位毫秒
	 */
	public static String end(String name){
		long starttime = timerpool.get(name);
		long endtime = System.currentTimeMillis();
		timerpool.remove(name);
		return String.valueOf(endtime-starttime);
	}
	
	/**
	 * 计时器：结束计时并打印时长
	 * @param name
	 */
	public static void endprint(String name){
		System.out.println((name+":"+end(name)+"ms"));
	}

}
