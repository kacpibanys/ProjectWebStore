package com.example.shop.order;

public record OrderStatusChangedEvent(Order order, OrderStatus oldStatus, OrderStatus newStatus) implements ShopEvent {
}
