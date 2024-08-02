package cache_eviction.exceptions;

public class NodeNotFoundException extends RuntimeException {
  public NodeNotFoundException(String message) {
    super(message);
  }
}
