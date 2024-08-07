package cache_eviction;

import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Simulacao {
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);

    try {
      // Adicionar 60 ordens de serviço iniciais
      System.out.println("---------------------------------------------");
      System.out.println("Início da Inserção de Ordens de Serviço");
      System.out.println("---------------------------------------------");
      for (int i = 1; i <= 60; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        cliente.registerOrderService(os);
      }

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // Realizar as operações conforme descrito

      // 1. Uma busca
      System.out.println("Busca Ordem de Serviço com código 1");
      cliente.searchOrderService(1);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 2. Busca 2
      System.out.println("Busca Ordem de Serviço com código 1 novamente");
      cliente.searchOrderService(1);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 3. Busca 3
      System.out.println("Busca Ordem de Serviço com código 2");
      cliente.searchOrderService(2);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 4. Uma listagem
      System.out.println("Listagem de Ordens de Serviço");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 5. Um cadastro
      System.out.println("Cadastro de Nova Ordem de Serviço");
      boolean registrado = cliente.registerOrderService(new OrderService("Nome61", "Descrição61"));
      System.out.println("Registro bem-sucedido: " + registrado);
      System.out.println("---------------------------------------------");

      // 6. Uma listagem
      System.out.println("Listagem de Ordens de Serviço Após Cadastro");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

      // 7. Outro cadastro
      System.out.println("Cadastro de Outra Ordem de Serviço");
      boolean registrado2 = cliente.registerOrderService(new OrderService("Nome62", "Descrição62"));
      System.out.println("Registro bem-sucedido: " + registrado2);
      System.out.println("---------------------------------------------");

      // 8. Uma listagem
      System.out.println("Listagem de Ordens de Serviço Após Cadastro");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

      // 9. Uma alteração
      System.out.println("Alteração da Ordem de Serviço com código 61");
      boolean alterado = cliente.alterOrderService(new OrderService(61, "Nome61 Alterado", "Descrição61 Alterada"));
      System.out.println("Alteração bem-sucedida: " + alterado);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 10. Uma listagem
      System.out.println("Listagem de Ordens de Serviço Após Alteração");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

      // 11. Uma remoção
      System.out.println("Remoção da Ordem de Serviço com código 61");
      boolean removido = cliente.removeOrderService(61);
      System.out.println("Remoção bem-sucedida: " + removido);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 12. Uma listagem
      System.out.println("Listagem de Ordens de Serviço Após Remoção");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

      // 13. Outra remoção
      System.out.println("Remoção da Ordem de Serviço com código 55");
      boolean removido2 = cliente.removeOrderService(55);
      System.out.println("Remoção bem-sucedida: " + removido2);
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache:");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // 14. Uma listagem
      System.out.println("Listagem Final de Ordens de Serviço");
      cliente.listOrdersService();
      System.out.println("---------------------------------------------");

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