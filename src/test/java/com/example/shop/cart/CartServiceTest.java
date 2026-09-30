package com.example.shop.cart;

import com.example.shop.product.InMemoryProductRepository;
import com.example.shop.product.ProductManager;
import com.example.shop.product.producttype.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private ProductManager productManager;
    private Cart cart;
    private CartService cartService;
    private Computer laptop;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager(new InMemoryProductRepository());
        cart = new Cart();
        cartService = new CartService(productManager, cart);

        laptop = new Computer(1, "ThinkPad", "Lenovo", new BigDecimal("3000.00"), 5);
        productManager.addProduct(laptop);
    }

    @Test
    void shouldAddProductToCartSuccessfully() {
        //Act
        cartService.addProductToCart(1, 2);

        //Assert
        assertEquals(2, cart.getItems().get(laptop));
    }

    @Test
    void shouldThrowExceptionWhenAddingMoreThanAvailableStock() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart(1, 10));
    }

    @Test
    void shouldRemoveProductFromCartSuccessfully() {
        //Arrange
        cartService.addProductToCart(1, 3);

        //Act
        cartService.removeProductFromCart(1, 2);

        //Assert
        assertEquals(1, cart.getItems().get(laptop));
    }

    @Test
    void shouldCheckoutSuccessfullyAndReduceStock() {
        //Arrange
        cartService.addProductToCart(1, 2);

        //Act
        BigDecimal totalToPay = cartService.checkoutCart();

        //Assert
        assertEquals(new BigDecimal("6000.00"), totalToPay);
        assertTrue(cart.getItems().isEmpty());
        assertEquals(3, productManager.getProductById(1).getAvailableQuantity());
    }

    @Test
    void shouldNotCheckoutWhenProductIsOutOfStockDuringTransaction() {
        //Arrange
        cartService.addProductToCart(1, 4);
        productManager.updateStock(1, 2);

        //Act and Assert
        assertThrows(IllegalStateException.class, () -> cartService.checkoutCart());
        assertEquals(2, productManager.getProductById(1).getAvailableQuantity());
        assertFalse(cart.getItems().isEmpty());
    }
}