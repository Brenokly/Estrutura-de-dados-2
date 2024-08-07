package cache_eviction;

import java.util.List;
import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class Cliente {
    private Server servidor;

    public Cliente(Server servidor) {
        this.servidor = servidor;
    }

    public OrderService searchOrderService(int codigo) throws NodeNotFoundException {
        return servidor.searchOrderService(codigo);
    }

    public Boolean registerOrderService(OrderService OrderService) throws NodeAlreadyExistsException {
        return servidor.registerOrderService(OrderService);
    }

    public List<OrderService> listOrdersService() {
        return servidor.listOrdersService();
    }

    public Boolean alterOrderService(OrderService OrderService) throws NodeNotFoundException {
        return servidor.alterOrderService(OrderService);
    }

    public Boolean removeOrderService(int codigo) throws InvalidOperationException {
        return servidor.removeOrderService(codigo);
    }

    public int getQuantityRecords() {
        return servidor.getQuantityRecords();
    }
}
