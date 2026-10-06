package com.example.shop.product.components.screen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScreenSize {
    INCH_13(13.3),
    INCH_14(14.0),
    INCH_15(15.6),
    INCH_16(16.0),
    INCH_17(17.3);

    private final double value;
}