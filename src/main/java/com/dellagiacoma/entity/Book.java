package com.dellagiacoma.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Book {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	    @SequenceGenerator(name = "id", sequenceName = "id")
	    private Long id;
	 	
	 	private String title;
	 	
	 	private double price;
	 	
	 	@OneToOne
	 	@JoinColumn(name = "idAuthor", referencedColumnName = "idAuthor", insertable = true, updatable = true)
	 	private Author author;
	 	 
	     public Long getId() {
	         return id;
	     }

	     public void setId(Long id) {
	         this.id = id;
	     }

	     public String getTitle() {
	         return title;
	     }

	     public void setTitle(String title) {
	         this.title = title;
	     }
	     
	     public double getPrice() {
	         return price;
	     }

	     public void setPrice(double price) {
	         this.price = price;
	     }
	     
	     public Author getAuthor() {
	 		return author;
	 	}

	 	public void setAuthor(Author author) {
	 		this.author = author;
	 	}
}
