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

import com.thegs.coffeeapp.dao.CoffeeDao;
import com.thegs.coffeeapp.model.Coffee;



public class CoffeeResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public CoffeeResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Coffee getBook() {
		Coffee b = CoffeeDao.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with" + id +  " not found");
		return b;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Coffee getBookHTML() {
		Coffee b = CoffeeDao.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with " + id +  " not found");
		return b;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putBook(JAXBElement<Coffee> b) {
		Coffee newb = b.getValue();
		return putAndGetResponse(newb);
	}
	
	@DELETE
	public void deleteBook() {
		Coffee delb = CoffeeDao.instance.getStore().remove(id);
		if(delb==null)
			throw new RuntimeException("DELETE: Book with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Coffee b) {
		Response res;
		if(CoffeeDao.instance.getStore().containsKey(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		CoffeeDao.instance.getStore().put(b.getId(), b);
		return res;
	}
}
