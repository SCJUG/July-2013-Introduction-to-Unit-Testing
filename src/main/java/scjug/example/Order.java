package scjug.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scjug.example.providers.OrderEmailNotificationGateway;
import scjug.example.providers.WarehouseOrderPublisher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Order {
    @Autowired
    private WarehouseOrderPublisher orderPublisher;

    @Autowired
    private OrderEmailNotificationGateway emailGateway;

    private List<OrderItem> orderItems;
    private Date creationDate;
    private Date submissionDate;

    public Order() {
        creationDate = new Date();
        orderItems = new ArrayList<OrderItem>();
    }

    public WarehouseOrderPublisher getOrderPublisher() {
        return orderPublisher;
    }

    public void setOrderPublisher(WarehouseOrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    public OrderEmailNotificationGateway getEmailGateway() {
        return emailGateway;
    }

    public void setEmailGateway(OrderEmailNotificationGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addItems(List<CatalogItem> catalogItems) {
        for (CatalogItem catalogItem : catalogItems) {
            OrderItem item = new OrderItem(catalogItem);
            orderItems.add(item);
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Boolean submit(Warehouse warehouse) {
        //send the order to the warehouse
        Boolean submitted = orderPublisher.submit(this, warehouse);
        if (submitted) {
            //set the submission date
            this.submissionDate = new Date();

            //email the customer that the order has been submitted
            Boolean emailed = emailGateway.sendOrderSubmittedEmail(this);

            return emailed;
        }

        return false;
    }
}
