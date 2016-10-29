package com.wsc.framework.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 数组工具类
 * @author WSC
 */
public class ArrayUtil {

	public ArrayUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 数组转ArrayList
	 * @param arr 数组
	 * @return 数组对应的List对象
	 */
	public static ArrayList<Object> toList(Object[] arr){
		ArrayList<Object> list = new ArrayList<Object>();
		for(Object t : arr){
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 数组转ArrayList
	 * @param arr 数组
	 * @return 数组对应的List对象
	 */
	public static ArrayList<String> toListOfString(String[] arr){
		ArrayList<String> list = new ArrayList<String>();
		for(String t : arr){
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 将数组转为Set集合
	 * @param arr 数组
	 * @return 数组转换后的Set集合
	 */
	public static Set<? extends Object> toSet(Object[] arr){
		Set<Object> set = new HashSet<Object>();
		for(Object t : arr){
			set.add(t);
		}
		return set;
	}
	
	/**
	 * 对Int数组求和
	 * @param x int类型的数组
	 * @return 数组求和结果
	 */
	public static int sumOfInt(int[] x){
		int sum = 0;
		for(int i : x){
			sum += i;
		}
		return sum;
	}
	
	/**
	 * 将数组中的指定元素移除并返回移除后的数组
	 * @param arr 要移除元素的数组
	 * @param strs 要移除的元素
	 * @return 移除指定元素后的数组
	 */
	public static Object[] remove(Object[] arr,Object... strs){
		Object[] resarr = new Object[arr.length-strs.length];
		int count = 0;
		for(Object obj : arr){
			if(!toSet(strs).contains(obj)){
				resarr[count] = obj;
				count++;
			}
		}
		return resarr;
	}
	
	/**
	 * 将Object类型数组强制转换为String类型
	 * @param objs Object类型数组s
	 * @return String类型数组
	 */
	public static String[] CStr(Object[] objs){
		String[] strs = new String[objs.length];
		for(int i=0; i<objs.length;i++){
			strs[i] = (String) objs[i];
		}
		return strs;
	}
	/**
	 * 判断两个数组内容是否相等，通过调用对象的toString()方法来判断两个元素是否相等
	 * @param obj1 要判断的第一个数组
	 * @param obj2 要判断的第二个数组 
	 * @return
	 */
	public static boolean contentEqual(Object[] obj1,Object[] obj2){
		boolean result = false;
		if(obj1 == null && obj2 == null){
			return true;
		}else if(!(obj1 != null && obj2 != null)){
			return false;
		}else{
			if(obj1.length != obj2.length){
				return false;
			}else if(obj1.length == obj2.length && obj2.length == 0){
				return true;
			}else{
				if(contains(obj2, obj1)){
					return true;
				}
			}
		}
		return result;
	}
	/**
	 * 数组中是否包含指定元素，调用对象的toString()方法来判断两个元素是否相等
	 * @param objs 数组对象
	 * @param obj 需要判断的对象，可以是多个
	 * @return
	 */
	public static boolean contains(Object[] objs,Object... obj){
		boolean result = true;
		Set<String> set = new HashSet<String>();
		for(Object tmp : objs){
			set.add(tmp.toString());
		}
		for(Object tmp : obj){
			if(!set.contains(tmp.toString())){
				return false;
			}
		}
		return result;
	}

}
