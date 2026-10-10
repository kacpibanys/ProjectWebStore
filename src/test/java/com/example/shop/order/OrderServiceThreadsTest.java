package com.example.shop.order;

import com.example.shop.customer.Customer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceThreadsTest {

    @Test
    void asyncShouldBeFasterThanSync(){
        //Arrange
        OrderRepository orderRepository = new InMemoryOrderRepository();
        OrderService orderService = new OrderService(orderRepository);

        int orderCount = 100;
        List<Order> syncOrders = generateTestOrders(orderCount, 1000);
        List<Order> asyncOrders = generateTestOrders(orderCount, 2000);

        //Act
        long startSync = System.currentTimeMillis();
        orderService.processOrdersSync(syncOrders);
        long endSync = System.currentTimeMillis();
        long syncDuration = endSync - startSync;

        long startAsync = System.currentTimeMillis();
        orderService.processOrdersAsync(asyncOrders);
        long endAsync = System.currentTimeMillis();
        long asyncDuration = endAsync - startAsync;

        System.out.println("Sync time: " + syncDuration + " ms");
        System.out.println("Async time: " + asyncDuration + " ms");

        //Assert
        assertTrue(asyncDuration < syncDuration);
    }

    private List<Order> generateTestOrders(int count, int startId) {
        List<Order> orders = new ArrayList<>();
        Customer testCustomer = new Customer(1, "Test", "Szefu", "test@szefu.com");

        for (int i = 0; i < count; i++) {
            orders.add(new Order(
                    startId + i,
                    testCustomer,
                    new HashMap<>(),
                    new BigDecimal("199.99"),
                    OrderStatus.NEW,
                    LocalDateTime.now()
            ));
        }
        return orders;
    }
}
