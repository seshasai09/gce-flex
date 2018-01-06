package com.example;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class SQLUtil {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gecspring";
	
	private static final String USER = "test123";
	
	private static final String PASSWORD = "test1234";
	
	private static GenericObjectPool gPool = null;
	
	private static DataSource ds;
	
	private static SQLUtil instance;
	private SQLUtil(){
		
	}
	
	public static DataSource getInstance(){
		try{
		if(instance==null){
			instance = new SQLUtil();
			instance.setupPool();
		}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return ds;
	}

	
	private void setupPool() throws ClassNotFoundException{
		
		Class.forName(JDBC_DRIVER);
		gPool = new GenericObjectPool();
		gPool.setMaxActive(5);
		
		ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_URL, USER, PASSWORD);
		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
		ds=new PoolingDataSource(gPool);


		
	}
	
	

}
