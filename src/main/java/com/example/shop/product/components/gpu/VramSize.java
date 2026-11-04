package com.example.shop.product.components.gpu;

import lombok.Getter;

@Getter
public enum VramSize {
    GB_4(4),
    GB_6(6),
    GB_8(8),
    GB_12(12),
    GB_16(16),
    GB_24(24);

    private final int value;

    VramSize(int value) {
        this.value = value;
    }
}