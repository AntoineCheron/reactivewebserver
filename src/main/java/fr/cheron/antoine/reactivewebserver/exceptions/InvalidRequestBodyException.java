package fr.cheron.antoine.reactivewebserver.exceptions;

public class InvalidRequestBodyException extends Exception {

  public InvalidRequestBodyException(Class<?> expectedClass) {
    super("The provided request body is invalid. Expected a " + expectedClass.getSimpleName() + " element.");
  }

}
