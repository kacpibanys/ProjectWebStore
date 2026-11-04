package com.example.shop.order;

import com.example.shop.customer.Customer;
import com.example.shop.file.FileSavingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceFileTest {
    private final Path csvPath = Paths.get("data", "orders_log.csv");

    @AfterEach
    void clearFiles() throws IOException {
        Files.deleteIfExists(csvPath);
    }

    @Test
    void shouldProcessOrderAndSaveDataToCsvFile() throws IOException {
        //Arrange

        OrderRepository orderRepository = new InMemoryOrderRepository();
        EventBus eventBus = new EventBus();
        FileSavingService fileService = new FileSavingService("data");
        eventBus.subscribe(OrderCompletedEvent.class, event -> {
            fileService.appendOrderToLog(event.order());
        });
        Clock clock = Clock.systemUTC();
        OrderService orderService = new OrderService(orderRepository, eventBus, clock);

        Customer testCustomer = new Customer(99, "Jan", "Niezbedny", "jan@czysty.com");

        Order testOrder = new Order(
                777,
                testCustomer,
                new HashMap<>(),
                new BigDecimal("999.50"),
                OrderStatus.NEW,
                Instant.now(clock)
        );

        //Act
        orderService.processOrder(testOrder);

        //Assert
        assertTrue(Files.exists(csvPath));

        String fileContent = Files.readString(csvPath);

        assertTrue(fileContent.contains("777"));
        assertTrue(fileContent.contains("jan@czysty.com"));
        assertTrue(fileContent.contains("999.50"));
        assertTrue(fileContent.contains("COMPLETED"));
    }
}
