package cache_eviction.exceptions;

public class NodeAlreadyExistsException extends RuntimeException {
  public NodeAlreadyExistsException(String message) {
    super(message);
  }
}