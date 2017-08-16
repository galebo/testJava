package com.galebo.test.threadLoacl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestThreadLocal {
	

	public static class TestDao {  
	    @SuppressWarnings("unused")
		private Connection conn;// ①一个非线程安全的变量  
	  
	    public void addTopic() throws SQLException {  
	        //Statement stat = conn.createStatement();// ②引用非线程安全变量  
	        // …  
	    }  
	}  
	public static class TestDaoNew {  
	    // ①使用ThreadLocal保存Connection变量  
	    private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<Connection>();  
	  
	    public static Connection getConnection() {  
	        // ②如果connThreadLocal没有本线程对应的Connection创建一个新的Connection，并将其保存到线程本地变量中。  
	        if (connThreadLocal.get() == null) {  
	            Connection conn = getNewConnection();  
	            connThreadLocal.set(conn);  
	            return conn;  
	        } else {
	        	// ③直接返回线程本地变量 
	            return connThreadLocal.get(); 
	        }  
	    }  
	  
	    private static Connection getNewConnection() {
			//自己定义
			return null;
		}

		public void addTopic() throws SQLException {  
	        // ④从ThreadLocal中获取线程对应的Connection  
	       // Statement stat = getConnection().createStatement();  
	    }  
	}
	
	public static class ConnectionManager {  
		  
	    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {  
	        @Override  
	        protected Connection initialValue() {  
	            Connection conn = null;  
	            try {  
	                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "username", "password");  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	            return conn;  
	        }  
	    };  
	  
	    public static Connection getConnection() {  
	        return connectionHolder.get();  
	    }  
		public void addTopic() throws SQLException {  
	        // ④从ThreadLocal中获取线程对应的Connection  
	       // Statement stat = getConnection().createStatement();  
	    }  
	}
}
