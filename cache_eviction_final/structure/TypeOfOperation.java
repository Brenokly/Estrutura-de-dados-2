package cache_eviction_final.structure;

public enum TypeOfOperation {
  INSERT, SEARCH, ALTER, REMOVE, LIST;

  /*
   * Criei de todas as operações, porém, para o log Só serão utlizadas as
   * operações de inserção, alteração e remoção.
   */

  public String toString() {
    switch (this) {
      case INSERT:
        return "Insercao";
      case SEARCH:
        return "Busca";
      case ALTER:
        return "Alteracao";
      case REMOVE:
        return "Remocao";
      case LIST:
        return "Listagem";
      default:
        return "Operacao invalida";
    }
  }
}