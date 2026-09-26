package com.example.shop.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductManager {
    private final Map<Integer, Product> productRepository = new ConcurrentHashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepository.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with id: " + product.getId() + " already exists");
        }
        productRepository.put(product.getId(), product);
    }

    public Product getProductById(int id) {
        if (!productRepository.containsKey(id)) {
            throw new IllegalArgumentException("Couldn't find a product with id: " + id);
        }
        return productRepository.get(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.values().stream().toList();
    }

    public void updateStock(int id, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        Product product = getProductById(id);
        product.setAvailableQuantity(newQuantity);
    }

    public void updatePrice(int id, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Product product = getProductById(id);
        product.setPrice(newPrice);
    }

    public void updateProduct(Product updatedProduct) {
        if (updatedProduct == null) {
            throw new IllegalArgumentException("Updated product cannot be null");
        }

        if (!productRepository.containsKey(updatedProduct.getId())) {
            throw new IllegalArgumentException("There is no product with id: " + updatedProduct.getId());
        }

        productRepository.put(updatedProduct.getId(), updatedProduct);
    }

    public void removeProduct(int id) {
        getProductById(id);
        productRepository.remove(id);
    }
}