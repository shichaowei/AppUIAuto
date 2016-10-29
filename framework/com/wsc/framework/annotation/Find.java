package com.wsc.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元素定位注解，与Selenium的FindBy注解类似。枚举值必须全大写,方法名必须全小写。<BR/>
 * (1)how默认方式，对应构造器参数：(WebDriver driver)，如：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <code>@Find(how=Locator.DEFAULT)</code><BR/>
 * (2)how只有一个参数，对应构造器参数：(WebDriver driver,String str)如：<BR/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>@Find(how=Locator.ID,id="idname")</code>
 * @author WSC
 * 
 */
@Retention(RetentionPolicy.RUNTIME) //注解的保留位置:注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented //注解将被包含在javadoc中
@Target({ElementType.TYPE,ElementType.FIELD}) //注解的作用目标
public @interface Find {
	
	
	/**
	 * HTML页面元素定位方式枚举类
	 * @author LY
	 * @date 2016年3月17日上午9:03:00
	 */
	public enum Locator{
		
		/**缺省定位，没有定位参数，对应构造器参数为：(WebDriver driver)*/
		DEFAULT,
		
		/**
		 * 根据HTML标签的ID属性定位<br/>
		 * <strong>对应的方法：</strong><code>id()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.ID,id="XXX")</code><BR/>
		 */
		ID,	
		
		/**
		 * 根据HTML标签的NAME属性定位<br/>
		 * <strong>对应的方法：</strong><code>name()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.NAME,name="XXX")</code><BR/>
		 */
		NAME,
		
		/**
		 * 根据HTML标签的CLASS属性定位，定位时只能使用一个Class<br/>
		 * <strong>对应的方法：</strong><code>class_()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.CLASS_,class_="XXX")</code><BR/>
		 */
		CLASS_,	
		
		/**
		 * 根据CSS选择器定位<br/>
		 * <strong>对应的方法：</strong><code>css()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.CSS,css="XXX")</code><BR/>
		 */
		CSS,	
		
		/**
		 * 根据XPath定位<br/>
		 * <strong>对应的方法：</strong><code>xpath()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.XPATH,xpath="XXX")</code><BR/>
		 */
		XPATH,

		/**
		 * 根据HTML的超链接定位<br/>
		 * <strong>对应的方法：</strong><code>link_text()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.ID,id="XXX")</code><BR/>
		 */
		LINK_TEXT,
		
		
		/**
		 * 根据HTML的超链接定位 定位文本为要定位的链接文本子集<br/>
		 * <strong>对应的方法：</strong><code>link_text()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.ID,id="XXX")</code><BR/>
		 */
		PARTIALLINK_TEXT,
		
		
		
		
		//以下两种方式为扩展定位方式，针对无法通过正常（以上）定位方式进行定位而使用。
		/**
		 * 通过JavaScript脚本定位，脚本中写控件的定位代码。该定位方式也需要配合控件的方法来使用。<br/>
		 * <strong>对应的方法：</strong><code>js()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.JS,js="XXX")</code><BR/>
		 */
		JS,
		
		/**
		 * 通过路径（多参数）定位，参数间使用左斜杠【/】来分隔。配合控件的方法来使用这些参数，即每个控件有自己特定的路径格式。<br/>
		 * 路径（多参数）：是指一个控件需要多个参数来共同定位，如表格中的控件。<br/>
		 * 针对表格中的控件的使用语法，举例如下：<br/>&nbsp;&nbsp;&nbsp;&nbsp;
		 *     表格id/定位单元格内容/定位单元格属性或其他定位内容/编辑单元格属性或其他定位内容<br/>
		 * <strong>对应的方法：</strong><code>path()</code><br/>
		 * 示例：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 * <code>@Find(how=Locator.PATH,path="XXX")</code><BR/>
		 */
		PATH
		
	};
	
	/**
	 * 该方法用来说明要使用哪种定位方式
	 * @return Locator类枚举值
	 */
	Locator how();

	String id() default "";
	
	String name() default "";
	
	String class_() default "";
	
	String css() default "";
	
	String xpath() default "";

	String link_text() default "";
	
	String js() default "";
	
	String path() default "";
	
	String partiallink_text() default "";
	
}
