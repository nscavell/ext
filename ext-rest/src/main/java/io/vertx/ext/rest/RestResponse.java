package io.vertx.ext.rest;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class RestResponse {

  public static RestResponse ok() {
    return new RestResponse(200);
  }

  public static RestResponse created() {
    return new RestResponse(201);
  }

  public static RestResponse notFound() {
    return error(404);
  }

  public static RestResponse error(int statusCode) {
    return error(statusCode, null);
  }

  public static RestResponse error(int statusCode, String statusMessage) {
    if (statusCode < 400) {
      throw new IllegalArgumentException("Error status codes should be greater then 400");
    }
    return new RestResponse(statusCode, statusMessage);
  }

  private final int statusCode;
  private final String statusMessage;
  private Object body;
  private Throwable cause;

  public RestResponse(int statusCode) {
    this(statusCode, null, null);
  }

  public RestResponse(int statusCode, String statusMessage) {
    this(statusCode, statusMessage, null);
  }

  public RestResponse(int statusCode, String statusMessage, Object body) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
    this.body = body;
  }

  public int statusCode() {
    return statusCode;
  }

  public String statusMessage() {
    return statusMessage;
  }

  public Object body() {
    return body;
  }

  public RestResponse body(Object body) {
    this.body = body;
    return this;
  }

  public Throwable cause() {
    return cause;
  }

  public RestResponse cause(Throwable cause) {
    this.cause = cause;
    return this;
  }
}
