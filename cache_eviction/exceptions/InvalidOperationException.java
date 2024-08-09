package cache_eviction.exceptions;

public class InvalidOperationException extends Exception { // Quando a operação não é válida
  public InvalidOperationException(String message) {       // Exemplo: tentar remover um nó que não existe
    super(message);
  }
}