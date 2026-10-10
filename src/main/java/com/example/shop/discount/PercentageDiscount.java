package com.example.shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountStrategy {
    private final BigDecimal percentage;

    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100%");
        }
        this.percentage = BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice) {
        BigDecimal discountAmount = originalPrice.multiply(percentage);
        return originalPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
