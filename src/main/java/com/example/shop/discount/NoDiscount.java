package com.example.shop.discount;

import java.math.BigDecimal;

public class NoDiscount  implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice){
        return originalPrice;
    }
}
