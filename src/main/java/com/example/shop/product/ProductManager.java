package com.example.shop.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductManager {
    private final Map<Integer, Product> inventory = new ConcurrentHashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produkt nie może być nullem");
        }
        if (inventory.containsKey(product.getId())) {
            throw new IllegalArgumentException("Produkt o id " + product.getId() + " juz istnieje");
        }
        inventory.put(product.getId(), product);
    }

    public Product getProductById(int id) {
        if (!inventory.containsKey(id)) {
            throw new IllegalArgumentException("Nie znaleziono produktu o ID: " + id);
        }
        return inventory.get(id);
    }

    public List<Product> getAllProducts() {
        return inventory.values().stream().toList();
    }

    public void updateStock(int id, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Ilosc na stanie nie moze byc ujemna");
        }

        Product product = getProductById(id);
        product.setAvailableQuantity(newQuantity);
    }

    public void updatePrice(int id, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cena nie moze byc ujemna");
        }

        Product product = getProductById(id);
        product.setPrice(newPrice);
    }

    public void updateProduct(Product updatedProduct) {
        if (updatedProduct == null) {
            throw new IllegalArgumentException("Produkt po aktualizacji nie może byc nullem");
        }

        if (!inventory.containsKey(updatedProduct.getId())) {
            throw new IllegalArgumentException("Nie ma produktu o id: " + updatedProduct.getId());
        }

        inventory.put(updatedProduct.getId(), updatedProduct);
    }

    public void removeProduct(int id) {
        getProductById(id);
        inventory.remove(id);
    }
}