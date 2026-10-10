package com.example.shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedAmountDiscount implements DiscountStrategy {
    private final BigDecimal discountAmount;

    public FixedAmountDiscount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Kwota rabatu nie może być ujemna");
        }
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice) {
        BigDecimal newPrice = originalPrice.subtract(discountAmount);
        return newPrice.compareTo(BigDecimal.ZERO) > 0 ? newPrice : BigDecimal.ZERO;
    }
}
