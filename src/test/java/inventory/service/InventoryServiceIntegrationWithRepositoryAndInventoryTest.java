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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceIntegrationWithRepositoryAndInventoryTest {
    private Inventory inventory;
    private ProductAndPartsRepository repository;
    private InventoryService service;

    private static final int id1 = 1;
    private static final int id2 = 2;
    private static final String name1 = "product1";
    private static final String name2 = "product2";
    private static final double price = 4;
    private static final int inStock = 15;
    private static final int min = 0;
    private static final int max = 100;
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(new InhousePart(1, "1", 2, 15, 10, 20, 1));

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        repository = new ProductAndPartsRepository(inventory);
        service = new InventoryService(repository);

        inventory.addProduct(new Product(id1, name1, price, inStock, min, max, addParts));
        inventory.addProduct(new Product(id2, name2, price, inStock, min, max, addParts));
    }

    @Test
    public void givenExistingProduct_whenCallingLookupProduct_thenProductIsReturned() {
        //given
        final String searchProductName = "product1";

        //when
        final Product searchedProduct = service.lookupProduct(searchProductName);

        //then
        assertEquals(searchedProduct.getProductId(), id1);
        assertEquals(searchedProduct.getName(), name1);
    }

    @Test
    public void whenCallingGetAllProducts_thenReturnAllProducts() {
        //given
        //when
        List<Product> products = service.getAllProducts();

        //then
        assertEquals(products.size(), 2);
        assertEquals(products.get(0).getProductId(), id1);
        assertEquals(products.get(1).getProductId(), id2);
        assertEquals(products.get(0).getName(),name1);
        assertEquals(products.get(1).getName(), name2);
    }
}
