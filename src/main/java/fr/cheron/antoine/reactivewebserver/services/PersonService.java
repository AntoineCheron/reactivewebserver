package fr.cheron.antoine.reactivewebserver.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import fr.cheron.antoine.reactivewebserver.Config;
import fr.cheron.antoine.reactivewebserver.exceptions.ForbiddenResourceOverrideException;
import fr.cheron.antoine.reactivewebserver.exceptions.NotFoundResourceException;
import fr.cheron.antoine.reactivewebserver.domain.Person;
import fr.cheron.antoine.reactivewebserver.utils.MonoUtils;

public class PersonService {

  private final List<Person> persons;

  public PersonService() {
    this.persons = Stream.of(
      new Person("1", "Foo", "Bar", 50),
      new Person("2", "Toto", "Tuto", 30),
      new Person("3", "John", "Doe", 30),
      new Person("4", "John", "Duff", 16)
    ).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
  }

  public Flux<Person> list() {
    return Flux.fromIterable(this.persons).subscribeOn(Config.APPLICATION_SCHEDULER);
  }

  public Mono<Person> findById(String id) {
    return Mono.fromSupplier(() -> this.persons). // to make sure nothing gets executed until one subscribes to the mono
      flatMap((persons) -> MonoUtils.fromOptionalWithNotFoundException(
        persons.stream().filter((person) -> person.getId().equals(id)).findFirst(),
        "person " + id
      )).subscribeOn(Config.APPLICATION_SCHEDULER);
  }

  public Mono<Boolean> deleteOneById(String id) {
    return this.findById(id).
      map(this.persons::remove).
      flatMap((succeeded) ->
        succeeded ? Mono.just(true) : Mono.error(new NotFoundResourceException("person " + id))
      ).
      subscribeOn(Config.APPLICATION_SCHEDULER);
  }

  public Mono<Boolean> createOne(Person person) {
    return this.findById(person.getId()).
      flatMap((notUsed) -> Mono.<Boolean>error(new ForbiddenResourceOverrideException())).
      onErrorResume(
        NotFoundResourceException.class,
        (notUsedException) -> Mono.fromCallable(() -> {
          this.persons.add(person);
          return true;
        })
      ).subscribeOn(Config.APPLICATION_SCHEDULER);
  }

  public Mono<Boolean> updateOneById(Person person) {
    return this.findById(person.getId()).
      map((previous) -> {
        this.persons.remove(previous);
        this.persons.add(person);
        return true;
      }).
      subscribeOn(Config.APPLICATION_SCHEDULER);
  }

}
