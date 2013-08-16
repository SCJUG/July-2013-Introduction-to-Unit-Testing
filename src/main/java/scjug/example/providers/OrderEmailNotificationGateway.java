package scjug.example.providers;

import org.springframework.stereotype.Component;
import scjug.example.Order;

@Component
public class OrderEmailNotificationGateway {
    public Boolean sendOrderSubmittedEmail(Order order) {
        System.out.println("Emailing customer to notify that order [?] was submitted ...");
        return true;
    }
}
