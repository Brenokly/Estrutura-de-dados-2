package hashtable.structure;

import hashtable.exceptions.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Cache {
  private final int CAPACIDADE = 20;
  private HashTable cache;

  /*
   * Usei uma fila para controlar a ordem de inserção dos elementos na cache.
   * Nela eu guardo os códigos das ordem de serviço que foram inseridas.
   * Quando a cache atinge a capacidade máxima, eu removo o elemento mais antigo
   * (o da frente da fila).
   * Como as únicas operações realizadas são de remover o primeiro elemento e
   * inserir no final, eu não preciso
   * percorrer a fila para realizar essas operações, o que é eficiente.
   */

  public Cache() {
    this.cache = new HashTable(CAPACIDADE, HashType.MULTIPLICATION, false);
  }

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
    String exclusionState = exclusion ? "Houve exclusao do elemento " + codeExcluded : "Nao houve exclusao";
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