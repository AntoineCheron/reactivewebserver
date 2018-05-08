package fr.cheron.antoine.reactivewebserver;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.ipc.netty.http.server.HttpServer;

import fr.cheron.antoine.reactivewebserver.services.PersonService;
import fr.cheron.antoine.reactivewebserver.webflux.PersonApi;

public class Main {

  public static void main(String[] args) {
    final PersonService personService = new PersonService(Config.APPLICATION_SCHEDULER);
    final PersonApi personApi = new PersonApi(personService);

    final RouterFunction<ServerResponse> routerFunction = RouterFunctions.
      nest(RequestPredicates.path("/persons"), personApi.routerFunction);
    final HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
    final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

    HttpServer.
      create(Config.HOST, Config.PORT).
      startAndAwait(adapter);
  }

}
