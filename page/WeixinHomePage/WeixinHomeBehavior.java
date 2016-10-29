package WeixinHomePage;

import org.openqa.selenium.support.PageFactory;

import com.wsc.framework.annotation.Find;
import com.wsc.framework.annotation.Find.Locator;
import com.wsc.framework.control.AppEmulator;
import com.wsc.framework.control.Link;
import com.wsc.framework.support.PageFactoryAppAT;

public class WeixinHomeBehavior {
	protected AppEmulator be;
	@Find(how=Locator.NAME,name="微信")
	public Link weixin;

	@Find(how=Locator.NAME,name="通讯录")
	public Link address;
	
	@Find(how=Locator.NAME,name="发现")
	public Link faxin;
	
	@Find(how=Locator.NAME,name="我")
	public Link wo;
	
	@Find(how=Locator.XPATH,xpath="//android.widget.TextView[contains(@content-desc,'搜索')]")
	public Link search;
	
	public WeixinHomeBehavior(AppEmulator be){
		this.be=be;
		PageFactory.initElements(be.getAppDriver(), this);
		PageFactoryAppAT.initElements(be, this);
	}
	
	public void actClickCycle(){
		address.click();
		faxin.click();
		weixin.click();
	}
	
	public void actClickSearch(){
		be.getAppDriver().findElementByAccessibilityId("搜索").click();
	}
	
	public static void main(String[] args){
		AppEmulator be= new AppEmulator(1,"Xiaomi Mi-4c","weixin_821.apk");
		System.out.println(be.getAndroidNetworkConnection());
//		new HomeBehavior(be).actClickSearch();
//		be.quit();
		
	}
	

}
