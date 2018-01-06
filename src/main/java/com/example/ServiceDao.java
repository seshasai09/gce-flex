package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;



public class ServiceDao {
	
	static DataSource ds;
	public ServiceDao(){
		ds= SQLUtil.getInstance();
	}
	
	 
	
	public boolean addReqBody(String req){
		String insertSql = "insert into test (name,time) values(?,?)";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1 ,req);
			ps.setObject(2, new Date());
			System.out.println("insersting data");
			return !ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
