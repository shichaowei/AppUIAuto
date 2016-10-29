package zhuanzhuanHomePage;

import org.openqa.selenium.support.PageFactory;

import com.wsc.framework.annotation.Find;
import com.wsc.framework.annotation.Find.Locator;
import com.wsc.framework.control.AppEmulator;
import com.wsc.framework.control.Link;
import com.wsc.framework.support.PageFactoryAppAT;

public class homepage {
	
	protected AppEmulator be;
	
	
	@Find(how=Locator.XPATH,xpath="//android.widget.ImageView[contains(@index,'4')]")
	public Link wo;
	@Find(how=Locator.ID,id="com.wuba.zhuanzhuan:id/a4w")
	public Link message;
	
	
	
	public homepage(AppEmulator be){
		this.be=be;
		PageFactory.initElements(be.getAppDriver(), this);
		PageFactoryAppAT.initElements(be, this);
	}
	
	public void actClickCycle(){
			wo.click();
	}
	
	
	
	public static void main(String[] args){
//		AppEmulator be= new AppEmulator(1);
//		System.out.println(be.getNetworkConnection());
//		new homepage(be).actClickCycle();
//		new HomeBehavior(be).actClickSearch();
//		be.quit();
		
	}

}
