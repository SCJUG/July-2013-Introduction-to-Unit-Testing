package scjug.example;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderItemTest {
    @Test
    public void testContructWithNoPrice() throws Exception {
        CatalogItem catalogItem = new CatalogItem(1L, "Ball", 0.99);

        OrderItem item = new OrderItem(catalogItem);

        assertThat(item.getPrice(), equalTo(0.99));
    }
}
