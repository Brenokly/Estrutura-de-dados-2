package hashtable.structure.tablestructure;

public enum CollisionTreatment {
  ENCADEAMENTO_EXTERIOR,
  ENDERECAMENTO_ABERTO;

  public String toString() {
    switch (this) {
      case ENCADEAMENTO_EXTERIOR:
        return "Encadeamento Exterior";
      case ENDERECAMENTO_ABERTO:
        return "Endereçamento Aberto";
      default:
        return "Encadeamento Exterior";
    }
  }
}
