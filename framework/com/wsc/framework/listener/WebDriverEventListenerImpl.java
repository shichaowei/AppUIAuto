package com.wsc.framework.listener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * WebDriver事件监听器
 * @author WSC
 * 
 * 
 */
public class WebDriverEventListenerImpl implements WebDriverEventListener {

	public WebDriverEventListenerImpl() {
		
	}

	/**
	 * 【导航】前事件，定义页面在发生跳转前需要执行的代码。<BR/>
	 * Called before {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
	 * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}.
	 */
	public void beforeNavigateTo(String url, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:URL-"+url+"\t:当前URL-"+driver.getCurrentUrl());*/
	}

	/**
	 * 【导航】后事件，定义页面在发生跳转后需要执行的代码。<BR/>
	 * Called after {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
	 * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}. <BR/>
	 * 如果抛出异常，则不调用。
	 */
	public void afterNavigateTo(String url, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:URL-"+url+"\t:当前URL-"+driver.getCurrentUrl());*/
	}

	/**
	 * 【浏览器后退】前事件，定义浏览器在执行后退操作前需要执行的代码。<BR/>
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#back navigate().back()}.
	 */
	public void beforeNavigateBack(WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:当前URL-"+driver.getCurrentUrl());*/
	}

	/**
	 * 【浏览器后退】后事件，定义浏览器在执行后退操作后需要执行的代码。<BR/>
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation navigate().back()}. 
	 * 如果抛出异常，则不调用。
	 */
	public void afterNavigateBack(WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:当前URL-"+driver.getCurrentUrl());*/
	}

	/**
	 * 【浏览器前进】前事件，定义浏览器在执行前进操作前需要执行的代码。<BR/>
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}.
	 */
	public void beforeNavigateForward(WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:当前URL-"+driver.getCurrentUrl());*/
	}

	/**
	 * 【浏览器前进】后事件，定义浏览器在执行前进操作后需要执行的代码。<BR/>
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}. <BR/>
	 * 如果抛出异常，则不调用。
	 */
	public void afterNavigateForward(WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:当前URL-"+driver.getCurrentUrl());*/
	}
	
	/**
	 * 【查找元素】前事件，定义Selenium在查找元素前需要执行的代码。<BR/>
	 * Called before {@link WebDriver#findElement WebDriver.findElement(...)}, or
	 * {@link WebDriver#findElements WebDriver.findElements(...)}, or 
	 * {@link WebElement#findElement WebElement.findElement(...)}, or 
	 * {@link WebElement#findElement WebElement.findElements(...)}.
	 *
	 * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
	 */
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:定位方式-"+by.toString());*/
	}

	/**
	 * 【找到元素】后事件，定义Selenium在找到元素后需要执行的代码。<BR/>
	 * Called after {@link WebDriver#findElement WebDriver.findElement(...)}, or
	 * {@link WebDriver#findElements WebDriver.findElements(...)}, or {@link WebElement#findElement
	 * WebElement.findElement(...)}, or {@link WebElement#findElement WebElement.findElements(...)}.
	 *
	 * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
	 */
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:定位方式-"+by.toString());*/
	}

	/**
	 * 【单击元素】前事件，定义Selenium在单击元素前需要执行的代码。<BR/>
	 * Called before {@link WebElement#click WebElement.click()}.
	 */
	public void beforeClickOn(WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:tagName-"+element.getTagName()+"\t:元素内容-"+element.getAttribute("value"));*/
	}

	/**
	 * 【单击元素】后事件，定义Selenium在单击元素后需要执行的代码。<BR/>
	 * Called after {@link WebElement#click WebElement.click()}. <BR/>
	 * 如果抛出异常，则不调用。
	 */
	public void afterClickOn(WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:tagName-"+element.getTagName()+"\t:元素内容-"+element.getAttribute("value"));*/
	}

	/**
	 * 【元素值变更】前事件，定义Selenium更改元素的值前需要执行的代码。<BR/>
	 * Called before {@link WebElement#clear WebElement.clear()}, 
	 * {@link WebElement#sendKeys WebElement.sendKeys(...)}.
	 */
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:class-"+element.getAttribute("class"));*/
	}

	/**
	 * 【元素值变更】后事件，定义Selenium更改元素的值后需要执行的代码。<BR/>
	 * Called after {@link WebElement#clear WebElement.clear()}, 
	 * {@link WebElement#sendKeys WebElement.sendKeys(...)}}. <BR/>
	 * 如果抛出异常，则不调用。
	 */
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:tagName-"+element.getTagName()+"\t:class-"+element.getAttribute("class"));*/
	}

	/**
	 * 【脚本执行】前事件，定义脚本执行前需要执行的代码。<BR/>
	 * Called before {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String, java.lang.Object[]) }
	 */
	// Previously: Called before {@link WebDriver#executeScript(String)}
	// See the same issue below.
	public void beforeScript(String script, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:script-"+script);*/
	}

	/**
	 * 【脚本执行】后事件，定义脚本执行后需要执行的代码。<BR/>
	 * Called after {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String, java.lang.Object[]) }. <BR/>
	 * 如果抛出异常，则不调用。
	 */
	// Previously: Called after {@link WebDriver#executeScript(String)}. Not called if an exception is thrown
	// So someone should check if this is right.  There is no executeScript method
	// in WebDriver, but there is in several other places, like this one
	public void afterScript(String script, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:script-"+script);*/
	}
	
	/**
	 * 【异常】事件，定义在使用Selenium测试发生异常时需要执行的代码。
	 * Called whenever an exception would be thrown.
	 */
	public void onException(Throwable throwable, WebDriver driver) {
		/*//to do something,for example:
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+"\t:异常信息-"+throwable.getMessage());*/
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

}
