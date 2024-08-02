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
      cache.removeLast(); // Remove o primeiro elemento
    }
    cache.addFirst(ordemServico); // Adiciona no final
  }

  // Métodos para exibir estado da cache
  public void show() {
    for (OrderService ordem : cache) {
      System.out.println(ordem.getCodigo() + " - " + ordem.getNome());
    }
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

  public boolean contains(OrderService ordemServico) {
    return cache.contains(ordemServico);
  }

  public void remove(OrderService ordemServico) {
    cache.remove(ordemServico);
  }

  public void remove(int codigo) {
    for (OrderService ordem : cache) {
      if (ordem.getCodigo() == codigo) {
        cache.remove(ordem);
        break;
      }
    }
  }

  public OrderService getFirst() {
    return cache.getFirst();
  }

  public OrderService getLast() {
    return cache.getLast();
  }
}