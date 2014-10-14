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

import com.thegs.coffeeapp.dao.BooksDao;
import com.thegs.coffeeapp.model.Book;



public class BookResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public BookResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Book getBook() {
		Book b = BooksDao.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with" + id +  " not found");
		return b;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Book getBookHTML() {
		Book b = BooksDao.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with " + id +  " not found");
		return b;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putBook(JAXBElement<Book> b) {
		Book newb = b.getValue();
		return putAndGetResponse(newb);
	}
	
	@DELETE
	public void deleteBook() {
		Book delb = BooksDao.instance.getStore().remove(id);
		if(delb==null)
			throw new RuntimeException("DELETE: Book with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Book b) {
		Response res;
		if(BooksDao.instance.getStore().containsKey(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		BooksDao.instance.getStore().put(b.getId(), b);
		return res;
	}
}
