package hashtable.structure.tablestructure;

/*
 * Enumeração que define os tratamentos de colisão possíveis em uma tabela hash.
 * Usei apenas dois tratamentos de colisão (Encadeamento Exterior e Endereçamento Aberto).
 * Mas existem outros tratamentos de colisão, como: Encadeamento Interior, Dispersão Dupla, Rehashing, etc...
*/

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
