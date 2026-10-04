package com.example.shop.product;


import com.example.shop.exceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ProductManager {
    private final ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product with id: " + product.getId() + " already exists");
        }
        productRepository.addProduct(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Couldn't find a product with id: " + id));

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void updateStock(int id, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        Product product = getProductById(id);
        product.setAvailableQuantity(newQuantity);
        productRepository.updateProduct(product);
    }

    public void updatePrice(int id, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Product product = getProductById(id);
        product.setPrice(newPrice);
        productRepository.updateProduct(product);
    }

    public void updateProduct(Product updatedProduct) {
        if (updatedProduct == null) {
            throw new IllegalArgumentException("Updated product cannot be null");
        }

        if (!productRepository.existsById(updatedProduct.getId())){
            throw new IllegalArgumentException("There is no product with id: " + updatedProduct.getId());
        }

        productRepository.updateProduct(updatedProduct);
    }

    public void removeProduct(int id) {
        getProductById(id);
        productRepository.removeProduct(id);
    }
}