package cache_eviction_final.structure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

import cache_eviction_final.exceptions.*;
import cache_eviction_final.structure.tablestructure.HashTable;
import cache_eviction_final.structure.tablestructure.HashType;
import java.util.List;

/*
  * Base de dados é uma tabela hashe com 127 posições iniciais,
  * usando o método da divisão e redimensionável.
  * As justificativas para essas escolhas
  * estão nas suas devidas classes (HashTable, HashType, HashFunctions, CollisionTreatment).

  * A cache é uma lista auto-ajustável com 30 posições, não redimensionável.
  * Ela é auto-ajustável e usa o algoritmo LRU (Least Recently Used) para remover o elemento menos recentemente usado
  * quando a lista está cheia e um novo elemento é inserido. E para o ajuste dos elementos, foi usado o algoritmo
  * MF (Move to Front), que move o elemento mais recentemente usado para a primeira posição da lista.
 */

public class Server {
  private HashTable dataBase;
  private Cache cache;
  private int hits;
  private int misses;

  // --------------------------------------------------------------------------------
  // Construtores

  public Server() {
    this.dataBase = new HashTable(103, HashType.DIVISION);
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidadeCache) {
    this.dataBase = new HashTable(103, HashType.DIVISION);
    this.cache = new Cache(capacidadeCache);
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidade, HashType hashType) {
    this.dataBase = new HashTable(capacidade, hashType);
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public Server(int capacidade, HashType hashType, Cache cache) {
    this.dataBase = new HashTable(capacidade, hashType);
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

  public Boolean registerOrderService(Message order) {
    String[] parts = order.decompress().split(","); // Descomprime a mensagem e separa os campos

    // Converte os campos para os tipos corretos
    int code = Integer.parseInt(parts[0]); // Converte o primeiro elemento para int
    String name = parts[1]; // Segundo elemento é o nome
    String description = parts[2]; // Terceiro elemento é a descrição
    LocalTime requestTime = LocalTime.parse(parts[3]).withSecond(0).withNano(0); // Quarto elemento é o horário

    // Cria a ordem de serviço
    OrderService orderService = new OrderService(code, name, description, requestTime);

    dataBase.insert(orderService);

    logState(TypeOfOperation.INSERT, orderService.getCode());

    return true;
  }

  public Message listOrdersService() {
    try {
      List<OrderService> orders = dataBase.list(); // Obtém a lista de ordens de serviço
      StringBuilder ordersString = new StringBuilder();

      // Converte cada ordem de serviço para string e adiciona ao StringBuilder
      for (OrderService order : orders) {
        ordersString.append(order.toString()).append("\n"); // Adiciona nova linha para separar as ordens
      }

      // Cria uma mensagem a partir da string da lista de ordens
      Message message = new Message(ordersString.toString()); // Comprime a string
      return message; // Retorna a mensagem comprimida

    } catch (ElementNotFoundException e) {
      // Retorna uma mensagem com informação de erro
      Message errorMessage = new Message("Nenhuma ordem de serviço encontrada.");
      return errorMessage; // Retorna a mensagem de erro
    }
  }

  public void listPrintOrdersService() {
    dataBase.printHashTable();
  }

  public Boolean alterOrderService(Message order) throws InvalidOperationException {
    // Descomprime a mensagem e separa os campos
    String[] parts = order.decompress().split(",");

    // Converte os campos para os tipos corretos
    int code = Integer.parseInt(parts[0]); // Converte o primeiro elemento para int
    String name = parts[1]; // Segundo elemento é o nome
    String description = parts[2]; // Terceiro elemento é a descrição
    LocalTime requestTime = LocalTime.parse(parts[3]); // Quarto elemento é o horário

    // Cria a ordem de serviço
    OrderService orderService = new OrderService(code, name, description, requestTime);

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
            "Codigo da Ordem de Servico: %d.\n" +
            "Quantidade de Ordem de Serviços: %d\n" +
            "Status do Redimensionamento: %s\n" +
            "Tamanho atual da tabela hash: %d\n" +
            "Estado da Tabela Hash:\n%s" +
            "----------------------------------------------------",
        operation.toString(), codigo, dataBase.getQuantityRecords(), resizeStatus, currentSize, tableState);

    try (FileWriter fw = new FileWriter("cache_eviction_final/ServerLog.txt", true);
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