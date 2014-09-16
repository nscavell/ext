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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.rest.impl.RestServerImpl;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public interface RestServer {

  static RestServer restServer(Vertx vertx, HttpServer server) {
    return new RestServerImpl(vertx, server);
  }

  RestServer start();

  RestServer start(Handler<AsyncResult<RestServer>> startHandler);

  RestServer stop();

  RestServer stop(Handler<AsyncResult<RestServer>> stopHandler);

  RestServer add(RestRequestHandler handler);

  RestServer add(RestResponseHandler handler);

  RestServer get(String path, RestRequestHandler handler);

  RestServer get(String path, String contentType, RestRequestHandler handler);

  RestServer post(String path, RestRequestHandler handler);

  RestServer post(String path, String contentType, RestRequestHandler handler);

  RestServer put(String path, RestRequestHandler handler);

  RestServer put(String path, String contentType, RestRequestHandler handler);

  RestServer delete(String path, RestRequestHandler handler);

  RestServer delete(String path, String contentType, RestRequestHandler handler);

  RestServer patch(String path, RestRequestHandler handler);

  RestServer patch(String path, String contentType, RestRequestHandler handler);

  RestServer options(String path, RestRequestHandler handler);

  RestServer options(String path, String contentType, RestRequestHandler handler);

  RestServer head(String path, RestRequestHandler handler);

  RestServer head(String path, String contentType, RestRequestHandler handler);
}
