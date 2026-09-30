package com.example.shop.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<Integer, Order> database = new ConcurrentHashMap<>();

    @Override
    public void addOrder(Order order) {
        database.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Order> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public void updateOrder(Order order) {
        database.put(order.getId(), order);
    }

    @Override
    public void removeOrder(int id) {
        database.remove(id);
    }

    @Override
    public boolean existsById(int id) {
        return database.containsKey(id);
    }
}