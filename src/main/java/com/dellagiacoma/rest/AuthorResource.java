package com.dellagiacoma.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dellagiacoma.entity.Author;

import javax.ws.rs.core.Application;

@Stateless
@ApplicationPath("/resources")
@Path("authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource extends Application {
	@PersistenceContext
	private EntityManager entityManager;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Author> listAuthors() {
		Query query = entityManager.createQuery("SELECT a FROM Author a");
		return query.getResultList();
	}

	@POST
	public Author saveAuthor(Author author) {
		Author personToSave = new Author();
		personToSave.setName(author.getName());
		entityManager.persist(author);
		return author;
	}
}
