package com.pcw.assignment.verticles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcw.assignment.models.LendEntry;
import com.pcw.assignment.models.LendEntryKey;
import com.pcw.assignment.services.LendEntryService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LendEntryVerticle extends AbstractVerticle {
    public static final String GET_ALL_LEND_ENTRIES = "get.lend.entry.all";
    public static final String ADD_LEND_ENTRY = "lend.entry.add";
    public static final String GET_LEND_ENTRY_BY_ID = "get.lend.entry.id";
    private final ObjectMapper mapper = Json.mapper;

    @Autowired
    private LendEntryService service;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .<String>consumer(GET_ALL_LEND_ENTRIES)
                .handler(getAllLendEntriesService(service));
        vertx.eventBus()
                .<JsonObject>consumer(ADD_LEND_ENTRY).handler(addLendEntryService(service));
        vertx.eventBus()
                .<JsonObject>consumer(GET_LEND_ENTRY_BY_ID).handler(getLEndEntryByIdService(service));
    }

    private Handler<Message<String>> getAllLendEntriesService(LendEntryService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                System.out.println(service.findAllLendEntries().get(0));
                future.complete(mapLendEntryListToString(service.findAllLendEntries()));
            } catch (Exception e) {
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

    private Handler<Message<JsonObject>> getLEndEntryByIdService(LendEntryService service) {

        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                LendEntry lendEntry = service.getById(new LendEntryKey(msg.body()));
                future.complete(lendEntry.toString());
            } catch (Exception e) {
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

    private Handler<Message<JsonObject>> addLendEntryService(LendEntryService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(service.addLendEntry(new LendEntryKey(msg.body())).toString());
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


    public String mapLendEntryListToString(List<LendEntry> lendEntryList){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(LendEntry lendEntry : lendEntryList){
            sb.append(lendEntry.toString());
            sb.append(", \n");
    }
        sb.append("}");
    return  sb.toString();}
}
