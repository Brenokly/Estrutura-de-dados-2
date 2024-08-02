package cache_eviction;

import java.util.List;

public class Servidor {
  private TreeAVL dataBase;
  private Cache cache;
  private int hits;
  private int misses;

  public Servidor() {
    this.dataBase = new TreeAVL();
    this.cache = new Cache();
    this.hits = 0;
    this.misses = 0;
  }

  public OrderService searchOrderService(int codigo) {
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

    logEstado(); // Loga o estado atual
    return ordem; // Retorna a ordem
  }

  public void registerOrderService(OrderService OrderService) {
    dataBase.insert(OrderService);
    logEstado();
  }

  public List<OrderService> listOrdersService() {
    logEstado();
    return dataBase.list();
  }

  public void alterOrderService(OrderService OrderService) {
    dataBase.alterar(OrderService);
    logEstado();
  }

  public void removeOrderService(int codigo) {
    dataBase.remove(codigo);
    logEstado();
  }

  public int getQuantityRecords() {
    logEstado();
    return dataBase.getQuantityRecords();
  }

  private void logEstado() {
    // Implementar logging da altura da árvore e operações de rotação
    // Implementar logging dos itens da cache
  }

  public TreeAVL getDataBase() {
    return dataBase;
  }

  /**
   * @return Cache return the cache
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * @return int return the hits
   */
  public int getHits() {
    return hits;
  }

  /**
   * @return int return the misses
   */
  public int getMisses() {
    return misses;
  }
}