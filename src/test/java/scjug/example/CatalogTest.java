package scjug.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import scjug.example.providers.CatalogRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogTest {

    @Mock
    private CatalogRepository repository;

    private Catalog fixture;

    @Before
    public void setUp() {
        fixture = new Catalog("KidsToys");
        ReflectionTestUtils.setField(fixture, "catalogRepository", repository);
    }

    @Test
    public void testHasItemIsFound() throws Exception {
        CatalogItem item = new CatalogItem();

        //stub out the return result
        when(repository.verifyItemExist(fixture, item)).thenReturn(true);

        //assert the final result
        assertThat(fixture.hasItem(item), equalTo(true));

        //verify the usage of the mock
        verify(repository, times(1)).verifyItemExist(fixture, item);
    }

    @Test
    public void testHasItemNotFound() throws Exception {
        CatalogItem item = new CatalogItem();

        //stub out the return result
        when(repository.verifyItemExist(fixture, item)).thenReturn(false);

        //assert the final result
        assertThat(fixture.hasItem(item), equalTo(false));

        //verify the usage of the mock
        verify(repository, times(1)).verifyItemExist(fixture, item);
    }

    @Test
    public void testEqualsSameCase() throws Exception {
        assertThat(new Catalog("kids"), equalTo(new Catalog("kids")));
    }

    @Test
    public void testEqualsDifferentCase() throws Exception {
        assertThat(new Catalog("kids"), equalTo(new Catalog("Kids")));
    }

    @Test
    public void testNotEquals() throws Exception {
        assertThat(new Catalog("kids"), not(equalTo(new Catalog("adults"))));
    }
}
