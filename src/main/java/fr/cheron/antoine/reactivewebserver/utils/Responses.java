package fr.cheron.antoine.reactivewebserver.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class Responses {

  public static Mono<ServerResponse> ok(String result) {
    return ServerResponse.ok().body(Mono.just(result), String.class);
  }

  public static Mono<ServerResponse> badRequest(Exception e) {
    return fromException(HttpStatus.BAD_REQUEST, e);
  }

  public static Mono<ServerResponse> forbidden(Exception... notUsedException) {
    return ServerResponse.status(HttpStatus.FORBIDDEN).build();
  }

  public static Mono<ServerResponse> noContent(Object... notUsed) {
    return ServerResponse.noContent().build();
  }

  public static Mono<ServerResponse> notFound(Exception... notUsed) {
    return ServerResponse.notFound().build();
  }

  public static Mono<ServerResponse> internalServerError() {
    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  public static Mono<ServerResponse> internalServerError(Exception e) {
    return fromException(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  private static Mono<ServerResponse> fromException(HttpStatus status, Exception e) {
    final ServerResponse.BodyBuilder responseBuilder = ServerResponse.status(status);
    return e.getMessage() == null
      ? responseBuilder.build()
      : responseBuilder.body(Mono.just(e.getMessage()), String.class);
  }

}
