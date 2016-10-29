package com.wsc.framework.control;

import org.openqa.selenium.WebElement;

/**
 * 自定义控件：输入框（包括文本输入框、日期时间控件等）。<br/>
 * <strong>控件类型：</strong>单标签控件（input）、多标签控件（日期控件等）<br/>
 * <strong>控件简写：</strong>inb<br/>
 * <strong>定位方式：</strong>
 * ID、CLASS_NAME、CSS、NAME、XPATH。<br/>
 * @author wsc
 * 创建日期：2016年3月14日<br/>
 * 修改日期：2016年6月30日
 */
public class InputBox extends BaseControl {

	/**
	 * 构造器(用于通过反射创建对象)：传入输入框的定位内容
	 */
	public InputBox(AppEmulator be,String how) {
		this.be = be;
		this.driver = be.getAppDriver();
		this.how = how;
	}
	
	/**
	 * 构造器(用于直接创建对象)：传入输入框对象
	 */
	public InputBox(AppEmulator be,WebElement element) {
		this.be = be;
		this.driver = be.getAppDriver();
		this.element = element;
		this.isWebElement = true;
	}

	/**
	 * 向输入框中输入字符串
	 * @param keysToSend 要输入的字符串
	 */
	public void sendKeys(String keysToSend){
		logger.info("[开始："+getObjectName()+"#sendKeys]输入文本["+keysToSend+"]。"+getFiledName());
		if(keysToSend == null || keysToSend.equals(NO_OPERATE_KEY)){
			return;
		}
		setWebElement();
		boolean isonly = Boolean.parseBoolean(getAttribute("readonly"));
		if(isonly){
			removeAttribute("readonly");
		}
		clear_();
		clear_();	//增强稳定性，某些情况下，第一次clear只聚焦不清空
		if(!keysToSend.isEmpty()){
			sendKeys_(keysToSend);
		}
		if(isonly){
			setAttribute("readonly", "readonly");
		}
		logger.info("[结束："+getObjectName()+"#sendKeys]输入文本["+keysToSend+"]。");
	}
	
	/**
	 * 上传文件
	 * @param keysToSend 文件路径
	 */
	public void upload(String keysToSend){
		logger.info("[开始："+getObjectName()+"#upload]上传文件，路径为["+keysToSend+"]。"+getFiledName());
		if(keysToSend == null || keysToSend.equals(NO_OPERATE_KEY)){
			return;
		}
		setWebElement();
		if(!keysToSend.isEmpty()){
			sendKeys_(keysToSend);
		}
		logger.info("[结束："+getObjectName()+"#upload]上传文件，路径为["+keysToSend+"]。");
	}
	
	/**
	 * 获取input标签中的value值
	 * @return value值
	 */
//	public String getText(){
//		setWebElement();
//		return element.getAttribute("value");
//	}
}
