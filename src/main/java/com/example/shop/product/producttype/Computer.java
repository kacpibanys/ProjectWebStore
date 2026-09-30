package com.example.shop.product.producttype;

import com.example.shop.product.Product;
import com.example.shop.product.components.DiskSize;
import com.example.shop.product.components.RamSize;
import com.example.shop.product.components.gpu.Gpu;
import com.example.shop.product.components.processor.Processor;
import com.example.shop.product.components.screen.ScreenSize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Computer extends Product {
    private ComputerType type;
    private Processor processor;
    private RamSize ramSize;
    private DiskSize diskSize;
    private Gpu gpu;
    private ScreenSize screenSize;

    public Computer(int id, String name, String brand, BigDecimal price, int availableQuantity) {
        super(id, name, brand, price, availableQuantity);
    }

    public void configureComputer(ComputerType type, Processor processor, RamSize ramSize, DiskSize diskSize, Gpu gpu, ScreenSize screenSize) {
        this.type = type;
        this.processor = processor;
        this.ramSize = ramSize;
        this.diskSize = diskSize;
        this.gpu = gpu;

        if (type == ComputerType.DESKTOP) {
            this.screenSize = null;
        } else {
            this.screenSize = screenSize;
        }
    }
}

