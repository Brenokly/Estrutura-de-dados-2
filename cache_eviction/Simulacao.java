package cache_eviction;

import java.time.LocalDateTime;

public class Simulacao {
  public static void main(String[] args) {
    Servidor servidor = new Servidor();
    Cliente cliente = new Cliente(servidor);

    // Adicionar 60 ordens de serviço iniciais
    for (int i = 1; i <= 60; i++) {
      OrderService os = new OrderService("Nome" + i, "Descrição" + i, LocalDateTime.now());
      cliente.registerOrderService(os);
    }

    System.out.println(servidor.getQuantityRecords());

    // Executar operações conforme descrito
    cliente.registerOrderService(new OrderService("Nome61", "Descrição61", LocalDateTime.now()));
    cliente.listOrdersService();

    // Alterar após adicionar
    cliente.alterOrderService(new OrderService(61, "Nome61 Alterado", "Descrição61 Alterada", LocalDateTime.now()));
    cliente.listOrdersService();

    servidor.getDataBase().ordem();

    // Remover
    cliente.removeOrderService(61);
    cliente.listOrdersService();

    servidor.getDataBase().ordem();

    cliente.removeOrderService(55);
    cliente.listOrdersService();
  }
}