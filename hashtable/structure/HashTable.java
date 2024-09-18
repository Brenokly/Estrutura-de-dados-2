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
import java.util.List;
import java.util.ArrayList;
import hashtable.structure.HashFunctions;

public class HashTable {
  final double IGR = 0.6180339887; // IGR = Inverse Golden Ratio
  final int mask = 0b00000111111000000000; // Máscara para extrair os 5 bits centrais

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
        return HashFunctions.divisionHash(key, size);

      case MULTIPLICATION:
        return HashFunctions.multiplicationHash(key, size);

      case FOLDING:
        return HashFunctions.foldingHash(key, size);

      case ANALYSIS:
        return HashFunctions.analysisHash(key, size);

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

  public boolean heWasResized() {
    boolean resizedAux = this.resized;
    this.resized = false;
    return resizedAux;
  }

  public int getQuantityRecords() {
    return count;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public void clear() throws InvalidOperationException {
    if (isEmpty()) {
      return; // Poderia lançar uma exceção, mas não é necessário
    }

    count = 0;

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

    int hashCode = hash(order.getCode());
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

  public int exclusiveInsertion(OrderService order) {
    if (resizable && (double) count / size > 0.75) {
      resize();
    }

    int hashCode = hash(order.getCode());
    int codeExcluded = -1;

    if (this.table[hashCode] == null) {
      // Não deveria entrar aqui, já que esse método só deve ser chamado se a tabela
      // está cheia
      // Mas esse if evita um NullPointerException
      this.table[hashCode] = new Node(order);
      count++;
    } else {
      codeExcluded = this.table[hashCode].data.getCode(); // código do excluído
      this.table[hashCode].data = order;
    }

    return codeExcluded;
  }

  public OrderService search(int code) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    int hashCode = hash(code);
    Node newOrder = this.table[hashCode];

    while (newOrder != null) {
      if (newOrder.data.getCode() == code) {
        return newOrder.data;
      }
      newOrder = newOrder.next;
    }

    throw new ElementNotFoundException("Elemento não encontrado");
  }

  public void alter(OrderService orderChanged) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    int hashCode = hash(orderChanged.getCode());
    Node newOrder = this.table[hashCode];

    if (newOrder == null) {
      throw new ElementNotFoundException("Elemento não encontrado");
    }

    while (newOrder != null) {
      if (newOrder.data.getCode() == orderChanged.getCode()) {
        newOrder.data = orderChanged;
        return;
      }
      newOrder = newOrder.next;
    }
  }

  public List<OrderService> list() throws ElementNotFoundException {
    List<OrderService> list = new ArrayList<OrderService>();

    for (int i = 0; i < table.length; i++) {
      Node newOrder = this.table[i];

      while (newOrder != null) {
        list.add(newOrder.data);
        newOrder = newOrder.next;
      }
    }

    return list;
  }

  public boolean contains(int code) {
    if (isEmpty()) {
      return false;
    }

    int hashCode = hash(code);

    if (table[hashCode] == null) {
      return false;
    }

    Node newOrder = this.table[hashCode];

    while (newOrder != null) {
      if (newOrder.data.getCode() == code) {
        return true;
      }
      newOrder = newOrder.next;
    }

    return false;
  }

  public void remove(int code) throws InvalidOperationException {
    if (isEmpty()) {
      throw new InvalidOperationException("Elemento não existe! Tabela está vazia");
    }

    int hashCode = hash(code);
    Node newOrder = this.table[hashCode];
    Node previous = null;

    while (newOrder != null) {
      if (newOrder.data.getCode() == code) {
        if (previous == null) {
          this.table[hashCode] = newOrder.next;
        } else {
          previous.next = newOrder.next;
        }
        count--;
        return;
      }

      previous = newOrder;
      newOrder = newOrder.next;
    }

    throw new InvalidOperationException("Elemento não existe!");
  }

  public void printHashTable() {
    if (isEmpty()) {
      return;
    }

    Node no;
    for (int i = 0; i < this.size; i++) {

      no = this.table[i];

      if (no == null) {
        continue;
      }

      System.out.print("posição: " + i + ":");
      while (no != null) {
        System.out.print(" --> ");
        System.out.print("Code: " + no.data.getCode() + " | ");
        no = no.next;
      }
      System.out.println();
    }
    System.out.println();
  }

  public String getTableState() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < table.length; i++) {
      Node current = table[i];
      if (current == null) {
        continue;
      }
      sb.append(i).append(": ");
      while (current != null) {
        sb.append(current.data.getCode()).append(" -> ");
        current = current.next;
      }
      sb.append("null");
      sb.append("\n");
    }

    return sb.toString();
  }
}