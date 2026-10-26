package com.example.shop.product.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiskSize {
    GB_128(128),
    GB_256(256),
    GB_512(512),
    TB_1(1024),
    TB_2(2048),
    TB_4(4096);

    private final int value;
}