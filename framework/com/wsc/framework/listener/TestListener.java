package com.wsc.framework.listener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.wsc.framework.control.AppEmulator;
import com.wsc.framework.utils.ScreenshotUtil;

/**
 * Test方法监听器
 * @author wsc
 * 
 */
public class TestListener extends TestListenerAdapter {

	private static Logger logger = Logger.getLogger(TestListener.class);				//日志
	
	public TestListener() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 测试方法执行前的处理代码
	 */
	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
	}
	
	/**
	 * 测试失败时执行的操作
	 */
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		//失败截图-判断是否具有APPEmulator对象
		Object testcase = tr.getInstance();							//获取testng类对象
		Field[] fields = testcase.getClass().getDeclaredFields();	//获取该类所有属性，包括私有的
		List<Field> bes = new ArrayList<Field>();					//所有声明类型为BrowserEmulator的属性
		for(Field field : fields){									//遍历查找所有声明类型为BrowserEmulator的属性
			boolean isAccessible = field.isAccessible();
			field.setAccessible(true); 	//设置些属性是可以访问的
			if(field.getType().equals(AppEmulator.class)){
				bes.add(field);
			}
			field.setAccessible(isAccessible);
		}
		if(bes.size() == 0){
			logger.error("截图失败，类【"+testcase.getClass().getName()+"】中没有声明类型为AppEmulator的属性！");
			return;
		}else if(bes.size() > 1){
			logger.error("截图失败，类【"+testcase.getClass().getName()+"】中声明类型为AppEmulator的属性存在"+bes.size()+"个，应该有且仅有一个！");
			return;
		}
		//失败截图-获取AppEmulator对象
		AppEmulator be = null;
		try {
			Field field_be = bes.get(0);
			boolean isAccessible = field_be.isAccessible();
			field_be.setAccessible(true);
			be = (AppEmulator) field_be.get(testcase);
			field_be.setAccessible(isAccessible);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//失败截图-提示信息，截图处理
		String limitMethodName = tr.getInstanceName() + "." + tr.getName();		//限定方法名=限定类名.方法名
		//错误日志
		logger.error("方法["+limitMethodName+"]执行失败",tr.getThrowable());
		//失败截图
		ScreenshotUtil.screenshot(be,tr.getName());
	}

}
