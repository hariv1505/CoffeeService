package com.thegs.coffeeapp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
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
	public PaymentResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Payment getPayment() {
		Payment p = PaymentDao.instance.getStore().get(id);
		if(p==null)
			throw new RuntimeException("GET: Payment with" + id +  " not found");
		return p;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Payment getPaymentHTML() {
		Payment p = PaymentDao.instance.getStore().get(id);
		if(p==null)
			throw new RuntimeException("GET: Payment with " + id +  " not found");
		return p;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putPayment(JAXBElement<Payment> p) {
		Payment newP = p.getValue();
		return putAndGetResponse(newP);
	}
	
	@DELETE
	public void deletePayment() {
		Payment delp = PaymentDao.instance.getStore().remove(id);
		if(delp==null)
			throw new RuntimeException("DELETE: Payment with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Payment p) {
		Response res;
		if(PaymentDao.instance.getStore().containsKey(p.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		PaymentDao.instance.getStore().put(p.getId(), p);
		return res;
	}
}
