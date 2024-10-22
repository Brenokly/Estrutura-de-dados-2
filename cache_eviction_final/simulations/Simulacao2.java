package cache_eviction_final.simulations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import cache_eviction_final.structure.Server;
import cache_eviction_final.structure.Cliente;
import cache_eviction_final.structure.Message;
import cache_eviction_final.structure.OrderService;
import cache_eviction_final.exceptions.*;

/*
 * Essa simulação foi criada para demonstrar o cache eviction na prática!
 */

public class Simulacao2 {
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);
    Message message;

    // Limpar o arquivo de log antes de começar
    try {
      File file = new File("cache_eviction_final/CacheLog.txt");
      FileWriter writer = new FileWriter(file);
      writer.close();

      file = new File("cache_eviction_final/ServerLog.txt");
      writer = new FileWriter(file);
      writer.close();
    } catch (Exception e) {
      System.out.println("Erro ao limpar os arquivos de log");
    }

    try {
      OrderService auxi;
      // Adicionar 192 ordens de serviço iniciais
      System.out.println("=============================================");
      System.out.println(" Início da Inserção de 192 Ordens de Serviço");
      System.out.println("=============================================");

      for (int i = 0; i < 192; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        message = new Message(os.toString()); // Passando a ordem de serviço como mensagem comprimida
        cliente.registerOrderService(message);
      }

      // Log da inserção de 100 ordens de serviço
      String messagem = String.format("\n----------------------------------------------------\n"
          + "Insercao de 192 Ordens de Servico concluida."
          + "\n----------------------------------------------------\n");
      try (FileWriter fw = new FileWriter("cache_eviction_final/ServerLog.txt", true);
          BufferedWriter bw = new BufferedWriter(fw);
          PrintWriter out = new PrintWriter(bw)) {
        bw.newLine();
        out.println(messagem);
        bw.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("\n>>> 192 Ordens de Serviço cadastradas com sucesso!");

      System.out.println("\n=============================================");
      System.out.println("Estado da Cache antes das operações:");
      servidor.getCache().show();
      System.out.println("=============================================");

      // --------------------------------------------------------------------------------
      // Realizar algumas consultas

      System.out.println("           Realizando 30 Consultas");
      System.out.println("=============================================");

      for (int i = 0; i < 30; i++) {
        System.out.printf("Consulta Ordem de Serviço com código %d\n", i);
        auxi = cliente.searchOrderService(i); // Passando a ordem de serviço como mensagem comprimida
        System.out.printf("Ordem de Serviço encontrada:\nCódigo: %d, Nome: %s, Descrição: %s\n",
            auxi.getCode(), auxi.getName(), auxi.getDescription());
        System.out.println("---------------------------------------------");
      }

      System.out.println("\n=============================================");
      System.out.println("Estado da Cache após as consultas:");
      System.out.println("Quantidade de elementos na cache: " + servidor.getCache().size());
      servidor.getCache().show();
      System.out.println("=============================================\n");

      // --------------------------------------------------------------------------------
      // Umas consultas apenas para checar o hit na cache

      for (int i = 0; i < 30; i++) {
        cliente.searchOrderService(i);
      }

      System.out.println("Buscando novamente as 30 primeiras ordens de serviço para dar hits na cache....\n");

      // --------------------------------------------------------------------------------
      // Exibir estado da cache após as operações

      System.out.println("\n============================================================");
      System.out.println("         Estado da Cache após as buscas para dar hits");
      System.out.println("Quantidade de elementos na cache: " + servidor.getCache().size());
      System.out.println("==============================================================");
      servidor.getCache().show();
      System.out.println("--------------------------------------------------------------");

      // --------------------------------------------------------------------------------
      // Consultar ordens de serviço inexistentes na cache!
      // Como a cache está cheia, as ordens de serviço serão removidas aleatoriamente!
      System.out.println("\n============================================================");
      System.out.println("         Novas Consultas para mostrar o Cache Eviction");
      for (int i = 30; i < 60; i++) {
        System.out.printf("Consulta Ordem de Serviço com código %d\n", i);
        auxi = cliente.searchOrderService(i);
        System.out.printf("Ordem de Serviço encontrada:\nCódigo: %d, Nome: %s, Descrição: %s\n",
            auxi.getCode(), auxi.getName(), auxi.getDescription());
        System.out.println("---------------------------------------------");
      }

      // Exibir estado da cache após as operações
      System.out.println("\n=============================================");
      System.out.println("         Estado da Cache após a busca");
      System.out.println("=============================================");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // --------------------------------------------------------------------------------

      // Exibir hits e misses
      System.out.println("\n>>> Resultados finais:");
      System.out.printf("Hits: %d\nMisses: %d\n", servidor.getHits(), servidor.getMisses());

    } catch (ElementNotFoundException e) {
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Não Encontrado");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    } catch (ElementAlreadyExistsException e) {
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Já Existe");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    } catch (InvalidOperationException e) {
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Operação Inválida");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    }
  }
}