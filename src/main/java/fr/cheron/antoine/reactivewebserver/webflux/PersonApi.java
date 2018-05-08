package fr.cheron.antoine.reactivewebserver.webflux;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import fr.cheron.antoine.reactivewebserver.exceptions.NotFoundResourceException;
import fr.cheron.antoine.reactivewebserver.services.PersonService;
import fr.cheron.antoine.reactivewebserver.json.Json;
import fr.cheron.antoine.reactivewebserver.utils.Responses;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

public class PersonApi {

  // Path variables
  public static final String PERSON_ID_PATH_VARIABLE = "id";

  // Paths
  public static final String BASE_PATH = "";
  public static final String ONE_PERSON = BASE_PATH + "/{" + PERSON_ID_PATH_VARIABLE + "}";

  private final PersonService personService;

  public final RouterFunction routerFunction;

  public PersonApi(PersonService personService) {
    this.personService = personService;

    this.routerFunction = RouterFunctions.
      route(GET(BASE_PATH), this::getAllPersons).
      andRoute(GET(ONE_PERSON), this::getOnePersonById).
      andRoute(POST(ONE_PERSON), this::updateOnePersonById).
      andRoute(DELETE(ONE_PERSON), this::deleteOnePersonById).
      andRoute(POST(BASE_PATH), this::createOnePerson);

  }

  private Mono<ServerResponse> getAllPersons(ServerRequest request) {
    return this.personService.
      list().
      collectList().
      flatMap(Json::write).
      flatMap(Responses::ok).
      onErrorResume(JsonProcessingException.class, Responses::internalServerError);
  }

  private Mono<ServerResponse> getOnePersonById(ServerRequest request) {
    return this.personService.
      findById(request.pathVariable(PERSON_ID_PATH_VARIABLE)).
      flatMap(Json::write).
      flatMap(Responses::ok).
      onErrorResume(NotFoundResourceException.class, Responses::notFound).
      onErrorResume(JsonProcessingException.class,Responses::internalServerError);
  }

  private Mono<ServerResponse> updateOnePersonById(ServerRequest request) {
    return request.bodyToMono(String.class).
    return this.personService.
      updateOneById(request.pathVariable(PERSON_ID_PATH_VARIABLE)).
      flatMap(Json::write).
      flatMap(Responses::ok).
      onErrorResume(NotFoundResourceException.class, Responses::notFound).
      onErrorResume(JsonProcessingException.class,Responses::internalServerError);
  }


}
