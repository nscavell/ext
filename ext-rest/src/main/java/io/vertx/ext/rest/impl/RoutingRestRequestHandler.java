package io.vertx.ext.rest.impl;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.rest.RestHandlerContext;
import io.vertx.ext.rest.RestRequest;
import io.vertx.ext.rest.RestRequestHandler;
import io.vertx.ext.rest.RestResponse;
import io.vertx.ext.routematcher.RouteMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class RoutingRestRequestHandler implements RestRequestHandler {
  private final Map<String, Route> routes;
  private final RouteMatcher routeMatcher;

  public RoutingRestRequestHandler() {
    this.routes = new HashMap<>();
    this.routeMatcher = RouteMatcher.routeMatcher();
  }

  @Override
  public void handle(RestHandlerContext ctx, RestRequest request) {
    routeMatcher.match(request.http(), (handler, params) -> {
      if (handler != null) {
        Route route = (Route) handler;
        request.pathParams().putAll(params);
        route.handle(ctx, request);
      } else {
        ctx.send(request, RestResponse.notFound());
      }
    });
  }

  public void add(String path, String method, String contentType, RestRequestHandler handler) {
    //routeMatcher.add(path, method, new Route(handler));

    Route route = new Route();
    Route existing = routes.putIfAbsent(path, route);
    if (existing != null) {
      route = existing;
    } else {
      routeMatcher.add(path, method, route);
    }

    route.add(contentType, handler);
  }

  private static class Route implements Handler<HttpServerRequest> {
    private Map<String, RestRequestHandler> handlers = new HashMap<>();
    private RestRequestHandler wildcard;

    private void add(String contentType, RestRequestHandler handler) {
      if (contentType == null || "*/*".equals(contentType)) {
        wildcard = handler;
      } else {
        handlers.put(contentType, handler);
      }
    }

    @Override
    public void handle(HttpServerRequest request) {
      throw new UnsupportedOperationException("Should not have gotten here...");
    }

    public void handle(RestHandlerContext ctx, RestRequest request) {
      String accept = request.http().headers().get("Accept");
      //TODO: Parse this appropriately including media-range, accept-params (q=), etc
      RestRequestHandler handler = handlers.get(accept);
      if (handler == null) {
        handler = wildcard;
      }

      if (handler != null) {
        handler.handle(ctx, request);
      }
    }
  }
}
