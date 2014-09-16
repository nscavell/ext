/*
 * Copyright (c) 2011-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.rest;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.vertx.ext.rest.RestResponse.*;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class RestTest extends VertxTestBase {

  private RestServer server;
  private HttpClient client;
  private RequestOptions requestOptions;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    int port = 8080;
    String host = "localhost";

    // Setup server
    HttpServer httpServer = vertx.createHttpServer(HttpServerOptions.options().setPort(port).setHost(host));
    server = RestServer.restServer(vertx, httpServer);

    // Setup client
    client = vertx.createHttpClient(HttpClientOptions.options());
    requestOptions = RequestOptions.options().setPort(port).setHost(host);
  }

  @Test
  public void testGet() throws Exception {
    server.get("/foo/bar", (ctx, req) -> {
      ctx.send(req, ok());
    });

    server.start(ar -> {
      assertTrue(ar.succeeded());
      client.getNow(requestOptions.setRequestURI("/foo/bar"), resp -> {
        assertEquals(200, resp.statusCode());
        testComplete();
      });
    });

    await();
  }

  @Test
  public void testGetWithSimplePattern() throws Exception {

    server.get("/people/:name", (ctx, req) -> {
      assertEquals("john", req.pathParam("name"));
      ctx.send(req, ok());
    });

    server.start(ar -> {
      assertTrue(ar.succeeded());
      client.getNow(requestOptions.setRequestURI("/people/john"), resp -> {
        assertEquals(200, resp.statusCode());
        testComplete();
      });
    });

    await();
  }

  @Test
  public void testPreRequestHandlers() throws Exception {
    server.add((ctx, req) -> {
      if (req.body() instanceof Buffer) {
        req.body(new JsonObject(req.body().toString()));
      }
      ctx.next(req);
    });

    server.post("/people/:name", (ctx, req) -> {
      assertNotNull(req.body());
      assertTrue(req.body() instanceof JsonObject);
      JsonObject json = (JsonObject) req.body();
      Integer age = json.getInteger("age");
      assertNotNull(age);
      assertEquals(30, (int) age);

      ctx.send(req, ok());
    });

    server.start(ar -> {
      assertTrue(ar.succeeded());
      client.post(requestOptions.setRequestURI("/people/john"), resp -> {
        assertEquals(200, resp.statusCode());
        testComplete();
      }).end(new JsonObject().putNumber("age", 30).toString());
    });

    await();
  }

  @Test
  public void testShortCircuit() {
    String msg = "Short circuit is a great movie !";
    server.get("/foo", (ctx, req) -> {
      req.http().response().end(msg);
    });

    server.start(ar -> {
      assertTrue(ar.succeeded());
      client.getNow(requestOptions.setRequestURI("/foo"), resp -> {
        assertEquals(200, resp.statusCode());
        resp.dataHandler(buff -> {
          assertEquals(msg, buff.toString());
          testComplete();
        });
      });
    });
  }

  private static void setConsoleHandlerLevel(Level level, Logger logger) {
    while (logger != null) {
      for (Handler lh : logger.getHandlers()) {
        if (lh instanceof ConsoleHandler) {
          lh.setLevel(level);
          return;
        }
      }
      logger = logger.getParent();
    }
  }
}
