package com.example.shop.order;

import com.example.shop.exceptions.InvalidOrderStatusException;
import com.example.shop.file.FileSavingService;
import com.example.shop.invoice.Invoice;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService implements OrderProcessor {
    private final OrderRepository orderRepository;
    private final EventBus eventBus;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.eventBus = new EventBus();
    }

    public OrderService(OrderRepository orderRepository, EventBus eventBus) {
        this.orderRepository = orderRepository;
        this.eventBus = eventBus;
    }

    public void processOrder(Order order) {
        if(order.getStatus() != OrderStatus.NEW) {
            throw new InvalidOrderStatusException("Only NEW orders can be processed. Current status is " + order.getStatus());
        }

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(OrderStatus.PROCESSING);
        eventBus.publish(new OrderStatusChangedEvent(order, oldStatus, OrderStatus.PROCESSING));

        simulateLongOrderProcessing();
        Invoice invoice = generateInvoice(order);

        order.setStatus(OrderStatus.COMPLETED);
        eventBus.publish(new OrderStatusChangedEvent(order, OrderStatus.PROCESSING, OrderStatus.COMPLETED));

        if (orderRepository.existsById(order.getId())) {
            orderRepository.updateOrder(order);
        } else {
            orderRepository.addOrder(order);
        }

        eventBus.publish(new OrderCompletedEvent(order, invoice));
    }

    public Invoice generateInvoice(Order order) {
        return new Invoice(order);
    }

    private void simulateLongOrderProcessing() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void processOrdersSync(List<Order> orders) {
        orders.forEach(this::processOrder);
    }
    public void processOrdersAsync(List<Order> orders) {
        List<CompletableFuture<Void>> futures = orders.stream()
                .map(order -> CompletableFuture.runAsync(() -> processOrder(order), executor))
                .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
