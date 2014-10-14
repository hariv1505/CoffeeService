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

import com.thegs.coffeeapp.dao.BooksDao;
import com.thegs.coffeeapp.model.Book;



// will map xxx.xxx.xxx/rest/books
@Path("/books")
public class BooksResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;


	// Return the list of books to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Book> getBooksBrowser() {
		List<Book> bs = new ArrayList<Book>();
		bs.addAll( BooksDao.instance.getStore().values() );
		return bs; 
	}
	
	// Return the list of books for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Book> getBooks() {
		List<Book> bs = new ArrayList<Book>();
		bs.addAll( BooksDao.instance.getStore().values() );
		return bs; 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = BooksDao.instance.getStore().size();
		return String.valueOf(count);
	}
	
        // Client should set Content Type accordingly
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newBook(
			@FormParam("id") String id,
			@FormParam("title") String title,
			@FormParam("detail") String detail,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Book b = new Book(id,title);
		if (detail!=null){
			b.setDetail(detail);
		}
		BooksDao.instance.getStore().put(id, b);
		
		// Redirect to some HTML page  
		// You need to create this file under WEB-INF
		servletResponse.sendRedirect("../create_book.html");
	}
	
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/books/{book}
	// It says 'the thing that comes after books/ is a parameter
	// and it is passed to the BookResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.books/rest/books/3
        // This matches this method which returns BookResource.
	@Path("{book}")
	public BookResource getBook(
			@PathParam("book") String id) {
		return new BookResource(uriInfo, request, id);
	}
	
}
