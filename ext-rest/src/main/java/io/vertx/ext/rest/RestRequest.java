package io.vertx.ext.rest;

import io.vertx.core.http.HttpServerRequest;

import java.util.Map;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public interface RestRequest {
  HttpServerRequest http();

  default String method() {
    return http().method();
  }

  default String path() {
    return http().path();
  }

  default String param(String name) {
    return http().params().get(name);
  }

  String pathParam(String name);

  Map<String, String> pathParams();

  Object body();

  RestRequest body(Object body);
}
