package cache_eviction.simulacoes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import cache_eviction.Cliente;
import cache_eviction.OrderService;
import cache_eviction.Server;
import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Simulacao3 { // Simulação 3: É uma simulação para testar todas as operações do sistema
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);

    // -------------------------------------------------------------------------------

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
      System.out.println("============================================================");
      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("============================================================");

      // Realizar as operações conforme descrito na descrição do arquivo

      // 1. Um cadastro
      System.out.println("Cadastro de Nova Ordem de Serviço");
      boolean registrado = cliente.registerOrderService(new OrderService("Nome61", "Descrição61"));
      System.out.println("Registro bem-sucedido: " + registrado);
      System.out.println("============================================================");

      // -------------------------------------------------------------------------------

      // 2. Uma busca
      System.out.println("Busca Ordem de Serviço com código 1");
      OrderService auxi = cliente.searchOrderService(1);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" + auxi.getDescricao() + "\t"
          + auxi.getHoraSolicitacao());
      System.out.println("============================================================");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("============================================================");

      // -------------------------------------------------------------------------------

      // 3. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      List<OrderService> orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("============================================================");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("============================================================");

      // -------------------------------------------------------------------------------

      // 4. Uma alteração
      System.out.println("Alteração da Ordem de Serviço com código 1");
      boolean alterado = cliente.alterOrderService(new OrderService(1, "Alterado", "Alterada"));
      System.out.println("Alteração bem-sucedida: " + alterado);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("============================================================");

      // -------------------------------------------------------------------------------

      // 5. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      orders = cliente.listOrdersService();
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println("------------------------------------------");
      for (OrderService order : orders) {
        System.out.println(order.getCodigo() + "\t" + order.getNome() + "\t" + order.getDescricao() + "\t"
            + order.getHoraSolicitacao());
      }
      System.out.println("============================================================");

      // --------------------------------------------------------------------------------

      // 6. Quantidade de registros
      System.out.println("Quantidade de registros: " + cliente.getQuantityRecords());

      // -------------------------------------------------------------------------------

      // 7. Uma remoção
      System.out.println("Remoção da Ordem de Serviço com código 1");
      Boolean removido = cliente.removeOrderService(1);
      System.out.println("Remoção bem-sucedida: " + removido);
      System.out.println("============================================================");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("============================================================");

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
      System.out.println("============================================================");

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