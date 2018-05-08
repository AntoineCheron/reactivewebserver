package fr.cheron.antoine.reactivewebserver.exceptions;

public class NotFoundResourceException extends Exception {

  public NotFoundResourceException(String resourceName) {
    super("Resource " + resourceName + " not found.");
  }

}
