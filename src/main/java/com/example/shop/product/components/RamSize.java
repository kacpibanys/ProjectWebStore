package com.example.shop.product.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RamSize {
    GB_2(2),
    GB_4(4),
    GB_8(8),
    GB_16(16),
    GB_24(24),
    GB_32(32),
    GB_48(48),
    GB_64(64);

    private final int value;
}