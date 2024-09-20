package hashtable.structure;

import java.util.List;
import hashtable.exceptions.*;

public class Cliente {
    private Server servidor;

    public Cliente(Server servidor) {
        this.servidor = servidor;
    }

    public OrderService searchOrderService(int codigo) {
        try {
            return servidor.searchOrderService(codigo);
        } catch (ElementNotFoundException e) {
            return null;
        }
    }

    public Boolean registerOrderService(OrderService orderService) {
        return servidor.registerOrderService(orderService);
    }

    public List<OrderService> listOrdersService() {
        return servidor.listOrdersService();
    }

    public void listPrintOrdersService() {
        servidor.listPrintOrdersService();
    }

    public Boolean alterOrderService(OrderService orderService) {
        return servidor.alterOrderService(orderService);
    }

    public Boolean removeOrderService(int codigo) {
        return servidor.removeOrderService(codigo);
    }

    public int getQuantityRecords() {
        return servidor.getQuantityRecords();
    }
}