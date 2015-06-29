package com.cbsweb.rest;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cbsweb.dao.VendorDAOImpl;
import com.cbsweb.rest.data.Vendor;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/v1/vendor")
public class VendorResource {

	@GET
	@Path("/hello")
	public Response sayHello() {
		return Response.ok().entity("Hello world").build();
	}
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginVendor(
			/*Vendor vendor) {*/			
			String jsonString) {
		
		JsonObject obj = (JsonObject) new JsonParser().parse(jsonString);
		String loginid = obj.get("loginid").getAsString();
		String pwd = obj.get("pwd").getAsString();
		
		System.out.println("Vendor login. LoginID:" + loginid + " Pwd: " + pwd);
		if(VendorDAOImpl.getInstance().loginVendor(loginid, pwd))
			return Response.accepted("Vendor logged in successfully").build();
		else
			return Response.status(500).entity("{message: login failed}").build();
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVendor(
			/*Vendor vendor) {*/			
			String jsonString) {
		
		Vendor vendor = new GsonBuilder().create().fromJson(jsonString, Vendor.class);
		System.out.println("Vendoor added. OTP sent to " + vendor.getPhno() + ";" + vendor.getEmail());
		if(VendorDAOImpl.getInstance().registerVendor(vendor))
			return Response.accepted("Vendor added. OTP sent to " + vendor.getPhno() + ";" + vendor.getEmail()).build();
		else
			return Response.status(500).entity("{\"message\" : \"error\"}").build();
	}
	
	@POST
	@Path("/verify")
	public Response verifyVendor(String jsonString) {
		JsonObject obj = (JsonObject) new JsonParser().parse(jsonString);
		String email = obj.get("email").getAsString();
		String phno = obj.get("phno").getAsString();
		String otp = obj.get("otp").getAsString();
		
		System.out.println(email + ";" + phno + ";" + otp);
		if(otp.equals("1234"))
			return Response.accepted("Vendor verified").build();
		else
			return Response.status(500).entity("{\"message\" : \"OTP wrong\"}").build();
	}
	
	@POST
	@Path("/resendOTP")
	public Response resendOTP(@FormParam("email") String email, @FormParam("phNo") String phNo, @FormParam("otp") String otp) {
		return Response.ok().entity("Vendor verifiiied. OTP sent to " + phNo + ";" + email).build();
	}
	
	
}
