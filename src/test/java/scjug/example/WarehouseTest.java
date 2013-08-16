package scjug.example;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class WarehouseTest {
    @Test
    public void testEqualsDifferentCase() throws Exception {
        assertThat(new Warehouse("cleveland"), equalTo(new Warehouse("Cleveland")));
    }

    @Test
    public void testEqualsSameCase() throws Exception {
        assertThat(new Warehouse("Cleveland"), equalTo(new Warehouse("Cleveland")));
    }

    @Test
    public void testNotEquals() throws Exception {
        assertThat(new Warehouse("cleveland"), not(equalTo(new Warehouse("Boston"))));
    }
}
