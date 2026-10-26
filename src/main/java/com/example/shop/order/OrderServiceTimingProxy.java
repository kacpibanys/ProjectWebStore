package com.example.shop.order;

public class OrderServiceTimingProxy implements OrderProcessor {
    private final OrderProcessor target;

    public OrderServiceTimingProxy(OrderProcessor target) {
        this.target = target;
    }

    @Override
    public void processOrder(Order order) {
        System.out.println("PROXY Starting processing order: " + order.getId());

        long startTime = System.currentTimeMillis();
        target.processOrder(order);
        long endTime = System.currentTimeMillis();

        System.out.println("PROXY Finished processing order: " + order.getId() +
                "\nDuration: " + (endTime - startTime) + " ms");
    }
}
