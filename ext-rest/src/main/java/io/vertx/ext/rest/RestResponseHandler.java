package io.vertx.ext.rest;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public interface RestResponseHandler extends RestHandler {
  void handle(RestHandlerContext ctx, RestRequest request, RestResponse response);
}
