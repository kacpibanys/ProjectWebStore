package com.example.shop.order;

import com.example.shop.customer.Customer;
import com.example.shop.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private Customer customer;
    private Map<Product, Integer> items;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}