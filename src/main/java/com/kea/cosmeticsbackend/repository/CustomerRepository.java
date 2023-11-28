package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
