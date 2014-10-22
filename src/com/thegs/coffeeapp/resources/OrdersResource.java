package com.thegs.coffeeapp.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.thegs.coffeeapp.dao.OrderDao;
import com.thegs.coffeeapp.model.Order;



// will map xxx.xxx.xxx/rest/orders
@Path("/orders")
public class OrdersResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	//TODO: not sure if actually in constructor
//	public OrdersResource(@HeaderParam("Authorization") String auth) {
//		//TODO: not actually isEmpty - need an actual authorization
//		if (auth.isEmpty()) {
//			//throw exception and stop everything
//		}
//	}
	
	// Return the list of orders to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Order> getOrdersBrowser() {
		OrderDao ord = new OrderDao();
		return ord.getAllOrders(); 
	}
	
	// Return the list of orders for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Order> getOrders() {
		OrderDao ord = new OrderDao();
		return ord.getAllOrders();	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		OrderDao ord = new OrderDao();
		int count = ord.getAllOrders().size();
		return String.valueOf(count);
	}
	
    // Client should set Content Type accordingly
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newOrder(
			@FormParam("id") String id,
			@FormParam("coffeetype") String cType,
			@FormParam("cost") String cost,
			@FormParam("additions") String additions,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Order o;
		if (additions != null) {
			o = new Order(id, cType, cost, additions);
		} else {
			o = new Order(id, cType, cost);
		}
		OrderDao ord = new OrderDao();
		ord.addOrder(o);
		
		// Redirect to some HTML page  
		// You need to create this file under WEB-INF
		servletResponse.sendRedirect("../ordercreated.html");
	}
	
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/orders/{order}
	// It says 'the thing that comes after orders/ is a parameter
	// and it is passed to the OrderResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.orders/rest/orders/3
        // This matches this method which returns OrderResource.
	@GET
	@Path("{order}")
	public OrderResource getOrder(
			@PathParam("order") String id) {
		return new OrderResource(uriInfo, request, id);
	}
	
	
	
}
