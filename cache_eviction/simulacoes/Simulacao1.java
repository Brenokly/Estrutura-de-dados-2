package cache_eviction.simulacoes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cache_eviction.Cliente;
import cache_eviction.OrderService;
import cache_eviction.Server;
import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Simulacao1 { // Simulação 1: É uma simulação geral, com todas as operações pedidas no arquivo
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);

    // ------------------------------------------------------------8------------------

    // Limpar o arquivo de log antes de começar

    File file = new File("cache_eviction/log.txt");

    try (FileWriter writer = new FileWriter(file)) {
      // Abrir o arquivo em modo de escrita sem escrever nada
      // Isso limpa o conteúdo do arquivo
    } catch (IOException e) {
      System.out.println("Ocorreu um erro ao limpar o arquivo: " + e.getMessage());
    }

    // -------------------------------------------------------------------------------

    try {
      // Adicionar 60 ordens de serviço iniciais
      System.out.println("---------------------------------------------");
      System.out.println("Início da Inserção de Ordens de Serviço");
      System.out.println("---------------------------------------------");
      for (int i = 1; i <= 60; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        cliente.registerOrderService(os);
      }

      // --------------------------------------------------------------------------------

      // Log da inserção de 60 ordens de serviço

      String message = String.format("\n----------------------------------------------------\n"
          + "Insercao de 60 Ordens de Servico concluida."
          + "\n----------------------------------------------------\n");
      try (FileWriter fw = new FileWriter("cache_eviction/log.txt", true);
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
      System.out.println("Busca Ordem de Serviço com código 1");
      OrderService auxi = cliente.searchOrderService(1);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" + auxi.getDescricao() + "\t"
          + auxi.getHoraSolicitacao());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 2. Busca 2
      System.out.println("Busca Ordem de Serviço com código 1 novamente");
      auxi = cliente.searchOrderService(1);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" + auxi.getDescricao() + "\t"
          + auxi.getHoraSolicitacao());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 3. Busca 3
      System.out.println("Busca Ordem de Serviço com código 2");
      auxi = cliente.searchOrderService(2);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" + auxi.getDescricao() + "\t"
          + auxi.getHoraSolicitacao());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 4. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      List<OrderService> orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 5. Um cadastro
      System.out.println("Cadastro de Nova Ordem de Serviço");
      boolean registrado = cliente.registerOrderService(new OrderService("Nome61", "Descrição61"));
      System.out.println("Registro bem-sucedido: " + registrado);
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 6. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 7. Outro cadastro
      System.out.println("Cadastro de Outra Ordem de Serviço");
      boolean registrado2 = cliente.registerOrderService(new OrderService("Nome62", "Descrição62"));
      System.out.println("Registro bem-sucedido: " + registrado2);
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 8. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 9. Uma alteração
      System.out.println("Alteração da Ordem de Serviço com código 61");
      boolean alterado = cliente.alterOrderService(new OrderService(61, "Alterado", "Alterada"));
      System.out.println("Alteração bem-sucedida: " + alterado);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 10. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 11. Uma remoção
      System.out.println("Remoção da Ordem de Serviço com código 1");
      Boolean removido = cliente.removeOrderService(1);
      System.out.println("Remoção bem-sucedida: " + removido);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 12. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 13. Outra remoção
      System.out.println("Remoção da Ordem de Serviço com código 55");
      boolean removido2 = cliente.removeOrderService(55);
      System.out.println("Remoção bem-sucedida: " + removido2);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // 14. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("---------------------------------------------");

      // --------------------------------------------------------------------------------

      // 15. Quantidade de registros
      System.out.println("Quantidade de Registros: " + cliente.getQuantityRecords());

    } catch (NodeNotFoundException e) { // Tratamento de nodes não encontrados
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Não Encontrado");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    } catch (NodeAlreadyExistsException e) { // Tratamento de nodes já existentes
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