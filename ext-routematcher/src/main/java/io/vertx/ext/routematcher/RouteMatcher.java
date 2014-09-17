/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.routematcher;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.ServiceHelper;
import io.vertx.core.http.HttpServerRequest;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface RouteMatcher {

  static RouteMatcher routeMatcher() {
    return factory.routeMatcher();
  }

  @Fluent
  RouteMatcher accept(HttpServerRequest request);

  @Fluent
  RouteMatcher match(HttpServerRequest request, BiConsumer<Handler<HttpServerRequest>, Map<String, String>> consumer);

  /**
   * Specify a handler that will be called for a matching HTTP GET
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher get(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP PUT
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher put(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP POST
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher post(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP DELETE
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher delete(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP OPTIONS
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher options(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP HEAD
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher head(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP TRACE
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher trace(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP CONNECT
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher connect(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP PATCH
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher patch(String pattern, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for all HTTP methods
   * @param pattern The simple pattern
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher all(String pattern, Handler<HttpServerRequest> handler);

  @Fluent
  RouteMatcher add(String pattern, String method, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP GET
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher getWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP PUT
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher putWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP POST
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher postWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP DELETE
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher deleteWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP OPTIONS
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher optionsWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP HEAD
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher headWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP TRACE
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher traceWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP CONNECT
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher connectWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for a matching HTTP PATCH
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher patchWithRegEx(String regex, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called for all HTTP methods
   * @param regex A regular expression
   * @param handler The handler to call
   */
  @Fluent
  RouteMatcher allWithRegEx(String regex, Handler<HttpServerRequest> handler);

  @Fluent
  RouteMatcher addWithRegEx(String regex, String method, Handler<HttpServerRequest> handler);

  /**
   * Specify a handler that will be called when no other handlers match.
   * If this handler is not specified default behaviour is to return a 404
   */
  @Fluent
  RouteMatcher noMatch(Handler<HttpServerRequest> handler);

  static final RouteMatcherFactory factory = ServiceHelper.loadFactory(RouteMatcherFactory.class);
}
