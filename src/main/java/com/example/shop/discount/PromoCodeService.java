package com.example.shop.discount;

import java.util.HashMap;
import java.util.Map;

public class PromoCodeService {
    private final Map<String, DiscountStrategy> promoCodes = new HashMap<>();

    public void addPromoCode(String promoCode, DiscountStrategy discountStrategy) {
        promoCodes.put(promoCode, discountStrategy);
    }

    public DiscountStrategy getStrategyForPromoCode(String promoCode) {
        return promoCodes.get(promoCode.toUpperCase());
    }

}
