package com.thegs.coffeeapp.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.thegs.coffeeapp.dao.PaymentDao;
import com.thegs.coffeeapp.model.Payment;



// will map xxx.xxx.xxx/rest/payments
@Path("/payments")
public class PaymentsResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	PaymentDao payDao = new PaymentDao();
	
	//TODO: not sure if actually in constructor
	/*public PaymentsResource(@HeaderParam("Authorization") String auth) {
		//TODO: not actually isEmpty - need an actual authorization
		if (auth.isEmpty()) {
			//throw exception and stop everything
		}
	}*/


	// Return the list of payments to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Payment> getPaymentsBrowser() {
		return payDao.getAllPayments();
	}
	
	// Return the list of payments for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Payment> getPayments() {
		return payDao.getAllPayments(); 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		return String.valueOf(payDao.getAllPayments().size());
	}
	
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/payments/{payment}
	// It says 'the thing that comes after payments/ is a parameter
	// and it is passed to the PaymentResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.payments/rest/payments/3
        // This matches this method which returns PaymentResource.
	@Path("{payment}")
	public PaymentResource getOrder(
			@PathParam("payment") String id) {
		return new PaymentResource(uriInfo, request, id);
	}
	
}
