package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;



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
	
	public Wrapper<Boolean,Ride> addRide(Wrapper<Boolean,Ride> wrapper){
		String insertSql = "insert into rides (resortId,daynumber,timestamp,skierid,liftid) values(?,?,?,?,?)";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1 ,wrapper.getRequest().getResortId());
			ps.setObject(2,wrapper.getRequest().getDayNumber());
			ps.setObject(3,wrapper.getRequest().getTimeStamp());
			ps.setObject(4,wrapper.getRequest().getSkierId());
			ps.setObject(5,wrapper.getRequest().getLiftId());
			System.out.println("insersting data: "+ps.toString());
			long start = System.nanoTime();
			wrapper.setBody( !ps.execute());
			wrapper.setDbTime(System.nanoTime()-start);
			wrapper.setError(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wrapper.setError(true);
		}
		return wrapper;
	}
	
	public Wrapper<JSONObject, UserVertical> getUserData(Wrapper<JSONObject, UserVertical> wrapper) {
		String selectSql = "select liftid from rides where skierid=? and daynumber=?";
		JSONObject json = null;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement   ps = conn.prepareStatement(selectSql);
			ps.setString(1, wrapper.getRequest().getSkierId());
			ps.setString(2, wrapper.getRequest().getDayNumber().toString());
				
			System.out.println("fetching data+ "+ps.toString());
			long start = System.nanoTime();
			ResultSet rs =ps.executeQuery();
			wrapper.setDbTime(System.nanoTime()-start);
			json = new JSONObject();
			int ver=0,lif=0;
			while(rs.next()){
				int id = Integer.parseInt(rs.getString(1));
				if(id>0 && id<11)
					ver+=200;
				else if(id>10 && id<21)
					ver+=300;
				else if(id>20 && id<31)
					 ver+=400;
				else if(id>30 && id<41)
					ver+=500;
				lif++;
			}
			json.put("Total vertical meters", ver);
			json.put("Lifts count is", lif);
			wrapper.setBody(json);
			wrapper.setError(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wrapper.setError(true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wrapper.setError(true);
		}
		return wrapper;
	}
	
	public void addMetrics(Wrapper wrapper){
		
		String insertSql = "insert into matrices (requesttime,dbtime,numberoferrors) values(?,?,?)";
		
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setObject (1 ,(double)wrapper.getReqTime()/(1e9));
			ps.setObject(2,(double)wrapper.getDbTime()/(1e9));
			ps.setObject(3,wrapper.isError());
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wrapper.setError(true);
		}
		
	}

}
