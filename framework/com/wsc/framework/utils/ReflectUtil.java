package com.wsc.framework.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 */
public class ReflectUtil {

	public ReflectUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取指定属性名称的属性值。如果属性名为空或null，则返回null。
	 * @param obj 属性所在类的对象
	 * @param fieldname 属性名称，区分大小写。只能是obj所属类的声明属性，不支持继承的属性。
	 * @return 属性值
	 */
	public static Object getFieldValue(Object obj, String fieldname){
		if(fieldname == null || fieldname.trim().isEmpty()){
			return null;
		}
		Class<? extends Object> clazz = obj.getClass();
		String exceptionMessage = "";	//异常提示信息
		Object result = null;
		try{
			exceptionMessage = "当前字段："+fieldname;	
			Field field = clazz.getDeclaredField(fieldname);	//获取属性
			exceptionMessage = "当前字段："+field.toString()+"\n";	
			boolean isAccessible = field.isAccessible();		//获取属性的访问权限
			field.setAccessible(true);							//设置属性能访问
			result = field.get(obj);							//获取属性值
			field.setAccessible(isAccessible);					//还原属性的访问权限
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (SecurityException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage,e);
		}
		return result;
	}
	
	/**
	 * 批量获取指定属性名称的属性值。<br/>
	 * 如果属性名参数为空，则返回一个空的Map。
	 * @param obj 属性所在类对象
	 * @param fieldnames 属性名称，区分大小写。只能是obj所属类的声明属性，不支持继承的属性。
	 * @return 属性值的Map组合，K：属性名称，V：属性值
	 */
	public static Map<String,Object> getFieldValues(Object obj,String... fieldnames){
		Map<String,Object> map = new HashMap<String,Object>();
		if(fieldnames.length == 0){
			return map;
		}
		for(String name : fieldnames){
			map.put(name, getFieldValue(obj,name));						//获取属性值，并存入Map
		}
		return map;
	}
	
	/**
	 * 设置属性的属性值。
	 * @param obj 属性所在类对象
	 * @param fieldname 要进行设置的属性名称
	 * @param value 要设置的属性值
	 */
	public static void setFiledValue(Object obj, String fieldname, Object value){
		if(fieldname == null || fieldname.trim().isEmpty()){
			return;
		}
		Class<? extends Object> clazz = obj.getClass();
		String exceptionMessage = "";							//异常提示信息
		try{
			Field field = clazz.getDeclaredField(fieldname);	//获取属性
			exceptionMessage = "当前字段："+field.toString()+"\n";	
			boolean isAccessible = field.isAccessible();		//获取属性的访问权限
			field.setAccessible(true);							//设置属性能访问
			field.set(obj, value);								//设置每个属性的值
			field.setAccessible(isAccessible);					//还原属性的访问权限
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (SecurityException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(exceptionMessage,e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage,e);
		}
	}
	
	/**
	 * 批量设置属性的属性值。
	 * @param obj 属性所在类对象
	 * @param map 属性值Map。K：属性名称，V：要设置的属性值。<br/>只能是obj所属类的声明属性，不支持继承的属性。
	 */
	public static void setFiledValues(Object obj,Map<String,Object> map){
		if(map == null || map.isEmpty()){
			return;
		}
		for(String name : map.keySet()){
			setFiledValue(obj, name, map.get(name));
		}
	}
	
}
