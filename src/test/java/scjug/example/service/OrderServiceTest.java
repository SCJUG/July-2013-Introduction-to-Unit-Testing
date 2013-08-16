package scjug.example.service;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import scjug.example.Catalog;
import scjug.example.CatalogItem;
import scjug.example.Order;
import scjug.example.Warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private Catalog catalog1;

    @Mock
    private Catalog catalog2;

    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private List<Catalog> catalogs;
    private List<Warehouse> warehouses;

    private OrderService fixture;

    @Before
    public void setUp() throws Exception {
        catalogs = new ArrayList<Catalog>(Arrays.asList(catalog1, catalog2));

        warehouse1 = new Warehouse("Atlanta");
        warehouse2 = new Warehouse("Singapore");
        warehouse3 = new Warehouse("Miami");
        warehouses = new ArrayList<Warehouse>(Arrays.asList(warehouse1, warehouse2));

        fixture = new OrderService();
        fixture.setCatalogs(catalogs);
        fixture.setWarehouses(warehouses);
    }

    @Test
    public void testCreateOrderWithExistingItems() throws Exception {
        CatalogItem item1 = new CatalogItem();
        CatalogItem item2 = new CatalogItem();

        List<CatalogItem> items = new ArrayList<>(Arrays.asList(item1, item2));

        //stub out behavior
        when(catalog1.hasItem(item1)).thenReturn(true);
        when(catalog1.hasItem(item2)).thenReturn(false);
        when(catalog2.hasItem(item2)).thenReturn(true);

        Order actual = fixture.createOrder(items);

        //assert return result
        assertThat(actual, notNullValue());
        assertThat(actual, hasProperty("orderItems", hasSize(2)));

        //verify mock usage
        verify(catalog1, times(2)).hasItem(argThat(isA(CatalogItem.class)));
        verify(catalog2, times(1)).hasItem(argThat(isA(CatalogItem.class)));
    }

    @Test
    public void testCreateOrderWithOneExistingItemAndOneThatDoesntExist() throws Exception {
        CatalogItem item1 = new CatalogItem();
        CatalogItem item2 = new CatalogItem();

        List<CatalogItem> items = new ArrayList<>(Arrays.asList(item1, item2));

        //stub out behavior
        when(catalog1.hasItem(item1)).thenReturn(true);
        when(catalog1.hasItem(item2)).thenReturn(false);
        when(catalog2.hasItem(item2)).thenReturn(false);

        Order actual = fixture.createOrder(items);

        //assert return result
        assertThat(actual, nullValue());

        //verify mock usage
        verify(catalog1, times(2)).hasItem(argThat(isA(CatalogItem.class)));
        verify(catalog2, times(1)).hasItem(argThat(isA(CatalogItem.class)));
    }

    @Test
    public void testCreateOrderWithFirstItemNotExisting() throws Exception {
        CatalogItem item1 = new CatalogItem();
        CatalogItem item2 = new CatalogItem();

        List<CatalogItem> items = new ArrayList<>(Arrays.asList(item1, item2));

        //stub out behavior
        when(catalog1.hasItem(item1)).thenReturn(false);
        when(catalog2.hasItem(item1)).thenReturn(false);

        Order actual = fixture.createOrder(items);

        //assert return result
        assertThat(actual, nullValue());

        //verify mock usage
        verify(catalog1, times(1)).hasItem(argThat(isA(CatalogItem.class)));
        verify(catalog2, times(1)).hasItem(argThat(isA(CatalogItem.class)));
    }

    @Test
    public void testSubmitOrderWithExistingWarehouseAndSuccessfulSubmission() throws Exception {
        Order order = mock(Order.class);

        when(order.submit(warehouse1)).thenReturn(true);

        Boolean actual = fixture.submitOrder(order, warehouse1);
        assertThat(actual, equalTo(true));
    }

    @Test
    public void testSubmitOrderWithExistingWarehouseButFailedSubmission() throws Exception {
        Order order = mock(Order.class);

        when(order.submit(warehouse1)).thenReturn(false);

        Boolean actual = fixture.submitOrder(order, warehouse1);
        assertThat(actual, equalTo(false));
    }

    @Test
    public void testSubmitOrderWithNonExistentWarehouse() throws Exception {
        Order order = mock(Order.class);
        Boolean actual = fixture.submitOrder(order, warehouse3);
        assertThat(actual, equalTo(false));
    }
}
