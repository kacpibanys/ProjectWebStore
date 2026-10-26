package com.example.shop.product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Integer, Product> products = new ConcurrentHashMap<>();

    @Override
    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return products.values().stream().toList();
    }

    @Override
    public void updateProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public void removeProduct(int id) {
        products.remove(id);
    }

    @Override
    public boolean existsById(int id) {
        return products.containsKey(id);
    }
}
