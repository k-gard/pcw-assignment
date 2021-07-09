package com.pcw.assignment.verticles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcw.assignment.models.User;
import com.pcw.assignment.services.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserVerticle extends AbstractVerticle {

    public static final String GET_ALL_USERS = "get.users.all";
    public static final String ADD_USER = "user.add";
    public static final String GET_USER_BY_ID = "get.user.id";

    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private UserService service;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .<String>consumer(GET_ALL_USERS)
                .handler(getAllUsersService(service));
        vertx.eventBus()
                .<JsonObject>consumer(ADD_USER).handler(addUserService(service));
        vertx.eventBus()
                .<String>consumer(GET_USER_BY_ID).handler(getUserByIdService(service));

    }

    private Handler<Message<String>> getUserByIdService(UserService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                User user = service.getById(Integer.parseInt(msg.body()));
                future.complete(mapper.writeValueAsString(user));
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


    private Handler<Message<String>> getAllUsersService(UserService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(service.findAllusers()));
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

    private Handler<Message<JsonObject>> addUserService(UserService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(service.addUser(new User(msg.body())).toString());
            } catch (Exception e) {
                e.printStackTrace();
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




