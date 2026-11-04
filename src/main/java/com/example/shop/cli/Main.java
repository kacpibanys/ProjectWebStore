package com.example.shop.cli;

import com.example.shop.cart.Cart;
import com.example.shop.cart.CartService;
import com.example.shop.customer.Customer;
import com.example.shop.discount.FixedAmountDiscount;
import com.example.shop.discount.PercentageDiscount;
import com.example.shop.discount.PromoCodeService;
import com.example.shop.file.FileSavingService;
import com.example.shop.order.*;
import com.example.shop.product.InMemoryProductRepository;
import com.example.shop.product.ProductManager;
import com.example.shop.product.ProductRepository;
import com.example.shop.product.producttype.Computer;

import java.math.BigDecimal;
import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        ProductRepository productRepository = new InMemoryProductRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        PromoCodeService promoCodeService = new PromoCodeService();
        promoCodeService.addPromoCode("STUDENT10", new PercentageDiscount(new BigDecimal("10")));
        promoCodeService.addPromoCode("MINUS50", new FixedAmountDiscount(new BigDecimal("50.00")));

        ProductManager productManager = new ProductManager(productRepository);
        Cart cart = new Cart();
        CartService cartService = new CartService(productManager, cart, promoCodeService);
        EventBus eventBus = new EventBus();
        FileSavingService fileService = new FileSavingService("data");

        eventBus.subscribe(OrderCompletedEvent.class, event -> {
            fileService.saveInvoiceAsText(event.invoice());
            fileService.appendOrderToLog(event.order());
        });

        eventBus.subscribe(OrderCompletedEvent.class, event -> {
            event.order().getItems().forEach((product, quantity) -> {
                int currentStock = product.getAvailableQuantity();
                product.setAvailableQuantity(currentStock - quantity);
            });
            cart.clear();
        });

        OrderService orderService = new OrderService(orderRepository, eventBus, clock);
        OrderProcessor orderServiceProxy = new OrderServiceTimingProxy(orderService);

        productManager.addProduct(new Computer(1, "MacBook Pro", "Apple", new BigDecimal("8000.00"), 10));
        productManager.addProduct(new Computer(2, "ThinkPad", "Lenovo", new BigDecimal("5500.00"), 5));

        Customer mockCustomer = new Customer(1, "Kacper", "Banys", "kacper@gmail.com");

        ShopCLI cli = new ShopCLI(productManager, cartService, orderServiceProxy, mockCustomer, clock);
        cli.start();
    }
}