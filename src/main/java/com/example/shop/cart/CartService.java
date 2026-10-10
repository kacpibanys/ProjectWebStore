package com.example.shop.cart;

import com.example.shop.discount.DiscountStrategy;
import com.example.shop.discount.FixedAmountDiscount;
import com.example.shop.discount.PercentageDiscount;
import com.example.shop.exceptions.EmptyCartException;
import com.example.shop.exceptions.OutOfStockException;
import com.example.shop.product.Product;
import com.example.shop.product.ProductManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private final ProductManager productManager;
    private final Cart cart;
    private final Map<String, DiscountStrategy> promoCodes = new HashMap<>();
    private DiscountStrategy activeDiscount = null;

    public CartService(ProductManager productManager, Cart cart) {
        this.productManager = productManager;
        this.cart = cart;
        promoCodes.put("STUDENT20", new PercentageDiscount(20));
        promoCodes.put("MINUS50", new FixedAmountDiscount(new BigDecimal("50.00")));
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

        items.forEach((productInCart, requestedQuantity) -> {
            Product productFromInventory = productManager.getProductById(productInCart.getId());
            if (productFromInventory.getAvailableQuantity() < requestedQuantity) {
                throw new OutOfStockException("We don't have enough quantity of: " + productFromInventory.getName());
            }
        });

        items.forEach((productInCart, requestedQuantity) -> {
            Product productFromInventory = productManager.getProductById(productInCart.getId());
            int quantityLeftOnStock = productFromInventory.getAvailableQuantity() - requestedQuantity;
            productManager.updateStock(productFromInventory.getId(), quantityLeftOnStock);
        });

        BigDecimal totalToPay = calculateFinalTotal();
        this.activeDiscount = null;
        cart.clear();

        return totalToPay;
    }

    public Map<Product, Integer> getCartItems() {
        return cart.getItems();
    }

    public BigDecimal calculateBaseTotal() {
        return cart.getItems().entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean applyPromoCode(String code) {
        String upperCode = code.trim().toUpperCase();
        if (promoCodes.containsKey(upperCode)) {
            this.activeDiscount = promoCodes.get(upperCode);
            return true;
        }
        return false;
    }

    public BigDecimal calculateFinalTotal() {
        BigDecimal baseTotal = calculateBaseTotal();
        if (activeDiscount != null) {
            return activeDiscount.applyDiscount(baseTotal);
        }
        return baseTotal;
    }

}
