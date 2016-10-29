package com.wsc.framework.constant;

/**
 * JS函数类
 * @author WSC<br/>
 * 
 * 
 */
public class JSFunction {

	public JSFunction() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 判断JQuery库是否成功加载，如果未加载成功返回字符串“noJQuery”
	 */
	public static final String isJQueryLoad = 
			"if(typeof jQuery =='undefined'){"+
			    "return 'noJQuery';"+
			"}";
	
	/**
	 * 根据给定的节点名称，返回给定数组中包含该节点名称的数组
	 * @param arr 元素数组
	 * @param name 节点名称
	 * @returns 包含指定节点名称的元素数组
	 */
	public static final String getArrByNodeName = 
			"function getArrByNodeName(arr,name){"+
				"var myarr = [];"+
				"var sum = 0;"+
				"for(var i=0;i<arr.length;i++){"+
					"if(arr[i].nodeName == name){"+
						"myarr[sum] = arr[i];"+
						"sum++;"+
					"}"+
				"}"+
				"return myarr;"+
			"}";
	
	/**
	 * 合并数组：这里显示参数为零个，但可以看成是可变长参数：可以传入一个多或多个数组参数。
	 */
	public static final String mergeArray = 
			"function mergeArray(){"+
				"var arr = [];"+
				"var count = 0;"+
				"for(var i=0; i<arguments.length; i++){"+//遍历参数：多个数组
					"for(var j=0; j<arguments[i].length; j++){"+//将每个数组中的元素添加到新数组
						"arr[count] = arguments[i][j];"+
						"count++;"+
					"}"+
				"}"+
				"return arr;"+
			"}";

	/**
	 * 自定义js数组方法：数组中是否包含指定元素<br/>
	 * 如果包含返回True，否则返回False
	 */
	public static final String contains =
			"Array.prototype.contains=function(str){"+
				"for(var i = 0; i<this.length;i++){"+
					"if(this[i]==str){"+
						"return true;"+
					"}"+
				"}"+
				"return false;"+
			"};";
	
	/**
	 * 将一个数组追加到调用数组中，并返回追加后的数组
	 */
	public static final String append = 
			"Array.prototype.append=function(arr){"+
				"var len = this.length;"+
				"for(var i=0; i<arr.length; i++){"+
					"this[len+i] = arr[i];"+
				"}"+
				"return this;"+
			"};";
	
	/**
	 * 自定义String方法：trim。去除字符串前后空白字符。
	 */
	public static final String trim = 
			"String.prototype.trim=function(){"+
				"return this.replace(/(^\\s*)|(\\s*$)/g, \"\");"+
			"};";
	
	/**
	 * 自定义String方法：ltrim。去除字符串左边空白字符。
	 */
	public static final String ltrim =
			"String.prototype.ltrim=function(){"+
				"return this.replace(/(^\\s*)/g,\"\");"+
			"};";			
			
	/**
	 * 自定义String方法：rtrim。去除字符串右边空白字符。
	 */
	public static final String rtrim = 
			"String.prototype.rtrim=function(){"+
				"return this.replace(/(\\s*$)/g,\"\");"+
			"};";	
	
	/**
	 * 获取参数object文档内的所有复选框对象，返回复选框对象数组
	 */
	public static final String getCheckBoxs = 
			"function getCheckBoxs(obj){"+
				"return $(obj).find(\"input[type='checkbox']\");"+
			"}";
	
	/**
	 * 勾选复选框
	 * @param inp 复选框的JS对象 
	 */
	public static final String check = 
			"function check(inp){"+
				"if(!inp.status){"+
					"inp.click();"+
				"}"+
			"}";

	/**
	 * 复选框去勾选
	 * @param inp 复选框的JS对象 
	 */
	public static final String uncheck = 
			"function uncheck(inp){"+
				"if(inp.status){"+
					"inp.click();"+
				"}"+
			"}";
	
}
