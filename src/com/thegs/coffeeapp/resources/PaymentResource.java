package com.thegs.coffeeapp.resources;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.thegs.coffeeapp.dao.PaymentDao;
import com.thegs.coffeeapp.model.Payment;



public class PaymentResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	PaymentDao payDao;
	
	private String AUTH_KEY = "def456";
	
	public PaymentResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.payDao = new PaymentDao();
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Payment getPayment(@HeaderParam("Auth") String auth,
			@Context final HttpServletResponse response) {
		if(auth == null || !auth.equals(AUTH_KEY)){
			response.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Forbidden")
					.header("authorised", "false").build());
		}
		Payment p = payDao.getPaymentById(id);
		if(p==null)
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.entity("Bad Request").build());
		return p;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Payment getPaymentHTML(@HeaderParam("Auth") String auth,
			@Context final HttpServletResponse response) {
		if(auth == null || !auth.equals(AUTH_KEY)){
			response.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Forbidden")
					.header("authorised", "false").build());
		}
		Payment p = payDao.getPaymentById(id);
		if(p==null)
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.entity("Bad Request").build());
		return p;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	public Response putPayment(JAXBElement<Payment> p,
			@HeaderParam("Auth") String auth,
			@Context final HttpServletResponse response) {
		if(auth == null || !auth.equals(AUTH_KEY)){
			response.setHeader("authorised", "false");
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN.getStatusCode())
					.entity("Forbidden")
					.header("authorised", "false").build());
		}
		Payment newP = p.getValue();
		Response res;
		Payment oldP = payDao.getPaymentById(id);
		
		payDao.deletePayment(oldP);

		payDao.addPayment(newP);
		res = Response.created(uriInfo.getAbsolutePath()).build();
		
		return res;
	}
	
}
