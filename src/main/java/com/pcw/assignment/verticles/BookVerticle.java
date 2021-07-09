package com.pcw.assignment.verticles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcw.assignment.models.Book;
import com.pcw.assignment.services.BookService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookVerticle extends AbstractVerticle {

    public static final String GET_ALL_BOOKS = "get.books.all";
    public static final String ADD_BOOK = "books.add";
    public static final String GET_BOOK_BY_ID = "get.book.id";

    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private BookService service;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .<String>consumer(GET_ALL_BOOKS)
                .handler(getAllBooksService(service));
        vertx.eventBus()
                .<JsonObject>consumer(ADD_BOOK).handler(addBooksService(service));
        vertx.eventBus()
                .<String>consumer(GET_BOOK_BY_ID).handler(getBookByIdService(service));

    }

    private Handler<Message<String>> getBookByIdService(BookService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                Book book = service.getById(Integer.parseInt(msg.body()));
                future.complete(mapper.writeValueAsString(book));
            } catch (JsonProcessingException e) {
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause()
                        .toString());
            }
        });

    }


    private Handler<Message<String>> getAllBooksService(BookService service) {

        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(service.getAllBooks()));
            } catch (JsonProcessingException e) {
                System.out.println("Failed to serialize result");
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause()
                        .toString());
            }
        });
    }

    private Handler<Message<JsonObject>> addBooksService(BookService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(service.addBook(new Book(msg.body()))));
            } catch (Exception e) {
                System.out.println("Failed to serialize result");
                future.fail("Failed");
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause()
                        .toString());
            }
        });

    }
}
