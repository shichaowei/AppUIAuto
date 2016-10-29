package com.wsc.framework.utils;

import java.util.HashSet;

public class StringUtil {

	public StringUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 格式化字符串，使其具有固定字符长度，不够指定长度在右边填充空格
	 * @param value 要格式化的字符串
	 * @param length 格式化后的固定字符长度
	 * @param type 填充空格类型。1-全角空格，2-半角空格。
	 * @return 固定字符长度的字符串
	 * @throws ParameterException 参数异常
	 */
	public static String formatStringByLength(String value,int length,int type){
		if(value==null){
			throw new IllegalArgumentException("参数value不能为null！");
		}else if(length<1){
			throw new IllegalArgumentException("参数length必须大于等于1！");
		}else if(value.length() > length){
			throw new IllegalArgumentException("参数value的字符长度不能大于指定字符长度！");
		}
		if(type==1){
			return value+space1(length-value.length());
		}else if(type==2){
			return value+space2(length-value.length());
		}else{
			throw new IllegalArgumentException("参数type的值只能为1或2！");
		}
	}
	
	/**
	 * 返回由指定数目的全角空格组成的字符串(注：一个全角空格和一个中文汉字占位等长)
	 * @param len 指定空格个数
	 * @return 由指定数目的全角空格组成的字符串
	 */
	public static String space1(int len){
		StringBuffer sb = new StringBuffer("");
		for(int i=1;i<=len;i++){
			sb.append("　");
		}
		return sb.toString();
	}
	
	/**
	 * 返回由指定数目的半角空格组成的字符串(注：一个半角空格和一个英文字母占位等长)
	 * @param len 指定空格个数
	 * @return 由指定数目的半角空格组成的字符串
	 */
	public static String space2(int len){
		StringBuffer sb = new StringBuffer("");
		for(int i=1;i<=len;i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 返回给定字符串中第一次出现非指定字符（可以是多个）的索引位置。如果字符串中都是指定字符，将返回-1。<br/>
	 * 示例：<br/>
	 * <code>&nbsp;&nbsp;
	 * int t1 = StringUtil.indexOfNotChar("uuu4", 'u');&nbsp;&nbsp;&nbsp;&nbsp;//返回值为t1=3<br/>&nbsp;&nbsp;
	 * int t2 = StringUtil.indexOfNotChar("uuuu", 'u');&nbsp;&nbsp;&nbsp;&nbsp;//返回值为t2=-1<br/>&nbsp;&nbsp;
	 * int t3 = StringUtil.indexOfNotChar("uabpp", 'u','a','b');&nbsp;&nbsp;&nbsp;&nbsp;//返回值为t2=3
	 * </code>
	 * @param str 要查找的字符串
	 * @param c 指定的字符
	 * @return 给定字符串中第一次出现非指定字符的索引位置
	 */
	public static int indexOfNotChar(String str,char... c){
		HashSet<Character> set = new HashSet<Character>();
		for(char ci : c){						//将指定字符数组放入集合中
			set.add(ci);
		}
		for(int i=0;i<str.length();i++){		//遍历给定字符串的每个字符
			if(!set.contains(str.charAt(i))){	//如果第一次出现非指定字符，返回该字符的索引
				return i;
			}
		}
		return -1;								//如果字符串由指定字符组成，则返回-1
	}
	
	/**
	 * 去除字符串左边的空白。如果参数为null，则返回空字符串。<br/>
	 * 示例：<br/>
	 * <code>&nbsp;&nbsp;
	 * String str = StringUtil.lTrim("\t\r\n   a b c");		//返回值str="a b c"
	 * </code>
	 * @param str 给定的字符串
	 * @return 此字符串移除了左边空白的副本
	 */
	public static String lTrim(String str){
		if(str == null) return "";
		/*
		 * 【^】：匹配输入字符串的开始位置。如果设置了RegExp对象的Multiline属性，^也匹配“\n”或“\r”之后的位置。
		 * 【\s】：匹配任何不可见字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。
		 * 【*】：匹配前面的子表达式任意次。例如，zo*能匹配“z”，“zo”以及“zoo”，但是不匹配“bo”。*等价于{0,}。
		 */
		return str.replaceAll("^\\s*", "");
	}
	
	/**
	 * 去除字符串右边的空白。如果参数为null，则返回空字符串。<br/>
	 * 示例：<br/>
	 * <code>&nbsp;&nbsp;
	 * String str = StringUtil.rTrim("a b c   \t\r\n");		//返回值str="a b c"
	 * </code>
	 * @param str 给定的字符串
	 * @return 此字符串移除了右边空白的副本
	 */
	public static String rTrim(String str){
		if(str == null) return "";
		/*
		 * 【\s】、【*】参考lTrim方法
		 * $：匹配输入字符串的结束位置。如果设置了RegExp对象的Multiline属性，$也匹配“\n”或“\r”之前的位置。
		 */
		return str.replaceAll("\\s*$", "");
	}
	
}
