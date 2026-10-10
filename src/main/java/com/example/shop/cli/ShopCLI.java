package com.example.shop.cli;

import com.example.shop.cart.CartService;
import com.example.shop.customer.Customer;
import com.example.shop.order.Order;
import com.example.shop.order.OrderService;
import com.example.shop.order.OrderStatus;
import com.example.shop.product.ProductManager;
import com.example.shop.product.components.DiskSize;
import com.example.shop.product.components.RamSize;
import com.example.shop.product.components.accessory.Accessory;
import com.example.shop.product.components.gpu.Gpu;
import com.example.shop.product.components.gpu.GpuModel;
import com.example.shop.product.components.gpu.VramSize;
import com.example.shop.product.components.processor.Processor;
import com.example.shop.product.components.processor.ProcessorModel;
import com.example.shop.product.producttype.Computer;
import com.example.shop.product.producttype.ComputerType;
import com.example.shop.product.producttype.Smartphone;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopCLI {
    private final ProductManager productManager;
    private final CartService cartService;
    private final OrderService orderService;
    private final Customer loggedCustomer;
    private final Scanner scanner;
    private int orderCounter = 1;
    private int customProductIdCounter = 9000;

    public ShopCLI(ProductManager productManager, CartService cartService, OrderService orderService, Customer loggedCustomer) {
        this.productManager = productManager;
        this.cartService = cartService;
        this.orderService = orderService;
        this.loggedCustomer = loggedCustomer;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        System.out.println("hello");

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            try {
                switch (input) {
                    case "1" -> showProducts();
                    case "2" -> addProductToCart();
                    case "3" -> checkoutAndProcessOrder();
                    case "4" -> viewCart();
                    case "5" -> configureComputer();
                    case "6" -> configureSmartphone();
                    case "0" -> {
                        System.out.println("Thank you for shopping");
                        running = false;
                    }
                    default -> System.out.println("Unknown operation, try again. \n");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage() +"\n");
            }
        }
    }

    private void configureSmartphone() {
        System.out.println("\nBUILD YOUR CUSTOM SMARTPHONE");

        System.out.print("Enter model (for example Galaxy S24, iPhone 16): ");
        String model = scanner.nextLine();

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        System.out.println("Select memory:");
        DiskSize[] disks = DiskSize.values();
        for (int i = 0; i < disks.length; i++) {
            System.out.println((i + 1) + ". " + disks[i].name() + " (" + disks[i].getValue() + "GB)");
        }
        System.out.print("Choice: ");
        DiskSize selectedMemory = disks[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.print("Enter battery capacity (mAh): ");
        int batteryCapacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Is it Dual SIM? (y/n): ");
        boolean isDualSim = scanner.nextLine().trim().equalsIgnoreCase("y");

        // 5. Accessories (wiele opcji)
        List<Accessory> selectedAccessories = new ArrayList<>();
        Accessory[] accessories = Accessory.values();
        boolean addingAccessories = true;

        while (addingAccessories) {
            System.out.println("Add accessory (or enter 0 to finish):");
            for (int i = 0; i < accessories.length; i++) {
                System.out.println((i + 1) + ". " + accessories[i].name());
            }
            System.out.print("Choice: ");
            int accChoice = Integer.parseInt(scanner.nextLine());

            if (accChoice == 0) {
                addingAccessories = false;
            } else {
                Accessory chosenAcc = accessories[accChoice - 1];
                selectedAccessories.add(chosenAcc);
                System.out.println(chosenAcc.name() + " added. Anything else?");
            }
        }

        String customName = "Custom Smartphone (" + model + " - " + color + ")";
        BigDecimal customPrice = new BigDecimal("5500.00"); // Bazowa cena

        Smartphone customPhone = new Smartphone(
                customProductIdCounter++,
                customName,
                "CustomBrand",
                customPrice,
                1
        );
        customPhone.configureSmartphone(model, color, selectedMemory, batteryCapacity, isDualSim, selectedAccessories);

        productManager.addProduct(customPhone);
        cartService.addProductToCart(customPhone.getId(), 1);

        System.out.println(customName + " has been configured and added to your cart\n");
    }

    private void printMenu() {
        System.out.println("\nWhat do you want to do?");
        System.out.println("1. Browse the shop");
        System.out.println("2. Add a product to cart");
        System.out.println("3. Checkout");
        System.out.println("4. View cart");
        System.out.println("5. Build custom computer");
        System.out.println("6. Configure phone");
        System.out.println("0. Exit");
    }

    private void showProducts() {
        System.out.println("\nPRODUCTS:");
        productManager.getAllProducts().forEach(p ->
                System.out.println(p.getId() + ". " + p.getName() + " - " + p.getPrice() + " zł. Availability: " + p.getAvailableQuantity())
        );
    }

    private void addProductToCart() {
        System.out.print("Enter product id: ");
        int productId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        cartService.addProductToCart(productId, quantity);
        System.out.println("Product has been added to your cart\n");
    }

    private void checkoutAndProcessOrder() {
        System.out.println("\nCHECKOUT");
        BigDecimal totalToPay = cartService.checkoutCart();

        Order order = new Order(
                orderCounter++,
                loggedCustomer,
                new java.util.HashMap<>(),
                totalToPay,
                OrderStatus.NEW,
                java.time.LocalDateTime.now()
        );

        orderService.processOrder(order);
    }

    private void viewCart() {
        System.out.println("\nYOUR CART");
        var items = cartService.getCartItems();
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            items.forEach((product, qty) ->
                    System.out.println("- " + product.getName() + " | Quantity: " + qty + " | Total: " + product.getPrice().multiply(BigDecimal.valueOf(qty)) + " zł")
            );
        }

    }

    private void configureComputer() {
        System.out.println("\nBUILD YOUR CUSTOM COMPUTER");

        System.out.println("Select Type:");
        ComputerType[] types = ComputerType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].name());
        }
        System.out.print("Choice: ");
        ComputerType selectedType = types[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.println("Select Processor:");
        ProcessorModel[] cpuModels = ProcessorModel.values();
        for (int i = 0; i < cpuModels.length; i++) {
            System.out.println((i + 1) + ". " + cpuModels[i].name() + " (" + cpuModels[i].getBrand() + ")");
        }
        System.out.print("Choice: ");
        ProcessorModel selectedCpuModel = cpuModels[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.print("Enter number of cores: ");
        int cores = Integer.parseInt(scanner.nextLine());
        Processor processor = new Processor(selectedCpuModel, cores);

        System.out.println("Select RAM:");
        RamSize[] rams = RamSize.values();
        for (int i = 0; i < rams.length; i++) {
            System.out.println((i + 1) + ". " + rams[i].name() + " (" + rams[i].getValue() + "GB)");
        }
        System.out.print("Choice: ");
        RamSize selectedRam = rams[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.println("Select Disk:");
        DiskSize[] disks = DiskSize.values();
        for (int i = 0; i < disks.length; i++) {
            System.out.println((i + 1) + ". " + disks[i].name() + " (" + disks[i].getValue() + "GB)");
        }
        System.out.print("Choice: ");
        DiskSize selectedDisk = disks[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.println("Select GPU Model:");
        GpuModel[] gpuModels = GpuModel.values();
        for (int i = 0; i < gpuModels.length; i++) {
            System.out.println((i + 1) + ". " + gpuModels[i].name());
        }
        System.out.print("Choice: ");
        GpuModel selectedGpuModel = gpuModels[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.println("Select VRAM:");
        VramSize[] vrams = VramSize.values();
        for (int i = 0; i < vrams.length; i++) {
            System.out.println((i + 1) + ". " + vrams[i].name() + " (" + vrams[i].getValue() + "GB)");
        }
        System.out.print("Choice: ");
        VramSize selectedVram = vrams[Integer.parseInt(scanner.nextLine()) - 1];
        Gpu gpu = new Gpu(selectedGpuModel, selectedVram);

        String customName = "Custom " + selectedType.name() + " (" + selectedCpuModel.name() + ", " + selectedGpuModel.name() + ")";
        BigDecimal customPrice = new BigDecimal("14500.00");

        Computer customPc = new Computer(customProductIdCounter++, customName, "CustomBuild", customPrice, 1);

        customPc.configureComputer(selectedType, processor, selectedRam, selectedDisk, gpu, null);

        productManager.addProduct(customPc);
        cartService.addProductToCart(customPc.getId(), 1);

        System.out.println(customName + " has been built and added to your cart\n");
        
    }
}