package com.stone.micro.server.vertx;

import com.stone.rules.facts.request.Request2Route;
import com.stone.rules.facts.routing.RoutingInfo;
import com.stone.service.app.RoutingPassService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by young on 16/7/5.
 */
@Component
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Autowired
    private RoutingPassService routingPassService;
    @Override
    public void start() {
        vertx.eventBus().consumer("http", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                Request2Route req=new Request2Route("get","/route");
                RoutingInfo routingInfo= routingPassService.getpayPass(req);
                vertx.createHttpClient().getNow(routingInfo.getPort(), routingInfo.getHost(), routingInfo.getPath(), resp -> {
                    resp.bodyHandler(body -> {
                        message.reply(body+"");
                    });
                });
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });

    }
}
