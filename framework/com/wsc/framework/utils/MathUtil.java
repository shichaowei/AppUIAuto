package com.wsc.framework.utils;

import java.math.BigDecimal;

/**
 * Math数学计算相关类
 * @author LY
 * @since 2015年10月8日上午10:08:04
 */
public class MathUtil {

	public MathUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 随机生成一个左闭右开区间内的double类型的数
	 * @param min 区间起始值，整型
	 * @param max 区间终止值，整型
	 * @return 一个左闭右开区间内的随机小数
	 */
	public static double random(long min,long max){
		return Math.random()*(max-min)+min;
	}
	
	/**
	 * 输入一个小数d，返回d保留x位小数的结果，小数不足x位在后面补零
	 * @param d double类型的小数
	 * @param x	保留x位小数
	 * @return 保留x位小数的数
	 */
	public static String subNumberToStr(double d,int x){
		BigDecimal b = new BigDecimal(d);
		BigDecimal f = b.setScale(x, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(f);
	}
	
}
