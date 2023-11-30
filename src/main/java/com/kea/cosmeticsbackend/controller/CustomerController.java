package com.kea.cosmeticsbackend.controller;

import com.kea.cosmeticsbackend.model.Customer;
import com.kea.cosmeticsbackend.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customerList = customerService.getCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer saveCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(saveCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null){
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
