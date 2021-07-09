package com.pcw.assignment;

import com.pcw.assignment.verticles.BookVerticle;
import com.pcw.assignment.verticles.LendEntryVerticle;
import com.pcw.assignment.verticles.UserVerticle;
import com.pcw.assignment.verticles.ServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AssignmentApplication {
    @Autowired
    private ServerVerticle serverVerticle;

    @Autowired
    private BookVerticle bookVerticle;
    @Autowired
    private UserVerticle userVerticle;
    @Autowired
    private LendEntryVerticle lendEntryVerticle;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(serverVerticle);
        vertx.deployVerticle(bookVerticle);
        vertx.deployVerticle(userVerticle);
        vertx.deployVerticle(lendEntryVerticle);
    }

}
