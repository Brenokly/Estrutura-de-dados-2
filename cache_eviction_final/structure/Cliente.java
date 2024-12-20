package cache_eviction_final.structure;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import cache_eviction_final.exceptions.*;

public class Cliente {
    private Server servidor;

    public Cliente(Server servidor) {
        this.servidor = servidor;
    }

    // Método para buscar uma ordem de serviço
    public OrderService searchOrderService(int codigo) {
        try {
            Message message = servidor.searchOrderService(codigo);

            // Descomprime a mensagem e separa os campos
            String[] parts = message.decompress().split(",");

            // Converte os campos para os tipos corretos
            int code = Integer.parseInt(parts[0]);              // Converte o primeiro elemento para int
            String name = parts[1];                             // Segundo elemento é o nome
            String description = parts[2];                      // Terceiro elemento é a descrição
            LocalTime requestTime = LocalTime.parse(parts[3]);  // Quarto elemento é o horário

            // Cria a ordem de serviço
            OrderService orderService = new OrderService(code, name, description, requestTime);

            return orderService;
        } catch (ElementNotFoundException e) {
            return null;
        }
    }

    // Método para registrar uma ordem de serviço
    public Boolean registerOrderService(Message orderService) {
        return servidor.registerOrderService(orderService);
    }

    // Método para alterar uma ordem de serviço
    public Boolean alterOrderService(Message orderService) {
        return servidor.alterOrderService(orderService);
    }

    // Método para remover uma ordem de serviço
    public Boolean removeOrderService(int code) {
        return servidor.removeOrderService(code);
    }

    // Método para listar as ordens de serviço
    public List<OrderService> listOrdersService() {
        // Chama o servidor para obter a mensagem comprimida
        Message message = servidor.listOrdersService(); // Recebe a mensagem comprimida

        // Descomprime a mensagem
        String decompressedData = message.decompress();

        // Converte a string em uma lista de OrderService
        List<OrderService> orders = new ArrayList<>();
        String[] orderStrings = decompressedData.split("\n"); // Divide a string em linhas

        for (String orderString : orderStrings) {
            if (!orderString.trim().isEmpty()) { // Verifica se a linha não está vazia
                // Cria um novo objeto OrderService a partir da string
                OrderService order = new OrderService();
                order.fromString(orderString);
                orders.add(order); // Adiciona à lista
            }
        }

        return orders; // Retorna a lista de ordens de serviço
    }

    // Método para obter a quantidade de registros
    public int getQuantityRecords() {
        return servidor.getQuantityRecords();
    }

    // Método que lista e imprime as ordens de serviço
    public void listPrintOrdersService() {
        servidor.listPrintOrdersService();
    }
}