package com.example.shop.order;

import com.example.shop.customer.Customer;
import com.example.shop.product.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Setter(AccessLevel.NONE)
    private int id;
    @Setter(AccessLevel.NONE)
    private Customer customer;
    @Setter(AccessLevel.NONE)
    private Map<Product, Integer> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Instant createdAt;
}