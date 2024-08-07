package cache_eviction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Server {
  private TreeAVL dataBase;
  private Cache cache;
  private int hits;
  private int misses;

  public Server() {
    this.dataBase = new TreeAVL();
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public OrderService searchOrderService(int codigo) throws NodeNotFoundException {
    OrderService ordem = cache.get(codigo); // Busca na cache
    if (ordem != null) { // Se encontrou na cache
      hits++; // Incrementa hits
      return ordem; // Retorna a ordem
    }

    ordem = dataBase.search(codigo); // Busca na base de dados caso não tenha encontrado na cache
    if (ordem != null) { // Se encontrou na base de dados
      cache.add(ordem); // Adiciona na cache
      misses++; // Incrementa misses
    }

    return ordem; // Retorna a ordem
  }

  public Boolean registerOrderService(OrderService orderService) throws NodeAlreadyExistsException {
    int oldHeight = dataBase.getHeight(); // armazena a altura da arvore AVL antes da insercao

    dataBase.insert(orderService);

    if (dataBase.wasRotated()) { // detecta se a arvore AVL foi balanceada
      logState("Insercao", true, oldHeight, dataBase.getHeight(), orderService.getCodigo());
    } else { // caso contrario
      logState("Insercao", false, oldHeight, dataBase.getHeight(), orderService.getCodigo());
    }

    return true;
  }

  public List<OrderService> listOrdersService() {
    return dataBase.list();
  }

  public Boolean alterOrderService(OrderService orderService) throws NodeNotFoundException {
    dataBase.alterar(orderService);

    OrderService ordem = cache.contains(orderService.getCodigo()); // verifica se a ordem esta na cache

    // altera na cache se existir
    if (ordem != null) {
      cache.remove(ordem);
      cache.add(orderService);
    }

    return true;
  }

  public Boolean removeOrderService(int codigo) throws InvalidOperationException {
    int oldHeight = dataBase.getHeight(); // armazena a altura da arvore AVL antes da remocao

    dataBase.remove(codigo); // remove da arvore AVL (dataBase)

    OrderService ordem = cache.contains(codigo); // verifica se a ordem esta na cache
    // remove da cache se existir
    if (ordem != null) {
      cache.remove(ordem);
    }

    if (dataBase.wasRotated()) { // detecta se a arvore AVL foi balanceada
      logState("Remocao", true, oldHeight, dataBase.getHeight(), codigo);
    } else { // caso contrario
      logState("Remocao", false, oldHeight, dataBase.getHeight(), codigo);
    }

    return true;
  }

  public int getQuantityRecords() {
    return dataBase.getQuantityRecords();
  }

  private void logState(String operation, boolean wasRotated, int oldHeight, int newHeight, int codigo) {
    String rotationStatus = wasRotated ? "Houve balanceamento" : "Nao houve balanceamento";

    String message = String.format(
        "[LOG ENTRY]\n----------------------------------------------------\n" +
            "Operacao: %s do Node %d\n" +
            "Status do Balanceamento: %s\n" +
            "Altura da Arvore Antes da Operacao: %d\n" +
            "Altura da Arvore Apos a Operacao: %d\n" +
            "----------------------------------------------------",
        operation, codigo, rotationStatus, oldHeight, newHeight);

    try (FileWriter fw = new FileWriter("cache_eviction/log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public TreeAVL getDataBase() {
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