package com.example.shop.order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void addOrder(Order order);
    Optional<Order> findById(int id);
    List<Order> findAll();
    void updateOrder(Order order);
    void removeOrder(int id);
    boolean existsById(int id);
}