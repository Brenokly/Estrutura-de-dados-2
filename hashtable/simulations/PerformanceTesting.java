package hashtable.simulations;

import hashtable.structure.Cliente;
import hashtable.structure.OrderService;
import hashtable.structure.Server;

// Esse teste, eu realizei a inserção de 1000 elementos e depois realizei a busca de um elemento que não está na cache e um que está na cache.

public class PerformanceTesting {

  public static void main(String[] args) {
    // Cria um objeto cliente (onde ocorre a inserção)
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);

    // Captura o tempo inicial em nanossegundos
    long startTime = System.nanoTime();

    // Insere os 1000 elementos
    for (int i = 0; i <= 1000; i++) {
      OrderService os = new OrderService("Nome" + i, "Descrição" + i);
      cliente.registerOrderService(os);
    }

    // Captura o tempo final em nanossegundos
    long endTime = System.nanoTime();

    // Calcula o tempo total em nanossegundos e converte para milissegundos
    long duration = endTime - startTime;
    double durationInMillis = duration / 1_000_000.0;

    // Exibe o tempo total de inserção
    System.out.println("Tempo total de inserção de 1000 elementos: " + durationInMillis + " ms");

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Captura o tempo inicial em nanossegundos
    startTime = System.nanoTime();

    // Busca um elemento
    cliente.searchOrderService(500);

    // Captura o tempo final em nanossegundos
    endTime = System.nanoTime();

    // Calcula o tempo total em nanossegundos e converte para milissegundos
    duration = endTime - startTime;
    durationInMillis = duration / 1_000_000.0;

    // Exibe o tempo total de busca
    System.out.println("Tempo total de busca de um elemento que não está na cache: " + durationInMillis + " ms");

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Captura o tempo inicial em nanossegundos
    startTime = System.nanoTime();

    // Busca um elemento
    cliente.searchOrderService(500);

    // Captura o tempo final em nanossegundos
    endTime = System.nanoTime();

    // Calcula o tempo total em nanossegundos e converte para milissegundos
    duration = endTime - startTime;
    durationInMillis = duration / 1_000_000.0;

    // Exibe o tempo total de busca
    System.out.println("Tempo total de busca de um elemento que está na cache: " + durationInMillis + " ms");
  }
}
