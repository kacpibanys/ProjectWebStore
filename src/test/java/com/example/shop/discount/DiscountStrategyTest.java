package com.example.shop.discount;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountStrategyTest {
    @Test
    void percentageDiscountShouldReducePriceCorrectly() {
        //arrange
        DiscountStrategy discount = new PercentageDiscount(20);
        BigDecimal originalPrice = new BigDecimal("100.00");

        //Act
        BigDecimal result = discount.applyDiscount(originalPrice);

        //Assert
        assertEquals(new BigDecimal("80.00"), result);
    }

    @Test
    void fixedAmountDiscountShouldReducePriceCorrectly() {
        //Arrange
        DiscountStrategy discount = new FixedAmountDiscount(new BigDecimal("30.00"));
        BigDecimal originalPrice = new BigDecimal("100.00");

        //Act
        BigDecimal result = discount.applyDiscount(originalPrice);

        //Assert
        assertEquals(new BigDecimal("70.00"), result);
    }

    @Test
    void fixedAmountDiscountShouldNotReturnNegativePrice() {
        //Arrange
        DiscountStrategy discount = new FixedAmountDiscount(new BigDecimal("50.00"));
        BigDecimal originalPrice = new BigDecimal("20.00");

        //Act
        BigDecimal result = discount.applyDiscount(originalPrice);

        //Assert
        assertEquals(BigDecimal.ZERO, result);
    }

}