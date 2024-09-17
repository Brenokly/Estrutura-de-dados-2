package cache_eviction.simulacoes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cache_eviction.Cliente;
import cache_eviction.OrderService;
import cache_eviction.Server;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Simulacao2 { // Simulação 2: É uma simulação para mostrar o funcionamento da cache
  public static void main(String[] args) {
    // Criar instâncias de Servidor e Cliente
    Server servidor = new Server();
    Cliente cliente = new Cliente(servidor);
    OrderService auxi;

    File file = new File("cache_eviction/log.txt");

    try (FileWriter writer = new FileWriter(file)) {
      // Abrir o arquivo em modo de escrita sem escrever nada
      // Isso limpa o conteúdo do arquivo
    } catch (IOException e) {
      System.out.println("Ocorreu um erro ao limpar o arquivo: " + e.getMessage());
    }

    try {
      OrderService os = new OrderService(41, "Nome" + 41, "Descrição" + 41);
      cliente.registerOrderService(os);

      os = new OrderService(38, "Nome" + 38, "Descrição" + 38);
      cliente.registerOrderService(os);

      os = new OrderService(31, "Nome" + 31, "Descrição" + 31);
      cliente.registerOrderService(os);

      os = new OrderService(12, "Nome" + 31, "Descrição" + 31);
      cliente.registerOrderService(os);

      os = new OrderService(19, "Nome" + 31, "Descrição" + 31);
      cliente.registerOrderService(os);

    } catch (NodeAlreadyExistsException e) { // Tratamento de nodes já existentes
      System.out.println("---------------------------------------------");
      System.out.println("Exceção: Nó Já Existe");
      System.out.println("Mensagem: " + e.getMessage());
      System.out.println("---------------------------------------------");
    }

    System.out.println("Hitts: " + servidor.getHits());
    System.out.println("Misses: " + servidor.getMisses());
  }
}