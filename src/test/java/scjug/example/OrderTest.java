package scjug.example;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import scjug.example.providers.OrderEmailNotificationGateway;
import scjug.example.providers.WarehouseOrderPublisher;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock
    private WarehouseOrderPublisher publisher;

    @Mock
    private OrderEmailNotificationGateway gateway;

    private Order fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new Order();
        fixture.setOrderPublisher(publisher);
        fixture.setEmailGateway(gateway);
    }

    @Test
    public void testConstruct() throws Exception {
        Date datePriorToCall = new Date();
        Order actual = new Order();

        assertThat(actual, hasProperty("creationDate", allOf(notNullValue(), greaterThanOrEqualTo(datePriorToCall))));
        assertThat(actual, hasProperty("orderItems", hasSize(0)));
    }

    @Test
    public void testAddItems() throws Exception {
        CatalogItem item1 = new CatalogItem(1L, "smallTeddyBear", 3.00);
        CatalogItem item2 = new CatalogItem(2L, "bigTeddyBear", 6.00);

        List<CatalogItem> items = Arrays.asList(new CatalogItem[]{item1, item2});

        fixture.addItems(items);

        assertThat(fixture.getOrderItems().size(), equalTo(2));
    }

    @Test
    public void testSubmitOnSuccessfulPublishAndEmail() throws Exception {
        Warehouse warehouse = new Warehouse();
        Date datePriorToSubmission = new Date();

        //stub out success from the publisher
        when(publisher.submit(fixture, warehouse)).thenReturn(true);

        //stub out success from the gateway if an order is provided with a submission date
        when(gateway.sendOrderSubmittedEmail(
                argThat(
                        allOf(
                                sameInstance(fixture),
                                hasProperty("submissionDate", allOf(
                                        notNullValue(),
                                        greaterThanOrEqualTo(datePriorToSubmission)
                                ))
                        )
                )
        )).thenReturn(true);

        boolean actual = fixture.submit(warehouse);

        //assert we succeeded
        assertThat(actual, equalTo(true));

        //verify the publisher and gateway were used in the way we expected
        InOrder order = inOrder(publisher, gateway);
        order.verify(publisher, times(1)).submit(fixture, warehouse);
        order.verify(gateway, times(1)).sendOrderSubmittedEmail(fixture);
    }

    @Test
    public void testSubmitOnSuccessfulPublishButFailedEmail() throws Exception {
        Warehouse warehouse = new Warehouse();
        Date datePriorToSubmission = new Date();

        //stub out success from the publisher
        when(publisher.submit(fixture, warehouse)).thenReturn(true);

        //stub out success from the gateway if an order is provided with a submission date
        when(gateway.sendOrderSubmittedEmail(
                argThat(
                        allOf(
                                sameInstance(fixture),
                                hasProperty("submissionDate", allOf(
                                        notNullValue(),
                                        greaterThanOrEqualTo(datePriorToSubmission)
                                ))
                        )
                )
        )).thenReturn(false);

        boolean actual = fixture.submit(warehouse);

        //assert we succeeded
        assertThat(actual, equalTo(false));

        //verify the publisher and gateway were used in the way we expected
        InOrder order = inOrder(publisher, gateway);
        order.verify(publisher, times(1)).submit(fixture, warehouse);
        order.verify(gateway, times(1)).sendOrderSubmittedEmail(fixture);
    }

    @Test
    public void testSubmitOnFailedPublish() throws Exception {
        Warehouse warehouse = new Warehouse();
        Date datePriorToSubmission = new Date();

        //stub out success from the publisher
        when(publisher.submit(fixture, warehouse)).thenReturn(false);

        boolean actual = fixture.submit(warehouse);

        //assert we succeeded
        assertThat(actual, equalTo(false));

        //verify the publisher and gateway were used in the way we expected
        verify(publisher, times(1)).submit(fixture, warehouse);
        verify(gateway, never()).sendOrderSubmittedEmail(fixture);
    }
}
