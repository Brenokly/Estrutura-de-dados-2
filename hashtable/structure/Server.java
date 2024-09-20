package hashtable.structure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import hashtable.exceptions.*;
import hashtable.structure.tablestructure.CollisionTreatment;
import hashtable.structure.tablestructure.HashTable;
import hashtable.structure.tablestructure.HashType;
import java.util.List;

/*
 * Base de dados é uma tabela hashe com 127 posições iniciais,
  * usando o método da divisão e redimensionável.
  * As justificativas para essas escolhas
  * estão nas suas devidas classes (HashTable, HashType, HashFunctions, CollisionTreatment).

  * A cache é uma hashtable com 20 posições, não redimensionável.
  * Está configurada para usar a função hash da dupla dispersão e tratamento de colisão por endereçamento aberto.
  * As justificativas para essas escolhas estão nas suas devidas classes (Cache)
 */

public class Server {
  private HashTable dataBase;
  private Cache cache;
  private int hits;
  private int misses;

  // --------------------------------------------------------------------------------
  // Construtores

  public Server() {
    this.dataBase = new HashTable(127, HashType.DIVISION, true, CollisionTreatment.ENCADEAMENTO_EXTERIOR);
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidadeCache) {
    this.dataBase = new HashTable(127, HashType.DIVISION, true, CollisionTreatment.ENCADEAMENTO_EXTERIOR);
    this.cache = new Cache(capacidadeCache);
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidade, HashType hashType, boolean redimensionavel, CollisionTreatment collisionTreatment) {
    this.dataBase = new HashTable(capacidade, hashType, redimensionavel, collisionTreatment);
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidade, HashType hashType, boolean redimensionavel, CollisionTreatment collisionTreatment,
      Cache cache) {
    this.dataBase = new HashTable(capacidade, hashType, redimensionavel, collisionTreatment);
    this.cache = cache;
    this.hits = 0;
    this.misses = 0;
  }

  // --------------------------------------------------------------------------------
  // Métodos

  public OrderService searchOrderService(int codigo) throws ElementNotFoundException {
    OrderService ordem = cache.search(codigo); // Busca na cache

    if (ordem != null) { // Se encontrou na cache
      hits++; // Incrementa hits
      return ordem; // Retorna a ordem
    }

    ordem = dataBase.search(codigo); // Busca na base de dados caso não tenha encontrado na cache
    if (ordem != null) { // Se encontrou na base de dados
      cache.insert(ordem); // Adiciona na cache
      misses++; // Incrementa misses
    }

    return ordem; // Retorna a ordem
  }

  public Boolean registerOrderService(OrderService orderService) {
    dataBase.insert(orderService);

    logState(TypeOfOperation.INSERT, orderService.getCode());

    return true;
  }

  public List<OrderService> listOrdersService() throws ElementNotFoundException {
    return dataBase.list();
  }

  public Boolean alterOrderService(OrderService orderService) throws InvalidOperationException {
    try {
      dataBase.alter(orderService);
    } catch (ElementNotFoundException e) {
      return false;
    }

    logState(TypeOfOperation.ALTER, orderService.getCode());

    cache.alter(orderService); // Altera na cache se existir e retorna true, mas como não preciso do retorno...

    return true;
  }

  public Boolean removeOrderService(int codigo) throws InvalidOperationException {
    try {
      dataBase.remove(codigo);
    } catch (InvalidOperationException e) {
      return false;
    }

    logState(TypeOfOperation.REMOVE, codigo);

    cache.remove(codigo); // Remove da cache, caso exista, e retorna true, mas como não preciso do retorno

    return true;
  }

  public int getQuantityRecords() {
    return dataBase.getQuantityRecords();
  }

  private void logState(TypeOfOperation operation, int codigo) {
    String resizeStatus = dataBase.heWasResized() ? "Houve redimensionamento" : "Nao houve redimensionamento";
    int currentSize = dataBase.size();
    String tableState = dataBase.getTableState();

    String message = String.format(
        "[LOG ENTRY]\n----------------------------------------------------\n" +
            "Operacao realizada: %s\n" +
            "Codigo da Ordem de Servico: %d\n" +
            "Status do Redimensionamento: %s\n" +
            "Tamanho atual da tabela hash: %d\n" +
            "Estado da Tabela Hash:\n%s" +
            "----------------------------------------------------",
        operation.toString(), codigo, resizeStatus, currentSize, tableState);

    try (FileWriter fw = new FileWriter("hashtable/ServerLog.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public HashTable getDataBase() {
    return dataBase;
  }

  public Cache getCache() {
    return cache;
  }

  public int getHits() {
    return hits;
  }

  public int getMisses() {
    return misses;
  }
}