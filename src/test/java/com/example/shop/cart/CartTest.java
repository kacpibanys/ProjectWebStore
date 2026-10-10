package com.example.shop.cart;

import com.example.shop.product.Product;
import com.example.shop.product.producttype.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class CartTest {
    private Cart cart;
    private Computer laptop;
    private Computer desktop;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        laptop = new Computer(1, "Office laptop", "Dell", new BigDecimal("3000.00"), 10);
        desktop = new Computer(2, "Office PC", "Dell", new BigDecimal("150.00"), 50);
    }

    @Test
    void shouldAddProductAndMergeQuantities() {
        //Act
        cart.addProduct(laptop, 1);
        cart.addProduct(laptop, 2);
        //Assert
        Map<Product, Integer> items = cart.getItems();
        assertEquals(3, items.get(laptop));
    }

    @Test
    void shouldRemoveProductPartiallyAndCompletely() {
        //Act
        cart.addProduct(laptop, 3);
        //first delete
        cart.removeProduct(laptop, 1);
        assertEquals(2, cart.getItems().get(laptop));
        //complete delete
        cart.removeProduct(laptop, 2);
        //Assert
        assertFalse(cart.getItems().containsKey(laptop));
    }

    @Test
    void shouldCalculateTotalPrice() {
        //Act
        cart.addProduct(laptop, 2);
        cart.addProduct(desktop, 3);
        //Assert
        assertEquals(new BigDecimal("6450.00"), cart.getTotalPrice());
    }

    @Test
    void shouldThrowExceptionWhenTryingToModifyItems() {
        //Act
        cart.addProduct(laptop, 1);
        //Assert
        assertThrows(UnsupportedOperationException.class, () -> cart.getItems().clear());
    }

    @Test
    void shouldRemoveProductCompletely() {
        //Arraneg
        cart.addProduct(laptop, 3);
        cart.addProduct(desktop, 1);
        //Act
        cart.removeProductCompletely(laptop);
        //Assert
        assertFalse(cart.getItems().containsKey(laptop));
        assertTrue(cart.getItems().containsKey(desktop));
    }
}