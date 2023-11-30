package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findCustomerByCustomerId(int id);
}
