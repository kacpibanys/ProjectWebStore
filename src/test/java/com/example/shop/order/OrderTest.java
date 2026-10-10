package com.example.shop.order;

import com.example.shop.customer.Customer;
import com.example.shop.product.Product;
import com.example.shop.product.producttype.Computer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateOrderCorrectly() {
        //Arrange
        Customer customer = new Customer(1, "Kacper", "Banys", "kacper@gmail.com");

        Computer laptop = new Computer(1, "Legion", "Lenovo", new BigDecimal("3000.00"), 5);
        Map<Product, Integer> items = new HashMap<>();
        items.put(laptop, 2);

        LocalDateTime now = LocalDateTime.now();

        //Act
        Order order = new Order(1, customer, items, new BigDecimal("6000.00"), OrderStatus.NEW, now);

        //Assert
        assertEquals(1, order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(1, order.getItems().size());
        assertEquals(new BigDecimal("6000.00"), order.getTotalPrice());
        assertEquals(now, order.getCreatedAt());
    }
}