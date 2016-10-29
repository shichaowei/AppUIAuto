package WeixinFengdai;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.wsc.framework.control.AppEmulator;
import com.wsc.framework.utils.XMLUtil;

import zhuanzhuanHomePage.homepage;

public class TestLogin {
	AppEmulator be;
	@Test
	public void testzhuanzhuan(){
		new homepage(be).actClickCycle();
		if(!be.isTextPresent("sss")){
			be.handleFailure("sss不存在", null);
		}
	}
	
	@Parameters({"type","deviceinfo","appname"})
	@BeforeMethod
	public void beforemethod(int type,String deviceinfo,String appname){
		be= new AppEmulator(type,deviceinfo,appname);
	}
	@AfterMethod
	public void aftermethod(){
		be.quit();
	}
	
	@Parameters({"deviceinfo","apppackage"})
	@AfterTest
	public void aftertest(String deviceinfo,String apppackage) throws Throwable{
		String cmd="cmd /c adb -s "+XMLUtil.parseDeviceXML(deviceinfo).get("udid")+" uninstall "+apppackage;
		System.out.println(cmd);
		Runtime.getRuntime().exec(cmd);
	}

}
