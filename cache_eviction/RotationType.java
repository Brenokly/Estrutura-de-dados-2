package cache_eviction;

public enum RotationType {
  LEFT, RIGHT, LEFT_RIGHT, RIGHT_LEFT, NONE;

  // método para retornar em string o tipo de rotação
  public String toString() {
    switch (this) {
      case LEFT:
        return "Rotacao a esquerda";
      case RIGHT:
        return "Rotacao a direita";
      case LEFT_RIGHT:
        return "Rotacao dupla a esquerda";
      case RIGHT_LEFT:
        return "Rotacao dupla a direita";
      case NONE:
        return "Nenhuma rotacao";
      default:
        return "Tipo de rotacao invalido";
    }
  }
}