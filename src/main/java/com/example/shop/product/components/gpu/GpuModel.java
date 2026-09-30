package com.example.shop.product.components.gpu;

import lombok.Getter;

@Getter
public enum GpuModel {
    RTX_3060(GpuBrand.NVIDIA),
    RTX_4070(GpuBrand.NVIDIA),
    RTX_4090(GpuBrand.NVIDIA),
    RX_7600(GpuBrand.AMD),
    RX_7800_XT(GpuBrand.AMD),
    RX_7900_XTX(GpuBrand.AMD),
    ARC_A770(GpuBrand.INTEL);

    private final GpuBrand brand;

    GpuModel(GpuBrand brand) {
        this.brand = brand;
    }
}
