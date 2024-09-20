package hashtable.structure;

import hashtable.exceptions.*;
import hashtable.structure.tablestructure.CollisionTreatment;
import hashtable.structure.tablestructure.HashTable;
import hashtable.structure.tablestructure.HashType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Classe Cache que é responsável por armazenar os elementos da tabela Hash
 * para que a busca seja mais rápida e eficiente.
 * 
 * A cache possui uma capacidade máxima de 20 elementos e utiliza a tabela Hash
 * para armazenar os elementos.
 * 
 * A hashTable da cach foi configurada para utilizar o método de hash duplo (dispersão dupla),
 * não permitir redimensionamento, e tratar colisões por endereçamento aberto.
 * 
 * Foram os métodos que eu achei mais apropriados para o caso da cache que não deve ser redimensionada.
 * Dessa forma ela não possuí colisões nenhuma. E quando a cache está cheia, a inserção é feita de forma exclusiva.
 * Então é como se a política de cache eviction fosse uma random! Já que não tem como advinhar qual elemento será removido.
 * 
 * Pois o elemento removido depende do código do elemento que será inserido. Para qual posição ele será direcionado?
 * Não dá para advinhar. Então é como se fosse random.
 */

public class Cache {
  private int CAPACIDADE;
  private HashTable cache;

  //--------------------------------------------------------------------------------
  // Construtores

  public Cache() {
    this.cache = new HashTable(CAPACIDADE, HashType.DOUBLEHASH, false, CollisionTreatment.ENDERECAMENTO_ABERTO);
    this.CAPACIDADE = 20;
  }

  public Cache(int capacidade) {
    this.CAPACIDADE = capacidade;
    this.cache = new HashTable(capacidade, HashType.DOUBLEHASH, false, CollisionTreatment.ENDERECAMENTO_ABERTO);
  }

  public Cache(int capacidade, HashType hashType, boolean redimensionavel, CollisionTreatment collisionTreatment) {
    this.CAPACIDADE = capacidade;
    this.cache = new HashTable(capacidade, hashType, redimensionavel, collisionTreatment);
  }

  //--------------------------------------------------------------------------------
  // Métodos

  public OrderService search(int code) {
    try {
      return cache.search(code);
    } catch (ElementNotFoundException e) {
      return null;
    }
  }

  public void insert(OrderService orderService) {
    if (cache.getQuantityRecords() == CAPACIDADE) {
      int codeExcluded = cache.exclusiveInsertion(orderService);

      logState(TypeOfOperation.INSERT, orderService.getCode(), true, codeExcluded);

      /*
       * Uma inserção exclusiva é feita quando a cache está cheia.
       * Ela remove o elemento que está ocupando a posição do elemento que será
       * inserido e insere o novo elemento no lugar dele, removendo o antigo.
       */

    } else {
      cache.insert(orderService);

      logState(TypeOfOperation.INSERT, orderService.getCode(), false, -1);
    }
  }

  public boolean alter(OrderService orderService) {
    try {
      cache.alter(orderService);
    } catch (ElementNotFoundException e) {
      return false;
    }

    logState(TypeOfOperation.ALTER, orderService.getCode(), false, -1);

    return true;
  }

  public boolean remove(int code) {
    try {
      cache.remove(code);
    } catch (ElementNotFoundException e) {
      return false;
    }

    logState(TypeOfOperation.REMOVE, code, false, -1);

    return true;
  }

  private void logState(TypeOfOperation operation, int code, boolean exclusion, int codeExcluded) {
    String resizeStatus = cache.heWasResized() ? "Houve redimensionamento" : "Nao houve redimensionamento";
    int currentElements = cache.getQuantityRecords();
    String exclusionState = exclusion ? "Houve exclusao do elemento de código " + codeExcluded : "Nao houve exclusao";
    String tableState = cache.getTableState(); // Adicione a chamada ao método getTableState()

    String message = String.format(
        "[LOG ENTRY]\n----------------------------------------------------\n" +
            "Operacao realizada: %s\n" +
            "Codigo da Ordem de Servico: %d\n" +
            "Status de Exclusao: %s\n" +
            "Status do Redimensionamento: %s\n" +
            "Quantidade de Elementos na cache: %d\n" +
            "Estado da Tabela Hash:\n%s" +
            "----------------------------------------------------",
        operation.toString(), code, exclusionState, resizeStatus, currentElements, tableState);

    try (FileWriter fw = new FileWriter("hashtable/CacheLog.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void show() {
    cache.printHashTable();
  }

  public int size() {
    return cache.getQuantityRecords();
  }

  public void clear() {
    cache.clear();
  }

  public boolean isEmpty() {
    return cache.isEmpty();
  }

  public boolean contains(int code) {
    return cache.contains(code);
  }
}