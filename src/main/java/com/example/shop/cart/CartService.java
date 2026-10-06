package com.example.shop.cart;

import com.example.shop.exceptions.EmptyCartException;
import com.example.shop.exceptions.OutOfStockException;
import com.example.shop.product.Product;
import com.example.shop.product.ProductManager;

import java.math.BigDecimal;
import java.util.Map;

public class CartService {
    private final ProductManager productManager;
    private final Cart cart;

    public CartService(ProductManager productManager, Cart cart) {
        this.productManager = productManager;
        this.cart = cart;
    }

    public void addProductToCart(int productId, int quantity) {
        Product product = productManager.getProductById(productId);
        if (product.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException("there are not enough products in this shop");
        }

        cart.addProduct(product, quantity);
    }

    public void removeProductFromCart(int productId, int quantityToRemove) {
        Product product = productManager.getProductById(productId);
        cart.removeProduct(product, quantityToRemove);
    }

    public BigDecimal checkoutCart() {
        Map<Product, Integer> items = cart.getItems();
        if (items.isEmpty()) {
            throw new EmptyCartException("Cannot checkout an empty cart");
        }

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product productInCart = entry.getKey();
            int requestedQuantity = entry.getValue();

            Product productFromInventory = productManager.getProductById(productInCart.getId());
            if (productFromInventory.getAvailableQuantity() < requestedQuantity) {
                throw new OutOfStockException("We don't have enough quantity of: " + productFromInventory.getName());
            }
        }

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product productInCart = entry.getKey();
            int requestedQuantity = entry.getValue();

            Product productFromInventory = productManager.getProductById(productInCart.getId());
            int quantityLeftOnStock = productFromInventory.getAvailableQuantity() - requestedQuantity;

            productManager.updateStock(productFromInventory.getId(), quantityLeftOnStock);
        }

        BigDecimal totalToPay = cart.getTotalPrice();
        cart.clear();

        return totalToPay;
    }

    public Map<Product, Integer> getCartItems() {
        return cart.getItems();
    }

}
