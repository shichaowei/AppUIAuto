package com.wsc.framework.datadriver;

/*
 * 
 * 功能描述：指定特定的列名，以map形式返回
* 创建人：魏士超
* 创建时间：2016年4月22日 下午3:00:00  
* 功能详述： 
* 把excel表以字典的形式返回回来，形式如下 
 * TreeMAP<key,ArrayList<HashMap<String,String>>>
 * 示例：
 * {2017/09/02=[{待还金额=97.50, 借款账单ID=201709020001267807ed4b310a70e4bd49150a5f63d9a770a}], 
 * 2017/09/03=[{待还金额=97.50, 借款账单ID=20170902000334186ffcd861239dc4259be25930d1787f258}], 
 * 2017/10/22=[{待还金额=97.50, 借款账单ID=201709020001267807ed4b310a70e4bd49150a5f63d9a770a}, 
 * {待还金额=97.50, 借款账单ID=20170902000334186ffcd861239dc4259be25930d1787f258}]}
 *  以date正向排序，把该日借款账号id和待还金额返回
* 
 * 
 * 
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ExcelMap {
	
	public TreeMap<String, ArrayList<HashMap<String,String>>> getxlsmap(String xlspath,String sheetname,String keyname,ArrayList<String> paranamelist){
		ExcelDataProvider temp=new ExcelDataProvider(xlspath,sheetname);
		TreeMap<String, ArrayList<HashMap<String,String>>> result=new TreeMap<String, ArrayList<HashMap<String,String>>>();
		
		//根据keyname(对应excel列名)，找到此列名对应的所有内容(去除重复)
		HashSet<String> keyset=new HashSet();
		while(temp.hasNext()){
			Map<String,String> excelcell=(Map<String,String>)(temp.next()[0]);
			//去除key为空的情况
			if(!excelcell.get(keyname).isEmpty()){
				keyset.add(excelcell.get(keyname));
			}
			
		}
		
		Iterator<String> keycontents = keyset.iterator();
		while(keycontents.hasNext()){
			String key=keycontents.next();
			ArrayList middleUnit=new ArrayList();
			ExcelDataProvider xlscontents=new ExcelDataProvider(xlspath,sheetname);
			
			while(xlscontents.hasNext()){
				Map<String,String> rowUnit=(Map<String,String>)(xlscontents.next()[0]);
				
				if(key.equals(rowUnit.get(keyname))){
					HashMap<String,String> miniunit=new HashMap<String,String>();
					for(int i=0;i<paranamelist.size();i++){
						
						//unitmirco.put(excelUnit.get("借款账单ID"), excelUnit.get("待还金额"));
						miniunit.put((String) paranamelist.get(i),rowUnit.get(paranamelist.get(i)));
					}
					middleUnit.add(miniunit);
					result.put(key,middleUnit);
					
				}
			}
		}
		return result;
	}
	

}
