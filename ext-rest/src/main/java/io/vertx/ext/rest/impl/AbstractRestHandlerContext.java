package io.vertx.ext.rest.impl;

import io.vertx.core.Vertx;
import io.vertx.ext.rest.RestHandler;
import io.vertx.ext.rest.RestHandlerContext;
import io.vertx.ext.rest.RestRequest;
import io.vertx.ext.rest.RestRequestHandler;
import io.vertx.ext.rest.RestResponse;
import io.vertx.ext.rest.RestResponseHandler;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
abstract class AbstractRestHandlerContext implements RestHandlerContext {
  private final RestServerImpl server;
  private final RestHandler handler;

  volatile AbstractRestHandlerContext next;
  volatile AbstractRestHandlerContext prev;

  protected AbstractRestHandlerContext(RestServerImpl server, RestHandler handler) {
    this.server = server;
    this.handler = handler;
  }

  @Override
  public Vertx vertx() {
    return server.vertx();
  }

  @Override
  public RestHandler handler() {
    return handler;
  }

  @Override
  public void next(RestRequest request) {
    downstream().doNext(request);
  }

  @Override
  public void send(RestRequest request, RestResponse response) {
    upstream().doSend(request, response);
  }

  @Override
  public void error(RestRequest request, Throwable cause) {
    RestResponse response = new RestResponse(500);
    response.cause(cause);

    upstream().doSend(request, response);
  }

  private void doNext(RestRequest request) {
    ((RestRequestHandler) handler()).handle(this, request);
  }

  private void doSend(RestRequest request, RestResponse response) {
    ((RestResponseHandler) handler()).handle(this, request, response);
  }

  private AbstractRestHandlerContext downstream() {
    AbstractRestHandlerContext ctx = this;
    do {
      ctx = ctx.next;
    } while (!(ctx.handler() instanceof RestRequestHandler));

    return ctx;
  }

  private AbstractRestHandlerContext upstream() {
    AbstractRestHandlerContext ctx = this;
    do {
      ctx = ctx.prev;
    } while (!(ctx.handler() instanceof RestResponseHandler));

    return ctx;
  }
}
