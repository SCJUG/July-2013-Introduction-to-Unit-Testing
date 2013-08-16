package scjug.example.providers;

import org.springframework.stereotype.Repository;
import scjug.example.Catalog;
import scjug.example.CatalogItem;

import java.util.List;

@Repository
public class CatalogRepository {
    public boolean verifyItemExist(Catalog catalog, CatalogItem catalogItems) {
        System.out.println("Searching for catalog items:\n");
        return true;
    }
}
