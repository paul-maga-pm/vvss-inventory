package inventory.service;

import inventory.model.AbstractPart;
import inventory.model.InhousePart;
import inventory.model.Product;
import inventory.model.exception.InvalidProductException;
import inventory.repository.ProductAndPartsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceUnitTest {

    @Mock
    ProductAndPartsRepository repositoryMock;

    @InjectMocks
    InventoryService service;

    @Test
    void givenValidProduct_whenAddProductIsCalled_thenProductIsSaved() {
        // given
        Integer id = 1;
        String name = "name";
        Double price = 10.;
        Integer inStock = 10;
        Integer min = 5;
        Integer max = 15;
        AbstractPart part = new InhousePart(1, "part_name", 10.0, 5, 1, 10, 2);
        ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(part);

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
        Product addedProduct = repositoryMock.lookupProduct(name);
        assertNotNull(addedProduct);
        assertEquals(id, addedProduct.getProductId());
    }
}