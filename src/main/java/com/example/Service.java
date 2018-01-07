package com.example;

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

	@Path("/alive")
	@GET
	public Response alive(){
		
		return Response.status(200).entity("Service is Alive").build();
	}

	@Path("/alive")
	@POST
	public Response postAlive(String req){
		ServiceDao sdao = new ServiceDao();
		if(sdao.addReqBody(req))
			return Response.status(200).entity("Service is Alive. This is post servie. your input is added").build();
		else
			return Response.status(200).entity("Service is Alive. This is post servie. your input was not added").build();
		
	}
	
	@Path("/load")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loadData(Ride ride){
		if(ride==null)
			return Response.status(400).entity("ride object cannot be emoty").build();
		ServiceDao sdao = new ServiceDao();
		if(sdao.addRide(ride))
			return Response.status(200).entity("Ride was added").build();
		else
			return Response.status(500).entity("Ride was not added").build();
		
	}
	
	@Path("/myvert")
	@GET
	public Response getUserData(@QueryParam("skierId") String skierId, @QueryParam("dayNumber") Integer dayNumber){
		UserVertical vert = new UserVertical();
		vert.setSkierId(skierId);
		vert.setDayNumber(dayNumber);
		if(vert.getSkierId()==null)
			return Response.status(500).entity("Skier Id cannot be empty").build();
		ServiceDao sdao = new ServiceDao();
		JSONObject json =sdao.getUserData(vert);
		
		return Response.status(200).entity(json).build(); 
	}
	
	
	
	

}
