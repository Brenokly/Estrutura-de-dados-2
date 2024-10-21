package cache_eviction_final.structure.tablestructure;

/*
 * Estrutura de dados HashTable
 * 
 * Qual tipo de função hash foi usada?
 * 
 * Nessa tabela hash, para o nosso problema em específico, eu configurei para utilizar o método da divisão na função de hash.
 * É o método mais recomendado para o nosso problema já que as chaves serão sempre inteiros sequenciais. 1, 2, 3, 4, 5,...
 * E com a propriedade moduladora, a distribuição dos elementos na tabela hash será mais uniforme.
 * Com o tamanho da tabela hash sendo um número primo, a propriedade moduladora ajuda a minimizar colisões.
 * 
 * Porque não utilizar outro método?
 * 
 * A chave não é grande o suficiente para valer apena usar o método da dobra ou da análise de digito.
 * O método da multiplicação seria uma segunda boa opção para o problema, mas o método da divisão é mais simples e eficiente.
 * 
 * Qual tratamento de colisão foi usado?
 * 
 * O método de tratamento configurado é o do encadeamento exterior, onde cada posição da tabela hash tem a possibilidade de armazenar
 * mais de um elemento. Cada posição da tabela hash é uma lista encadeada, onde os elementos são inseridos no início da lista.
 * 
 * NÃO FIZ TESTES DE DESEMPENHO COM OUTROS MÉTODOS DE TRATAMENTO DE COLISÃO NA BASE DE DADOS!
 * 
 * Informações adicionais:
 * 
 * A tabela hash é redimensionável, ou seja, ela pode ser redimensionada quando a quantidade de elementos ultrapassar um certo limite.
 * Esse limite (fator de carga) eu testei vários valores! Com um fator de carga inferior a 1.0, a tabela hash ficou muito grande, mas
 * não apresenta NENHUMA COLISÃO. Com um fator de carga de 1.5, a tabela hash ficou com um tamanho bom e com poucas colisões.
 * 
 * O maior diferencial dessa tabela para a anterior é que, como um dos requisitos era que, caso a tabela da base dados utilizasse encadeamento
 * exterior, ela deveria ter uma lista-auto-ajustável. Isso meio que quebrou a ideia da tabela ser algo configurável, mas ainda assim, funciona!
 */

import cache_eviction_final.exceptions.*;
import cache_eviction_final.structure.OrderService;
import cache_eviction_final.structure.listsAAtructure.LinkedListAACF;
import java.util.List;
import java.util.ArrayList;

public class HashTable {
  // --------------------------------------------------------------------------------
  // Atributos

  int size; // Tamanho da tabela hash
  HashType hashType; // Tipo de função hash que será usada na tabela
  boolean resized; // Atributo para indicar se a tabela foi redimensionada
  int count; // Quantidade atual de elementos na tabela
  LinkedListAACF[] table; // Tabela hash

  // --------------------------------------------------------------------------------
  // Construtores

  public HashTable(int size, HashType hashType) {
    this.size = size; // Tamanho da tabela
    this.hashType = hashType; // Tipo de função hash
    this.resized = false; // Atributo para indicar se a tabela foi redimensionada
    this.count = 0; // Quantidade atual de elementos na tabela
    table = new LinkedListAACF[this.size]; // Inicializa a tabela hash
  }

  public HashTable(int size) {
    this.size = size;
    this.hashType = HashType.DIVISION; // Por padrão, o método da divisão será usado
    this.resized = false;
    this.count = 0;
    table = new LinkedListAACF[this.size]; // Inicializa a tabela hash
  }

  // --------------------------------------------------------------------------------
  // Funções hash's

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

  // Função hash para o endereçamento aberto
  private int hash(int key, int attempt) {
    return HashFunctions.doubleHash(key, size, attempt);
  }

  // --------------------------------------------------------------------------------
  // Métodos de redimensionamento

  // Método que redimensiona a tabela para um novo tamanho menor que o atual
  private void resizeDown() {
    int newSize;

    if (hashType == HashType.DIVISION) {
      newSize = previousCousin(size / 2);
    } else {
      newSize = size / 2;
    }

    resize(newSize);
  }

  private void resizeUp() {
    int newSize;

    if (hashType == HashType.DIVISION) {
      newSize = previousCousin(size * 2);
    } else {
      newSize = size * 2;
    }

    resize(newSize);
  }

  private void resize(int newSize) {
    resized = true; // Atributo para indicar que a tabela foi redimensionada
    size = newSize; // Atualiza o tamanho da tabela

    LinkedListAACF[] oldTable = table;
    table = new LinkedListAACF[size]; // Cria uma nova tabela com o novo tamanho

    count = 0; // Reinicia o contador de elementos

    // Reinsere os elementos na nova tabela
    for (int i = 0; i < oldTable.length; i++) {
      if (oldTable[i] != null) {
        for (int j = 0; j < oldTable[i].size(); j++) {
          insert(oldTable[i].peek(j));
        }
      }
    }
  }

