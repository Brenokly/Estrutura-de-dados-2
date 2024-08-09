package cache_eviction.exceptions;

public class NodeAlreadyExistsException extends Exception { // Quando o nó já existe
  public NodeAlreadyExistsException(String message) {
    super(message);
  }
}