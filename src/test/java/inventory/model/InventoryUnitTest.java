package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InventoryUnitTest {
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        this.inventory = new Inventory();
    }

    private static final int id = 1;
    private static final String name = "name";
    private static final String updatedName = "new_name";
    private static final double price = 10.;
    private static final int inStock = 10;
    private static final int min = 5;
    private static final int max = 15;
    private static final AbstractPart part = new InhousePart(1, "part_name", 10.0, 5, 1, 10, 2);
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(part);

    @Test
    public void givenExistingProduct_whenUpdateProductIsCalled_thenProductIsUpdated() {
        //given
        Product product = new Product(id, name, price, inStock, min, max, addParts);
        Product updatedProduct = new Product(id, updatedName, price, inStock, min, max, addParts);
        inventory.addProduct(product);

        //when
        inventory.updateProduct(0, updatedProduct);

        //then
        Product searchProduct = inventory.lookupProduct(updatedName);
        assertEquals(searchProduct.getProductId(), id);
    }

    @Test
    public void givenEmptyInventory_whenLookupProductIsCalled_thenReturnDefaultProduct() {
        //given
        final String name = "name";

        //when
        final Product searchedProduct = inventory.lookupProduct(name);

        //then
        assertEquals(searchedProduct.getProductId(), 0);
        assertNull(searchedProduct.getName());
        assertEquals(Double.compare(searchedProduct.getPrice(), 0.0), 0);
        assertEquals(searchedProduct.getInStock(), 0);
        assertEquals(searchedProduct.getMin(), 0);
        assertEquals(searchedProduct.getMax(), 0);
        assertNull(searchedProduct.getAssociatedParts());

    }
}
