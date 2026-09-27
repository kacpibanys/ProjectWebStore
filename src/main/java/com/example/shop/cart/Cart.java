package com.example.shop.cart;

import com.example.shop.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Cart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        Objects.requireNonNull(product, "Product cannot be null");
        validateQuantity(quantity);

        items.merge(product, quantity, Integer::sum);
    }

    public void removeProduct(Product product, int quantityToRemove) {
        Objects.requireNonNull(product, "Product cannot be null");
        validateQuantity(quantityToRemove);

        items.computeIfPresent(product, (existingProduct, currentQuantity) -> {
            int newQuantity = currentQuantity - quantityToRemove;
            return newQuantity > 0 ? newQuantity : null;
        });
    }

    public void removeProductCompletely(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        items.remove(product);
    }

    public BigDecimal getTotalPrice() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<Product, Integer> getItems() {
        return Map.copyOf(items);
    }

    public void clear() {
        items.clear();
    }


    public void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}
