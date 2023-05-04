package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    static Inventory inventory;
    static Inventory emptyInventory;
    @BeforeAll
    public static void setUpInventory() {
        Product p1 = new Product(1, "cuie", 0, 0, 0, 0, null);
        Product p2 = new Product(2, "ciment", 0, 0, 0, 0, null);
        Product p3 = new Product(3, "var", 0, 0, 0, 0, null);
        inventory = new Inventory(FXCollections.observableArrayList(p1, p2, p3));
        emptyInventory = new Inventory();
    }
    // Testlink - do not change name
    @DisplayName("F02_TC01")
    @Test
    public void givenNonEmptyList_whenSearchCuie_thenCuieIsReturned() {
        Product foundProduct = inventory.lookupProduct("cuie");
        assertNotNull(foundProduct);
        assertEquals(foundProduct.getName(), "cuie");
    }

    @DisplayName("F02_TC02")
    @Test
    public void givenNonEmptyList_whenSearch2_thenCimentIsReturned() {
        Product foundProduct = inventory.lookupProduct("2");
        assertNotNull(foundProduct);
        assertEquals(foundProduct.getName(), "ciment");
    }

    @DisplayName("F02_TC03")
    @Test
    public void givenNonEmptyList_whenSearchVar_thenVarIsReturned() {
        Product foundProduct = inventory.lookupProduct("var");
        assertNotNull(foundProduct);
        assertEquals(foundProduct.getName(), "var");
    }

    // Testlink - do not change name
    @Test
    @DisplayName("F02_TC04")
    public void givenNonEmptyList_whenSearchByNonExistentName_thenNullIsReturned() {
       assertNull(inventory.lookupProduct("alabala"));
    }

    @Test
    @DisplayName("F02_TC05")
    public void givenEmptyList_whenSearchByAlabala_thenDefaultProductIsReturned() {
        Product foundProduct = emptyInventory.lookupProduct("alabala");
        assertNotNull(foundProduct);
        assertEquals(0, foundProduct.getProductId());
        assertNull(foundProduct.getName());
        assertEquals(0, foundProduct.getPrice());
        assertEquals(0, foundProduct.getMin());
        assertEquals(0, foundProduct.getMax());
        assertEquals(0, foundProduct.getInStock());
        assertNull(foundProduct.getAssociatedParts());
    }
}