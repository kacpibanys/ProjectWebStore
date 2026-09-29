package com.example.shop.product.components.gpu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Gpu {
    private GpuBrand brand;
    private GpuModel model;
    private VramSize vram;

    public Gpu(GpuModel model, VramSize vram) {
        this.model = model;
        this.vram = vram;
        this.brand = model.getBrand();
    }
}
