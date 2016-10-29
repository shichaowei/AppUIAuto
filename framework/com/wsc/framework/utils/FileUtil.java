package com.wsc.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件操作工具类
 * @author WSC
 * @date 2016年1月30日下午12:36:45
 */
public class FileUtil {

	public FileUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取当前工程的绝对目录
	 * @return 当前工程的绝对目录
	 */
	public static String getProjectPath(){
		return System.getProperty("user.dir");
	}
	
	/**
	 * 删除指定路径下的所有文件夹和文件，除了指定的文件夹或文件
	 * @param old 要删除的文件夹或文件
	 * @param names 要保留的文件夹和文件名称
	 */
	public static void deleteFileOrFolder(File old,String... names){
		Set<String> set = new HashSet<String>();
		for(String str:names){
			set.add(str);
		}
		if(old.isDirectory()){
			for(String filename:old.list()){
				if(set.contains(filename)){
					continue;
				}
				File file = new File(old,filename);
				if(file.isFile()){
					file.delete();
				}else{
					deleteFileOrFolder(file,names);
				}
			}
			old.delete();
		}else{
			old.delete();
		}
	}
	
	/** 
     * 复制一个目录的子目录、文件到另外一个目录 
     * @param src 源文件
     * @param dest 目标文件
     * @throws IOException IO异常 
     */  
    public static void copyFolder(File src, File dest) throws IOException {  
        if (src.isDirectory()) {  
            if (!dest.exists()) {  
                dest.mkdir();  
            }  
            String files[] = src.list();  
            for (String file : files) {  
                File srcFile = new File(src, file);  
                File destFile = new File(dest, file);  
                // 递归复制  
                copyFolder(srcFile, destFile);  
            }  
        } else {  
            InputStream in = new FileInputStream(src);  
            OutputStream out = new FileOutputStream(dest);  
            byte[] buffer = new byte[1024];  
            int length;  
            while ((length = in.read(buffer)) > 0) {  
                out.write(buffer, 0, length);  
            }  
            in.close();  
            out.close();  
        }  
    } 
    
    /**
     * 读取文件内容，并按行将其拼接成可直接赋值给String变量的字符串格式。<br/>
     * 示例：<br/><code>
     * <strong>code.txt文件内容为：</strong><br/>&nbsp;&nbsp;
     * var a = "hello";<br/>&nbsp;&nbsp;
     * alert(a);<br/>
     * <strong>方法调用结果：</strong><br/>&nbsp;&nbsp;
     * String str = convertJSToJava("D:\\MyTest\\code.txt");<br/>&nbsp;&nbsp;
     * //返回值str为：<br/>&nbsp;&nbsp;
     * "var a = \"hello\";"+<br/>&nbsp;&nbsp;
	 * "alert(a);";
     * </code>
     * @param filePath 要进行格式化的文件路径
     * @param encode 文件的编码格式，默认为UTF-8
     * @return	格式化后的字符串
     */
    public static String convertJSToJava(String filePath,String... encode){
    	StringBuffer result = new StringBuffer();
    	File file = new File(filePath);
    	String encoding = null;
    	if(encode.length == 1){
    		encoding = encode[0];
    	}else{
    		encoding = "UTF-8";
    	}
        if(file.isFile() && file.exists()){ 	//判断文件是否存在
            InputStreamReader read = null;
			try {
				read = new InputStreamReader(new FileInputStream(file),encoding);	//考虑到编码格式
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            StringBuffer sbtmp = new StringBuffer();
            try {
				while((lineTxt = bufferedReader.readLine()) != null){	//如果还有内容未读取
					if(!lineTxt.trim().isEmpty()){						//删除空行
						/* 这里用四个反斜杠\\\\表示实际输出效果\：
						 * 因为【\】在正则表达式和java中都需要转义，\\\\经过正则表达式转义后变为\\，再经过java转义后变为\ */
						sbtmp.append(lineTxt.replaceAll("\"", "\\\\\""));					//将一行文本赋值给sbtmp
						sbtmp.insert(StringUtil.indexOfNotChar(lineTxt, '\t',' '), "\"");	//格式化行的左边：加上"
						int indexOfDoubleSlash = sbtmp.toString().indexOf("//");			//格式化行的右边：加上"+
						if(indexOfDoubleSlash != -1){
							sbtmp.insert(indexOfDoubleSlash, "\"+");				
						}else{
							sbtmp.append("\"+");
						}
					    result.append(StringUtil.rTrim(sbtmp.toString())+"\n");				//格式化行的右边：加上换行
					    sbtmp.delete(0, sbtmp.length());				//清空sbtmp
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
            try {
				read.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
        }else{
            throw new RuntimeException("系统中找不到指定的文件【"+filePath+"】");
        }
    	return result.substring(0, result.lastIndexOf("+")).concat(";");			//删除最后一个多余的加号及其后的内容
    }
    
    /**
     * 读取文件内容，并将字符串拼接的JS代码格式化为JS代码。<br/>
     * 示例：<br/><code>
     * <strong>code.txt文件内容为：</strong><br/>&nbsp;&nbsp;
     * "var a = 'hello';"+<br/>&nbsp;&nbsp;
     * "alert(a);";<br/>
     * <strong>方法调用结果：</strong><br/>&nbsp;&nbsp;
     * String str = convertJSToJava("D:\\MyTest\\code.txt");<br/>&nbsp;&nbsp;
     * //返回值str为：<br/>&nbsp;&nbsp;
     * var a = 'hello';<br/>&nbsp;&nbsp;
	 * alert(a);
     * </code>
     * @param filePath 要进行格式化的文件路径
     * @param encode 文件的编码格式，默认为UTF-8
     * @return	格式化后的字符串
     */
    public static String convertJavaToJS(String filePath,String... encode){
    	StringBuffer result = new StringBuffer();
    	File file = new File(filePath);
    	String encoding = null;
    	if(encode.length == 1){
    		encoding = encode[0];
    	}else{
    		encoding = "UTF-8";
    	}
        if(file.isFile() && file.exists()){ 	//判断文件是否存在
            InputStreamReader read = null;
			try {
				read = new InputStreamReader(new FileInputStream(file),encoding);	//考虑到编码格式
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            StringBuffer sbtmp = new StringBuffer();
            try {
				while((lineTxt = bufferedReader.readLine()) != null){	//如果还有内容未读取
					if(!lineTxt.trim().isEmpty()){						//删除空行
						/* 这里用四个反斜杠\\\\表示实际输出效果\：
						 * 因为【\】在正则表达式和java中都需要转义，\\\\经过正则表达式转义后变为\\，再经过java转义后变为\ */
						sbtmp.append(lineTxt.replaceAll("\\\\\"", "\""));			//将一行文本赋值给sbtmp
						int start = StringUtil.indexOfNotChar(lineTxt, ' ','\t');	//格式化行的左边
						sbtmp.deleteCharAt(start);
						int end = sbtmp.lastIndexOf("\"+");
						if(end == -1){
							end = sbtmp.lastIndexOf("\";");
						}
					    result.append(sbtmp.toString().substring(0,end)+"\n");		//格式化行的右边
					    sbtmp.delete(0, sbtmp.length());				//清空sbtmp
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
            try {
				read.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
        }else{
            throw new RuntimeException("系统中找不到指定的文件【"+filePath+"】");
        }
    	return result.toString();			//删除最后一个多余的加号及其后的内容
    }
    
}
