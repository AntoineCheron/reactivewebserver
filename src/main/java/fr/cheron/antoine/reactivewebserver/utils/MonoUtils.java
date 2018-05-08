package fr.cheron.antoine.reactivewebserver.utils;

import java.util.Optional;

import fr.cheron.antoine.reactivewebserver.exceptions.NotFoundResourceException;
import reactor.core.publisher.Mono;

public class MonoUtils {

  public static <T> Mono<T> fromOptional(Optional<T> option) {
    return option.map(Mono::just).orElseGet(Mono::empty);
  }

  public static <T> Mono<T> fromOptionalWithNotFoundException(Optional<T> option, String resourceName) {
    return option.
      map(Mono::just).
      orElseGet(() -> Mono.error(new NotFoundResourceException(resourceName)));
  }

}
