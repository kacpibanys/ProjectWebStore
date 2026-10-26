package com.example.shop.product.producttype;

import com.example.shop.product.components.DiskSize;
import com.example.shop.product.components.RamSize;
import com.example.shop.product.components.gpu.Gpu;
import com.example.shop.product.components.gpu.GpuModel;
import com.example.shop.product.components.gpu.VramSize;
import com.example.shop.product.components.processor.Processor;
import com.example.shop.product.components.processor.ProcessorModel;
import com.example.shop.product.components.screen.ScreenSize;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {
    @Test
    void shouldConfigureLaptopWithScreenSize() {
        // Arrange
        Computer computer = new Computer(1, "MacBook Pro", "Apple", new BigDecimal("10000"), 10);
        Processor processor = new Processor(ProcessorModel.CORE_I7, 8);
        Gpu gpu = new Gpu(GpuModel.ARC_A770, VramSize.GB_8);

        // Act
        computer.configureComputer(
                ComputerType.LAPTOP,
                processor,
                RamSize.GB_16,
                DiskSize.GB_512,
                gpu,
                ScreenSize.INCH_15
        );

        // Assert
        assertEquals(ComputerType.LAPTOP, computer.getType());
        assertNotNull(computer.getScreenSize(), "Laptop musi miec ekran");
        assertEquals(ScreenSize.INCH_15, computer.getScreenSize());
        assertEquals(processor, computer.getProcessor());
    }

    @Test
    void shouldSetScreenSizeToNullForDesktop() {
        // Arrange
        Computer computer = new Computer(2, "Omen", "HP", new BigDecimal("7000"), 5);
        Processor processor = new Processor(ProcessorModel.RYZEN_5, 6);
        Gpu gpu = new Gpu(GpuModel.RTX_4070, VramSize.GB_12);

        // Act
        computer.configureComputer(
                ComputerType.DESKTOP,
                processor,
                RamSize.GB_32,
                DiskSize.TB_1,
                gpu,
                ScreenSize.INCH_17 //daje ekran zeby zobaczyc czy walidacja enumowa dziala
        );

        // Assert
        assertEquals(ComputerType.DESKTOP, computer.getType());
        assertNull(computer.getScreenSize(), "PC nie moze miec ekranu");
    }
}