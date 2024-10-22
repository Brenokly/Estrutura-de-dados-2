package cache_eviction_final.structure;

import cache_eviction_final.exceptions.*;
import cache_eviction_final.structure.listsAAtructure.LinkedListAAMF;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * A classe Cache é responsável por gerenciar a cache de ordens de serviço.
 * Ela foi implementada utilizando uma lista duplamente encadeada auto-ajustável.
 * A cache possui uma capacidade máxima, e quando atinge essa capacidade, a política de cache eviction
 * LRU (Least Recently Used) é aplicada, removendo o último elemento da cache.
 * 
 * Porque escolhi o LRU?
 * 
 * Como se trata de uma lista auto-ajustável, que move os elementos mais acessados para o início, 
 * fica mais fácil de implementar a política de cache eviction LRU, já que o último elemento da lista
 * é, graças a auto-ajustabilidade, o menos acessado.
*/

public class Cache {
  private int CAPACIDADE;
  private LinkedListAAMF cache;

  // --------------------------------------------------------------------------------
  // Construtores

  public Cache() {
    this.CAPACIDADE = 30;
    cache = new LinkedListAAMF();
  }

  public Cache(int capacidade) {
    this.CAPACIDADE = capacidade;
    cache = new LinkedListAAMF();
  }

  // --------------------------------------------------------------------------------
  // Métodos

  public OrderService search(int code) {
    try {
      OrderService order = cache.search(code);

      logState(TypeOfOperation.SEARCH, code, false, -1);

      return order;
    } catch (ElementNotFoundException e) {
      return null;
    }
  }

  public void insert(OrderService orderService) {
    if (cache.size() == CAPACIDADE) {
      // Remove o último elemento da cache
      // Já que estamos usando a política de cache eviction LRU
      OrderService removed = cache.removeLast();
      logState(TypeOfOperation.INSERT, orderService.getCode(), true, removed.getCode());
    }

    // Insere a nova ordem de serviço
    cache.insertFirst(orderService);
    logState(TypeOfOperation.INSERT, orderService.getCode(), false, -1);
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
    String listState = cache.getListState();
    String exclusionState = exclusion ? "Houve exclusao do elemento de código " + codeExcluded : "Nao houve exclusao";

    String message = String.format(
        "[LOG ENTRY]\n----------------------------------------------------\n" +
            "Operacao realizada: %s\n" +
            "Codigo da Ordem de Servico: %d.\n" +
            "Status de Exclusao: %s\n" +
            "Quantidade de Elementos na cache após a operacao: %d\n" +
            "Estado da cache:\n%s" +
            "----------------------------------------------------",
        operation.toString(), code, exclusionState, cache.size(), listState);

    try (FileWriter fw = new FileWriter("cache_eviction_final/CacheLog.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void show() {
    cache.show();
  }

  public int size() {
    return cache.size();
  }

  public boolean isEmpty() {
    return cache.isEmpty();
  }
}