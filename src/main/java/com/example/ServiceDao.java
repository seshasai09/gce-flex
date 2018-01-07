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
	
	public boolean addRide(Ride ride){
		String insertSql = "insert into rides (resortId,daynumber,timestamp,skierid,liftid) values(?,?,?,?,?)";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1 ,ride.getResortId());
			ps.setObject(2,ride.getDayNumber());
			ps.setObject(3,ride.getTimeStamp());
			ps.setObject(4,ride.getSkierId());
			ps.setObject(5,ride.getLiftId());
			System.out.println("insersting data");
			return !ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public JSONObject getUserData(UserVertical vertical) {
		String selectSql = "select liftid from rides where skierid=? and daynumber=?";
		JSONObject json = null;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement   ps = conn.prepareStatement(selectSql);
			ps.setString(1, vertical.getSkierId());
			ps.setString(2, vertical.getDayNumber().toString());
				
			System.out.println("fetching data+ "+ps.toString());
			ResultSet rs =ps.executeQuery();
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
			return json;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
