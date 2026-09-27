package com.example.shop.product.producttype;

import com.example.shop.product.components.DiskSize;
import com.example.shop.product.components.accessory.Accessory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.example.shop.product.components.accessory.Accessory.CASE;
import static com.example.shop.product.components.accessory.Accessory.CHARGER;
import static org.junit.jupiter.api.Assertions.*;

class SmartphoneTest {

    @Test
    void shouldCreateSmartphoneWithBaseProductProperties() {
        //Arrange and Act
        Smartphone phone = new Smartphone(1, "Galaxy S24", "Samsung", new BigDecimal("4000.00"), 50);

        //Assert
        assertEquals(1, phone.getId());
        assertEquals("Galaxy S24", phone.getName());
        assertEquals("Samsung", phone.getBrand());
        assertEquals(new BigDecimal("4000.00"), phone.getPrice());
        assertEquals(50, phone.getAvailableQuantity());
        assertNotNull(phone.getAccessories());
        assertTrue(phone.getAccessories().isEmpty());
    }

    @Test
    void shouldConfigureSmartphone() {
        //Arrange
        Smartphone phone = new Smartphone(1, "Galaxy S24", "Samsung", new BigDecimal("4000.00"), 50);
        List<Accessory> accessoriesToAdd = List.of(CASE, CHARGER);

        //Act
        phone.configureSmartphone("S24 Ultra", "Black", DiskSize.GB_512, 5000, true, accessoriesToAdd);

        //Assert
        assertEquals("S24 Ultra", phone.getModel());
        assertEquals("Black", phone.getColor());
        assertEquals(DiskSize.GB_512, phone.getMemory());
        assertEquals(5000, phone.getBatteryCapacity());
        assertTrue(phone.isDualSim());
        assertEquals(2, phone.getAccessories().size());
        assertTrue(phone.getAccessories().contains(CASE));
    }

    @Test
    void shouldHandleNullAccessoriesListWithoutException() {
        //Arrange
        Smartphone phone = new Smartphone(2, "iPhone 15", "Apple", new BigDecimal("5000.00"), 30);

        //Act
        phone.configureSmartphone("15 Pro", "Titanium", DiskSize.GB_256, 3200, false, null);

        //Assert
        assertFalse(phone.isDualSim());
        assertNotNull(phone.getAccessories(), "Lista nie powinna byc nullem");
        assertTrue(phone.getAccessories().isEmpty(), "Lista po przekazaniu nulla powinna byc pusta");
    }

    @Test
    void shouldAppendAccessoriesWhenConfiguredMultipleTimes() {
        // Arrange
        Smartphone phone = new Smartphone(3, "Pixel 8", "Google", new BigDecimal("3500.00"), 20);
        phone.configureSmartphone("8 Pro", "White", DiskSize.GB_128, 4500, true, List.of(CHARGER));

        // Act
        phone.configureSmartphone("8 Pro", "White", DiskSize.GB_128, 4500, true, List.of(CASE));

        // Assert
        assertEquals(2, phone.getAccessories().size());
        assertTrue(phone.getAccessories().contains(CHARGER));
        assertTrue(phone.getAccessories().contains(CASE));
    }
}