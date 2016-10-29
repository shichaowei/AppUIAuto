package com.wsc.framework.control;

import org.openqa.selenium.WebElement;

import com.wsc.framework.annotation.Find.Locator;

/**
 * 自定义控件：任何可点击的单个标签（包括超链接A标签、Button标签等）。<br/>
 * <strong>控件类型：</strong>单标签控件（a、button等）<br/>
 * <strong>控件简写：</strong>lnk<br/>
 * <strong>定位方式：</strong>
 * ID、CLASS_NAME、CSS、NAME、XPATH。参考:{@link Locator}<br/>
 * @author wsc
 * 创建日期：2016年5月4日<br/>
 * 修改日期：2016年6月30日
 */
public class Link extends BaseControl {

	/**
	 * 构造器(用于通过反射创建对象)：传入按钮的定位内容
	 */
	public Link(AppEmulator be,String how) {
		this.be = be;
		this.driver = be.getAppDriver();
		this.how = how;
	}
	
	/**
	 * 构造器(主要用于直接创建对象)：传入按钮的WebElement对象
	 */
	public Link(AppEmulator be,WebElement element) {
		this.be = be;
		this.driver = be.getAppDriver();
		this.element = element;
		this.isWebElement = true;
	}

	/**
	 * 点击链接
	 */
	public void click(){
		logger.info("[开始："+getObjectName()+"#click]点击。"+getFiledName());
		setWebElement();
		click_();
		logger.info("[结束："+getObjectName()+"#click]点击。");
	}
	
	
	/**
	 * 点击链接
	 */
	public void click(String info){
		logger.info("[开始："+getObjectName()+"#click]点击。"+info);
		setWebElement();
		//clickByJS();
		click_();
		logger.info("[结束："+getObjectName()+"#click]点击。"+info);
	}
	
}
