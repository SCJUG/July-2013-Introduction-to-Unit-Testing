package scjug.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scjug.example.Catalog;
import scjug.example.CatalogItem;
import scjug.example.Order;
import scjug.example.Warehouse;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private List<Catalog> catalogs;

    @Autowired
    private List<Warehouse> warehouses;

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public Order createOrder(List<CatalogItem> items) {
        //verify each items exist in at least one catalog
        for (CatalogItem item : items) {
            boolean exists = false;

            for (Catalog catalog : catalogs) {
                if (catalog.hasItem(item)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                return null;
            }
        }

        //create order
        Order order = new Order();
        order.addItems(items);

        return order;
    }

    public Boolean submitOrder(Order order, Warehouse possibleWarehouse) {
        //verify warehouse is real
        boolean exists = false;

        for (Warehouse warehouse : warehouses) {
            if (warehouse.equals(possibleWarehouse)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            return false;
        }

        //submit the order
        return order.submit(possibleWarehouse);
    }
}
