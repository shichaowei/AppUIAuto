package TestBase;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XpathTools {

	
	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		// 解析文件，生成document对象
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = builder.parse(new File("resource/xml/test.xml"));

		// 生成XPath对象
		XPath xpath = XPathFactory.newInstance().newXPath();

//		// 获取节点值
//		String webTitle = (String) xpath.evaluate("//node[@class='android.widget.ImageView']", document,
//				XPathConstants.STRING);
//		System.out.println(webTitle);

//		System.out.println("===========================================================");
//
//		// 获取节点属性值
//		String webTitleLang = (String) xpath.evaluate("/bookstore/book[@category='WEB']/title/@lang", document,
//				XPathConstants.STRING);
//		System.out.println(webTitleLang);
//
//		System.out.println("===========================================================");
//
		// 获取节点对象
		Node bookWeb = (Node) xpath.evaluate("//node[@class='android.widget.ImageView' and @resource-id='com.wuba.zhuanzhuan:id/a4w']", document, XPathConstants.NODE);
		System.out.println(bookWeb.getNodeName());

		System.out.println("===========================================================");

		// 获取节点集合
		NodeList books = (NodeList) xpath.evaluate("//node[@class='android.widget.ImageView']", document, XPathConstants.NODESET);
		for (int i = 0; i < books.getLength(); i++) {
			Node book = books.item(i);
			System.out.println(xpath.evaluate("@resource-id", book, XPathConstants.STRING));
		}

		System.out.println("===========================================================");
	}

}
