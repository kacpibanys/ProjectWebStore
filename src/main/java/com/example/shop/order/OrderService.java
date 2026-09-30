package com.example.shop.order;

import com.example.shop.invoice.Invoice;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void processOrder(Order order) {
        if(order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException("Only NEW orders can be processed. Current status is " + order.getStatus());
        }

        order.setStatus(OrderStatus.PROCESSING);

        Invoice invoice = generateInvoice(order);
        order.setStatus(OrderStatus.COMPLETED);

        if (orderRepository.existsById(order.getId())) {
            orderRepository.updateOrder(order);
        } else {
            orderRepository.addOrder(order);
        }
    }

    public Invoice generateInvoice(Order order) {
        return new Invoice(order);
    }
}
