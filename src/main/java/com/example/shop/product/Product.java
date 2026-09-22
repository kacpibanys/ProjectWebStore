package com.example.shop.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public abstract class Product {
    private final int id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int availableQuantity;
}
