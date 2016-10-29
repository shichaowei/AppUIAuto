package com.wsc.framework.datadriver;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.testng.Assert;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**   
* 功能描述：把指定特定的列名，以map形式返回的对象，转换为迭代器 
* 创建人：魏士超
* 创建时间：2016年4月25日 上午10：00  
*  
* Excel放在Data文件夹下</p>
* Excel命名方式：项目名/测试类名.xls</p>
* 
* 
*
*/

public class ExcelMapProvider implements Iterator<Object[]>{

	//map的keyset索引
    private int      currentkeysetindex = 0;
    //map的keyset大小
    private int      keynum=0;
    //map中的list对象的索引
    private int      currentlistinmapindex=0;
    //map中的list所含对象的数量
    private int      listinmapnum=0;
    //返回的treemap（包含excel内容）
	private TreeMap<String, ArrayList<HashMap<String,String>>> xlsmapdata;
	private Set<String> keyset;
	//map中的key列表
	private ArrayList<String> keylist=new ArrayList<String>();
	private ArrayList<String> paranamelist =new ArrayList<String>();
	
	 public ExcelMapProvider(String excelpath,String sheetname,String keyname,ArrayList<String> paranamelist) {
		 	ExcelMap datamap=new ExcelMap();
		 	xlsmapdata=datamap.getxlsmap(excelpath,sheetname, keyname, paranamelist);
		 	//keyset顺序为从小到大
		 	keyset=xlsmapdata.keySet();
		 	for(String keytemp:keyset){
		 		System.out.println(keytemp);
		 		keylist.add(keytemp);
		 	}
			keynum=keyset.size();
	    }

	    public boolean hasNext() {
	    	if(this.keynum==0||this.currentkeysetindex>=this.keynum){
	    		return false;
	    	}
	    	else{
	    		return true;
	    	}
	    	
		}

		public Object[] next() {
			HashMap data=new HashMap();
			listinmapnum=xlsmapdata.get(keylist.get(currentkeysetindex)).size();
			if(this.currentlistinmapindex<listinmapnum){
				data=xlsmapdata.get(keylist.get(currentkeysetindex)).get(this.currentlistinmapindex);
				//System.out.println(data);
				this.currentlistinmapindex++;
				if(this.currentlistinmapindex>=listinmapnum){
					this.currentkeysetindex++;
					this.currentlistinmapindex=0;
				}
			}
			
	        Object object[] = new Object[1];
	        object[0] = data;
	        return object;
	    }

}
