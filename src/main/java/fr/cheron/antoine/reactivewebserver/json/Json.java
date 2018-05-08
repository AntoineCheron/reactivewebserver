package fr.cheron.antoine.reactivewebserver.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

public class Json {

  public static final ObjectMapper JSON = new ObjectMapper();

  public static Mono<String> write(Object value) {
    try { return Mono.just(Json.JSON.writeValueAsString(value)); }
    catch (JsonProcessingException e) { return Mono.error(e); }
  }

}
