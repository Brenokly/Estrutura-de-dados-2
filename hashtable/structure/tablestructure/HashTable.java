package hashtable.structure.tablestructure;

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
 * A tabela foi construída para ser bem flexível e fácil de ser adaptada para outros problemas.
 * Ela pode ser "configurada" para usar outros métodos de função hash e tratamento de colisão.
 */

import hashtable.exceptions.*;
import hashtable.structure.OrderService;
import hashtable.structure.tablestructure.Node.NodeExternal;
import hashtable.structure.tablestructure.Node.NodeOpen;
import java.util.List;
import java.util.ArrayList;

public class HashTable {
  // --------------------------------------------------------------------------------

  int size; // Tamanho da tabela hash
  HashType hashType; // Tipo de função hash que será usada na tabela
  boolean resized; // Atributo para indicar se a tabela foi redimensionada
  boolean resizable; // Atributo para indicar se a tabela pode ou não ser redimensionada
  int count; // Quantidade atual de elementos na tabela
  CollisionTreatment collisionTreatment; // Tipo de tratamento de colisão que será usado
  Node[] table; // Tabela hash

  // --------------------------------------------------------------------------------
  // Construtores

  public HashTable(int size, HashType hashType, boolean resizable, CollisionTreatment collisionTreatment) {
    this.size = size; // Tamanho da tabela
    this.hashType = hashType; // Tipo de função hash
    this.resizable = resizable; // Se a tabela pode ser redimensionada
    this.resized = false; // Atributo para indicar se a tabela foi redimensionada
    this.count = 0; // Quantidade atual de elementos na tabela
    this.collisionTreatment = collisionTreatment; // Tipo de tratamento de colisão

    // Cria a tabela hash com base no tratamento de colisão
    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      table = new NodeExternal[this.size]; // NodeExternal tem um atributo next
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      table = new NodeOpen[this.size]; // NodeOpen é um nó simples só com o atributo data
    }
  }

  // NodeExternal é um nó que tem um atributo next, criado para ser usado no
  // encadeamento exterior.
  // NodeOpen é um nó simples que tem apenas o atributo data, criado para ser
  // usado no endereçamento aberto.
  // Já que ele não precisa de um atributo next, pois o próximo elemento é
  // encontrado através da função hash.

  public HashTable(int size) {
    this.size = size;
    this.hashType = HashType.DIVISION; // Por padrão, o método da divisão será usado
    this.resizable = true; // Por padrão, a tabela pode ser redimensionada
    this.resized = false;
    this.count = 0;
    this.collisionTreatment = CollisionTreatment.ENCADEAMENTO_EXTERIOR; // Padrão
    table = new NodeExternal[this.size];
  }

  public HashTable(int size, boolean resizable) {
    this.size = size;
    this.hashType = HashType.DIVISION; // Por padrão, o método da divisão será usado
    this.resizable = resizable;
    this.resized = false;
    this.count = 0;
    this.collisionTreatment = CollisionTreatment.ENCADEAMENTO_EXTERIOR; // Padrão
    table = new NodeExternal[this.size];
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

  private void resizeDown() {
    resized = true; // Atributo para indicar que a tabela foi redimensionada
    int newSize;

    if (hashType == HashType.DIVISION) {
      newSize = previousCousin(size / 2);
    } else {
      newSize = size / 2;
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      resizeExternal(newSize);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      resizeOpen(newSize);
    }
  }

  private void resizeUp() {
    resized = true; // Atributo para indicar que a tabela foi redimensionada
    int newSize;

    if (hashType == HashType.DIVISION) {
      newSize = previousCousin(size * 2);
    } else {
      newSize = size * 2;
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      resizeExternal(newSize);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      resizeOpen(newSize);
    }
  }

  // Redimensiona a tabela hash quando o tratamento de colisão é o endereçamento
  // aberto
  private void resizeOpen(int newSize) {
    NodeOpen[] oldTable = (NodeOpen[]) table;
    table = new NodeOpen[newSize];
    size = newSize;
    count = 0; 

    // Rehash elements
    for (int i = 0; i < oldTable.length; i++) {
      if (oldTable[i] != null) {
        insert(oldTable[i].data);
      }
    }
  }

  // Redimensiona a tabela hash quando o tratamento de colisão é o encadeamento
  // exterior
  private void resizeExternal(int newSize) {
    NodeExternal[] oldTable = (NodeExternal[]) table;
    table = new NodeExternal[newSize];
    size = newSize;
    count = 0; 

    // Rehash elements
    for (int i = 0; i < oldTable.length; i++) {
      if (oldTable[i] instanceof NodeExternal) {
        NodeExternal current = (NodeExternal) oldTable[i];
        while (current != null) {
          insert(current.data);
          current = (NodeExternal) current.next;
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
  
  // Método que verifica se a tabela foi redimensionada e reseta o atributo resized
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

    // Itera sobre a tabela hash
    for (int i = 0; i < this.size; i++) {
      Node no = this.table[i];

      // Caso o node seja um NodeExternal, percorre a lista encadeada
      while (no != null && no instanceof Node.NodeExternal) {
        Node next = ((Node.NodeExternal) no).next;
        no = null; // Libera o nó atual
        no = next; // Vai para o próximo nó na lista encadeada
      }

      // Caso seja NodeOpen, simplesmente remove
      this.table[i] = null;
    }
  }

  // --------------------------------------------------------------------------------
  // Métodos que envolvem a tabela hash como: Inserir, Buscar, Remover e Imprimir

  public void insert(OrderService order) {
    // Verifica se a tabela precisa ser redimensionada
    if (resizable && (((double) count) / ((double) size)) > 1.5f) {
      resizeUp();
    }

    // Verifica o tipo de tratamento de colisão e chamma o método certo
    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      insertExternal(order);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      insertOpen(order);
    }

    count++;
  }

  // Método de inserção para o tratamento de colisão por endereçamento aberto
  private void insertOpen(OrderService order) {
    int attempt = 0;
    int hashCode = hash(order.getCode(), attempt);

    while (this.table[hashCode] != null) {
      if (this.table[hashCode].data == order) {
        break;
      }

      hashCode = hash(order.getCode(), ++attempt);
    }

    if (this.table[hashCode] == null) {
      this.table[hashCode] = new NodeOpen();
      this.table[hashCode].data = order;
    }
  }

  // Método de inserção para o tratamento de colisão por encadeamento exterior
  private void insertExternal(OrderService order) {
    int hashCode = hash(order.getCode());
    Node currentNode = this.table[hashCode];

    // Tratamento por encadeamento exterior (lista ligada)
    NodeExternal externalNode = (NodeExternal) currentNode;

    // Percorre a lista encadeada para verificar duplicatas
    while (externalNode != null) {
      if (externalNode.data.equals(order)) {
        return; // Não insere duplicatas
      }
      externalNode = (NodeExternal) externalNode.next;
    }

    // Insere o novo nó no início da lista encadeada
    NodeExternal nodeToInsert = new NodeExternal(order);
    nodeToInsert.next = (NodeExternal) this.table[hashCode]; // Adiciona no início
    this.table[hashCode] = nodeToInsert;
  }

  // Método de inserção exclusiva para o caso da tabela não ser redimensionável
  // e não aceitar colisões. Esse método procura um espaço vazio na tabela e
  // insere o novo elemento. Se não tiver espaço, ele remove o elemento que está
  // no índice calculado e insere o novo elemento. Retorna o código do elemento
  // excluído.
  public int exclusiveInsertion(OrderService order) {
    if (resizable && (((double) count) / ((double) size)) > 1.5) {
      resizeUp();
    }

    int hashCode = hash(order.getCode());
    int codeExcluded = -1; // -1 indica que não houve exclusão

    if (this.table[hashCode] != null) {
      codeExcluded = this.table[hashCode].data.getCode(); // código do excluído
    } else {
      this.table[hashCode] = new NodeOpen(); // Cria um novo nó
    }
    
    this.table[hashCode].data = order;

    return codeExcluded;
  }

  public OrderService search(int code) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      return searchExternal(code);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      return searchOpen(code);
    }

    throw new ElementNotFoundException("Elemento não encontrado");
  }

  // Método de busca para o tratamento de colisão por encadeamento exterior
  private OrderService searchExternal(int code) {
    int hashCode = hash(code);
    Node currentNode = this.table[hashCode];

    // Tratamento por encadeamento exterior (lista ligada)
    NodeExternal externalNode = (NodeExternal) currentNode;

    while (externalNode != null) {
      if (externalNode.data.getCode() == code) {
        return externalNode.data;
      }
      externalNode = (NodeExternal) externalNode.next;
    }

    return null;
  }

  // Método de busca para o tratamento de colisão por endereçamento aberto
  private OrderService searchOpen(int code) {
    int attempt = 0;
    int hashCode = hash(code, attempt);

    // Limitamos o número de tentativas ao tamanho da tabela, para evitar loops
    while (this.table[hashCode] != null && attempt < size + 1) {
      // Verifica se o código é o mesmo
      if (this.table[hashCode].data.getCode() == code) {
        return this.table[hashCode].data;
      }

      // Incrementa a tentativa e recalcula o índice
      hashCode = hash(code, ++attempt);
    }

    // Se não for encontrado, retorna null
    return null;
  }

  public void alter(OrderService orderChanged) throws ElementNotFoundException {
    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      alterExternal(orderChanged);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      alterOpen(orderChanged);
    }
  }

  // Método de alteração para o tratamento de colisão por endereçamento aberto
  private void alterExternal(OrderService orderChanged) {
    int hashCode = hash(orderChanged.getCode());
    Node newOrder = this.table[hashCode];

    // Tratamento por encadeamento exterior (lista ligada)
    NodeExternal externalNode = (NodeExternal) newOrder;

    while (newOrder != null) {
      if (externalNode.data.getCode() == orderChanged.getCode()) {
        externalNode.data = orderChanged;
        return;
      }
      externalNode = (NodeExternal) externalNode.next;
    }
  }

  // Método de alteração para o tratamento de colisão por encadeamento exterior
  private void alterOpen(OrderService orderChanged) {
    int attempt = 0;
    int hashCode = hash(orderChanged.getCode(), attempt);

    while (this.table[hashCode] != null) {
      if (this.table[hashCode].data.getCode() == orderChanged.getCode()) {
        this.table[hashCode].data = orderChanged;
        return;
      }

      hashCode = hash(orderChanged.getCode(), ++attempt);
    }
  }

  public List<OrderService> list() throws ElementNotFoundException {
    List<OrderService> list = new ArrayList<OrderService>();

    if (isEmpty()) {
      throw new ElementNotFoundException("Tabela vazia");
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      for (int i = 0; i < table.length; i++) {
        Node current = table[i];
        if (current == null) {
          continue;
        }

        while (current != null) {
          list.add(current.data);
          current = ((NodeExternal) current).next;
        }
      }
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      for (int i = 0; i < table.length; i++) {
        if (table[i] != null) {
          list.add(table[i].data);
        }
      }
    }

    return list;
  }

  public boolean contains(int code) {
    if (isEmpty()) {
      return false;
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      return containsExternal(code);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      return containsOpen(code);
    }

    return false;
  }

  // Método que verifica se um elemento existe na tabela hash
  private boolean containsOpen(int code) {
    int attempt = 0;
    int hashCode = hash(code, attempt);

    while (this.table[hashCode] != null) {
      if (this.table[hashCode].data.getCode() == code) {
        return true;
      }

      hashCode = hash(code, ++attempt);
    }

    return false;
  }

  // Método que verifica se um elemento existe na tabela hash
  private boolean containsExternal(int code) {
    int hashCode = hash(code);

    if (table[hashCode] == null) {
      return false;
    }

    Node newOrder = this.table[hashCode];

    // Tratamento por encadeamento exterior (lista ligada)
    NodeExternal externalNode = (NodeExternal) newOrder;

    while (newOrder != null) {
      if (externalNode.data.getCode() == code) {
        return true;
      }
      externalNode = (NodeExternal) externalNode.next;
    }

    return false;
  }

  public void remove(int code) throws InvalidOperationException {
    if (isEmpty()) {
      throw new InvalidOperationException("Elemento não existe! Tabela está vazia");
    }

    if (resizable && (((double) count) / ((double) size)) < 0.3) {
      resizeDown();
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      removeExternal(code);
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      removeOpen(code);
    }

    throw new InvalidOperationException("Elemento não existe!");
  }

  // Método de remoção para o tratamento de colisão por endereçamento aberto
  private void removeExternal(int code) {
    int hashCode = hash(code);
    Node newOrder = this.table[hashCode];
    NodeExternal previous = null;

    // Tratamento por encadeamento exterior (lista ligada)
    NodeExternal externalNode = (NodeExternal) newOrder;

    while (newOrder != null) {
      if (externalNode.data.getCode() == code) {
        if (previous == null) {
          this.table[hashCode] = externalNode.next;
        } else {
          previous.next = externalNode.next;
        }
        count--;
        return;
      }

      previous = externalNode;
      externalNode = (NodeExternal) externalNode.next;
    }
  }

  // Método de remoção para o tratamento de colisão por encadeamento exterior
  private void removeOpen(int code) {
    int attempt = 0;
    int hashCode = hash(code, attempt);

    while (this.table[hashCode] != null) {
      if (this.table[hashCode].data.getCode() == code) {
        this.table[hashCode] = null;
        count--;
        return;
      }

      hashCode = hash(code, ++attempt);
    }
  }

  public void printHashTable() {
    if (isEmpty()) {
      return;
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      printExternal();
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      printOpen();
    }
  }

  private void printExternal() {
    for (int i = 0; i < table.length; i++) {
      Node current = table[i];
      if (current == null) {
        continue;
      }
      while (current != null) {
        System.out.print(i + ": ");
        System.out.print(current.data.getCode() + " -> ");
        current = ((NodeExternal) current).next;
      }
      System.out.println("null");
    }
  }

  private void printOpen() {
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        System.out.println(i + " --> " + table[i].data.getCode());
      }
    }
  }

  public String getTableState() {
    StringBuilder sb = new StringBuilder();

    if (isEmpty()) {
      return "Tabela vazia";
    }

    if (collisionTreatment == CollisionTreatment.ENCADEAMENTO_EXTERIOR) {
      for (int i = 0; i < table.length; i++) {
        Node current = table[i];
        if (current == null) {
          continue;
        }
        while (current != null) {
          sb.append(i + ": ");
          sb.append(current.data.getCode() + " -> ");
          current = ((NodeExternal) current).next;
        }
        sb.append("null\n");
      }
    } else if (collisionTreatment == CollisionTreatment.ENDERECAMENTO_ABERTO) {
      for (int i = 0; i < table.length; i++) {
        if (table[i] != null) {
          sb.append(i + " --> " + table[i].data.getCode() + "\n");
        }
      }
    }

    return sb.toString();
  }
}