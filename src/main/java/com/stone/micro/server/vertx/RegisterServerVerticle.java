package com.stone.micro.server.vertx;

import com.stone.discovery.ServiceDiscovery;
import com.stone.registration.ServiceRegistry;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by young on 16/7/21.
 */
public class RegisterServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryServerVerticle.class);

    private ServiceRegistry serviceRegistry;


    @Override
    public void start() {
        vertx.eventBus().consumer("http", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                serviceRegistry.registerService("","");
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });

    }
}
