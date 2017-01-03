package com.dellagiacoma.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Author {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idAuthor")
    @SequenceGenerator(name = "idAuthor", sequenceName = "idAuthor")
    private Long idAuthor;

    private String name;
   
    
    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
