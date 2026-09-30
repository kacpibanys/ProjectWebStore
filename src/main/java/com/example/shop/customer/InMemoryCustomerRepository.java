package com.example.shop.customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<Integer, Customer> database = new ConcurrentHashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        database.put(customer.getId(), customer);
    }

    @Override
    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public void updateCustomer(Customer customer) {
        database.put(customer.getId(), customer);
    }

    @Override
    public void removeCustomer(int id) {
        database.remove(id);
    }

    @Override
    public boolean existsById(int id) {
        return database.containsKey(id);
    }
}