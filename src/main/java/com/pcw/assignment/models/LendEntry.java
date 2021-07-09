package com.pcw.assignment.models;

import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Proxy(lazy = false)
public class LendEntry {

    @EmbeddedId
    LendEntryKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime dateTime;

    public LendEntry() {
    }

    public LendEntry(Book book, User user, LocalDateTime localDateTime) {
        this.book = book;
        this.user = user;
        this.dateTime = localDateTime;
    }

    public LendEntry(JsonObject jsonObject) {
        this.book = new Book(jsonObject.getJsonObject("book"));
        this.user = new User(jsonObject.getJsonObject("user"));
        this.dateTime = LocalDateTime.now();

    }

    public LendEntryKey getId() {
        return id;
    }

    public void setId(LendEntryKey id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id.toString() + "," +
                "\"book\" : " + book.toString() + "," +
                "\"user\" : " + user.toString() + "," +
                "\"dateTime\" : " + dateTime.toString() +
                '}';
    }
}
