package com.example.shop.invoice;

import com.example.shop.order.Order;

import java.time.LocalDateTime;

public record Invoice(String invoiceNumber, Order order, LocalDateTime issueDate) {

    public Invoice(Order order) {
        this("INV/2026/" + order.getId(), order, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Invoice: " + invoiceNumber + "\n" +
                "Date: " + issueDate + "\n" +
                "Client: " + order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName() + "\n" +
                "Amount: " + order.getTotalPrice() + " PLN";
    }
}