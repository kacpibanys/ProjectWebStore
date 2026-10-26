package com.example.shop.product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Product {
    @EqualsAndHashCode.Include
    private final int id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int availableQuantity;
}
