package com.wsc.framework.control;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.wsc.framework.annotation.Find.Locator;
import com.wsc.framework.config.ConfigInit;

import io.appium.java_client.AppiumDriver;

/**
 * 基础控件类，其他所有控件继承该控件。
 * @author WSC<br/>
 * 
 * 
 */
public abstract class BaseControl implements IControl {

	protected AppEmulator be;
	protected AppiumDriver<WebElement> driver;
	
	protected String fieldName;			//对象的完整变量名
	protected boolean isWebElement = false;		//是否直接传入WebElement对象，而不是通过定位方式和定位内容
	protected Locator locator;			//定位方式
	protected final String NO_OPERATE_KEY = ConfigInit.config.getProperty("NO_OPERATE_KEY"); //主要用于输入框测试的时候，比如均不输入，程序如何处理
	
	protected String how;				//定位内容
	protected WebElement element;		//控件对应的页面标签元素或控件的入口标签元素，如一般情况下，输入框控件InputBox对应input标签元素
	
	protected Object dependvar;				//控件所依赖的变量，来自Element层
	
	
	protected static Logger logger = Logger.getLogger(AppEmulator.class.getName());			//日志
	
	//------------------------------实现接口中的方法------------------------------
	
	@Override
	public void setBrowserEmulator(AppEmulator be) {
		this.be = be;
		this.driver = be.getAppDriver();
	}

	@Override
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	
	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	

	
	@Override
	public void setDependVar(Object obj) {
		this.dependvar = obj;
	}
	
	//------------------------------WebElement的get和set------------------------------
	
	
	
	/**
	 * 将自定义控件对象转换成对应的WebElement对象【默认通过基础定位方式】<br/>
	 * 如果要使用扩展定位方式，请在子类中重写方法{@link BaseControl#getWebElementNOWait()}
	 * @param timeout 超时时间，单位秒
	 * @return 自定义控件对象对应的WebElement对象
	 */
	public WebElement getWebElement(){

		
		if(locator == null || how == null){
			throw new IllegalArgumentException("FindBean对象的locator和using属性为空，无法进行元素查找。");
		}
		WebElement element = null;
		
		
		/*
		 * 此处无法使用反射，因为By为一个抽象类，无法初始化为一个对象
		 * String methodname = locator.name().toLowerCase();			//获取定位方式对应的方法名称
		Method method = By.class.getMethod(methodname);	//获取方法对象
		method.invoke(By, methodname);
		*/
		
		switch(locator){
		case ID:
			element = be.find(By.id(how),"");
			break;
		case CLASS_:
			element = be.find(By.className(how),"");
			break;
		case CSS:
			element = be.find(By.cssSelector(how),"");
			break;
		case NAME:
			element = be.find(By.name(how),"");
			break;
		case XPATH:
			element = be.find(By.xpath(how),"");
			break;
		case LINK_TEXT:
			element = be.find(By.linkText(how), "");
		case PARTIALLINK_TEXT:
			element = be.find(By.partialLinkText(how), "");	
		default:
			break;
		}
		return element;

	}
	



	
	/**
	 * 如果element属性值为null，对其进行赋值
	 */
	protected void setWebElement(){
		this.element=getWebElement();
	}
	
	
	/**
	 * 重新设置element属性的值。针对以下情况：<br/>
	 * element已经赋过值，但元素发生变化或原先的element对象失效，需要重新赋值。
	 */
	public void resetWebElement(){
		element = getWebElement();
	}
	
	//------------------------------WebElement对象对应标签属性或其他信息的get和set------------------------------
	
	/**
	 * 获取自身的HTML标签内容
	 * @return 自身的HTML标签内容，不包含子标签的HTML内容
	 */
	public String getSelfHTML(){
		String outerHTML = getAttribute("outerHTML");
		String innerHTML = getAttribute("innerHTML");
		return outerHTML.replaceAll(innerHTML, "");
	}
	
	/**
	 * 获取元素某个属性的内容
	 * @return 属性值
	 */
	public String getAttribute(String name){
		setWebElement();
		//String str = element.getAttribute(name);
		
		//logger.info("获取元素"+getLocatorMsg()+"属性名称为["+name+"]的属性值，值为["+str+"]。");
		return be.getAttribute(element, name, getLocatorMsg());
	}
	
