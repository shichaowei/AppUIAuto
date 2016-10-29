/**
 * 
 */
package com.wsc.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库连接工具
 * @author WSC
 */
public class DBUtil {
	
	private Connection conn;

	/**
	 * 连接数据库
	 */
	public DBUtil() {
		InputStream is = super.getClass().getClassLoader().getResourceAsStream("db.properties");
		
		Properties p = new Properties();
		
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
		try {
			Class.forName(p.getProperty("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(p.getProperty("jdbc.url"), p.getProperty("jdbc.username"), p.getProperty("jdbc.password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 建立sql语句发送通道
	 * @param sql 执行语句
	 * @return PreparedStatement实例
	 */
	public PreparedStatement getPreparedStatement(String sql){
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}
	
	/**
	 * 资源释放
	 */
	public void close(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <b>调用完该方法，务必记得调用close方法，释放资源。</b><br/>Executes the SQL statement in this PreparedStatement object, 
	 * which must be an SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; 
	 * or an SQL statement that returns nothing, such as a DDL statement.
	 * @param sqls 要执行的SQL语句，如果有多条SQL，用英文分号【;】隔开
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements 
	 * or (2) 0 for SQL statements that return nothing 
	 */
	public int[] executeUpdateSQL(String sqls){
		String[] sqlarr = sqls.split(";");
		//连接数据库
//		PreparedStatement statement = this.getPreparedStatement(sqls);
		Statement stmt = null;
		int[] result = null;							//the row count for DML SQL or 0 for SQL statements that return nothing
		try {
			conn.setAutoCommit(false);		//关闭事务自动提交
			stmt = conn.createStatement();
			for(String sql : sqlarr){
				stmt.addBatch(sql);
			}
			result = stmt.executeBatch();
//			i =statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();			//如果出现异常，回滚事务
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
//		finally{
//			this.close();
//		}
		return result;						//返回执行结果数组
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBUtil db = new DBUtil();
		String sql = "select * from auto_gh";
		PreparedStatement ps = db.getPreparedStatement(sql);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("Key")+"  "+rs.getString("Value"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
	}
	
}
