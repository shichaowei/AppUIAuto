package com.wsc.framework.utils;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.wsc.framework.config.ConfigInit;
import com.wsc.framework.control.AppEmulator;



/**
 * 截图工具类
 */
public class ScreenshotUtil {

	public ScreenshotUtil() {
	}
	
	static String dir = "screenshot";	//存放截图文件的文件夹名称
	static String filetype = ConfigInit.config.getProperty("screenshot_file_extension");	//截图文件后缀名
	
	private static Logger logger = Logger.getLogger(ScreenshotUtil.class);				//日志
	

	

    
    /**
     * 通过{@link TakesScreenshot#getScreenshotAs(OutputType)}方法截图，截取驱动所在浏览器的当前内容，用于ReportNG。
     * @param be 
     */
    public static void screenshot(AppEmulator be){
    	screenshot(be, "");
    }
    
    /**
     * 通过{@link TakesScreenshot#getScreenshotAs(OutputType)}方法截图，截取驱动所在浏览器的当前内容，用于ReportNG。
     * @param be 
     * @param name 截图文件名称，实际名称为：name+时间戳+文件后缀名。
     */
    public static void screenshot(AppEmulator be,String name) {
    	//参数设置
		String format = null;
		if(name.trim().equals("") || name == null){
			format = "yyyyMMdd-HHmmss";
		}else{
			format = "-yyyyMMdd-HHmmss";
		}
		//截图文件名称
		String fileName = name+DateUtil.formatDate(format,new Date())+"-"+getRandomNum()+"."+filetype;
		//截图文件存放路径
		String filePath = FileUtil.getProjectPath()+File.separator+"test-output"+File.separator+dir;
		//截图文件完整路径
		String path = filePath +File.separator +fileName;
		//获取WebDriver对象
		WebDriver driver = be.getAppDriver();
		
		//截图
		try {
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String src = "../"+dir+"/"+fileName;
		//这里实现把图片链接直接输出到结果文件中
		Reporter.log("<img src=\"" + src + "\" alt=\""+fileName+"\"/>");
		System.out.println(path);
		logger.info("截图成功，文件路径为："+path);
	}
	
    //获取三位随机整数
    private static String getRandomNum(){
    	return MathUtil.subNumberToStr(MathUtil.random(100, 999), 0);
    }
    
}
