package com.example.shop.product;

import com.example.shop.product.producttype.Electronics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    private ProductManager manager;
    private Electronics testProduct;

    @BeforeEach
    void setUp() {
        manager = new ProductManager(new InMemoryProductRepository());
        testProduct = new Electronics(1, "Headphones", "Sony", new BigDecimal("300.00"), 10);
    }

    @Test
    void shouldAddAndCheckProduct() {
        //Arrange and act
        manager.addProduct(testProduct);

        //Assert
        assertEquals(testProduct, manager.getProductById(1));
        assertEquals(1, manager.getAllProducts().size());
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateId() {
        //Arrange and act
        manager.addProduct(testProduct);
        Electronics duplicateProduct = new Electronics(1, "Different headphones", "JBL", new BigDecimal("200.00"), 5);

        //Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> manager.addProduct(duplicateProduct));

        assertTrue(exception.getMessage().contains("exists"));
    }

    @Test
    void shouldUpdateStockAndPrice() {
        //Arrange
        manager.addProduct(testProduct);

        //Act
        manager.updateStock(1, 25);
        manager.updatePrice(1, new BigDecimal("250.00"));

        //Assert
        assertEquals(25, manager.getProductById(1).getAvailableQuantity());
        assertEquals(new BigDecimal("250.00"), manager.getProductById(1).getPrice());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNegativePrice() {
        //Arrange and act
        manager.addProduct(testProduct);

        //Assert
        assertThrows(IllegalArgumentException.class,
                () -> manager.updatePrice(1, new BigDecimal("-50.00")));
    }

    @Test
    void shouldRemoveProduct() {
        //Arrange and act
        manager.addProduct(testProduct);
        manager.removeProduct(1);

        //Assert
        assertThrows(IllegalArgumentException.class, () -> manager.getProductById(1));
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistentProduct() {
        //Assert
        assertThrows(IllegalArgumentException.class, () -> manager.getProductById(999));
    }

    @Test
    void shouldUpdateEntireProduct() {
        //Arrange
        manager.addProduct(testProduct);
        Electronics updatedProduct = new Electronics(1, "Sluchawki z anc", "Sony", new BigDecimal("450.00"), 50);

        //Act
        manager.updateProduct(updatedProduct);

        //Assert
        Product retrievedProduct = manager.getProductById(1);
        assertEquals("Sluchawki z anc", retrievedProduct.getName());
        assertEquals(new BigDecimal("450.00"), retrievedProduct.getPrice());
        assertEquals(50, retrievedProduct.getAvailableQuantity());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        //Arrange
        Electronics phantomProduct = new Electronics(999, "Atrapa", "Noname", new BigDecimal("10.00"), 1);

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> manager.updateProduct(phantomProduct));

        //Assert
        assertTrue(exception.getMessage().contains("is no"));
    }
}