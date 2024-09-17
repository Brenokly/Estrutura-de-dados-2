package hashtable.structure;

/*
 * Estrutura de dados HashTable
 * 
 * Nessa tabela hash, para o nosso problema em específico, eu vou utilizar o método da divisão na função de hash.
 * É o método mais recomendado para o nosso problema já que as chaves serão sempre inteiros sequenciais. 1, 2, 3, 4, 5, ...
 * A chave não é grande o suficiente para valer apena usar o método da dobra ou da análise de digito.
 * O método da multiplicação seria uma segunda boa opção para o problema, mas o método da divisão é mais simples.
 * 
 */

import hashtable.exceptions.*;

public class HashTable {
  final double IGR = 0.6180339887; // IGR = Inverse Golden Ratio

  class Node {
    OrderService data;
    Node next;

    Node() {
    }

    Node(OrderService data) {
      this.data = data;
    }
  }

  // --------------------------------------------------------------------------------

  int size; // Tamanho da tabela hash
  HashType hashType; // Tipo de função hash que será usada na tabela
  boolean resized; // Atributo para indicar se a tabela foi redimensionada
  boolean resizable; // Atributo para indicar se a tabela pode ser redimensionada
  int count; // Quantidade de elementos na tabela
  Node[] table; // Tabela hash

  // --------------------------------------------------------------------------------

  public HashTable(int size, HashType hashType, boolean resizable) {
    this.size = size;
    this.hashType = hashType;
    this.resizable = resizable;
    this.resized = false;
    this.count = 0;
    table = new Node[this.size];
  }

  public HashTable(int size) {
    this.size = size;
    this.hashType = HashType.DIVISION; // Por padrão, o método da divisão será usado
    this.resizable = true; // Por padrão, a tabela pode ser redimensionada
    this.resized = false;
    this.count = 0;
    table = new Node[this.size];
  }

  public HashTable(int size, boolean resizable) {
    this.size = size;
    this.hashType = HashType.DIVISION; // Por padrão, o método da divisão será usado
    this.resizable = resizable;
    this.resized = false;
    this.count = 0;
    table = new Node[this.size];
  }

  // --------------------------------------------------------------------------------
  // Métedos que envolvem a tabela hash como: Função Hash e Redimensionamento da
  // tabela

  private int hash(int key) {
    switch (hashType) {
      case DIVISION:
        return key % this.size;

      case MULTIPLICATION:
        return (int) (this.size * ((key * IGR) % 1));

      case FOLDING:
        // Não pretendo implementar, mas existe

      case ANALYSIS:
        // Não pretendo implementar, mas existe

      default:
        return key % this.size; // Defini o método da divisão como padrão
    }
  }

  private void resize() {
    resized = true;
    int newSize;

    if (hashType == HashType.DIVISION) {
      newSize = nextCousin(size * 2);
    } else {
      newSize = size * 2;
    }

    Node[] oldTable = table;
    table = new Node[newSize];
    size = newSize;
    count = 0; // Will be updated in the rehash process

    for (Node head : oldTable) {
      Node current = head;
      while (current != null) {
        insert(current.data); // Rehash elements
        current = current.next;
      }
    }
  }

  // Métodos que busca o próximo primo maior que N
  private int nextCousin(int n) {
    int candidato;
    if (n % 2 == 0) {
      candidato = n + 1;
    } else {
      candidato = n + 2;
    }

    while (!isCousin(candidato)) {
      candidato += 2;
    }

    return candidato;
  }

  // Método que verifica se um número é primo
  private boolean isCousin(int n) {
    if (n <= 1) {
      return false;
    }
    if (n <= 3) {
      return true;
    }
    if (n % 2 == 0 || n % 3 == 0) {
      return false;
    }

    // Checa divisibilidade de n por números da forma 6k ± 1 até √n
    for (int i = 5; i * i <= n; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0)
        return false;
    }

    return true;
  }

  // --------------------------------------------------------------------------------
  // Métodos que envolvem a tabela hash como: Inserir, Buscar, Remover e Imprimir

  public boolean isResized() {
    boolean resizedAux = this.resized;
    this.resized = false;
    return resizedAux;
  }

  public int getQuantityRecords() {
    return count;
  }

  public int size() {
    return table.length;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public void clear() throws InvalidOperationException {
    if (isEmpty()) {
      throw new InvalidOperationException("Tabela vazia");
    }

    for (int i = 0; i < this.size; i++) {
      Node no = this.table[i];
      while (no != null) {
        Node next = no.next;
        no = null;
        no = next;
      }
      this.table[i] = null;
    }
  }

  public void insert(OrderService order) {
    if (resizable && (double) count / size > 0.75) {
      resize();
    }

    int hashCode = hash(order.getCodigo());
    Node newOrder = this.table[hashCode];

    while (newOrder != null) {

      if (newOrder.data == order) {
        return; // Não permite elementos duplicados
      }
      newOrder = newOrder.next;
    }

    if (newOrder == null) {
      newOrder = new Node(order);
      newOrder.next = this.table[hashCode];
      this.table[hashCode] = newOrder;
      count++;
    }
  }

  public OrderService search(int code) throws ElementNotFoundException {
    int hashCode = hash(code);
    Node newOrder = this.table[hashCode];

    while (newOrder != null) {
      if (newOrder.data.getCodigo() == code) {
        return newOrder.data;
      }
      newOrder = newOrder.next;
    }

    throw new ElementNotFoundException("Elemento não encontrado");
  }

  public boolean contains(int code) {
    int hashCode = hash(code);

    if (table[hashCode] == null) {
      return false;
    }

    Node newOrder = this.table[hashCode];

    while (newOrder != null) {
      if (newOrder.data.getCodigo() == code) {
        return true;
      }

      newOrder = newOrder.next;
    }

    return false;
  }

  public void remove(int code) throws InvalidOperationException {
    if (isEmpty()) {
      throw new InvalidOperationException("Tabela vazia");
    }

    int hashCode = hash(code);
    Node newOrder = this.table[hashCode];
    Node previous = null;

    while (newOrder != null) {
      if (newOrder.data.getCodigo() == code) {
        if (previous == null) {
          this.table[hashCode] = newOrder.next;
        } else {
          previous.next = newOrder.next;
        }
        return;
      }

      previous = newOrder;
      newOrder = newOrder.next;
    }
  }

  public void printHashTable() {
    Node no;
    for (int i = 0; i < this.size; i++) {

      no = this.table[i];

      System.out.print(i + ":");

      while (no != null) {
        System.out.print(" --> " + no.data.getCodigo() + " " + no.data.getNome() + " | ");
        no = no.next;
      }
      System.out.println();
    }
  }
}