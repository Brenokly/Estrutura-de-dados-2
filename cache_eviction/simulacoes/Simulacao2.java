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
      // Adicionar 21 ordens de serviço iniciais
      System.out.println("---------------------------------------------");
      System.out.println("Início da Inserção de Ordens de Serviço");
      System.out.println("---------------------------------------------");
      for (int i = 1; i <= 21; i++) {
        OrderService os = new OrderService("Nome" + i, "Descrição" + i);
        cliente.registerOrderService(os);
      }

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
      System.out.println("---------------------------------------------");

      // -------------------------------------------------------------------------------

      // Realizar buscas (em um for) para gerar hits e misses
      System.out.println("---------------------------------------------");
      System.out.println("Início da Simulação de Buscas");
      System.out.println("---------------------------------------------");

      // Buscar 20 ordens de serviço
      for (int i = 1; i <= 20; i++) {
        System.out.println("Busca Ordem de Serviço com código " + i);
        auxi = cliente.searchOrderService(i);
        System.out.println("Ordem de Serviço encontrada:\n");
        System.out.println("Código\tNome\tDescrição\tHora Solicitação");
        System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" +
            auxi.getDescricao() + "\t"
            + auxi.getHoraSolicitacao());
        System.out.println("---------------------------------------------");

        // Exibir estado da cache
        System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
        servidor.getCache().show();
        System.out.println("---------------------------------------------");
      }

      // -------------------------------------------------------------------------------

      // 20 vigésima primeira busca (cache cheia) será necessário substituir o último
      System.out.println("Busca Ordem de Serviço com código " + 21);
      auxi = cliente.searchOrderService(21);
      System.out.println("Ordem de Serviço encontrada:\n");
      System.out.println("Código\tNome\tDescrição\tHora Solicitação");
      System.out.println(auxi.getCodigo() + "\t" + auxi.getNome() + "\t" +
          auxi.getDescricao() + "\t"
          + auxi.getHoraSolicitacao());
      System.out.println("---------------------------------------------");

      // Exibir estado da cache
      System.out.println("Estado da Cache: " + servidor.getCache().size() + " elementos");
      servidor.getCache().show();
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
    }

    System.out.println("Hitts: " + servidor.getHits());
    System.out.println("Misses: " + servidor.getMisses());
  }
}