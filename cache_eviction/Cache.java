package cache_eviction;

import java.util.LinkedList;

public class Cache {
  private final int CAPACIDADE = 20;
  private LinkedList<OrderService> cache;

  public Cache() {
    this.cache = new LinkedList<>();
  }

  public OrderService get(int codigo) {
    for (OrderService ordem : cache) {
      if (ordem.getCodigo() == codigo) {
        return ordem;
      }
    }
    return null;
  }

  public void add(OrderService ordemServico) { // Algoritmo FIFO
    if (cache.size() >= CAPACIDADE) { // Cache cheia
      cache.removeLast(); // Remove o último elemento
    }
    cache.addFirst(ordemServico); // Adiciona no início
  }

  // Métodos para exibir estado da cache
  public void show() {
    if (cache.isEmpty()) {
      System.out.println("Cache vazia.");
      return;
    }
    System.out.println("--------------------");
    for (OrderService ordem : cache) {
      System.out.println("Código: " + ordem.getCodigo() + " - Nome: " + ordem.getNome());
    }
    System.out.println("--------------------");
  }

  public int size() {
    return cache.size();
  }

  public void clear() {
    cache.clear();
  }

  public boolean isEmpty() {
    return cache.isEmpty();
  }

  public OrderService contains(int codigo) {
    for (OrderService ordem : cache) {
      if (ordem.getCodigo() == codigo) {
        return ordem;
      }
    }
    return null;
  }

  public void remove(OrderService codigo) {
    cache.remove(codigo);
  }

  public OrderService getFirst() {
    return cache.getFirst();
  }

  public OrderService getLast() {
    return cache.getLast();
  }
}