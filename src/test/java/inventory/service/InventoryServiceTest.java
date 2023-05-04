package inventory.service;

import inventory.model.AbstractPart;
import inventory.model.InhousePart;
import inventory.model.Product;
import inventory.model.exception.InvalidProductException;
import inventory.repository.ProductAndPartsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    public static final String DATA_TEST_FILE = "data/test.txt";
    private ProductAndPartsRepository productRepository;
    private InventoryService inventoryServiceThatIsTested;

    private static final String name = "product";
    private static final double price = 4;
    private static final int inStock = 15;
    private static final ObservableList<AbstractPart> addParts = FXCollections.observableArrayList(new InhousePart(1, "1", 2, 15, 10, 20, 1));

    @BeforeEach
    public void setUp() {
        clearTestFile();
        productRepository = new ProductAndPartsRepository(DATA_TEST_FILE);
        inventoryServiceThatIsTested = new InventoryService(productRepository);
        try {
            inventoryServiceThatIsTested.addProduct("existing", price, inStock, 1, 100, addParts);
            inventoryServiceThatIsTested.addProduct("another", price, inStock, 1, 100, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }
    }

    private void clearTestFile() {
        try (FileWriter writer = new FileWriter(DATA_TEST_FILE)) {
            writer.write("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @ParameterizedTest
    @MethodSource("getValidProducts")
    public void givenValidProduct_whenAddProduct_thenProductIsSaved(String name,
                                                             double price,
                                                             int inStock,
                                                             int min,
                                                             int max,
                                                             ObservableList<AbstractPart> addParts) {
        // when
        try {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        assertNotNull(productRepository.lookupProduct(name));
    }

    @ParameterizedTest
    @ArgumentsSource(ProductArgumentsProvider.class)
    public void givenValidProductByProvider_whenAddProduct_thenProductIsSaved(String name,
                                                                       double price,
                                                                       int inStock,
                                                                       int min,
                                                                       int max,
                                                                       ObservableList<AbstractPart> addParts) {
        // when
        try {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        assertNotNull(productRepository.lookupProduct(name));

    }

    public static class ProductArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                    Arguments.arguments(name, price, 15, 10, 20, addParts),
                    Arguments.arguments(name, price, 0, 0, 1, addParts),
                    Arguments.arguments(name, price, 1, 1, 2, addParts)
            );
        }
    }

    public static Stream<Arguments> getValidProducts() {
        // given
        return Stream.of(
                Arguments.arguments(name, price, 15, 10, 20, addParts),
                Arguments.arguments(name, price, 0, 0, 1, addParts),
                Arguments.arguments(name, price, 1, 1, 2, addParts)
        );
    }

    @Test
    @DisplayName("Test TC1_ECP")
    @Timeout(1)
    public void givenMin10AndMax20_whenAddProduct_thenProductIsSaved() {
        // given
        int min = 10;
        int max = 20;

        // when
        try {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        assertNotNull(productRepository.lookupProduct("product"));
    }

    @Test
    @DisplayName("Test TC2_ECP")
    public void givenMinMinus1AndMax20_whenAddProduct_thenInvalidProductExceptionIsThrown() {
        // given
        int min = -1;
        int max = 20;

        // when
        // then
        assertThrows(InvalidProductException.class, () -> {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        });
    }

    @Test
    @DisplayName("Test TC3_ECP")
    public void givenMin10AndMax5_whenAdd_Product_thenInvalidProductExceptionIsThrown() {
        // given
        int min = 10;
        int max = 5;

        // when
        // then
        assertThrows(InvalidProductException.class, () -> {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        });
    }

    @Test
    @DisplayName("Test TC4_BVA")
    public void givenMin0AndMax1_whenAddProduct_thenProductIsSaved() {
        // given
        int min = 0;
        int max = 1;
        int inStock = 0;

        // when
        try {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        assertNotNull(productRepository.lookupProduct(name));
    }

    @Test
    @DisplayName("Test TC5_BVA")
    public void givenMin1AndMax2_whenAddProduct_thenProductIsSaved() {
        // given
        int min = 1;
        int max = 2;
        int inStock = 1;

        // when
        try {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        } catch (InvalidProductException e) {
            e.printStackTrace();
            fail();
        }

        // then
        assertNotNull(productRepository.lookupProduct(name));
    }

    @Test
    @DisplayName("Test TC6_BVA")
    public void givenMinMinus1AndMax0_whenAddProduct_thenInvalidProductExceptionIsThrown() {
        // given
        int min = -1;
        int max = 0;

        // when
        // then
        assertThrows(InvalidProductException.class, () -> {
           inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        });
    }

    @Test
    @DisplayName("Test TC7_BVA")
    public void givenMin2AndMax1_whenAddProduct_thenInvalidProductExceptionIsThrown() {

        // given
        int min = 2;
        int max = 1;

        // when
        // then
        assertThrows(InvalidProductException.class, () -> {
            inventoryServiceThatIsTested.addProduct(name, price, inStock, min, max, addParts);
        });
    }
}