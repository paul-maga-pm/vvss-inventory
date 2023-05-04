package inventory.service;

import inventory.model.AbstractPart;
import inventory.model.InhousePart;
import inventory.model.Product;
import inventory.model.exception.InvalidProductException;
import inventory.repository.ProductAndPartsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceUnitTest {

    @Mock
    private ProductAndPartsRepository repositoryMock;

    @InjectMocks
    private InventoryService service;

    private static final int id = 1;
    private static final String name = "name";
    private static final double price = 10.;
    private static final int inStock = 10;
    private static final int min = 5;
    private static final int max = 15;
    private static final AbstractPart part = new InhousePart(1, "part_name", 10.0, 5, 1, 10, 2);
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(part);

    @Test
    public void givenValidProduct_whenAddProductIsCalled_thenProductIsSaved() {
        // given
        Mockito.doNothing().when(repositoryMock).addProduct(any());
        Mockito.when(repositoryMock.getAutoProductId()).thenReturn(id);
        Mockito.when(repositoryMock.lookupProduct(name)).thenReturn(new Product(id, name, price, inStock, min, max, addParts));

        // when
        try {
            service.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        Mockito.verify(repositoryMock, times(1)).addProduct(new Product(id, name, price, inStock, min, max, addParts));
        Product addedProduct = service.lookupProduct(name);
        assertNotNull(addedProduct);
        assertEquals(id, addedProduct.getProductId());
    }

    @Test
    public void givenProduct_whenDeleteProductIsCalled_thenProductIsDeleted() {
        // given
        Mockito.doNothing().when(repositoryMock).deleteProduct(any());
        Mockito.when(repositoryMock.lookupProduct(name)).thenReturn(null);

        //when
        service.deleteProduct(new Product(id, name, price, inStock, min, max, addParts));

        //then
        Mockito.verify(repositoryMock, times(1)).deleteProduct(new Product(id, name, price, inStock, min, max, addParts));
        Product product = service.lookupProduct(name);
        assertNull(product);

    }
}