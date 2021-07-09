
package com.pcw.assignment.verticles;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerVerticle extends AbstractVerticle {


    @Autowired
    private Integer defaultPort;
    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);


        router.route().handler(BodyHandler.create());
        router.get("/api/books").handler(this::getAllBooksHandler);
        router.get("/api/books/:id").handler(this::getBookByIdHandler);
        router.post("/api/books").handler(this::addBookHandler);
        router.get("/api/users").handler(this::getAllUsersHandler);
        router.get("/api/users/:id").handler(this::getUserByIdHandler);
        router.post("/api/users").handler(this::addUserHandler);
        router.get("/api/lendentry").handler(this::getAllLendEntriesHandler);
        router.get("/api/lendentry/:bookId/:userId").handler(this::getLendEntryByIdHandler);
        router.post("/api/lendentry").handler(this::addLendEntryHandler);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", defaultPort));
    }

    private void addLendEntryHandler(RoutingContext routingContext) {

        JsonObject bookJson = routingContext.getBody().toJsonObject();
        vertx.eventBus()
                .<String>send(LendEntryVerticle.ADD_LEND_ENTRY, bookJson, result -> {
                    if (result.succeeded() && result.result().body().contains("Failed")) {
                        routingContext.response()
                                .setStatusCode(400).putHeader("content-type","application/json; charset=utf-8")
                                .end("Data integrity violation");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });

    }

    private void getLendEntryByIdHandler(RoutingContext routingContext) {
        JsonObject lendEntryKey = new JsonObject();
        lendEntryKey.put("bookId",Integer.parseInt(routingContext.request().getParam("bookId")));
        lendEntryKey.put("userId",Integer.parseInt(routingContext.request().getParam("userId")));
        System.out.println(lendEntryKey);
        vertx.eventBus()
                .<String>send(LendEntryVerticle.GET_LEND_ENTRY_BY_ID, lendEntryKey, result -> {
                    if (result.succeeded() && result.result().body().contains("EntityNotFoundException")) {
                        routingContext.response()
                                .setStatusCode(404).putHeader("content-type","application/json; charset=utf-8")
                                .end("Not Found");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

    private void getAllLendEntriesHandler(RoutingContext routingContext) {
        vertx.eventBus()
                .<String>send(LendEntryVerticle.GET_ALL_LEND_ENTRIES, "", result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(result.result()
                                        .body());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }


    private void getAllBooksHandler(RoutingContext routingContext) {
        vertx.eventBus()
                .<String>send(BookVerticle.GET_ALL_BOOKS, "", result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(result.result()
                                        .body());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

    private void addBookHandler(RoutingContext routingContext) {
        JsonObject bookJson = routingContext.getBody().toJsonObject();
        vertx.eventBus()
                .<String>send(BookVerticle.ADD_BOOK, bookJson, result -> {
                    if (result.succeeded() && result.result().body().contains("Failed")) {
                        routingContext.response()
                                .setStatusCode(400).putHeader("content-type","application/json; charset=utf-8")
                                .end("Data integrity violation");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }
    private void getBookByIdHandler(RoutingContext routingContext) {
        vertx.eventBus()
                .<String>send(BookVerticle.GET_BOOK_BY_ID, routingContext.request().getParam("id"), result -> {
                    if (result.succeeded() && result.result().body().contains("EntityNotFoundException")) {
                        routingContext.response()
                                .setStatusCode(404).putHeader("content-type","application/json; charset=utf-8")
                                .end("Not Found");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }




    private void getUserByIdHandler(RoutingContext routingContext) {

        vertx.eventBus()
                .<String>send(UserVerticle.GET_USER_BY_ID, routingContext.request().getParam("id"), result -> {
                    if (result.succeeded() && result.result().body().contains("EntityNotFoundException")) {
                        routingContext.response()
                                .setStatusCode(404).putHeader("content-type","application/json; charset=utf-8")
                                .end("Not Found");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });

    }
    private void addUserHandler(RoutingContext routingContext) {
        JsonObject userJson = routingContext.getBody().toJsonObject();
        vertx.eventBus()
                .<String>send(UserVerticle.ADD_USER, userJson, result -> {
                    if (result.succeeded() && result.result().body().contains("Failed")) {
                        routingContext.response()
                                .setStatusCode(400).putHeader("content-type","application/json; charset=utf-8")
                                .end("Data integrity violation");

                    }else if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(201).putHeader("content-type","application/json; charset=utf-8")
                                .end(result.result().body());
                    }else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }
    private void getAllUsersHandler(RoutingContext routingContext) {
        vertx.eventBus()
                .<String>send(UserVerticle.GET_ALL_USERS, "", result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(result.result()
                                        .body());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });

    }


}