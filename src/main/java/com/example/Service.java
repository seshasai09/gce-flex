package com.example;

import java.util.concurrent.ThreadPoolExecutor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

@Path("/service")
public class Service {
	
	private ThreadPoolExecutor ex;
	public Service(){
		ex = ThreadPool.getThreadPoolInstance();
	}

	@Path("/alive")
	@GET
	public Response alive(){
		
		return Response.status(200).entity("Service is Alive").build();
	}

	@Path("/alive")
	@POST
	public Response postAlive(String req){
		Long start =System.nanoTime();
		ServiceDao sdao = new ServiceDao();
		boolean isAlive = sdao.addReqBody(req);
		Long stop = System.nanoTime();
	
		if(isAlive)
			return Response.status(200).entity("Service is Alive. This is post servie. your input is added").build();
		else
			return Response.status(200).entity("Service is Alive. This is post servie. your input was not added").build();
		
	}
	
	@Path("/load")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loadData(Ride ride){
		Long start =System.nanoTime();
		Wrapper<Boolean,Ride> wrapper = new Wrapper<Boolean,Ride>();
		if(ride==null){
			wrapper.setError(true);
			ex.submit(new MonitorResponseTime<>(wrapper));
			return Response.status(400).entity("ride object cannot be emoty").build();
		}
		wrapper.setRequest(ride);
		ServiceDao sdao = new ServiceDao();
		wrapper= sdao.addRide(wrapper);
		wrapper.setReqTime(System.nanoTime()-start);
		ex.submit(new MonitorResponseTime<>(wrapper));
		if((boolean)wrapper.getBody())
			return Response.status(200).entity("Ride was added").build();
		else
			return Response.status(500).entity("Ride was not added").build();
		
	}
	
	@Path("/myvert")
	@GET
	public Response getUserData(@QueryParam("skierId") String skierId, @QueryParam("dayNumber") Integer dayNumber){
		Wrapper<JSONObject,UserVertical> wrapper = new Wrapper<JSONObject,UserVertical>();
		Long start = System.nanoTime();
		UserVertical vert = new UserVertical();
		vert.setSkierId(skierId);
		vert.setDayNumber(dayNumber);
		wrapper.setRequest(vert);
		if(vert.getSkierId()==null){
			return Response.status(500).entity("Skier Id cannot be empty").build();
		}
		ServiceDao sdao = new ServiceDao();
		wrapper =sdao.getUserData(wrapper);
		wrapper.setReqTime(System.nanoTime()-start);
		ex.submit(new MonitorResponseTime<>(wrapper));
		return Response.status(200).entity(wrapper.getBody()).build(); 
	}
	
	
	
	

}
