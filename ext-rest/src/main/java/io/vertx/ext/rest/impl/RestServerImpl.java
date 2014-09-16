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

package io.vertx.ext.rest.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.ext.rest.RestHandler;
import io.vertx.ext.rest.RestHandlerContext;
import io.vertx.ext.rest.RestRequest;
import io.vertx.ext.rest.RestRequestHandler;
import io.vertx.ext.rest.RestResponse;
import io.vertx.ext.rest.RestResponseHandler;
import io.vertx.ext.rest.RestServer;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class RestServerImpl implements RestServer, Handler<HttpServerRequest> {
  private static Logger log = LoggerFactory.getLogger(RestServerImpl.class);

  private final Vertx vertx;
  private final HttpServer server;

  private final HeadContext head;
  private final TailContext tail;

  public RestServerImpl(Vertx vertx, HttpServer server) {
    if (server.requestHandler() != null)
      throw new IllegalArgumentException("The http server already has a request handler associated with it.");
    this.vertx = vertx;
    this.server = server;

    this.server.requestHandler(this);

    this.head = new HeadContext(this);
    this.tail = new TailContext(this);
    head.next = tail;
    tail.prev = head;
  }

  public RestServer start() {
    return start(h -> {
    });
  }

  public RestServer start(Handler<AsyncResult<RestServer>> startHandler) {
    server.listen(ar -> {
      if (startHandler != null) {
        if (ar.succeeded()) {
          startHandler.handle(Future.completedFuture(this));
        } else {
          startHandler.handle(Future.completedFuture(ar.cause()));
        }
      } else if (ar.failed()) {
        log.error("Could not start rest server.", ar.cause());
      } else {
        log.info("Rest Server started !");
      }
    });
    return this;
  }

  public RestServer stop() {
    return stop(null);
  }

  public RestServer stop(Handler<AsyncResult<RestServer>> stopHandler) {
    server.close(ar -> {
      if (stopHandler != null) {
        if (ar.succeeded()) {
          stopHandler.handle(Future.completedFuture(this));
        } else {
          stopHandler.handle(Future.completedFuture(ar.cause()));
        }
      } else if (ar.failed()) {
        log.error("Could not stop rest server.", ar.cause());
      } else {
        log.info("Rest Server stopped !");
      }
    });

    return this;
  }

  @Override
  public RestServer add(RestRequestHandler handler) {
    return doAdd(handler);
  }

  @Override
  public RestServer add(RestResponseHandler handler) {
    return doAdd(handler);
  }

  @Override
  public RestServer get(String path, RestRequestHandler handler) {
    return get(path, null, handler);
  }

  @Override
  public RestServer get(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "GET", contentType, handler);
    return this;
  }

  @Override
  public RestServer post(String path, RestRequestHandler handler) {
    return post(path, null, handler);
  }

  @Override
  public RestServer post(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "POST", contentType, handler);
    return this;
  }

  @Override
  public RestServer put(String path, RestRequestHandler handler) {
    return put(path, null, handler);
  }

  @Override
  public RestServer put(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "PUT", contentType, handler);
    return this;
  }

  @Override
  public RestServer delete(String path, RestRequestHandler handler) {
    return delete(path, null, handler);
  }

  @Override
  public RestServer delete(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "DELETE", contentType, handler);
    return this;
  }

  @Override
  public RestServer patch(String path, RestRequestHandler handler) {
    return patch(path, null, handler);
  }

  @Override
  public RestServer patch(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "PATCH", contentType, handler);
    return this;
  }

  @Override
  public RestServer options(String path, RestRequestHandler handler) {
    return options(path, null, handler);
  }

  @Override
  public RestServer options(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "OPTIONS", null, handler);
    return this;
  }

  @Override
  public RestServer head(String path, RestRequestHandler handler) {
    return head(path, null, handler);
  }

  @Override
  public RestServer head(String path, String contentType, RestRequestHandler handler) {
    addRoute(path, "HEAD", contentType, handler);
    return this;
  }

  @Override
  public void handle(HttpServerRequest request) {
    boolean trace = log.isTraceEnabled();
    if (trace) {
      log.trace("Request: Uri=" + request.uri() + ", Method=" + request.method() + "");
    }

    request.bodyHandler(buff -> head.next(new RestRequestImpl(request, buff)));
  }

  Vertx vertx() {
    return vertx;
  }

  private RestServer doAdd(RestHandler handler) {
    AbstractRestHandlerContext add = new RestHandlerContextImpl(this, handler);

    AbstractRestHandlerContext prev = tail.prev;
    prev.next = add;
    add.prev = prev;
    add.next = tail;
    tail.prev = add;

    return this;
  }

  private void addRoute(String path, String method, String contentType, RestRequestHandler handler) {
    tail.router().add(path, method, contentType, handler);
  }

  private static class HeadContext extends AbstractRestHandlerContext implements RestHandlerContext, RestResponseHandler {

    private HeadContext(RestServerImpl server) {
      super(server, null);
    }

    @Override
    public RestHandler handler() {
      return this;
    }

    @Override
    public void handle(RestHandlerContext ctx, RestRequest request, RestResponse response) {
      Object body = response.body();
      if (body != null && !(body instanceof Buffer)) {
        log.error("Unknown response body " + body.getClass());
      }
      HttpServerResponse httpResponse = request.http().response();
      httpResponse.setStatusCode(response.statusCode());
      if (response.statusMessage() != null) {
        httpResponse.setStatusMessage(response.statusMessage());
      }
      if (body == null) {
        httpResponse.end();
      } else {
        httpResponse.end((Buffer) body);
      }
    }
  }

  private static class TailContext extends AbstractRestHandlerContext {
    private TailContext(RestServerImpl server) {
      super(server, new RoutingRestRequestHandler());
    }

    private RoutingRestRequestHandler router() {
      return (RoutingRestRequestHandler) handler();
    }
  }
}
