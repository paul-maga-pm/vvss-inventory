package inventory.service;

import inventory.model.AbstractPart;
import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Product;
import inventory.repository.ProductAndPartsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceIntegrationWithRepositoryTest {
    @Mock
    Inventory inventoryMock;

    @InjectMocks
    ProductAndPartsRepository repository;

    InventoryService service;

    private static final int id = 1;
    private static final String name = "product";
    private static final double price = 4;
    private static final int inStock = 15;
    private static final int min = 0;
    private static final int max = 100;
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(new InhousePart(1, "1", 2, 15, 10, 20, 1));


    @BeforeEach
    public void setUp() {
        service = new InventoryService(repository);
    }

    @Test
    public void givenExistingProduct_whenLookupProductIsCalled_thenProductIsReturned() {
        //given
        final Product product = new Product(id, name, price, inStock,min, max, addParts);
        Mockito.when(inventoryMock.lookupProduct(name)).thenReturn(product);

        //when
        Product searchedProduct = service.lookupProduct(name);

        //then
        Mockito.verify(inventoryMock, times(1)).lookupProduct(name);
        assertEquals(searchedProduct.getProductId(), id);
        assertEquals(searchedProduct.getName(), name);
    }

    @Test
    public void whenGetAllProductsIsCalled_thenReturnAllProducts() {
        //given
        final Product product = new Product(id, name, price, inStock, min, max, addParts);
        Mockito.when(inventoryMock.getProducts()).thenReturn(FXCollections.observableArrayList(product));

        //when
        ObservableList<Product> products = service.getAllProducts();

        //then
        Mockito.verify(inventoryMock, times(1)).getProducts();
        assertEquals(products.size(), 1);
        assertEquals(products.get(0).getProductId(), id);
        assertEquals(products.get(0).getName(), name);

    }
}
