package com.wsc.framework.config;

import java.util.Properties;

import com.wsc.framework.utils.PropUtils;
/**
 * 属性文件初始化类
 * @author weisc
 */
public class ConfigInit {

	public static Properties config = PropUtils.getProperties("resource/properties/config");
	public static Properties android = PropUtils.getProperties("resource/properties/android");
	public static Properties imagecheck = PropUtils.getProperties("resource/properties/imagecheck");
	
	
}
