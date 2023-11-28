package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.model.Customer;
import com.kea.cosmeticsbackend.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int id, Customer updatedCustomer){
        if (customerRepository.existsById(id)){
            updatedCustomer.setCustomerId(id);
            return customerRepository.save(updatedCustomer);
        }else {
            return null;
        }
    }
    public void deleteCustomer(int id){
        customerRepository.deleteById(id);
    }
}
