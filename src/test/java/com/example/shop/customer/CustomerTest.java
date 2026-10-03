package com.example.shop.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerCorrectly() {
        //Arrange and Act
        Customer customer = new Customer(1, "Kacper", "Banys", "kacper@gmail.com");

        //Assert
        assertEquals(1, customer.getId());
        assertEquals("Kacper", customer.getFirstName());
        assertEquals("Banys", customer.getLastName());
        assertEquals("kacper@gmail.com", customer.getEmail());
    }
}