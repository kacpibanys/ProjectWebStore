package com.example.shop.cli;

import com.example.shop.cart.Cart;
import com.example.shop.cart.CartService;
import com.example.shop.customer.Customer;
import com.example.shop.file.FileSavingService;
import com.example.shop.order.InMemoryOrderRepository;
import com.example.shop.order.OrderRepository;
import com.example.shop.order.OrderService;
import com.example.shop.product.InMemoryProductRepository;
import com.example.shop.product.ProductManager;
import com.example.shop.product.ProductRepository;
import com.example.shop.product.producttype.Computer;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();

        ProductManager productManager = new ProductManager(productRepository);
        Cart cart = new Cart();
        CartService cartService = new CartService(productManager, cart);
        FileSavingService fileService = new FileSavingService();
        OrderService orderService = new OrderService(orderRepository, fileService);

        productManager.addProduct(new Computer(1, "MacBook Pro", "Apple", new BigDecimal("8000.00"), 10));
        productManager.addProduct(new Computer(2, "ThinkPad", "Lenovo", new BigDecimal("5500.00"), 5));

        Customer mockCustomer = new Customer(1, "Kacper", "Banys", "kacper@gmail.com");

        ShopCLI cli = new ShopCLI(productManager, cartService, orderService, mockCustomer);
        cli.start();
    }
}