/*
 * Copyright (c) 2011-2013 The original author or authors
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

package io.vertx.ext.sockjs.impl;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.routematcher.RouteMatcher;
import io.vertx.ext.sockjs.SockJSServerOptions;
import io.vertx.ext.sockjs.SockJSSocket;

import static io.vertx.core.buffer.Buffer.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
class HtmlFileTransport extends BaseTransport {

  private static final Logger log = LoggerFactory.getLogger(HtmlFileTransport.class);

  private static final String HTML_FILE_TEMPLATE;

  static {
    String str =
    "<!doctype html>\n" +
    "<html><head>\n" +
    "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
    "</head><body><h2>Don't panic!</h2>\n" +
    "  <script>\n" +
    "    document.domain = document.domain;\n" +
    "    var c = parent.{{ callback }};\n" +
    "    c.start();\n" +
    "    function p(d) {c.message(d);};\n" +
    "    window.onload = function() {c.stop();};\n" +
    "  </script>";

    String str2 = str.replace("{{ callback }}", "");
    StringBuilder sb = new StringBuilder(str);
    int extra = 1024 - str2.length();
    for (int i = 0; i < extra; i++) {
      sb.append(' ');
    }
    sb.append("\r\n");
    HTML_FILE_TEMPLATE = sb.toString();
  }

  HtmlFileTransport(Vertx vertx, RouteMatcher rm, String basePath, LocalMap<String, Session> sessions, final SockJSServerOptions options,
            final Handler<SockJSSocket> sockHandler) {
    super(vertx, sessions, options);
    String htmlFileRE = basePath + COMMON_PATH_ELEMENT_RE + "htmlfile";

    rm.getWithRegEx(htmlFileRE, new Handler<HttpServerRequest>() {
      public void handle(final HttpServerRequest req) {
        if (log.isTraceEnabled()) log.trace("HtmlFile, get: " + req.uri());
        String callback = req.params().get("callback");
        if (callback == null) {
          callback = req.params().get("c");
          if (callback == null) {
            req.response().setStatusCode(500);
            req.response().end("\"callback\" parameter required\n");
            return;
          }
        }

        String sessionID = req.params().get("param0");
        Session session = getSession(options.getSessionTimeout(), options.getHeartbeatPeriod(), sessionID, sockHandler);
        session.setInfo(req.localAddress(), req.remoteAddress(), req.uri(), req.headers());
        session.register(new HtmlFileListener(options.getMaxBytesStreaming(), req, callback, session));
      }
    });
  }

  private class HtmlFileListener extends BaseListener {

    final int maxBytesStreaming;
    final String callback;
    boolean headersWritten;
    int bytesSent;
    boolean closed;

    HtmlFileListener(int maxBytesStreaming, HttpServerRequest req, String callback, Session session) {
      super(req, session);
      this.maxBytesStreaming = maxBytesStreaming;
      this.callback = callback;
      addCloseHandler(req.response(), session);
    }

    public void sendFrame(String body) {
      if (log.isTraceEnabled()) log.trace("HtmlFile, sending frame");
      if (!headersWritten) {
        String htmlFile = HTML_FILE_TEMPLATE.replace("{{ callback }}", callback);
        req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
        setNoCacheHeaders(req);
        req.response().setChunked(true);
        setJSESSIONID(options, req);
        req.response().end(htmlFile);
        headersWritten = true;
      }
      body = escapeForJavaScript(body);
      StringBuilder sb = new StringBuilder();
      sb.append("<script>\np(\"");
      sb.append(body);
      sb.append("\");\n</script>\r\n");
      Buffer buff = buffer(sb.toString());
      req.response().end(buff);
      bytesSent += buff.length();
      if (bytesSent >= maxBytesStreaming) {
        if (log.isTraceEnabled()) log.trace("More than maxBytes sent so closing connection");
        // Reset and close the connection
        close();
      }
    }

    public void close() {
      if (!closed) {
        try {
          session.resetListener();
          req.response().end();
          req.response().close();
          closed = true;
        } catch (IllegalStateException e) {
          // Underlying connection might already be closed - that's fine
        }
      }
    }
  }
}
