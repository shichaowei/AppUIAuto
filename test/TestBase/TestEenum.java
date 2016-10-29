package TestBase;

import java.lang.annotation.Annotation;

import com.wsc.framework.annotation.Find;
import com.wsc.framework.annotation.Find.Locator;

public class TestEenum {

	
	
	public static void main(String argv[]){
		Find test =new Find() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String xpath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String path() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String partiallink_text() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String name() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String link_text() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String js() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String id() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locator how() {
				// TODO Auto-generated method stub
				return Locator.ID;
			}
			
			@Override
			public String css() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String class_() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		System.out.println(test.how().name().toString());
	}
	
	
}
