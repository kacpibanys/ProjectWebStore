package com.example.shop.product.components.processor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessorModel {
    CORE_I3(ProcessorBrand.INTEL),
    CORE_I5(ProcessorBrand.INTEL),
    CORE_I7(ProcessorBrand.INTEL),
    RYZEN_5(ProcessorBrand.AMD),
    RYZEN_7(ProcessorBrand.AMD),
    RYZEN_9(ProcessorBrand.AMD);

    private final ProcessorBrand brand;
}