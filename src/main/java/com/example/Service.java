package com.example;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
	    name = "Service",
	    urlPatterns = {"/alive"}
	)
public class Service extends HttpServlet{

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		resp.getWriter().print("Service is Alive");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader input = req.getReader();
		System.out.println(input);
		StringBuilder sb = new StringBuilder();
		String line= null;
		while((line=input.readLine())!=null)
			sb.append(line+"\n");
		ServiceDao sdao = new ServiceDao();
		if(sdao.addReqBody(sb.toString().toString()))
			resp.getWriter().print("Service is Alive. This is post servie. your input is added");
		else
			resp.getWriter().print("Service is Alive. This is post servie. your input was not added");
	}
	
	
	
	
	

}
