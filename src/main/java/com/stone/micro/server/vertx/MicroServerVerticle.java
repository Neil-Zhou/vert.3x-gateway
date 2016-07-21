/*
 * Copyright (c) 2016-2018 The Stone Team

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.stone.micro.server.vertx;

import com.alibaba.fastjson.JSON;
import com.stone.service.ConfigLoader;
import com.stone.service.app.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Young
 * @date 2016/2/16 0016
 */
@Component
public class MicroServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroServerVerticle.class);

    @Autowired
    private ConfigLoader configLoader;

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route("/").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("http", req.getBodyAsString(), ar -> {
                        if (ar.succeeded()) {
                            req.response().end(JSON.toJSONString(ar.result().body()));
                        }
                    });
                });


        router.route("/register").handler(
                req -> {
            LOGGER.info("Received a http request");
            vertx.eventBus().send("register", req.getBodyAsString(), ar -> {
                if (ar.succeeded()) {
                    req.response().end(JSON.toJSONString(ar.result().body()));
                }
            });
        });


        router.route("/discovery").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("discovery", req.getBodyAsString(), ar -> {
                        if (ar.succeeded()) {
                            req.response().end(JSON.toJSONString(ar.result().body()));
                        }
                    });
                });


        router.route("/monitor").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("monitor", req.getBodyAsString(), ar -> {
                        if (ar.succeeded()) {
                            req.response().end(JSON.toJSONString(ar.result().body()));
                        }
                    });
                });

        vertx.createHttpServer().requestHandler(router::accept).listen(18081);
        LOGGER.info("Started HttpServer(port=18081).");
    }
}
