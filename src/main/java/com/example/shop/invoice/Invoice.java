package com.example.shop.invoice;

import com.example.shop.order.Order;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

public record Invoice(String invoiceNumber, Order order, Instant issueDate) {

    public Invoice(Order order, Clock clock) {
        this("INV/" + LocalDateTime.now().getYear() + "/" + order.getId(), order, Instant.now(clock));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice: ").append(invoiceNumber).append("\n");
        sb.append("Date: ").append(issueDate).append("\n");
        sb.append("Client: ").append(order.getCustomer().getFirstName()).append(" ")
                .append(order.getCustomer().getLastName()).append("\n");
        sb.append("Items:\n");
        BigDecimal baseTotal = BigDecimal.ZERO;

        for (var entry : order.getItems().entrySet()) {
            var product = entry.getKey();
            int qty = entry.getValue();
            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));

            baseTotal = baseTotal.add(lineTotal);

            sb.append(String.format(" - %s | Quantity: %d | Unit price: %s PLN | Total: %s PLN\n",
                    product.getName(), qty, unitPrice, lineTotal));
        }

        BigDecimal discount = baseTotal.subtract(order.getTotalPrice());

        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            sb.append("Base total: ").append(baseTotal).append(" PLN\n");
            sb.append("Discount applied: -").append(discount).append(" PLN\n");
        }

        sb.append("Amount to pay: ").append(order.getTotalPrice()).append(" PLN\n");

        return sb.toString();
    }
}