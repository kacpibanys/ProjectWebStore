package com.example.shop.product.components.processor;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Processor {
    private ProcessorBrand brand;
    private ProcessorModel model;
    private int cores;

    public Processor(ProcessorModel model, int cores) {
        this.model = model;
        this.cores = cores;
        this.brand = model.getBrand();
    }
}
