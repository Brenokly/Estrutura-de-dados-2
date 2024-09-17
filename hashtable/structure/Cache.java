package hashtable.structure;

import java.util.LinkedList;
import java.util.Queue;

public class Cache {
  private final int CAPACIDADE = 20;
  private HashTable cache;
  private Queue<Integer> orderQueue; // Fila para controlar a ordem de inserção

  public Cache() {
    this.cache = new HashTable(CAPACIDADE, HashType.MULTIPLICATION, false);
    this.orderQueue = new LinkedList<>();
  }

  public OrderService search(int code) {
    return cache.search(code);
  }

  public void insert(OrderService orderService) {
    if (cache.size() == CAPACIDADE) {
      // Remover o elemento mais antigo (o da frente da fila)
      int oldCode = orderQueue.poll(); // Remove e obtém o código da frente da fila
      cache.remove(oldCode);
    }
    cache.insert(orderService);
    orderQueue.add(orderService.getCodigo()); // Adiciona o código à fila
  }

  public void remove(OrderService orderService) {
    cache.remove(orderService.getCodigo());
    orderQueue.remove(orderService.getCodigo()); // Remove da fila também
  }

  public void remove(int code) {
    cache.remove(code);
    orderQueue.remove(code); // Remove da fila também
  }

  public void show() {
    cache.printHashTable();
  }

  public int size() {
    return cache.size();
  }

  public void clear() {
    cache.clear();
    orderQueue.clear();
  }

  public boolean isEmpty() {
    return cache.isEmpty();
  }

  public boolean contains(int code) {
    return cache.contains(code);
  }
}