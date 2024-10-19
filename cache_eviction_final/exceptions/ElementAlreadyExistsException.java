package cache_eviction_final.exceptions;

public class ElementAlreadyExistsException extends RuntimeException {
  public ElementAlreadyExistsException(String message) {
    super(message);
  }
}