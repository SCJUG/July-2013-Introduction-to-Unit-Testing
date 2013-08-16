package scjug.example;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CatalogItemTest {
    @Test
    public void testToString() throws Exception {
        CatalogItem item = new CatalogItem(1L, "Rocking Horse", 23.55);
        assertThat(item.toString(), equalTo("#1 Rocking Horse"));
    }
}
