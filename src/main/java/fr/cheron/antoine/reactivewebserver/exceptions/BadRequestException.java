package fr.cheron.antoine.reactivewebserver.exceptions;

public class BadRequestException extends Exception {

  public BadRequestException() {}

  public BadRequestException(String message) {
    super(message);
  }

}
