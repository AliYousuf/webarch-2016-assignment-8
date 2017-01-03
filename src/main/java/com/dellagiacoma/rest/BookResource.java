package com.dellagiacoma.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.dellagiacoma.entity.Book;

@Stateless
@ApplicationPath("/resources")
@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource extends Application {
	@PersistenceContext
	private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
    	Query query =entityManager.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

	@POST
	public void saveBook(Book book) {
		Book bookToSave = new Book();
		bookToSave.setTitle(book.getTitle());
		bookToSave.setPrice(book.getPrice());
		bookToSave.setAuthor(book.getAuthor());
		entityManager.persist(book);
	}

	@DELETE
	@Path("{id}")
	public void deleteBook(@PathParam("id") Long id) {
		entityManager.remove(getBook(id));
	}
	
	private Book getBook(Long id) {
		return entityManager.find(Book.class, id);
	}
}
