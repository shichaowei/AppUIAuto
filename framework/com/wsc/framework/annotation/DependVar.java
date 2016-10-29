package com.wsc.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控件依赖注解，指明该控件依赖哪一个控件。
 * @author WSC<br/>
 */
@Retention(RetentionPolicy.RUNTIME) //注解的保留位置:注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented //注解将被包含在javadoc中
@Target({ElementType.TYPE,ElementType.FIELD}) //注解的作用目标
public @interface DependVar {

	/**
	 * 依赖的控件名称。<br/>
	 * 使用示例：<br/>
	 * <pre class="code">
	 * &#64;Find(how=Locator.ID,id="pnaone")
	 * public PNA pna;
	 * 
	 * &#64;DependVar(var="pna")
	 * &#64;Find(how=Locator.ID,id="tblone")
	 * public Table tbl;
	 * </pre>
	 */
	String var();
	
}
