package com.thegs.coffeeapp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.thegs.coffeeapp.dao.OrderDao;
import com.thegs.coffeeapp.model.Order;



public class OrderResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	String AUTH_KEY = "abc123";
	public OrderResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Order getOrder(@HeaderParam("Auth") String auth) {
		if(auth == null || !auth.equals(AUTH_KEY))
			// TODO need to change this, e.g. return something better than an empty order
			return new Order();
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if(o==null)
			throw new RuntimeException("GET: Order with" + id +  " not found");
		return o;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Order getOrderHTML() {
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if(o==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return o;
		
	}
	
	@DELETE
	public String deleteOrder(@HeaderParam("Auth") String auth) {
		if(auth == null || !auth.equals(AUTH_KEY))
			return "";
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if (o!=null) {
			ord.deleteOrder(o);
			return "200";
		} else {
			new RuntimeException("DELETE: Order with " + id +  " not found").printStackTrace();
			return "404";
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Order putOrder(JAXBElement<Order> o,@HeaderParam("Auth") String auth) {
		if(auth == null || !auth.equals(AUTH_KEY))
			// TODO need to change this, e.g. return something better than an empty order
			return new Order();
		Order newO = o.getValue();
		Response r = putAndGetResponse(newO);
		if (r.getStatus() == 201) {
			return newO;
		} else {
			throw new RuntimeException("UPDATE: Error " + r.getStatus());
		}
	}
	
	
	private Response putAndGetResponse(Order newOrder) {
		Response res;
		OrderDao ord = new OrderDao();
		if(ord.getOrderById(newOrder.getId()) == null) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			ord.updateOrder(newOrder);
		}
		return res;
	}
}
