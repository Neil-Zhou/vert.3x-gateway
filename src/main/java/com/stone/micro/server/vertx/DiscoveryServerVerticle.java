package com.stone.micro.server.vertx;

import com.stone.discovery.ServiceDiscovery;
import com.stone.rules.facts.request.Request2Route;
import com.stone.rules.facts.routing.RoutingInfo;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by young on 16/7/21.
 */
public class DiscoveryServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryServerVerticle.class);

    private ServiceDiscovery serviceDiscovery;


    @Override
    public void start() {
        vertx.eventBus().consumer("http", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                serviceDiscovery.discoverServiceURI("");
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });

    }
}
