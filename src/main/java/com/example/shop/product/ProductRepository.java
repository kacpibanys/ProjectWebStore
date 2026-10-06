package com.example.shop.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void addProduct(Product product);
    Optional<Product> findById(int id);
    List<Product> findAll();
    void updateProduct(Product product);
    void removeProduct(int id);
    boolean existsById(int id);
}