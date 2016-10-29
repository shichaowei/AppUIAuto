package com.wsc.framework.control;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.wsc.framework.config.ConfigInit;
import com.wsc.framework.constant.Const;
import com.wsc.framework.constant.JS;
import com.wsc.framework.utils.XMLUtil;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * 
 * @author Wsc<br/>
 * 修改日期：2016年7月22日
 */
public class AppEmulator {


	AppiumDriver<WebElement> driver;
	String currenWindowHanler;
	
	int deviceType;							//浏览器类型
	int stepInterval = Integer.parseInt(ConfigInit.config.getProperty("StepInterval", "500"));					//模拟鼠标键盘操作的速度，单位为毫秒
	int timeout_findElement = Integer.parseInt(ConfigInit.config.getProperty("timeout_findElement", "30000"));	//查找元素的超时时间，单位为毫秒

	
	
	
	private static Logger logger = Logger.getLogger(AppEmulator.class.getName());				//日志

	
	
	
	/**
	 * 启动APP驱动，参数通过testng的xml拿到
	 * @param type APP驱动类型，1：Android；2：IOS；
	 * @param devicename 设备名称对应device.xml中的devicename属性值
	 * @param appname APP的具体名称（app需要在resource/apps文件夹里面）
	 * @throws MalformedURLException 
	 */
	public AppEmulator(int type,String devicename,String appname)  {
		deviceType = type;
		String platformName=XMLUtil.parseDeviceXML(devicename).get("platformName");
		String deviceName=XMLUtil.parseDeviceXML(devicename).get("devicename");
		String udid=XMLUtil.parseDeviceXML(devicename).get("udid");
		String huburl=XMLUtil.parseDeviceXML(devicename).get("huburl");
		String capabilitytypeversion=XMLUtil.parseDeviceXML(devicename).get("capabilitytypeversion");
		// set up appium
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "resource/apps");
        File app = new File(appDir, appname);
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, platformName);
        capabilities.setCapability("deviceName",deviceName);
        capabilities.setCapability(CapabilityType.VERSION, capabilitytypeversion);
        capabilities.setCapability("app", app.getAbsolutePath());
        System.out.println(app.getAbsolutePath());
        capabilities.setCapability("udid",udid );
        
		switch(type){
		case Const.Androoid:
			
	        try {
				driver = new AndroidDriver<WebElement>(new URL(huburl), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("启动了Android APP");
			break;
		case Const.Ios:
	        try {
				driver = new IOSDriver<WebElement>(new URL(huburl), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("启动了Ios APP");
			
			break;
		
		default:
			String message = "不支持的类型，请使用以下浏览器：Firefox、chrome、IE或Safari！";
			logger.error(message);
			Assert.fail(message);
			break;
		}
		//设置超时时间
		driver.manage().timeouts().implicitlyWait(timeout_findElement, TimeUnit.MILLISECONDS);


	}
	
	//------------------------------变量的get/set方法------------------------------
	
	/**
	 * 获取APP驱动对象
	 * @return 浏览器驱动对象
	 */
	@SuppressWarnings("unchecked")
	public AppiumDriver<WebElement> getAppDriver(){
		return this.driver;
	}
	

	
	
	
	/**
	 * 获取APP类型
	 * @return APP类型，1：android 2：ios
	 */
	public int getDeviceType(){
		return this.deviceType;
	}
	
	
	
	
	//------------------------------native APP操作------------------------------
	
	/**
	 * 
	 * 启动其他应用，跨APP(适用安卓) 
	 */
	public void startAndroidAPP(String appPackage, String appActivity){
		((AndroidDriver<WebElement>)driver).startActivity(appPackage, appActivity);
		logger.info("打开新的app，包名"+appPackage+"主activity"+appActivity);
	}
	
	//------------------安卓专用----------------------
	/**
	 * 获取当前的activity名称（安卓专用）
	 * @return
	 */
	public String getAndroidActivity(){
		logger.info("获取当前的activity名称");
		return ((AndroidDriver<WebElement>)driver).currentActivity();
	}
	/**
	 * 获取网络状况（安卓专用）
	 * @return
	 */
	public String getAndroidNetworkConnection(){
		logger.info("获取网络状态");
		return ((AndroidDriver<WebElement>)driver).getConnection().toString();
	}
	/**
	 * 
	 * 推送文件到设备中去(安卓专用)
	 * @param remotePath
	 * @param filepath
	 */
	public void androidpushfile(String remotePath,String filepath){
		logger.info("上传文件");
		try {
			((AndroidDriver<WebElement>)driver).pushFile(remotePath, new File(filepath));
		} catch (IOException e) {
			handleFailure("文件不存在", e);
		}
	}
	/**
	 * 从设备中拉出文件
	 * @param remotePath
	 * @param filepath
	 */
	public void pullfile(String remotePath){
		logger.info("上传文件");
		driver.pullFile(remotePath);
	}
	
	public boolean isAppInstalled(String packagename){
		logger.info("获取应用是否存在");
		return driver.isAppInstalled(packagename);
	}
	/**
	 * 关闭APP，退出进程
	 */
	public void quit() {
		pause();
		driver.closeApp();
		driver.quit();
		logger.info("退出了APP");
	}
	
	public void switchToContext(String Context){
		isexistcontext(Context);
		driver.context(Context);
		logger.info("切换上下文");
	}
	
	public void switchtoNative(){
		isexistcontext("NATIVE_APP");
		driver.context("NATIVE_APP");
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getallcontexts(){
		return driver.getContextHandles(); 
	}
	
	/**
	 * 上下文是否存在
	 * @param Context
	 * @return
	 */
	public boolean isexistcontext(String Context){
		boolean flag=false;
		Set<String> contextNames = driver.getContextHandles();  
		for (String contextName : contextNames) {  
		    if (contextName.contains(Context)) {  
		        flag=true;
		        break;  
		    }  
		}  
		logger.info("上下文"+Context+"是否存在");
		return flag;
	}
	/**
	 * 根据首尾两个元素的位置进行滑动(可进行上下左右任意滑动)
	 * @param start
	 * @param end
	 * @param during
	 * @return
	 */
	public boolean swipebyelement(WebElement start,WebElement end,int during){ 
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		String   beforeswipe=driver.getPageSource();
		driver.swipe(start.getLocation().x, start.getLocation().y, end.getLocation().x , end.getLocation().y, during);
		String afterswipe=driver.getPageSource();
		//防止网络原因造成的滑动失败
		if(beforeswipe.equals(afterswipe)?true:false){
			afterswipe=driver.getPageSource();
		}
		logger.info("向上滑动");
		return  beforeswipe.equals(afterswipe)?true:false;
	}
	/**
	 * 向上滑动，Y轴固定在屏幕正当中，Y轴滑动屏幕的1/2，Y轴的起始位置为屏幕的3/4，滑动终点为Y轴的1/4
	 * @param during
	 */
	public boolean swipeToUp(int during){ 
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		String   beforeswipe=driver.getPageSource();
		driver.swipe(width/2, height * 3/ 4, width /2 , height /4, during);
		String afterswipe=driver.getPageSource();
		//防止网络原因造成的滑动失败
		if(beforeswipe.equals(afterswipe)?true:false){
			afterswipe=driver.getPageSource();
		}
		logger.info("向上滑动");
		return  beforeswipe.equals(afterswipe)?true:false;
	}
	
	/**
	 * 向下滑动
	 * @param during
	 * @return 
	 */
	 public boolean swipeToDown( int during) {
		 int width = driver.manage().window().getSize().width;
			int height = driver.manage().window().getSize().height;
			String   beforeswipe=driver.getPageSource();
			driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, during);
			String afterswipe=driver.getPageSource();
			//防止网络原因造成的滑动失败
			if(beforeswipe.equals(afterswipe)?true:false){
				afterswipe=driver.getPageSource();
			}
			logger.info("向下滑动");
			return  beforeswipe.equals(afterswipe)?true:false;
        
	 }
    /**
     * 向左滑动 X轴的起始位置为屏幕的3/4，滑动终点为X轴的1/4
     * 
     */
    public void swipeToLeft(int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, during);
        // wait for page loading
    }
    
    /**
     * 向右滑动 X轴的起始位置为屏幕的1/4，滑动终点为X轴的3/4
     * 
     */
    public void swipeToRight( int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, during);
        // wait for page loading
    }
    
    //获取含超时机制的AppiumDriver对象
	private WebDriverWait getWebDriverWait(){
		return new WebDriverWait(driver, timeout_findElement/1000);
	}
	
	
	
	
	//--------------------------------h5界面操作--------------
	/**
	 * 刷新当前页面 
	 */
	public void refresh() {
		pause();
		driver.navigate().refresh();
		logger.info("点击了浏览器的刷新按钮");
	}
	
	/**
	 * 在浏览器的历史记录中进行一次前进操作。如果不能前进，则不做处理。
	 */
	public void forward(){
		pause();
		driver.navigate().forward();
		logger.info("点击了浏览器的前进按钮");
	}
	
	/**
	 * 在浏览器的历史记录中进行一次后退操作。
	 */
	public void back(){
		pause();
		driver.navigate().back();
		logger.info("点击了浏览器的后退按钮");
	}
	
	//------------------------------JS操作------------------------------
	/**
	 * 执行JavaScript脚本，返回类型为String。<br/>
	 * <strong>参考：</strong>{@link BrowserEmulator#exeJS(String, Object...)}
	 * @param code 要执行的代码
	 * @param info 提示信息
	 * @param objects 可变长参数
	 * @return 脚本执行结果,返回String类型
	 */
	public String executeJS(String code,String info,Object... objects){
		return (String) (exeJS(code,info,objects));
	}
	
	/**
	 * 在当前选中frame或window上以匿名函数的形式执行JavaScript脚本。<br/>
	 * <strong>[1]</strong>如果脚本有返回值（如脚本中包含return语句），将遵循以下规则：<ul>
	 * <li>对于一个HTML元素，返回WebElement对象</li>
	 * <li>对于一个decimal类型的值，返回Double类型的值</li>
	 * <li>对于一个非decimal类型的值，返回Long类型的值</li>
	 * <li>对于一个boolean类型的值，返回Boolean类型的值</li>
	 * <li>对于所有其他情况，返回String类型的值</li>
	 * <li>对于一个数组，返回一个List&lt;Object&gt;对象。其中每个Object对象遵循以上规则。这里允许嵌套list。</li></ul>
	 * <strong>[2]</strong>如果返回值是null或者没有返回值，将返回null值。<br/>
	 * <strong>[3]</strong>参数必须是number、boolean、String、WebElement类型，或由以上类型组成的List。如果参数不是这些类型，将抛出异常。<br/>
	 * <strong>[4]</strong>类似于"Function.apply"，在JavaScript脚本代码中，参数objects的值通过"arguments"来使用。
	 * @param code 要执行的代码
	 * @param info 提示信息，如："遍历表格，返回所有的td"
	 * @param objects 可变长参数
	 * @return Boolean、Long、String、List、WebElement类型值或者是null
	 */
	public Object exeJS(String code,String info,Object... objects){
		pause();
		Object obj = null;
		try {
			obj = ((JavascriptExecutor) driver).executeScript(code,objects);
		} catch (Exception e) {
			handleFailure("执行JS脚本【"+code+"】出错。"+info, e);
		}
		logger.info("执行JS脚本【"+code+"】。"+info);
		return obj;
	}
	

	/**
	 * 通过JS点击元素
	 * @param we 要点击的元素
	 * @param info 提示信息，如："定位方式：ID，定位内容：idname。"
	 */
	public void clickByJS(WebElement we, String info){
		executeJS(JS.CLICK,"", we);
		logger.info("通过JS点击元素。"+info);
	}
	
	//------------------------------页面元素基本操作(包含原生和H5)------------------------------
	/**
	 * 判断元素是否存在
	 * @param by 
	 * @param info
	 * @return
	 */
	public boolean isElementExit(final By by,String info){
		boolean flag=true;
		try {
			getWebDriverWait().until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return driver.findElement(by);
				}
				
			});
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			flag=false;
		} catch(Exception e){
			handleFailure("判断元素"+by.toString()+"是否存在,出现严重错误", e);
		}
		logger.info("元素是否存在["+by.toString()+"]。结果是:"+flag);
		return flag;
	}
	
	
	
	/**
	 * 判断元素是否存在(层级定位查找)
	 * @param elements 元素的基准节点
	 * @param by 
	 * @param info
	 * @return
	 */
	public boolean isInterElementExit(WebElement element, final By by,String info){
		boolean flag=true;
		try {
			getWebDriverWait().until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return element.findElement(by);
				}
			});
		} catch (NoSuchElementException e) {
			flag=false;
		} catch(Exception e){
			handleFailure("判断元素"+by.toString()+"是否存在,出现严重错误"+"。info is "+info, e);
		}
		logger.info("元素是否存在["+by.toString()+"]。结果是:"+flag+"。info is "+info);
		return flag;
	}
	
	
	
	/**
	 * 查找元素，返回单个元素。
	 * @param by 定位元素方式和内容
	 * @param info 提示信息，如："下拉箭头"
	 * @return 查找到的元素
	 */
	public WebElement find(final By by,String info){
		pause();
		WebElement we;
		if(isElementExit(by, info)){
			we=driver.findElement(by);
		}else{
			we=null;
		}
		logger.info("查找到元素["+by.toString()+"]。"+info);
		return we;
	}
	
	
	/**
	 * 查找元素，返回多个元素。
	 * @param by 定位元素方式和内容
	 * @param info 提示信息，如"查询下拉框所有下拉选项"
	 * @return 查找到的元素集合
	 */
	public List<WebElement> finds(final By by,String info){
		pause();
		List<WebElement> we;
		if(isElementExit(by, info)){
			we=driver.findElements(by);
		}else{
			we=null;
		}
		logger.info("查找元素["+by.toString()+"]。"+info);
		return we;
	}
	
	/**
	 * 层级定位过程中用于查找元素，返回单个元素。
	 * @param webelement 父元素
	 * @param by 层级定位方式
	 * @param info 提示信息，如："下拉箭头"
	 * @return 查找到的元素
	 */
	public WebElement Interfind(final WebElement element,final By by,String info){
		pause();
		WebElement we = null;
		if(isInterElementExit(element, by, info)){
			we=element.findElement(by);
		}else{
			we=null;
		}
		logger.info("层级查找元素["+by.toString()+"]。"+info);
		return we;
	}
	
	/**
	 * 层级定位过程中用于查找元素，返回多个元素。
	 * @param webelement 父元素
	 * @param by 层级定位方式
	 * @param info 提示信息，如："下拉箭头"
	 * @return 查找到的元素
	 */
	public List<WebElement> Interfinds(final WebElement element,final By by,String info){
		pause();
		List<WebElement> we = null;
		if(isInterElementExit(element, by, info)){
			we=element.findElements(by);
		}else{
			we=null;
		}
		logger.info("层级查找元素["+by.toString()+"]。"+info);
		return we;
	}
	
	/**
	 * 获取元素某个属性的内容
	 * @return 属性值
	 */
	public String getAttribute(WebElement element,String name, String info){
		String str = element.getAttribute(name);
		logger.info("获取元素"+info+"属性名称为["+name+"]的属性值，值为["+str+"]。");
		return str;
	}

	
	
	/**
	 * 点击元素
	 * @param we 要点击的元素
	 * @param info 提示信息，如："定位方式：ID，定位内容：idname。"或者具体元素描述
	 */
	public void click(WebElement we, String info){
		pause();
		try {
			we.click();
		} catch (Exception e) {
			handleFailure("点击元素失败。定位方式"+info, e);
		}
		logger.info("点击元素。"+info);
	}
	/**
	 * 
	 * 点击元素，只适合原生APP
	 * @param by
	 */
	public void tap(By by){
		try {
			driver.tap(1, driver.findElement(by), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			handleFailure("点击元素失败。定位方式"+by.toString(), e);
		}
		logger.info("点击元素。"+by.toString());
	}
	
	
	/**
	 * 清空输入框
	 * @param we 要清空文本的元素
	 * @param info 提示信息，如："定位方式：ID，定位内容：idname。"
	 */
	public void clear(WebElement we, String info){
		pause();
		try {
			we.clear();
		} catch (Exception e) {
			handleFailure("清空输入框失败。"+info, e);
		}
		logger.info("清空输入框。"+info);
	}
	
	/**
	 * 输入文本
	 * @param we 要进行文本输入的元素
	 * @param text 要输入的文本内容
	 * @param info 提示信息，如："定位方式：ID，定位内容：idname。"
	 */
	public void sendKeys(WebElement we,String text,String info){
		pause();
		try {
			we.sendKeys(text);
		} catch (Exception e) {
			handleFailure("输入文本【"+text+"】失败。"+info, e);
		}
		logger.info("输入文本【"+text+"】。"+info);
	}
	

	
	//------------------------------H5页面检查------------------------------
	
	/**
	 * 当前页面上是否包含指定文本内容
	 * @param text 预期文本内容
	 * @return 如果页面上包含指定文本内容，返回True；否则返回False。
	 */
	public boolean H5isTextPresent(String text) {
		pause();
		boolean isPresent = find(By.tagName("body"),"").getText().contains(text);
		if (isPresent) {
			logger.info("在页面【"+driver.getCurrentUrl()+"】上查找文本【"+text+"】，查找结果：找到。");
			return true;
		} else {
			logger.info("在页面【"+driver.getCurrentUrl()+"】上查找文本【"+text+"】，查找结果：未找到。");
			return false;
		}
	}
	/**
	 * 判断控件android.widget.TextView是否有特定的元素文本
	 * @param text
	 * @return
	 */
	public boolean isTextPresent(String text) {
		pause();
		boolean flag=false;
		List<WebElement> welist= finds(By.className("android.widget.TextView"), "获取所有的textview");
		for(WebElement we:welist){
			System.out.println(we.getText());
		}
		return flag;
	}
	
	
	/**
	 * 属性是否符合预期 
	 * @checkMap k 属性 v 属性值 
	 * 			例如：{class：disabled;id=pageNav}
	 * @return 如果符合预期 返回true 不符合预期返回false
	 * 
	 */
	public boolean checkattr(WebElement element,Map<String, String> checkMap){
		boolean flag=false;
		//System.out.println(element.getAttribute("outerHTML"));
		for(Map.Entry<String, String>entry:checkMap.entrySet()){
			if(element.getAttribute(entry.getKey()).equals(entry.getValue())){
				flag=true;
			}
			else {
				flag=false;
				break;
			}
		}
		
		return flag;
	}
	
	

	//------------------------------非浏览器操作的一些方法------------------------------
	
	/**
	 * 失败时的日志及异常处理
	 * @param notice 提示信息
	 * @param throwable 异常对象
	 */
	public void handleFailure(String notice,Throwable throwable){
		//发送错误日志到log4j
		logger.error(notice, throwable);
		//发送错误日志到测试报告
		Reporter.log(notice);
		throw new RuntimeException(notice,throwable);
	}
	
	/**
	 * 暂停一段时间
	 * @param time 时间间隔，单位毫秒
	 */
	public void pause(int time) {
		if (time <= 0) {
			return;
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停一段时间，时间间隔从配置文件中读取
	 */
	public void pause(){
		pause(stepInterval);
	}

	//待整理(H5相关)----------------------------------------------
	/**
	 * get the popupwindow
	 */
	public void selectPopUpWindow() {
		currenWindowHanler = getcurrentWindowHandle();
		// 得到所有窗口的句柄
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			String handle = it.next();
			if (currenWindowHanler.equals(handle))
				continue;
			driver.switchTo().window(handle);
			logger.info("Switched to window " + driver.getTitle());
			// System.out.println("title,url = "+driver.getTitle()+","+driver.getCurrentUrl());
		}
	}

	/**
	 * close the popupwindow
	 */
	public void closePopUpWindow() {
		driver.close();
		driver.switchTo().window(currenWindowHanler);
	}
    /**
     * get the current windowHandle
     * @return
     */
	private String getcurrentWindowHandle() {
		return driver.getWindowHandle();
	}
	
}