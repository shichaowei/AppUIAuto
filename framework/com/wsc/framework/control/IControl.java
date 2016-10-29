package com.wsc.framework.control;

import java.util.ArrayList;

import com.wsc.framework.annotation.Find.Locator;

/**
 * 控件接口类
 * @author WSC
 */
public interface IControl {
	
	
	/**
	 * 将BrowserEmulator对象传给控件对象或更新控件对象的BrowserEmulator属性
	 * @param driver WebDriver对象
	 */
	public void setBrowserEmulator(AppEmulator be);
	
	/**
	 * 将定位方式传给控件对象
	 * @param locator
	 */
	public void setLocator(Locator locator);
	
	/**
	 * 将变量名称传给控件对象
	 * @param fieldName 对象的变量名
	 */
	public void setFieldName(String fieldName);
	
	
	
	/**
	 * 将控件依赖的变量传给控件对象
	 * @param obj 控件所依赖的变量
	 */
	public void setDependVar(Object obj);
	
}
