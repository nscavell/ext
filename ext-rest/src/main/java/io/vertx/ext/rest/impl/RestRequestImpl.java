package io.vertx.ext.rest.impl;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.rest.RestRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class RestRequestImpl implements RestRequest {
  private final HttpServerRequest request;
  private final Map<String, String> pathParams;
  private Object body;

  public RestRequestImpl(HttpServerRequest request, Object body) {
    this.request = request;
    this.pathParams = new HashMap<>();
    this.body = body;
  }

  @Override
  public HttpServerRequest http() {
    return request;
  }

  @Override
  public String pathParam(String name) {
    return pathParams.get(name);
  }

  @Override
  public Map<String, String> pathParams() {
    return pathParams;
  }

  @Override
  public Object body() {
    return body;
  }

  @Override
  public RestRequest body(Object body) {
    this.body = body;
    return this;
  }
}
