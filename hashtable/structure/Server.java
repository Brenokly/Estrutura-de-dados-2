package hashtable.structure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import hashtable.exceptions.*;

public class Server {
  private HashTable dataBase;
  private Cache cache;
  private int hits;
  private int misses;

  public Server() {
    this.dataBase = new HashTable(127, HashType.DIVISION, true);
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

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

  // public Boolean registerOrderService(OrderService orderService) throws NodeAlreadyExistsException {
  //   int oldHeight = dataBase.getHeight(); // armazena a altura da arvore AVL antes da insercao

  //   dataBase.insert(orderService);

  //   logState("Insercao", oldHeight, dataBase.getHeight(), orderService.getCodigo());

  //   return true;
  // }

  // public List<OrderService> listOrdersService() throws NodeNotFoundException {
  //   return dataBase.list();
  // }

  // public Boolean alterOrderService(OrderService orderService) throws InvalidOperationException {
  //   dataBase.alter(orderService);

  //   OrderService ordem = cache.contains(orderService.getCodigo()); // verifica se a ordem esta na cache

  //   // altera na cache se existir
  //   if (ordem != null) {
  //     cache.remove(ordem);
  //     cache.add(orderService);
  //   } else {
  //     cache.add(orderService);
  //   }

  //   return true;
  // }

  public Boolean removeOrderService(int codigo) throws InvalidOperationException {
    dataBase.remove(codigo); // remove da arvore AVL (dataBase)

    OrderService ordem = cache.search(codigo); // verifica se a ordem esta na cache
    // remove da cache se existir
    if (ordem != null) {
      cache.remove(ordem);
    }

    return true;
  }

  public int getQuantityRecords() {
    return dataBase.getQuantityRecords();
  }

  private void logState(String operation, int oldHeight, int newHeight, int codigo) {
    try (FileWriter fw = new FileWriter("hashtable/log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println("mensagem: " + operation + " de ordem de servico");
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