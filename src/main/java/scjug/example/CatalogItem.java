package scjug.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class CatalogItem {

    private Long id;
    private String name;
    private Double suggestedRetailPrice;

    public CatalogItem() {
    }

    public CatalogItem(Long id, String name, Double suggestedRetailPrice) {
        this.id = id;
        this.name = name;
        this.suggestedRetailPrice = suggestedRetailPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSuggestedRetailPrice() {
        return suggestedRetailPrice;
    }

    public void setSuggestedRetailPrice(Double suggestedRetailPrice) {
        this.suggestedRetailPrice = suggestedRetailPrice;
    }

    @Override
    public String toString() {
        return "#" + id + " " + name;
    }
}
