package com.thegs.coffeeapp.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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


	// Return the list of payments to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Payment> getPaymentsBrowser() {
		List<Payment> ps = new ArrayList<Payment>();
		ps.addAll( PaymentDao.instance.getStore().values() );
		return ps; 
	}
	
	// Return the list of payments for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Payment> getPayments() {
		List<Payment> ps = new ArrayList<Payment>();
		ps.addAll( PaymentDao.instance.getStore().values() );
		return ps; 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = PaymentDao.instance.getStore().size();
		return String.valueOf(count);
	}
	
        // Client should set Content Type accordingly
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newPayment(
			@FormParam("id") String id,
			@FormParam("paytype") String pType,
			@FormParam("amount") String amnt,
			@FormParam("carddetails") String cardDetails,
			@FormParam("detail") String detail,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Payment p = new Payment(id, pType, amnt);
		if (detail != null){
			p.setDetail(detail);
		}
		if (cardDetails != null){
			p.setCardDetails(cardDetails);
		}
		PaymentDao.instance.getStore().put(id, p);
		
		// Redirect to some HTML page  
		// You need to create this file under WEB-INF
		servletResponse.sendRedirect("../create_payment.html");
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
