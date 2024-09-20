package hashtable.structure.tablestructure;

import hashtable.structure.OrderService;

/*
 * Classe abstrata Node que é utilizada para a criação de dois tipos de nós:
 * 
 * NodeExternal: Nó com ponteiro para o próximo elemento utilizado no encadeamento
 * 
 * NodeOpen: Nó sem ponteiro para o próximo elemento utilizado no endereçamento aberto
 * 
 * Ambos os nós possuem um atributo data que é do tipo OrderService
*/

public abstract class Node {
  OrderService data;

  Node() {
  }

  Node(OrderService data) {
    this.data = data;
  }

  // Node com ponteiro para o próximo elemento utilizado no encadeamento exterior
  // e entre outros
  public static class NodeExternal extends Node {
    Node next;

    NodeExternal() {
    }

    NodeExternal(OrderService data) {
      super(data);
    }
  }

  // Node sem ponteiro para o próximo elemento utilizado no enderecamento aberto
  public static class NodeOpen extends Node {

    NodeOpen() {
    }

    NodeOpen(OrderService data) {
      super(data);
    }
  }
}
