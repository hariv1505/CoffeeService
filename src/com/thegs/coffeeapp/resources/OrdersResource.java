package com.thegs.coffeeapp.resources;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

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
	String AUTH_KEY = "abc123";
	
	// Return the list of orders to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Order> getOrdersBrowser(
			@HeaderParam("Auth") String auth,
			@Context final HttpServletResponse response) {
		if(auth == null || !auth.equals(AUTH_KEY)){
			response.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("<error>Forbidden</error>")
					.header("authorised", "false").build());
		}
		OrderDao ord = new OrderDao();
		return ord.getAllOrders(); 
	}
	
	// Return the list of orders for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Order> getOrders(@HeaderParam("Auth") String auth,
		@Context final HttpServletResponse response) {
		if(auth == null || !auth.equals(AUTH_KEY)){
			response.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("<error>Forbidden</error>")
					.header("authorised", "false").build());
		}
		OrderDao ord = new OrderDao();
		return ord.getAllOrders();	
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount(@HeaderParam("Auth") String auth) {
		if(auth == null || !auth.equals(AUTH_KEY))
			return "Unauthorized";
		OrderDao ord = new OrderDao();
		int count = ord.getAllOrders().size();
		return String.valueOf(count);
	}
	
        // Client should set Content Type accordingly
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newOrder(
			@FormParam("id") String id,
			@FormParam("coffeetype") String cType,
			@FormParam("cost") String cost,
			@FormParam("additions") String additions,
			@Context HttpServletResponse servletResponse,
			@HeaderParam("Auth") String auth
	) throws IOException {
		if(auth == null || !auth.equals(AUTH_KEY)){
			servletResponse.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("<html>Forbidden</html>")
				.header("authorised", "false").build());
		} else {
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
			servletResponse.setHeader("cost", cost);
			servletResponse.setHeader("uri", "/orders/" + id);
			servletResponse.setHeader("authorised", "true");
			// TODO gives 204 no content error, needs to spit out some html
			//servletResponse.sendRedirect("../create_order.html");
		}
		
		return "done";
	}
	
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/orders/{order}
	// It says 'the thing that comes after orders/ is a parameter
	// and it is passed to the OrderResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.orders/rest/orders/3
        // This matches this method which returns OrderResource.
	@Path("{order}")
	public OrderResource getOrder(
			@PathParam("order") String id) {
		return new OrderResource(uriInfo, request, id);
	}
	
	@OPTIONS
	public Response optionsReq() {
		
		String accConAllMet = "GET, POST, OPTIONS";
		
		ResponseBuilder rb = Response.ok().header("Access-Control-Allow-Origin", "*")
			      .header("Access-Control-Allow-Methods", accConAllMet);
		
		return rb.build();
		
	}
	
}
