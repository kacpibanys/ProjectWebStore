package com.example.shop.cart;

import com.example.shop.discount.*;
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
    private final PromoCodeService promoCodeService;

    public CartService(ProductManager productManager, Cart cart, PromoCodeService promoCodeService) {
        this.productManager = productManager;
        this.cart = cart;
        this.promoCodeService = promoCodeService;
    }

    public void addProductToCart(int productId, int quantity) {
        Product product = productManager.getProductById(productId);
        int quantityAlreadyInCart = cart.getProductQuantity(product);

        if (product.getAvailableQuantity() < (quantity + quantityAlreadyInCart) ) {
            throw new OutOfStockException("there are not enough products in this shop");
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
            productManager.decreaseStock(productInCart.getId(), requestedQuantity);
        });

        BigDecimal totalToPay = calculateFinalTotal();
        cart.clear();

        return totalToPay;
    }

    public Map<Product, Integer> getCartItems() {
        return cart.getItems();
    }

    public BigDecimal calculateBaseTotal() {
        return cart.getTotalPrice();
    }

    public boolean applyPromoCode(String code) {
        DiscountStrategy foundStrategy = promoCodeService.getStrategyForPromoCode(code);
        if (foundStrategy != null) {
            cart.setDiscountStrategy(foundStrategy);
            return true;
        }
        return false;
    }

    public BigDecimal calculateFinalTotal() {
        BigDecimal baseTotal = calculateBaseTotal();
        return cart.getDiscountStrategy().applyDiscount(baseTotal);
    }

}