  // --------------------------------------------------------------------------------
  // Métodos auxiliares

  // Métodos que busca o próximo primo maior que N
  private int previousCousin(int n) {
    int candidato;

    // Começa de um número ímpar menor que n
    if (n % 2 == 0) {
      candidato = n - 1;
    } else {
      candidato = n - 2;
    }

    // Continua decrementando até encontrar um primo
    while (candidato > 1 && !isCousin(candidato)) {
      candidato -= 2;
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

  public int getQuantityRecords() {
    return count;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  // Método que verifica se a tabela foi redimensionada e reseta o atributo
  // resized
  public boolean heWasResized() {
    boolean resizedAux = this.resized;
    this.resized = false;
    return resizedAux;
  }

  public void clear() throws InvalidOperationException {
    if (isEmpty()) {
      return; // Nenhuma ação é necessária se a tabela já estiver vazia
    }

    count = 0; // Reinicia o contador de elementos

    for (int i = 0; i < table.length; i++) {
      table[i] = null; // Limpa a tabela hash
    }
  }

  // --------------------------------------------------------------------------------
  // Métodos que envolvem a tabela hash como: Inserir, Buscar, Remover e Imprimir

  public void insert(OrderService order) {
    // Verifica se a tabela precisa ser redimensionada
    if ((((double) count) / ((double) size)) > 2.0f) {
      resizeUp();
    }

    // Calcula o hash do elemento
    int hashCode = hash(order.getCode());

    // Se a posição da tabela estiver vazia, cria uma nova lista e insere o elemento
    if (table[hashCode] == null) {
      table[hashCode] = new LinkedListAACF();
      table[hashCode].insertLast(order);
      count++;
      return;
    } else {
      // Se a posição da tabela não estiver vazia, insere o elemento no início da
      // lista
      table[hashCode].insertLast(order);
    }

    count++;
  }

  public OrderService search(int code) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    int hashCode = hash(code);

    if (table[hashCode] != null) {
      return table[hashCode].search(code);
    }

    throw new ElementNotFoundException("Elemento não encontrado");
  }

  public void alter(OrderService orderChanged) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    int hashCode = hash(orderChanged.getCode());

    if (table[hashCode] != null) {
      table[hashCode].alter(orderChanged);
      return;
    }

    throw new ElementNotFoundException("Tabela vazia");
  }

  public List<OrderService> list() throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    List<OrderService> list = new ArrayList<OrderService>();

    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        for (int j = 0; j < table[i].size(); j++) {
          list.add(table[i].peek(j));
        }
      }
    }

    return list;
  }

  public void remove(int code) throws InvalidOperationException {
    if (isEmpty()) {
      throw new InvalidOperationException("Elemento não existe! Tabela está vazia");
    }

    if ((((double) count) / ((double) size)) < 0.3f) {
      resizeDown();
    }

    int hashCode = hash(code);

    if (table[hashCode] != null) {
      table[hashCode].remove(code);
      count--;

      if (table[hashCode].isEmpty()) {
        table[hashCode] = null;
      }

      return;
    }

    throw new InvalidOperationException("Elemento não existe! Tabela está vazia");
  }

  public void printHashTable() {
    if (isEmpty()) {
      System.out.println("Tabela vazia");
      return;
    }

    System.out.println("========== Hash Table ==========");
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        for (int j = 0; j < table[i].size(); j++) {
          System.out.printf("%d: [Código: %d, Nome: %s, Descrição: %s, Hora: %s] -> ",
              i, table[i].peek(j).getCode(), table[i].peek(j).getName(), table[i].peek(j).getDescription(),
              table[i].peek(j).getRequestTime());
        }
        System.out.println("null");
      }
    }
    System.out.println("=========================================");
  }

  public String getTableState() {
    StringBuilder sb = new StringBuilder();

    if (isEmpty()) {
      return "Tabela vazia";
    }

    sb.append("Estado da Tabela Hash:\n");
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null && table[i].size() > 0) {
        sb.append("Indice ").append(i).append(": ");
        for (int j = 0; j < table[i].size(); j++) {
          OrderService order = table[i].peek(j);
          sb.append("[Código: ").append(order.getCode())
              .append(", Nome: ").append(order.getName())
              .append(", Descrição: ").append(order.getDescription())
              .append(", Hora: ").append(order.getRequestTime()).append("] ");
        }
        sb.append("\n");
      }
    }

    return sb.toString();
  }
}