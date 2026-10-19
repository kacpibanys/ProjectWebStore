package com.example.shop.order;

import com.example.shop.invoice.Invoice;

public record OrderCompletedEvent(Order order, Invoice invoice) implements ShopEvent {
}
