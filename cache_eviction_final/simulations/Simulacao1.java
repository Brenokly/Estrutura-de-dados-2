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
 * Essa simulação foi criada com base na simulação pedida no documento!
 * Inicia o programa:
 
 * 1) 100 ordens de serviço são cadastradas.
 
 * Depois é realizada:
 
 * 2) Três consultas.
 * 3) Uma listagem.
 * 4) Dois cadastros seguidos de listagens.
 * 5) Duas alterações seguidas de listagens.
 * 6) Três remoções seguidas de listagens..
*/

public class Simulacao1 {
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
      // Adicionar 70 ordens de serviço iniciais
      System.out.println("=============================================================================");
      System.out.println(" Início da Inserção de 100 Ordens de Serviço");
      System.out.println("=============================================================================");

      for (int i = 0; i < 100; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        message = new Message(os.toString()); // Passando a ordem de serviço como mensagem comprimida
        cliente.registerOrderService(message);
      }

      // Log da inserção de 70 ordens de serviço
      String messagem = String.format("\n----------------------------------------------------\n"
          + "Insercao de 100 Ordens de Servico concluida."
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

      System.out.println("\n>>> 70 Ordens de Serviço cadastradas com sucesso!");

      System.out.println("\n=============================================================================");
      System.out.println("Estado da Cache antes das operações:");
      servidor.getCache().show();
      System.out.println("=============================================================================");

      // Realizar três consultas
      System.out.println("          Realizando Consultas");
      System.out.println("=============================================================================");

      for (int i = 0; i < 3; i++) {
        System.out.printf("Consulta Ordem de Serviço com código %d\n", i);
        OrderService auxi = cliente.searchOrderService(i); // Passando a ordem de serviço como mensagem comprimida
        System.out.printf("Ordem de Serviço encontrada:\nCódigo: %d, Nome: %s, Descrição: %s\n",
            auxi.getCode(), auxi.getName(), auxi.getDescription());

        System.out.println("\n=============================================================================");
        System.out.println("Estado da Cache após a consulta:");
        servidor.getCache().show();
        System.out.println("=============================================================================");
      }

      // Listar todas as ordens de serviço
      System.out.println("           Listagem de Ordens de Serviço");
      System.out.println("=============================================================================");
      cliente.listPrintOrdersService();

      // Cadastrar duas novas ordens e listar novamente
      System.out.println("\n=============================================================================");
      System.out.println("  Cadastrando duas novas Ordens de Serviço");
      System.out.println("=============================================================================");

      for (int i = 70; i < 72; i++) {
        OrderService os = new OrderService("NomeNovo" + i, "DescriçãoNova" + i);
        message = new Message(os.toString()); // Passando a ordem de serviço como mensagem comprimida
        cliente.registerOrderService(message);
        System.out.printf("Ordem de Serviço %d cadastrada: Nome: %s, Descrição: %s\n",
            os.getCode(), os.getName(), os.getDescription());
      }

      System.out.println("\n>>> Listagem após o cadastro de duas novas ordens:");
      cliente.listPrintOrdersService();

      // Alterar duas ordens e listar novamente
      System.out.println("\n=============================================================================");
      System.out.println("       Alterando duas Ordens de Serviço");
      System.out.println("=============================================================================");

      for (int i = 0; i < 2; i++) {
        OrderService os = new OrderService(i, "NomeAlterado" + i, "DescriçãoAlterada" + i);
        message = new Message(os.toString()); // Passando a ordem de serviço como mensagem comprimida
        cliente.alterOrderService(message);
        System.out.printf("Ordem de Serviço %d alterada: Novo Nome: %s, Nova Descrição: %s\n",
            os.getCode(), os.getName(), os.getDescription());
      }

      System.out.println("\n=============================================================================");
      System.out.println("Estado da Cache após as alterações:");
      servidor.getCache().show();
      System.out.println("=============================================================================");

      System.out.println("\n>>> Listagem após a alteração de duas ordens:");
      cliente.listPrintOrdersService();

      // Remover duas ordens e listar novamente
      System.out.println("\n=============================================================================");
      System.out.println("         Removendo três Ordens de Serviço");
      System.out.println("=============================================================================");

      for (int i = 0; i < 2; i++) {
        cliente.removeOrderService(i);
        System.out.printf("Ordem de Serviço %d removida com sucesso!\n", i);
      }

      // Terceira remoção
      cliente.removeOrderService(55);
      System.out.printf("Ordem de Serviço %d removida com sucesso!\n", 55);

      System.out.println("\n=============================================================================");
      System.out.println("Estado da Cache após as remoções:");
      servidor.getCache().show();
      System.out.println("=============================================================================");

      System.out.println("\n>>> Listagem após a remoção de duas ordens:");
      cliente.listPrintOrdersService();

      // Exibir estado da cache após as operações
      System.out.println("\n=============================================================================");
      System.out.println("         Estado da Cache após operações");
      System.out.println("=============================================================================");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // Exibir hits e misses
      System.out.println("\n>>> Resultados finais:");
      System.out.printf("Hits: %d\nMisses: %d\n", servidor.getHits(), servidor.getMisses());

      System.out.printf("---------------------------------------------\n");

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