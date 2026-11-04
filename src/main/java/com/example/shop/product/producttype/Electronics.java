package com.example.shop.product.producttype;

import com.example.shop.product.Product;

import java.math.BigDecimal;

public class Electronics extends Product {
    public Electronics(int id, String name, String brand, BigDecimal price, int availableQuantity) {
        super(id, name, brand, price, availableQuantity);
    }
}
