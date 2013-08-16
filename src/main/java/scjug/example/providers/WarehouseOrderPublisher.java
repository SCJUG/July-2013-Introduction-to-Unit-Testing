package scjug.example.providers;

import org.springframework.stereotype.Component;
import scjug.example.Order;
import scjug.example.Warehouse;

@Component
public class WarehouseOrderPublisher {
    public Boolean submit(Order order, Warehouse warehouse) {
        System.out.println("Publishing order [?] as message to warehouse [?] queue ...");
        return true;
    }
}
