package inventory.repository;

import inventory.model.AbstractPart;
import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductAndPartsRepositoryUnitTest {
    @Mock
    private Inventory inventoryMock;

    @InjectMocks
    private ProductAndPartsRepository repository;

    private static final int id = 1;
    private static final String name = "name";
    private static final double price = 10.;
    private static final int inStock = 10;
    private static final int min = 5;
    private static final int max = 15;
    private static final AbstractPart part = new InhousePart(1, "part_name", 10.0, 5, 1, 10, 2);
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(part);

    @Test
    public void whenCallingGetAllProducts_ShouldReturnAllProducts() {
        //given
        Mockito.when(inventoryMock.getProducts()).thenReturn(FXCollections.observableArrayList(
                new Product(id, name, price, inStock, min, max, addParts)));

        //when
        ObservableList<Product> products = repository.getAllProducts();

        //then
        Mockito.verify(inventoryMock, times(1)).getProducts();
        assertEquals(products.size(),1);
        assertEquals(products.get(0).getProductId(), id);
    }

    @Test
    public void whenCallingGetAllParts_ShouldReturnAllParts() {
        //given
        Mockito.when(inventoryMock.getAllParts()).thenReturn(FXCollections.observableArrayList(part));

        //when
        ObservableList<AbstractPart> parts = repository.getAllParts();

        //then
        Mockito.verify(inventoryMock, times(1)).getAllParts();
        assertEquals(parts.size(), 1);
        assertEquals(parts.get(0).getPartId(), 1);
    }
}
