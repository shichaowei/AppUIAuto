package com.wsc.framework.utils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.api.jdbc.Statement;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.org.apache.xpath.internal.operations.And;
/*
* 功能描述： 操作数据库
* 创建人：魏士超
* 创建时间：2016年4月22日 下午3:00：00  
*  
*  
*  
*  
*/
public class OpsDB {
	private Connection conn = null;
	
	// connect to MySQL  
    void connSQL(String url,String username,String password) {  
        try {   
            Class.forName("com.mysql.jdbc.Driver" );   
            conn = DriverManager.getConnection( url,username, password );   
            }  
        //捕获加载驱动程序异常  
         catch ( ClassNotFoundException cnfex ) {  
             System.err.println(  
             "装载 JDBC/ODBC 驱动程序失败。" );  
             cnfex.printStackTrace();   
         }   
         //捕获连接数据库异常  
         catch ( SQLException sqlex ) {  
             System.err.println( "无法连接数据库" );  
             sqlex.printStackTrace();   
         }  
    }  
  
    // disconnect to MySQL  
    void deconnSQL() {  
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            System.out.println("关闭数据库问题 ：");  
            e.printStackTrace();  
        }  
    }  
    
	
	public Map<String, ArrayList<String>> inquireDB(String url,String loginuser,String passwd,String sql){
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try{
				connSQL(url, loginuser, passwd);
	            PreparedStatement preStat = null;    
	            preStat = conn.prepareStatement(sql);
	            ResultSet rs = preStat.executeQuery();
	            ResultSetMetaData rsMeta = rs.getMetaData();
	            for(int i = 0; i < rsMeta.getColumnCount(); ++i){
				    map.put(rsMeta.getColumnName(i+1), new ArrayList<String>());
				    
			  	}
	            while(rs.next()){
		            for(int i = 0; i < rsMeta.getColumnCount(); ++i){
		            	String columnName = rsMeta.getColumnName(i+1);
		            	//System.out.println(rs.getString(columnName));
		            	map.get(columnName).add(rs.getString(columnName));
		            	}
		            }
	
	            rs.close();
	            preStat.close();
	            deconnSQL();
	            return map;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
		deconnSQL();
		return map;
		
	}
	
	
	 //execute update language  
    public boolean updateSQL(String url,String loginuser,String passwd,String sql) {  
        try {  
        	connSQL(url, loginuser, passwd);
        	PreparedStatement statement = null;    
            statement = conn.prepareStatement(sql);  
            statement.executeUpdate();
            deconnSQL();
            return true;  
        } catch (SQLException e) {  
            System.out.println("插入数据库时出错：");  
            e.printStackTrace();  
        } catch (Exception e) {  
            System.out.println("插入时出错：");  
            e.printStackTrace();  
        }
        deconnSQL();
        return false;  
    }  
    
    

}