	/**
	 * 移除元素的某个属性
	 * @param name 属性名称
	 */
	public void removeAttribute(String name){
		setWebElement();
		String code = "arguments[0].removeAttribute('"+name+"');";
		//如果元素没有该属性，执行js脚本也不会报错，所以暂不进行异常处理
//		try {
			be.executeJS(code,"", element);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException("元素没有属性["+name+"]",e);
//		}
		logger.info("移除元素"+getLocatorMsg()+"名称为["+name+"]的属性。");
	}
	
	/**
	 * 设置元素某个属性的值
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public void setAttribute(String name,String value){
		setWebElement();
		String code = "arguments[0].setAttribute('"+name+"','"+value+"');";
		be.executeJS(code,"", element);
		logger.info("设置元素"+getLocatorMsg()+"属性["+name+"]的值为["+value+"]。");
	}
	
	/**
	 * 获取元素的文字（innerText）
	 * @return 元素上的文字
	 */
	public String getText(){
		setWebElement();
		String str = element.getText();
		logger.info("获取元素"+getLocatorMsg()+"的文本内容，为["+str+"]");
		return str;
	}
	
	/**
	 * 获取元素的标签名称
	 * @return 元素的标签名称
	 */
	public String getTagName(){
		setWebElement();
		String str = element.getTagName();
		logger.info("获取元素"+getLocatorMsg()+"的标签名，为["+str+"]");
		return str;
	}
	
	//------------------------------页面元素的基本操作------------------------------
	
	/**
	 * 通过JS点击给定元素
	 * @param element 要点击的元素
	 */
	protected void clickByJS(){
		be.clickByJS(element, getLocatorMsg());
	}
	
	/**
	 * 点击元素
	 * @param we
	 */
	protected void click_(){
		be.click(element, getLocatorMsg());
	}
	
	/**
	 * 清空输入框
	 * @param we
	 */
	protected void clear_(){
		be.clear(element, getLocatorMsg());
	}
	
	/**
	 * 向输入框中输入文本
	 * @param we 输入框对应的WebElement对象
	 * @param text 文本内容
	 */
	protected void sendKeys_(String text) {
		be.sendKeys(element, text, getLocatorMsg());
	}
	

	
	//------------------------------元素状态判断------------------------------
	
	/**
	 * 是否选中
	 * @return 如果选中返回True，否则返回False
	 */
	public boolean isSelected(){
		setWebElement();
		boolean is = element.isSelected();
		logger.info("判断元素"+getLocatorMsg()+"是否选中，结果为["+is+"]");
		return is;
	}
	
	/**
	 * 是否显示
	 * @return 如果显示返回True，否则返回False
	 */
	public boolean isDisplayed(){
		setWebElement();
		boolean is = element.isDisplayed();
		logger.info("判断元素"+getLocatorMsg()+"是否显示，结果为["+is+"]");
		return is;
	}
	
	/**
	 * 是否可用
	 * @return 如果可用返回True，否则返回False
	 */
	public boolean isEnabled(){
		setWebElement();
		boolean is = element.isEnabled();
		logger.info("判断元素"+getLocatorMsg()+"是否可用，结果为["+is+"]");
		return is;
	}
	

	
	//------------------------------控件内部使用的一些方法------------------------------
	
	/**
	 * 获取类名+变量名：LoginPageElement.inb账户名
	 */
	protected String getObjectName(){
		if(fieldName == null){
			return "unknow";
		}
		String strs[] = fieldName.split("\\.");
		int length = strs.length;
		if(length < 1){
			return "unknow";
		}
		return strs[length-2]+"."+strs[length-1];
	}
	
	/**
	 * 获取控件变量名完整信息：public com.tairan.framework.control.InputBox com.tairan.taihe.element.LoginPageElement.inb账户名
	 */
	protected String getFiledName(){
		return "控件【"+fieldName+"】。";
	}
	
	/**
	 * 获取定位相关信息:[Locator.ID:login]
	 */
	protected String getLocatorMsg(){
		return "[Locator."+locator+":"+how+"]";
	}
	
	/**
	 * 暂停一段时间，时间间隔从配置文件中读取
	 */
	protected void pause() {
		be.pause();
	}
	
}
