package com.pcw.assignment.models;

import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    private int id;
    @Column(unique = true)
    private int isbn;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;

    public Book(int isbn, String title, String author, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public Book() {
    }

    public Book(JsonObject jsonObject) {
        this.isbn = jsonObject.getInteger("isbn");
        this.title = jsonObject.getString("title");
        this.author = jsonObject.getString("author");
        this.publisher = jsonObject.getString("publisher");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id + " ," +
                "\"isbn\":" + isbn + " ," +
                "\"title\":" + "\"" + title + "\"" + " ," +
                "\"author\":" + "\"" + author + "\"" + " ," +
                "\"publisher\":" + "\"" + publisher + "\"" +
                "}";
    }
}
