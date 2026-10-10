package com.example.shop.order;

import com.example.shop.exceptions.InvalidOrderStatusException;
import com.example.shop.file.FileSavingService;
import com.example.shop.invoice.Invoice;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService {
    private final OrderRepository orderRepository;
    private final FileSavingService fileSavingService;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.fileSavingService = null;
    }

    public OrderService(OrderRepository orderRepository, FileSavingService fileSavingService) {
        this.orderRepository = orderRepository;
        this.fileSavingService = fileSavingService;
    }

    public void processOrder(Order order) {
        if(order.getStatus() != OrderStatus.NEW) {
            throw new InvalidOrderStatusException("Only NEW orders can be processed. Current status is " + order.getStatus());
        }

        order.setStatus(OrderStatus.PROCESSING);
        simulateLongOrderProcessing();
        Invoice invoice = generateInvoice(order);
        order.setStatus(OrderStatus.COMPLETED);

        if (orderRepository.existsById(order.getId())) {
            orderRepository.updateOrder(order);
        } else {
            orderRepository.addOrder(order);
        }

        if (fileSavingService != null) {
            fileSavingService.saveInvoiceAsText(invoice);
            fileSavingService.appendOrderToLog(order);
        }
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
