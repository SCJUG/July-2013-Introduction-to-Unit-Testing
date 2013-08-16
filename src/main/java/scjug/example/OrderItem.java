package scjug.example;

public class OrderItem {
    private CatalogItem catalogItem;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
        this.price = catalogItem.getSuggestedRetailPrice();
    }

    public OrderItem(CatalogItem catalogItem, Double price) {
        this.catalogItem = catalogItem;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CatalogItem getCatalogItem() {
        return catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }
}
