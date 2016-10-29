package com.wsc.framework.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	
	private static Logger logger = Logger.getLogger(XMLUtil.class);
	
	/**
	 * 解析db.xml文件，返回Map
	 * @param dbname 数据库名称
	 * @return 包含数据库连接信息的Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> parseDeviceXML(String devicename){
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(FileUtil.getProjectPath()+"\\resource\\xml\\device.xml");
			//获取根元素databases
			Element root = document.getRootElement();
			//获取子元素集合
			List<Element> devices = root.elements("device");
			for(Element device : devices){
				if(device.attribute("devicename").getValue().equals(devicename)){
					for(Attribute attr : device.attributes()){
						map.put(attr.getName(), attr.getValue());
					}
				}
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			logger.info("XML文件解析出错", e1);
		}
		return map;
	}
	

	
}
