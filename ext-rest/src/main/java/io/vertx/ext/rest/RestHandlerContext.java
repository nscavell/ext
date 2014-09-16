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

import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public interface RestHandlerContext {

  Vertx vertx();

  RestHandler handler();

  void next(RestRequest request);

  //TODO: I don't like having to pass in the request object
  void send(RestRequest request, RestResponse response);

  default void error(RestRequest request, Throwable cause) {
    RestResponse response = new RestResponse(500);
    response.cause(cause);

    send(request, response);
  }
}
