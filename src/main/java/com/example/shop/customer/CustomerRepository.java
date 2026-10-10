package com.example.shop.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void addCustomer(Customer customer);
    Optional<Customer> findById(int id);
    List<Customer> findAll();
    void updateCustomer(Customer customer);
    void removeCustomer(int id);
    boolean existsById(int id);
}