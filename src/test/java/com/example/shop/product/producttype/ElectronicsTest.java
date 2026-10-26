package com.example.shop.product.producttype;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ElectronicsTest {
    @Test
    void shouldCreateElectronicsProduct() {
        // Arrange
        int id = 100;
        String name = "Kabel Usb";
        String brand = "Hama";
        BigDecimal price = new BigDecimal("50");
        int quantity = 200;

        // Act
        Electronics cable = new Electronics(id, name, brand, price, quantity);

        // Assert
        assertEquals(id, cable.getId());
        assertEquals(name, cable.getName());
        assertEquals(brand, cable.getBrand());
        assertEquals(price, cable.getPrice());
        assertEquals(quantity, cable.getAvailableQuantity());
    }
}