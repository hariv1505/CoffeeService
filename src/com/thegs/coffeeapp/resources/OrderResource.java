package com.thegs.coffeeapp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
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
	public OrderResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Order getOrder() {
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if(o==null)
			throw new RuntimeException("GET: Order with" + id +  " not found");
		return o;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.TEXT_HTML})
	public Order getOrderHTML() {
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if(o==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return o;
	}
	
	@DELETE
	//TODO: changed method signature - added argument
	public void deleteOrder(@Context HttpServletResponse servletResponse) {
		OrderDao ord = new OrderDao();
		Order o = ord.getOrderById(id);
		if(o==null)
			throw new RuntimeException("DELETE: Order with " + id +  " not found");
			//TODO: status codes?
		else {
			ord.deleteOrder(o);
			//TODO: return code - see if this works
			//look at this for details 
			//http://www.javamex.com/tutorials/servlets/http_status_code.shtml
			servletResponse.setStatus(200);	
			//HttpServletResponse.)
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Order> o) {
		Order newO = o.getValue();
		Response res;
		OrderDao ord = new OrderDao();
		String newId = ord.updateOrder(newO);
		//res not working correctly
		if(ord.getOrderById(newId) != null) {
			res = Response.noContent().build();
		} else {
			//TODO: do not understand this
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		return res;
	}
	
	//TODO: getting rid of this
	//private Response putAndGetResponse(Order newOrder) {
	//	
	//}
	
	@OPTIONS
	@Produces(MediaType.TEXT_HTML)
	@Path("options")		//TODO: are we allowed to do this? :S
	public void getOptions() {
		//TODO change answer depending on status
		// no idea how to do this
		
	}
}
