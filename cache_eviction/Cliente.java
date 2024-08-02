package cache_eviction;

import java.util.List;

public class Cliente {
    private Servidor servidor;

    public Cliente(Servidor servidor) {
        this.servidor = servidor;
    }

    public OrderService searchOrderService(int codigo) {
        return servidor.searchOrderService(codigo);
    }

    public void registerOrderService(OrderService OrderService) {
        servidor.registerOrderService(OrderService);
    }

    public List<OrderService> listOrdersService() {
        return servidor.listOrdersService();
    }

    public void alterOrderService(OrderService OrderService) {
        servidor.alterOrderService(OrderService);
    }

    public void removeOrderService(int codigo) {
        servidor.removeOrderService(codigo);
    }

    public int getQuantityRecords() {
        return servidor.getQuantityRecords();
    }
}
