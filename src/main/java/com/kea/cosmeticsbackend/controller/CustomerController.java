package com.kea.cosmeticsbackend.controller;

import com.kea.cosmeticsbackend.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {
    private CustomerService customerService;
}
