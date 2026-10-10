package com.example.shop.product.producttype;

import com.example.shop.product.Product;
import com.example.shop.product.components.DiskSize;
import com.example.shop.product.components.accessory.Accessory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Smartphone extends Product {
    private String model;
    private String color;
    private DiskSize memory;
    private int batteryCapacity;
    private boolean isDualSim;
    private List<Accessory> accessories;

    public Smartphone(int id, String name, String brand, BigDecimal price, int availableQuantity) {
        super(id, name, brand, price, availableQuantity);
        this.accessories = new ArrayList<>();
    }

    public void configureSmartphone(String model, String color, DiskSize memory, int batteryCapacity, boolean isDualSim, List<Accessory> accessories) {
        this.model = model;
        this.color = color;
        this.memory = memory;
        this.batteryCapacity = batteryCapacity;
        this.isDualSim = isDualSim;
        if (accessories != null) {
            this.accessories.addAll(accessories);
        }
    }

}
