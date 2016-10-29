package com.wsc.framework.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;

import com.wsc.framework.annotation.DependVar;
import com.wsc.framework.annotation.Find;
import com.wsc.framework.annotation.Find.Locator;
import com.wsc.framework.control.AppEmulator;
import com.wsc.framework.utils.ReflectUtil;

import io.appium.java_client.AppiumDriver;


/**
 * 为了使用PageObject模型时更简单、容易。与Selenium的PageFactory类似。<br/>
 * 示例：<br/>
 * （1）用于Element类的构造器:<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>PageFactoryAT.initElements(be,this);</code>
 * @author wsc
 * 
 */
public class PageFactoryAppAT {

	public PageFactoryAppAT() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 元素初始化，针对Control【创建对象】。<br/>前提条件：元素使用了Find注解。<BR/>
	 * how只有一个参数，如：<BR/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>@Find(how=Locator.ID,id="idname")</code><BR/>
	 * how有多个参数（<strong>how数组中值的顺序需要和构造器对应</strong>），如：<BR/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>@Find(how={Locator.ID,Locator.TEXT},id="idname",text="content")</code>
	 * @param be BrowserEmulator对象
	 * @param page 页面类对象
	 */
	public static void initElements(AppEmulator be,Object page){
		Field[] fields = page.getClass().getFields();				//获取该类及其所有父类的公共字段（该方法要求属性修饰符必须为public）
//		Field[] fields = page.getClass().getDeclaredFields();		//获取此类所有已声明字段（该方法无法获取从父类继承的字段）
		String exceptionMessage = "";								//异常提示信息
		//初始化变量
		for(Field field:fields){
			//获取成员变量上的注解
			Find find = field.getAnnotation(Find.class);          //此处的field其实每个都是控件类	
		
			
			if(find != null){
				Locator how = find.how();							//获取定位方式
				boolean isAccessible = field.isAccessible();
				field.setAccessible(true); 							//设置些属性是可以访问的
				exceptionMessage = "当前字段："+field.toString()+"\n";	
				try {
					Constructor<?> con = null;
					Object value = null;
					if(how.equals(Locator.DEFAULT)){				//如果使用缺省定位，即不传入具体定位方式
						con = field.getType().getConstructor(AppiumDriver.class);
						value = con.newInstance(be); 				//要赋给属性的值
					}else{											//如果使用含参定位
						/*
						 *  由于注解是@Find(how=Locator.LINK_TEXT,link_text="首页")
						 *  通过how.name可以拿到定位方式是什么（此处是LINK_TEXT）
						 *  小写为link_text，这个就是Find的方法名
						 *  这三句等价于con.newInstance(be,find.link_text)
						 *  Method method = find.getClass().getMethod(methodname);	//获取方法对象
						    value = con.newInstance(be,method.invoke(find)); 		//要赋给属性的值	
						 *  由于how.name回来的值会有很多种情况，那么find.****(可能每次都不同)，需要调用的方法不同
						 *  要不：使用switch语句，这样会导致代码臃肿
						 *       使用反射，这样代码会比较少，这些强制
						 *  
						 *  
						 */
						String methodname = how.name().toLowerCase();			//获取定位方式对应的方法名称
						Method method = find.getClass().getMethod(methodname);	//获取方法对象
						con = field.getType().getConstructor(AppEmulator.class,String.class); //getType 获取属性声明时类型对象（返回class对象）
						value = con.newInstance(be,method.invoke(find)); 		//要赋给属性的值	
						
					}
					field.set(page, value);							//成员变量初始化（相当于obj = new Object();）
					Object obj = field.get(page);					//获取该字段的对象
					//将定位方式传给控件对象
					Method methodLocator = obj.getClass().getMethod("setLocator", Locator.class); 
					methodLocator.invoke(obj, how);
					//将对象的变量名传给控件对象 ,主要是日志的时候可能用到
					Method methodFieldName = obj.getClass().getMethod("setFieldName", String.class); 
					methodFieldName.invoke(obj, field.toString());
					

				} catch (IllegalArgumentException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (InstantiationException e) {
					throw new RuntimeException(exceptionMessage,e); 
				} catch (InvocationTargetException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (SecurityException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(exceptionMessage,e);
				}
				field.setAccessible(isAccessible);
			}
		}
		
		
		//设置变量依赖
		for(Field field : fields){
			DependVar dependvar = field.getAnnotation(DependVar.class);
			if(dependvar != null){
				boolean isAccessible = field.isAccessible();
				field.setAccessible(true); 							//设置些属性是可以访问的
				exceptionMessage = "当前字段："+field.toString()+"\n";	
				try {
					String var = dependvar.var();
					if(var.trim().isEmpty()){
						throw new IllegalArgumentException("依赖变量的名称不能为空或不可见字符");
					}
					Object servant = ReflectUtil.getFieldValue(page, var);
					if(servant == null){
						throw new IllegalArgumentException("所指定的依赖变量["+var+"]不存在或未初始化");
					}
					Object owner = field.get(page);			//主变量
					Method mt = owner.getClass().getMethod("setDependVar", Object.class);
					mt.invoke(owner, servant);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (SecurityException e) {
					throw new RuntimeException(exceptionMessage,e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(exceptionMessage,e);
				}
				field.setAccessible(isAccessible);
			}
		}
	}
}
