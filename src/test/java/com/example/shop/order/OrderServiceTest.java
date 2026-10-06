package com.example.shop.order;

import com.example.shop.customer.Customer;
import com.example.shop.invoice.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderService = new OrderService(orderRepository);

        Customer customer = new Customer(1, "Kacper", "Banys", "kacper@gmail.com");

        testOrder = new Order(1, customer, new HashMap<>(), new BigDecimal("6000.00"), OrderStatus.NEW, LocalDateTime.now());
    }

    @Test
    void shouldProcessNewOrderSuccessfully() {
        //Act
        orderService.processOrder(testOrder);

        //Assert
        assertEquals(OrderStatus.COMPLETED, testOrder.getStatus());
        assertTrue(orderRepository.existsById(testOrder.getId()));
    }

    @Test
    void shouldThrowExceptionWhenOrderIsNotNew() {
        //Arrange
        testOrder.setStatus(OrderStatus.CANCELLED);

        //Act
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            orderService.processOrder(testOrder);
        });

        assertTrue(exception.getMessage().contains("Only NEW"));

        //Assert
        assertFalse(orderRepository.existsById(testOrder.getId()));
    }

    @Test
    void shouldGenerateInvoiceCorrectly() {
        //Act
        Invoice invoice = orderService.generateInvoice(testOrder);

        //Assert
        assertNotNull(invoice);
        assertEquals("INV/2026/1", invoice.invoiceNumber());
        assertEquals(testOrder, invoice.order());
        assertNotNull(invoice.issueDate());
    }
}