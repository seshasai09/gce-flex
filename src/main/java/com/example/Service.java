package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
	
	
	
	
	

}
