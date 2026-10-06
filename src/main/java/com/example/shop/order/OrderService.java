package com.example.shop.order;

import com.example.shop.exceptions.InvalidOrderStatusException;
import com.example.shop.file.FileSavingService;
import com.example.shop.invoice.Invoice;

public class OrderService {
    private final OrderRepository orderRepository;
    private final FileSavingService fileSavingService;

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

        Invoice invoice = generateInvoice(order);
        order.setStatus(OrderStatus.COMPLETED);

        if (orderRepository.existsById(order.getId())) {
            orderRepository.updateOrder(order);
        } else {
            orderRepository.addOrder(order);
        }

        fileSavingService.saveInvoiceAsText(invoice);
        fileSavingService.appendOrderToLog(order);
    }

    public Invoice generateInvoice(Order order) {
        return new Invoice(order);
    }
}
