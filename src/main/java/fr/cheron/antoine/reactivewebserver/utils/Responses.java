package fr.cheron.antoine.reactivewebserver.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class Responses {

  public static Mono<ServerResponse> ok(String result) {
    return ServerResponse.ok().body(Mono.just(result), String.class);
  }

  public static Mono<ServerResponse> noContent(Object notUsed) {
    return ServerResponse.noContent().build();
  }

  public static Mono<ServerResponse> notFound(Exception e) {
    return ServerResponse.notFound().build();
  }

  public static Mono<ServerResponse> internalServerError(Exception e) {
    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).
      body(Mono.just(e.getMessage()), String.class);
  }

}
