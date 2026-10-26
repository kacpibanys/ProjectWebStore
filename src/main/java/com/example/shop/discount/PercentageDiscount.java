package com.example.shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountStrategy {
    private final BigDecimal percentage;

    public PercentageDiscount(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100%");
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice) {
        BigDecimal discountAmount = originalPrice.multiply(percentage)
                .divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP);

        return originalPrice.subtract(discountAmount);
    }
}
