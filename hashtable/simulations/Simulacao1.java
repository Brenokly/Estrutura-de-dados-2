package hashtable.simulations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import hashtable.structure.Server;
import hashtable.structure.Cliente;
import hashtable.structure.OrderService;
import hashtable.exceptions.*;

public class Simulacao1 { // Simulação 1: É uma simulação geral, com todas as operações pedidas no arquivo
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);

    // ------------------------------------------------------------8------------------

    // Limpar o arquivo de log antes de começar
    try {
      // Limpa o log da cache
      File file = new File("hashtable/CacheLog.txt");
      FileWriter writer = new FileWriter(file);
      writer.close();

      file = new File("hashtable/ServerLog.txt");
      writer = new FileWriter(file);
      writer.close();
    } catch (Exception e) {
      System.out.println("Erro ao limpar os arquivos de log");
    }

    // -------------------------------------------------------------------------------

    try {
      // Adicionar 60 ordens de serviço iniciais
      System.out.println("---------------------------------------------");
      System.out.println("Início da Inserção de Ordens de Serviço");
      System.out.println("---------------------------------------------");
      for (int i = 0; i <= 1000; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        cliente.registerOrderService(os);
      }

      // --------------------------------------------------------------------------------

      // Log da inserção de 60 ordens de serviço

      String message = String.format("\n----------------------------------------------------\n"
          + "Insercao de 1000 Ordens de Servico concluida."
          + "\n----------------------------------------------------\n");
      try (FileWriter fw = new FileWriter("hashtable/ServerLog.txt", true);
          BufferedWriter bw = new BufferedWriter(fw);
          PrintWriter out = new PrintWriter(bw)) {
        bw.newLine();
        out.println(message);
        bw.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }

      // -------------------------------------------------------------------------------

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // Realizar as operações conforme descrito na descrição do arquivo

      // 1. Uma busca
      System.out.println("Busca Ordem de Serviço com código 0");
      OrderService auxi = cliente.searchOrderService(0);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCode() + "\t" + auxi.getName() + "\t" + auxi.getDescription() + "\t"
          + auxi.getRequestTime());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos\n");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 2. Busca 2
      System.out.println("Busca Ordem de Serviço com código 1");
      auxi = cliente.searchOrderService(1);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCode() + "\t" + auxi.getName() + "\t" + auxi.getDescription() + "\t"
          + auxi.getRequestTime());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos\n");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      for (int i = 27; i <= 60; i++) {
        auxi = cliente.searchOrderService(i);
      }

      // Exibir estado da cache
      System.out.println("Estado da Cache pós-cheia: " + servidor.getCache().size() + " elementos\n");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      auxi = cliente.searchOrderService(40);

      // -------------------------------------------------------------------------------

      // Exibir estado da cache
      System.out.println("Estado da Cache pós-cheia: " + servidor.getCache().size() + " elementos\n");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

    } catch (ElementNotFoundException e) { // Tratamento de nodes não encontrados
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Não Encontrado");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    } catch (ElementAlreadyExistsException e) { // Tratamento de nodes já existentes
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Já Existe");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    } catch (InvalidOperationException e) { // Tratamento de operações inválidas
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Operação Inválida");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    }

    System.out.println("Hitts: " + servidor.getHits());
    System.out.println("Misses: " + servidor.getMisses());
  }
}